package com.example.task7.main.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.task7.databinding.ItemViewBinding
import com.example.task7.main.MainViewModel
import com.example.task7.retrofit.Field

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract val binding: ViewBinding
}

abstract class FormItemViewHolder(itemView: View) : BaseViewHolder(itemView) {
    override val binding = ItemViewBinding.bind(itemView)
    abstract fun bindView(viewModel: MainViewModel, field: Field)
}

