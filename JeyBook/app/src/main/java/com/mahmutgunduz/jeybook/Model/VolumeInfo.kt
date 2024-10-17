package com.mahmutgunduz.jeybook.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class VolumeInfo (
    @SerializedName("title") val title: String,
    @SerializedName("imageLinks") val imageLinks: ImageLinks,
    @SerializedName("authors") val authors: List<String>,// yazar
    @SerializedName("publisher") val publisher: String,  // yayinci
    @SerializedName("publishedDate") val publishedDate: String,
    @SerializedName("description") val description: String,// aCIKLm

    @SerializedName("pageCount") val pageCount: Int,
    @SerializedName("printType") val printType: String,
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("maturityRating") val maturityRating: String,
    @SerializedName("allowAnonLogging") val allowAnonLogging: Boolean,
    @SerializedName("contentVersion") val contentVersion: String,

    @SerializedName("language") val language: String,
    @SerializedName("previewLink") val previewLink: String,
    @SerializedName("infoLink") val infoLink: String,
    @SerializedName("canonicalVolumeLink") val canonicalVolumeLink: String
):Serializable


