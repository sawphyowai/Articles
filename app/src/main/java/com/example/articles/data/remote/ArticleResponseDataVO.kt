package com.example.articles.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleResponseDataVO(
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "articles")
    val articles:List<ArticlesDataVO>? = null,
    @Json(name = "message")
    val message: String? = null,
    var error:String ?=null,
    val loading:Boolean?=null
)




