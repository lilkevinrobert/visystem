package com.example.vi_system.lecturer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.vi_system.R
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.google.android.material.button.MaterialButton

class UploadMaterialActivity : AppCompatActivity() {
    private lateinit var pickFileButton: MaterialButton
    private lateinit var uploadFileButton: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var pdfView: PDFView
    private lateinit var materialUri: Uri
    private var isValid: Boolean = false
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


    }
}