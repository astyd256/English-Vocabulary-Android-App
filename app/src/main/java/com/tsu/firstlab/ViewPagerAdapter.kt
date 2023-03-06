package com.tsu.firstlab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter(private var headers: List<String>, private var description: List<String>,
                       private var images: List<Int>, private var paginations: List<Int>)
    : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemHeaders: TextView = itemView.findViewById(R.id.introHeader)
        val itemDescriptions: TextView = itemView.findViewById(R.id.introDesc)
        val itemImages: ImageView = itemView.findViewById(R.id.introImage)
        val itemPaginations: ImageView = itemView.findViewById(R.id.pagination)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))
    }

    override fun getItemCount(): Int {
        return headers.size
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.itemHeaders.text = headers[position]
        holder.itemDescriptions.text = description[position]
        holder.itemImages.setImageResource(images[position])
        holder.itemPaginations.setImageResource(paginations[position])
    }

}