package com.example.vi_system.student

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.vi_system.R
import com.example.vi_system.lecturer.LecturerDashboardActivity
import com.example.vi_system.lecturer.RetrievePdfStream
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class StudentViewPDFActivity : AppCompatActivity() {
    private lateinit var pdfView: PDFView
    private lateinit var dialog: ProgressDialog
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pdfactivity)

        val intent: Intent = intent
        val fileUrl: String? = intent.getStringExtra("fileUrl")
        val testString: String? = intent.getStringExtra("contents")

        pdfView = findViewById(R.id.pdfViewer)

        dialog =  ProgressDialog(this)
        dialog.setMessage("Loading..")
        dialog.show()

        Log.d("fileUrl".uppercase(), "onCreate: $fileUrl")
        Log.d("TEXT_CONTENT".uppercase(), "onCreate: $testString")


        /*//loading the pdf
        pdfView.fromUri(Uri.parse("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"))
            .load()
*/
        RetrievePdfStream(pdfView, dialog)
            .execute(fileUrl)

        textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US
                textToSpeech.setSpeechRate(1.0f)
                textToSpeech.speak(testString, TextToSpeech.QUEUE_ADD, null, null)
            }

        })

    }

    override fun onBackPressed() {
        textToSpeech.stop()
        startActivity(Intent(this, StudentDashboardActivity::class.java))
        finish()
    }

}