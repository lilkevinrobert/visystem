package com.example.vi_system.admin.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.vi_system.R

class AddSubject : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.add_new_subject, null))

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
