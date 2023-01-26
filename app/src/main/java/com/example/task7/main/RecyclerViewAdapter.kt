package com.example.task7.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.task7.R
import com.example.task7.databinding.ItemViewBinding
import com.example.task7.retrofit.FormDefinition
import com.example.task7.retrofit.ViewTypes

class RecyclerViewAdapter(private val formDefinition: FormDefinition) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemViewBinding.bind(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return when (formDefinition.fields[position].type) {
                "TEXT" -> ViewTypes.TEXT.ordinal
                "NUMERIC" -> ViewTypes.NUMERIC.ordinal
                "LIST" -> ViewTypes.LIST.ordinal
                else -> throw UnsupportedOperationException()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        val view = when (ViewTypes.values()[viewType]) {
            ViewTypes.TEXT -> {
                View.inflate(parent.context, R.layout.text_input, null)
            }
            ViewTypes.NUMERIC -> {
                View.inflate(parent.context, R.layout.numeric_input, null)
            }
            ViewTypes.LIST -> {
                val strings : List<String> = formDefinition.fields[ViewTypes.LIST.ordinal].values.values.toList()
                val adapter = ArrayAdapter(parent.context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, strings)
                val spinner : Spinner = View.inflate(parent.context, R.layout.dropdown_input, null) as Spinner
                spinner.adapter = adapter
                spinner
            }
        }
        ViewHolder(itemView).binding.itemLayout.addView(view)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.fieldTitle.text = formDefinition.fields[position].title
    }

    override fun getItemCount(): Int {
        return formDefinition.fields.size
    }
}