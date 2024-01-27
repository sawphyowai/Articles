package com.example.articles.domain.usecase

import com.example.articles.commom.ResultStatus
import com.example.articles.data.remote.ArticleResponseDataVO
import com.example.articles.data.remote.db.ArticlesDao
import com.example.articles.data.remote.db.toEntity
import com.example.articles.domain.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    private val articlesDao: ArticlesDao
) {

    private val result = ResultStatus<ArticleResponseDataVO>().copy(loading = false)

    fun getArticle(refresh: String): Flow<ResultStatus<ArticleResponseDataVO>> = flow {
        emit(result)
            try {
                val getAllCoinFromDb = articlesDao.getAllPage()
                val query=if(getAllCoinFromDb.isEmpty()) "bitcoin" else refresh
                val response = repository.getArticles(query)
                val uniqueEntities = response.articles?.toEntity()?.distinctBy {
                    Triple(it.title, it.author, it.publishDate)
                }
                articlesDao.insertAll(result = uniqueEntities ?: listOf())
                emit(result.copy(loading = false, result = response, error = null))
            } catch (e: Exception) {
                val error = result.copy(loading = false)
                emit(error.copy(error = e.localizedMessage))
            }
        }

}
