package com.example.task7.retrofit

import java.util.Dictionary

data class FormDefinition (
    var title: String,
    var image: String,
    var fields: ArrayList<Field>,
)

data class Field (
    var title: String,
    var name: String,
    var type: String,
    var values: Map<String, String>,
)

enum class ViewTypes {
    TEXT, NUMERIC, LIST
}

