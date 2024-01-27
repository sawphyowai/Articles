package com.example.articles.paing3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.articles.data.remote.db.ArticlesDao
import com.example.articles.data.remote.db.ArticlesEntity
import kotlinx.coroutines.delay

class ArticlePagingSource(private val articleDao:ArticlesDao) : PagingSource<Int, ArticlesEntity>()
{
    override fun getRefreshKey(state: PagingState<Int, ArticlesEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesEntity> {
        val page=params.key ?:0
        return  try{
            val entities = articleDao.getWithPageList(params.loadSize, page * params.loadSize)
            if (page != 0) delay(1000)
            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}