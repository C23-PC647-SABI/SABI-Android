package com.example.sabi.data.api

import com.example.sabi.model.ResponseList
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("list")
    suspend fun getDictonaryList(
    ): ResponseList
}