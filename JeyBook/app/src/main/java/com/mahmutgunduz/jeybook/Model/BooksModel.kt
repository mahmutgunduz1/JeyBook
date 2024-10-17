package com.mahmutgunduz.jeybook.Model

import com.google.gson.annotations.SerializedName

data class BooksModel(

    @SerializedName("kind") val kind: String,
    @SerializedName("totalItems") val totalItems: Int,
    @SerializedName("items") val items: List<BookItem>


)
