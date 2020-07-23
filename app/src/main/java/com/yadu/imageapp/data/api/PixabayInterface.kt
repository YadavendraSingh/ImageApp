package com.yadu.imageapp.data.api

import com.yadu.imageapp.data.vo.ImageList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayInterface {

    @GET("/api/")
    fun getImageResults(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<ImageList>
}