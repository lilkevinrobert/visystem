package com.example.vi_system.admin.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.example.vi_system.util.User

class StudentAdapter (
    private val context1: Context,
    private val data: ArrayList<User>,
    private val onStudentClickListener: OnStudentClickListener
) : RecyclerView.Adapter<StudentAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View, onStudentClickListener: OnStudentClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val studentUsername: TextView = itemView.findViewById(R.id.username)
        val studentName: TextView = itemView.findViewById(R.id.studentName)
        val studentId: TextView = itemView.findViewById(R.id.studentId)

        private val onStudentClickListener: OnStudentClickListener

        init {

            this.onStudentClickListener = onStudentClickListener
            itemView.setOnClickListener {
                onStudentClickListener.onStudentClickListener(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return MyViewHolder(view, onStudentClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.studentUsername.text = data[position].username
        holder.studentName.text = data[position].name
        holder.studentId.text = data[position].userId
    }

    interface OnStudentClickListener {
        fun onStudentClickListener(position: Int)
    }


}