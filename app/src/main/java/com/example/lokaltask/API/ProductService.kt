package com.example.lokaltask.API

import com.example.lokaltask.ApiResponse.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    fun getProducts(): Call<ProductResponse>
}