package com.example.articles.commom

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultStatus<T>(
    val loading:Boolean=false,
    val result:T?=null,
    var error:String?=null
)
