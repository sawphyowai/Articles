package com.example.articles.di.network

import com.example.articles.commom.Constants
import com.example.articles.data.remote.ArticleResponseDataVO
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("everything")
    suspend fun getAllDataFromNetwork(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = Constants.apiKey
    ): ArticleResponseDataVO
}