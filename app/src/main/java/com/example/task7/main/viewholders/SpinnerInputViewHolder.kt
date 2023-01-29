package com.example.task7.main.viewholders

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.task7.R
import com.example.task7.databinding.ItemViewBinding
import com.example.task7.main.MainViewModel
import com.example.task7.retrofit.Field

class SpinnerInputViewHolder(itemView: View) : FormItemViewHolder(itemView) {
    override val binding = ItemViewBinding.bind(itemView)
    override fun bindView(viewModel: MainViewModel, field: Field) {
        val spinner = itemView.findViewById<Spinner>(R.id.spinner)

        val spinnerValues : List<String> = field.values.values.toList()
        val adapter = ArrayAdapter(
            itemView.context,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            spinnerValues
        )
        spinner.adapter = adapter

        spinner.setSelection(
            field.values.keys.toList().indexOfFirst {
                it == viewModel.formData[field.name]
            }
        )
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.formData[field.name] = field.values.keys.toList().first()
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.formData[field.name] = field.values.keys.toList()[position]
            }
        }
    }
}