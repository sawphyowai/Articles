package com.example.articles.domain

import com.example.articles.data.remote.ArticleResponseDataVO

interface ArticlesRepository {
    suspend fun getArticles(refresh: String):ArticleResponseDataVO
}