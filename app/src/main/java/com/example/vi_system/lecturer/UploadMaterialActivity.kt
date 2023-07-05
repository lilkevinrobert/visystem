package com.example.vi_system.lecturer

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vi_system.R
import com.example.vi_system.util.Material
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.log

class UploadMaterialActivity : AppCompatActivity() {
    private lateinit var pickFileButton: MaterialButton
    private lateinit var uploadFileButton: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var pdfView: PDFView
    private val PRIMARY: String = "primary"
    private val LOCAL_STORAGE = "/storage/self/primary/"

    private var materialUri: Uri? = null
    private var isValid: Boolean = false

    private val databaseReference = FirebaseDatabase.getInstance().getReference("materials")
    private val storageReference = FirebaseStorage.getInstance().reference
    private var holder: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_material)

        pickFileButton = findViewById(R.id.pickFile)
        uploadFileButton = findViewById(R.id.fileUpload)
        progressBar = findViewById(R.id.progressBar)
        pdfView = findViewById(R.id.pdfView)


        val activityResultLauncher =
            registerForActivityResult<Intent, ActivityResult>(ActivityResultContracts.StartActivityForResult()) { result ->
                // Show the progress bar before setting the document
                progressBar.visibility = View.VISIBLE
                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    materialUri = data!!.data!!
                    isValid = true
                    Log.d("MATERIAL_URI", "onCreate: $materialUri")
                    pdfView.fromUri(materialUri)
                        .onLoad(object : OnLoadCompleteListener {
                            override fun loadComplete(nbPages: Int) {
                                Log.d("COMPLETED", "loadComplete: ")
                                progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this@UploadMaterialActivity,
                                    "Completed loading",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                        .load()
                    pdfView.fromUri(materialUri).load()

                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@UploadMaterialActivity,
                        "No Material Selected",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        pickFileButton.setOnClickListener {
            val filePicker = Intent()
            filePicker.action = Intent.ACTION_GET_CONTENT
            filePicker.type = "application/pdf"
            activityResultLauncher.launch(filePicker)
        }


        uploadFileButton.setOnClickListener {
            materialUri?.let {
                uploadFile(fileUri = it)
            }
            if (materialUri == null) {
                //Or dialog for alerting
                Toast.makeText(this, "Choose a file for upload..", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun uploadFile(fileUri: Uri?) {
        lifecycleScope.launch {
            Log.d("HOLDER", "onCreate: $fileUri")
            val extractedData:String = extractData(fileUri!!)!!



            val filename: String? = getFileName(contentResolver, fileUri!!)
            var filenameUrl: String

            //Firebase Storage
            val storageReference: StorageReference =
                FirebaseStorage.getInstance().reference.child("materials")
            val materialRef =
                storageReference.child(UUID.randomUUID().toString() + ".pdf")

            //Firebase Realtime Database
            val databaseReference = FirebaseDatabase.getInstance().getReference("materials")

            materialRef.putFile(fileUri)
                .addOnCompleteListener {
                    materialRef.downloadUrl.addOnSuccessListener { uri ->
                        filenameUrl = uri.toString()

                        // Push the content data to a new node in the database
                        val newContentRef = databaseReference.push()
                        newContentRef.setValue(
                            Material(
                                filename = filename!!,
                                fileUrl = filenameUrl,
                                contents = extractedData
                            )
                        )
                    }
                }.addOnSuccessListener { task ->
                    if (task.task.isSuccessful) {
                        Toast.makeText(applicationContext, "successfully uploaded", Toast.LENGTH_LONG).show()
                        startActivity(Intent(applicationContext, LecturerDashboardActivity::class.java))
                        finish()
                    }
                }

        }


    }

    private suspend fun extractData(fileUri: Uri): String? {
        try {
            val contentResolver = applicationContext.contentResolver
            val inputStream = contentResolver.openInputStream(fileUri)
            val pdfReader = PdfReader(inputStream)
            val n = pdfReader.numberOfPages
            var extractedText = ""

            for (i in 0 until n) {
                val pageText = withContext(Dispatchers.IO) {
                    PdfTextExtractor.getTextFromPage(pdfReader, i + 1).trim { it <= ' ' }
                }
                extractedText = "$extractedText$pageText\n"
            }

            Log.d("EXTRACTED_TEXT", "extractData: $extractedText")
            pdfReader.close()
            return extractedText
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getFileName(resolver: ContentResolver, uri: Uri): String? {
        val returnCursor = resolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

    override fun onBackPressed() {
        if (isValid) {
            AlertDialog.Builder(this)
                .setTitle("Discard your changes?")
                .setMessage("Are you sure you want to close, The selected document will be discarded.")
                .setPositiveButton("Yes") { _, _ ->
                    startActivity(Intent(this, LecturerDashboardActivity::class.java))
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        } else {
            startActivity(Intent(this, LecturerDashboardActivity::class.java))
            finish()
        }
    }
}