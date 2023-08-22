package com.example.lokaltask.API

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "https://dummyjson.com/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }
}

