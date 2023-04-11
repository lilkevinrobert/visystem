package com.example.vi_system.admin.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.vi_system.R
import com.google.android.material.button.MaterialButton

class AddSubject : DialogFragment() {
    // Use this instance of the interface to deliver action events
    private lateinit var listener: SubjectDialogListener
    private lateinit var positiveButton: MaterialButton
    private lateinit var negativeButton: MaterialButton

    interface SubjectDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            val view: View = inflater.inflate(R.layout.add_new_subject, null)
            positiveButton =  view.findViewById(R.id.subjectName)
            negativeButton =  view.findViewById(R.id.subjectCode)
            
            positiveButton.setOnClickListener {
                Toast.makeText(requireContext(), "contest", Toast.LENGTH_SHORT).show()
            }
            
            
            builder.setView(inflater.inflate(R.layout.add_new_subject, null))
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
    
    



    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = targetFragment as SubjectDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(("$context must implement SubjectDialogListener"))
        }
    }
}
