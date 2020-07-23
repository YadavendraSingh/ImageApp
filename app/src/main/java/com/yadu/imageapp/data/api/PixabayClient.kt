package com.yadu.imageapp.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY = "12887834-e4336495d6ff69e659fa87748"
const val BASE_URL = "https://pixabay.com"
const val QUERY = "scenery"
const val VERTICAL = "vertical"

const val DATABASE_NAME = "image.db"

const val FIRST_PAGE = 1
const val IMAGE_PER_PAGE = 20

object PixabayClient {

    fun getClient(): PixabayInterface {
        val requestInterceptor = Interceptor { chain ->
            // Interceptor take only one argument which is a lambda function so parenthesis can be omitted

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("key", API_KEY)
                .addQueryParameter("q", QUERY)
                .addQueryParameter("orientation", VERTICAL)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PixabayInterface::class.java)
    }
}