package com.example.vi_system.admin

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.vi_system.R
import com.example.vi_system.lecturer.RetrievePdfStream
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.storage.FirebaseStorage
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ViewMaterialActivity : AppCompatActivity() {

    private lateinit var pdfView: PDFView
    private lateinit var dialog: ProgressDialog
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var pdfReader: PdfReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_material)

        val intent: Intent = intent
        val fileUrl: String? = intent.getStringExtra("fileUrl")
        Log.d("FILE_URL", "onCreate: $fileUrl")
        pdfView = findViewById(R.id.pdfViewer)
        dialog = ProgressDialog(this)
        dialog.setMessage("Loading..")
        dialog.show()

        RetrievePdfStream(pdfView, dialog)
            .execute(fileUrl)

        /*lifecycleScope.launch {
            extractData()
        }*/

        // Example usage
/*        lifecycleScope.launch {
            val filePath = downloadPdfFromFirebase(applicationContext, fileUrl)

            if (filePath != null) {
                // File downloaded successfully, filePath contains the absolute path
                // Use the filePath as needed
                Toast.makeText(this@ViewMaterialActivity, "File downloaded successfully", Toast.LENGTH_SHORT).show()
            } else {
                // File download failed
                Toast.makeText(this@ViewMaterialActivity, "File download failed", Toast.LENGTH_SHORT).show()
            }
        }*/

        lifecycleScope.launch {
            //val firebaseStorageUrl = "https://firebasestorage.googleapis.com/v0/b/vi-system-8ec85.appspot.com/o/materials%2F4fc8ecf5-22ea-44e4-a9b4-b7bc87e01ea3.pdf?alt=media&token=76da2423-f087-4441-812f-c832f0016033"
            val filePath = downloadPdfFromFirebase(applicationContext, fileUrl!!)
            if (filePath != null) {
                // File downloaded successfully, filePath contains the absolute path
                Log.d("DOWNLOAD_PATH", "File downloaded successfully. Path: $filePath")
                Toast.makeText(this@ViewMaterialActivity, "File downloaded successfully", Toast.LENGTH_SHORT).show()

            } else {
                // File download failed
                Log.e("DOWNLOAD_ERROR", "File download failed")
                Toast.makeText(this@ViewMaterialActivity, "File download failed", Toast.LENGTH_SHORT).show()

            }
        }





        val testString: String = "Cloud security is the whole bundle of technology,\n" +
                "protocols, and best practices that protect cloud\n" +
                "computing environments, applications running in the\n" +
                "cloud, and data held in the cloud.\n" +
                "\uF0B4 Securing cloud services begins with understanding what\n" +
                "exactly is being secured, as well as, the system aspects\n" +
                "that must be managed.\n"

        //extractData() // it must return string data

        textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US
                textToSpeech.setSpeechRate(1.0f)
                textToSpeech.speak(testString, TextToSpeech.QUEUE_ADD, null, null)
            }

        })


//        pdfReader = PdfReader(fileUrl)
        ///storage/self/primary/Download/1.pdf


        val packageName = packageName
        val resourceName = resources.getResourceEntryName(R.raw.receipt)
        val uri: Uri = Uri.parse("android.resource://$packageName/raw/$resourceName")

    }


    suspend fun downloadPdfFromFirebase(context: Context, firebaseStorageUrl: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                // Create a reference to the Firebase Storage file
                val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(firebaseStorageUrl)

                // Create a temporary file to save the downloaded PDF
                val tempFile = File(context.cacheDir, "temp.pdf")

                // Download the file to the local storage
                val outputStream = FileOutputStream(tempFile)
                storageRef.stream.addOnSuccessListener { taskSnapshot ->
                    val inputStream = taskSnapshot.stream
                    inputStream.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }

                    outputStream.close()
                    inputStream.close()
                }.addOnFailureListener { exception ->
                    exception.printStackTrace()
                    //continuation.resume(null)
                }.await()

                // Return the absolute path of the downloaded file
                return@withContext tempFile.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("DOWNLOAD_ERROR", "downloadPdfFromFirebase: ${e.message.toString()}")
                return@withContext null
            }
        }
    }



/*
    suspend fun downloadPdfFromFirebase(context: Context, firebaseStorageUrl: String?): String? {
        return withContext(Dispatchers.IO) {
            try {
                // Create a reference to the Firebase Storage file
                val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(firebaseStorageUrl!!)
                Log.d("FILE_URL", "FirebaseReference: $storageRef")

                // Create a temporary file to save the downloaded PDF
                val tempFile = File.createTempFile("documents", "pdf")

                // Download the file to the local storage using suspendCancellableCoroutine
                val task = storageRef.getFile(tempFile)

                suspendCancellableCoroutine<String?> { continuation ->
                    task.addOnSuccessListener {
                        // File downloaded successfully
                        continuation.resume(tempFile.absolutePath) {}
                    }.addOnFailureListener { exception ->
                        // File download failed
                        exception.printStackTrace()
                        continuation.resume(null) {}
                    }

                    continuation.invokeOnCancellation {
                        // If coroutine is cancelled, cancel the ongoing download task
                        task.cancel()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
*/



    /*   suspend fun downloadPdfFromFirebase(context: Context, firebaseStorageUrl: String?): String? {
           return withContext(Dispatchers.IO) {
               try {
                   // Create a reference to the Firebase Storage file
                   val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(firebaseStorageUrl!!)
                   Log.d("FILE_URL", "FirebaseReference: $storageRef")
                   // Create a temporary file to save the downloaded PDF
                   //val tempFile = File(context.cacheDir, "temp.pdf")
                   val tempFile = File.createTempFile("documents", "pdf")

                   // Download the file to the local storage
                   storageRef.getFile(tempFile).addOnSuccessListener {
                       // File downloaded successfully
                   }.addOnFailureListener {
                       // File download failed
                   }.await()

                   // Return the absolute path of the downloaded file
                   return@withContext tempFile.absolutePath
               } catch (e: Exception) {
                   e.printStackTrace()
                   return@withContext null
               }
           }
       }*/


/*    private suspend fun extractData() {
        try {
            var extractedText = ""
            val pdfReader: PdfReader = PdfReader("res/raw/receipt.pdf")
            val n = pdfReader.numberOfPages

            for (i in 0 until n) {
                val pageText = withContext(Dispatchers.IO) {
                    PdfTextExtractor.getTextFromPage(pdfReader, i + 1).trim { it <= ' ' }
                }
                extractedText = "$extractedText$pageText\n"
            }

            Log.d("EXTRACTED_TEXT", "extractData: $extractedText")
            pdfReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/




}