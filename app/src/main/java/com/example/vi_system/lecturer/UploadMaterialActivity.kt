package com.example.vi_system.lecturer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.vi_system.R
import com.google.android.material.button.MaterialButton

class UploadMaterialActivity : AppCompatActivity() {
    private lateinit var pickFileButton: MaterialButton
    private lateinit var uploadFileButton: MaterialButton
    private lateinit var materialUri: Uri
    private var isValid: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_material)

        pickFileButton = findViewById(R.id.pickFile)
        uploadFileButton = findViewById(R.id.fileUpload)

        val activityResultLauncher =
            registerForActivityResult<Intent, ActivityResult>(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    materialUri = data!!.data!!
                    Log.d("MATERIAL_URI", "onCreate: $materialUri")
                    /*imageView.setImageURI(materialUri)
                    imageView.scaleType = ImageView.ScaleType.FIT_XY*/

                } else {
                    Toast.makeText(
                        this@UploadMaterialActivity,
                        "No Material Selected",
                        Toast.LENGTH_SHORT
                    )
                        .show()
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