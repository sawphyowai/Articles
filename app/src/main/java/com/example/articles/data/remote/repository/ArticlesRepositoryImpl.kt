package com.example.articles.data.remote.repository

import com.example.articles.data.remote.ArticleResponseDataVO
import com.example.articles.di.network.API
import com.example.articles.domain.ArticlesRepository
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(private val api: API) : ArticlesRepository {
    override suspend fun getArticles(query: String): ArticleResponseDataVO {
        return api.getAllDataFromNetwork(query)
    }
}