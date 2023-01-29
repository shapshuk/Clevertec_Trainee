package com.example.task7.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task7.R
import com.example.task7.main.viewholders.*
import com.example.task7.retrofit.FormDefinition

class RecyclerViewAdapter(
    private val formDefinition: FormDefinition,
    private val viewModel: MainViewModel
    ) : RecyclerView.Adapter<BaseViewHolder>() {

    enum class AdapterViewTypes {
        TEXT, NUMERIC, LIST, BUTTON
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == formDefinition.fields.size) {
            AdapterViewTypes.BUTTON.ordinal
        } else {
            AdapterViewTypes.valueOf(formDefinition.fields[position].type).ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == AdapterViewTypes.BUTTON.ordinal) {
            val view = LayoutInflater
                .from(parent.context).inflate(R.layout.button_item, parent, false)
            return ButtonViewHolder(view)
        }
        else {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)

            return when (AdapterViewTypes.values()[viewType]) {
                AdapterViewTypes.TEXT -> {
                    val view = View.inflate(parent.context, R.layout.text_input, null)
                    val textInputViewHolder = TextInputViewHolder(itemView)
                    textInputViewHolder.binding.itemLayout.addView(view)
                    textInputViewHolder
                }
                AdapterViewTypes.NUMERIC -> {
                    val view = View.inflate(parent.context, R.layout.numeric_input, null)
                    val numericInputViewHolder = NumericInputViewHolder(itemView)
                    numericInputViewHolder.binding.itemLayout.addView(view)
                    numericInputViewHolder
                }
                AdapterViewTypes.LIST -> {
                    val view = View.inflate(parent.context, R.layout.spinner_input, null)
                    val spinnerInputViewHolder = SpinnerInputViewHolder(itemView)
                    spinnerInputViewHolder.binding.itemLayout.addView(view)
                    spinnerInputViewHolder
                }
                else -> { throw  UnsupportedOperationException() }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is FormItemViewHolder -> {
                holder.bindView(viewModel, formDefinition.fields[position])
                holder.binding.fieldTitle.text = formDefinition.fields[position].title
            }
            is ButtonViewHolder -> {
                holder.binding.button.setOnClickListener {
                    viewModel.postData()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return formDefinition.fields.size + 1
    }
}