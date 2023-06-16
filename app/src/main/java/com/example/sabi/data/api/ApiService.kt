package com.example.sabi.data.api

import com.example.sabi.model.ResponseListItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("list")
    suspend fun getDictonaryList(
    ): List<ResponseListItem>

    @GET("searchWord")
    suspend fun getSearchList(
        @Query("word") query: String
    ): List<ResponseListItem>
}