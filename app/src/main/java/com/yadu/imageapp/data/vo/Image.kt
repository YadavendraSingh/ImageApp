package com.yadu.imageapp.data.vo


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id")
    val id: Int,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("user")
    val user: String,
    @SerializedName("webformatURL")
    val webformatURL: String,
    @SerializedName("userImageURL")
    val userImageURL: String,
    var page:Int

)