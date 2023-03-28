package com.example.vi_system.lecturer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.example.vi_system.util.Material

class MaterialAdapter(
    private val context1: Context,
    private val data: ArrayList<Material>,
    private val onMaterialClickListener: OnMaterialClickListener
) : RecyclerView.Adapter<MaterialAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View, onMaterialClickListener: OnMaterialClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val pdfImage: ImageView = itemView.findViewById(R.id.pdfImage)
        private val onMaterialClickListener: OnMaterialClickListener

        init {

            this.onMaterialClickListener = onMaterialClickListener
            itemView.setOnClickListener {
                onMaterialClickListener.onMaterialClickListener(adapterPosition)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return MyViewHolder(view, onMaterialClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = data[position].filename
        //holder.title.description = data[position].description
    }


    interface OnMaterialClickListener {
        fun onMaterialClickListener(position: Int)
    }
}