package com.example.task7.main.viewholders

import android.view.View
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.example.task7.R
import com.example.task7.databinding.ItemViewBinding
import com.example.task7.main.MainViewModel
import com.example.task7.retrofit.Field

class TextInputViewHolder(itemView: View) : FormItemViewHolder(itemView) {
    override val binding = ItemViewBinding.bind(itemView)
    override fun bindView(viewModel: MainViewModel, field: Field) {
        val textView = itemView.findViewById<TextView>(R.id.edit_text)
        if (viewModel.formData[field.name].isNullOrBlank()) {
            viewModel.formData[field.name] = ""
        } else {
            textView.text = viewModel.formData[field.name]
        }
        textView.doAfterTextChanged  {
            viewModel.formData[field.name] = it.toString()
        }
    }
}