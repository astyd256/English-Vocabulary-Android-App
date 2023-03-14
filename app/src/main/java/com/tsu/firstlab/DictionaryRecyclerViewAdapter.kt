package com.tsu.firstlab

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DictionaryRecyclerViewAdapter() : RecyclerView.Adapter<DictionaryRecyclerViewAdapter.ViewHolder>(){

    private var examples: ArrayList<String> = ArrayList<String>()
    private var definitions: ArrayList<String> = ArrayList<String>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val definition = itemView.findViewById<TextView>(R.id.definition)
        val example = itemView.findViewById<TextView>(R.id.phonetic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("adapter__", "onCreate")
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meanings_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return definitions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.definition.text = definitions[position]
        Log.d("adapter__", position.toString())
        if (examples[position] != "")
        {
            val spannable: Spannable = SpannableString("Example: ${examples[position]}")
            spannable.setSpan(ForegroundColorSpan(Color.parseColor("#65AAEA")), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            holder.example.text = spannable
        } else {
            holder.example.visibility = View.GONE
        }
        if (definitions[position] != "")
        {
            holder.definition.text = definitions[position]
        } else {
            holder.example.visibility = View.GONE
        }

    }

    fun setData(def: ArrayList<String>, exm: ArrayList<String>) {
        Log.d("adapter__", "setData")
        examples.apply {
            clear()
            addAll(exm)
        }
        definitions.apply {
            clear()
            addAll(def)
        }
        notifyDataSetChanged()
    }
}