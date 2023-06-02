package com.example.sabi.ui.dictonary

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sabi.R
import com.example.sabi.model.ResponseListItem

class DictonaryAdapter(private val listItem: List<ResponseListItem?>):
    RecyclerView.Adapter<DictonaryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item, viewGroup, false)
        )

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dictonary = listItem[position]
        holder.apply {
            Glide.with(itemView.context)
                .load(dictonary?.attachment)
                .centerCrop()
                .into(ivDict)
            tvDesc.text = dictonary?.description
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivDict: ImageView = view.findViewById(R.id.iv_dict)
        val tvDesc: TextView = view.findViewById(R.id.tv_desc)
    }
}