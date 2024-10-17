package com.mahmutgunduz.jeybook.Model

import com.google.gson.annotations.SerializedName

class BookItem (

    @SerializedName("kind") val kind: String,
    @SerializedName("id") val id: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("selfLink") val selfLink: String,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo

)
