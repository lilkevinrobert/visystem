package com.example.vi_system.admin.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.vi_system.R
import com.example.vi_system.util.Subject
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class AddSubject : DialogFragment() {
    // Use this instance of the interface to deliver action events
    private lateinit var listener: SubjectDialogListener
    private lateinit var positiveButton: MaterialButton
    private lateinit var negativeButton: MaterialButton
    private lateinit var subjectNameTextInputLayout: TextInputLayout
    private lateinit var subjectCodeTextInputLayout: TextInputLayout

    interface SubjectDialogListener {
        fun onDialogPositiveClick(subject: Subject)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout

            val view: View = inflater.inflate(R.layout.add_new_subject, null)
            positiveButton = view.findViewById(R.id.positiveButton)
            negativeButton = view.findViewById(R.id.negativeButton)
            subjectNameTextInputLayout = view.findViewById(R.id.subjectName)
            subjectCodeTextInputLayout = view.findViewById(R.id.subjectCode)

            positiveButton.setOnClickListener {
                val subjectName:String = subjectNameTextInputLayout.editText?.text.toString().trim()
                val subjectCode:String = subjectCodeTextInputLayout.editText?.text.toString().trim()
                
                // validateInputs
                listener.onDialogPositiveClick(Subject(subjectCode,subjectName,null))
                dismiss()
            }

            negativeButton.setOnClickListener {
                Toast.makeText(requireContext(), "close dialog", Toast.LENGTH_SHORT).show()
                dialog?.cancel()
            }


            builder.setView(view)
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
