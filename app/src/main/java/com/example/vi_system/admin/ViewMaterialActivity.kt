package com.example.vi_system.admin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vi_system.R
import com.example.vi_system.lecturer.RetrievePdfStream
import com.github.barteksc.pdfviewer.PDFView

class ViewMaterialActivity : AppCompatActivity() {

    private lateinit var pdfView: PDFView
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_material)

        val intent: Intent = intent
        val fileUrl: String? = intent.getStringExtra("fileUrl")
        pdfView = findViewById(R.id.pdfViewer)

        dialog =  ProgressDialog(this)
        dialog.setMessage("Loading..")
        dialog.show()

        RetrievePdfStream(pdfView, dialog)
            .execute(fileUrl)


    }
}