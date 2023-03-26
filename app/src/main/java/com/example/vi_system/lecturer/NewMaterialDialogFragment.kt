package com.example.vi_system.lecturer

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.vi_system.R

class NewMaterialDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_upload_material, null))
                // Add action buttons
                /*.setPositiveButton("Upload",
                    DialogInterface.OnClickListener { dialog, id ->
                        // sign in the user ...
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })*/
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}