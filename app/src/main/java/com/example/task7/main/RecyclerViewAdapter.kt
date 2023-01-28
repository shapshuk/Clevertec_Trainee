package com.example.task7.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.task7.R
import com.example.task7.databinding.ItemViewBinding
import com.example.task7.databinding.ButtonItemBinding
import com.example.task7.retrofit.FormDefinition
import com.example.task7.retrofit.ViewTypes

class RecyclerViewAdapter(private val formDefinition: FormDefinition, private val viewModel: MainViewModel) : RecyclerView.Adapter<RecyclerViewAdapter.BaseViewHolder>() {

    companion object {
        const val FIELD_BUTTON = 10
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract val binding: ViewBinding
    }

    class FormViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override val binding = ItemViewBinding.bind(itemView)
    }

    class ButtonViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override val binding = ButtonItemBinding.bind(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == formDefinition.fields.size) {
            FIELD_BUTTON
        } else {
            when (formDefinition.fields[position].type) {
                "TEXT" -> ViewTypes.TEXT.ordinal
                "NUMERIC" -> ViewTypes.NUMERIC.ordinal
                "LIST" -> ViewTypes.LIST.ordinal
                else -> throw UnsupportedOperationException()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == FIELD_BUTTON) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.button_item, parent, false)
            return ButtonViewHolder(view)
        } else {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)
            val formType = ViewTypes.values()[viewType]
            val view = when (formType) {
                ViewTypes.TEXT -> {
                    View.inflate(parent.context, R.layout.text_input, null)
                }
                ViewTypes.NUMERIC -> {
                    View.inflate(parent.context, R.layout.numeric_input, null)
                }
                ViewTypes.LIST -> {
                    val strings : List<String> = formDefinition.fields[ViewTypes.LIST.ordinal].values.values.toList()
                    val adapter = ArrayAdapter(parent.context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, strings)
                    val spinner : Spinner = View.inflate(parent.context, R.layout.spinner_input, null) as Spinner
                    spinner.adapter = adapter
                    spinner
                }
            }
            FormViewHolder(itemView).binding.itemLayout.addView(view)
            return FormViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is FormViewHolder -> {
                holder.binding.fieldTitle.text = formDefinition.fields[position].title
                val field = formDefinition.fields[position]

                when(field.type) {
                    ViewTypes.TEXT.name -> {
                        val textView = holder.itemView.findViewById<TextView>(R.id.edit_text)
                        if (viewModel.formData[field.name].isNullOrBlank()) {
                            viewModel.formData[field.name] = ""
                        } else {
                            textView.text = viewModel.formData[field.name]
                        }
                        textView.doAfterTextChanged  {
                            viewModel.formData[field.name] = it.toString()
                        }
                    }
                    ViewTypes.NUMERIC.name -> {
                        val textView = holder.itemView.findViewById<TextView>(R.id.edit_numbers)
                        if (viewModel.formData[field.name].isNullOrBlank()) {
                            viewModel.formData[field.name] = ""
                        } else {
                            textView.text = viewModel.formData[field.name]
                        }
                        textView.doAfterTextChanged  {
                            viewModel.formData[field.name] = it.toString()
                        }
                    }
                    ViewTypes.LIST.name -> {
                        val spinner = holder.itemView.findViewById<Spinner>(R.id.spinner)
                        spinner.setSelection(field.values.keys.toList().indexOfFirst { it == viewModel.formData[field.name] })
                        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                viewModel.formData[field.name] = field.values.keys.toList().first()
                            }

                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                viewModel.formData[field.name] = field.values.keys.toList()[position]
                            }
                        }
                    }
                }
            }
            is ButtonViewHolder -> {
                viewModel.postData()
                holder.binding.button.setOnClickListener {
                    Toast.makeText(
                        holder.itemView.context,
                        viewModel.formData.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return formDefinition.fields.size + 1
    }
}