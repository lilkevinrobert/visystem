package com.example.vi_system.lecturer

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.vi_system.R
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ViewPDFActivity : AppCompatActivity() {
    private lateinit var pdfView: PDFView
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pdfactivity)

        val intent: Intent = intent
        val fileUrl: String? = intent.getStringExtra("fileUrl")
        pdfView = findViewById(R.id.pdfViewer)

        dialog =  ProgressDialog(this)
        dialog.setMessage("Loading..")
        dialog.show()

        Log.d("File_url".uppercase(), "onCreate: $fileUrl")


        /*//loading the pdf
        pdfView.fromUri(Uri.parse("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"))
            .load()
*/
        RetrievePdfStream(pdfView, dialog)
            .execute(fileUrl)

    }




}