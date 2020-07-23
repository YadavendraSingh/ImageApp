package com.yadu.imageapp.data.vo


import com.google.gson.annotations.SerializedName

data class ImageList(
    @SerializedName("hits")
    val hits: List<Image>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)