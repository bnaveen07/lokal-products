package com.example.lokaltask.ApiResponse

import com.google.gson.annotations.SerializedName

data class ProductResponse(

    @SerializedName("products")
    val products : List<Products>
)
