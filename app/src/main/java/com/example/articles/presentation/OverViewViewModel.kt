package com.example.articles.presentation

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.articles.data.remote.ArticleResponseDataVO
import com.example.articles.data.remote.ArticlesDataVO
import com.example.articles.data.remote.db.ArticlesDao
import com.example.articles.data.remote.db.ArticlesEntity
import com.example.articles.domain.usecase.ArticlesUseCase
import com.example.articles.paing3.ArticlePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverViewViewModel @Inject constructor(
    private val articlesUseCase: ArticlesUseCase,
    private val articlesDao: ArticlesDao
) : ViewModel() {

    private val _pullRefreshList = MutableLiveData<ArticleResponseDataVO?>()
    val pullRefreshList: LiveData<ArticleResponseDataVO?> get() = _pullRefreshList

    private val _navigateToDetail = MutableLiveData<ArticlesEntity?>()
    val navigateToDetail: LiveData<ArticlesEntity?> get() = _navigateToDetail

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() = _searchQuery

    var data: Flow<PagingData<ArticlesEntity>> = Pager(
        PagingConfig(
            pageSize = 50,
            enablePlaceholders = false
        ),
    ) {
        ArticlePagingSource(articlesDao)
    }.flow.cachedIn(viewModelScope)

    init {
        pullRefresh("bitcoin")
    }

    fun pullRefresh(textChangeForRequest: String) {
        viewModelScope.launch {
            articlesUseCase.getArticle(textChangeForRequest)
                .flowOn(Dispatchers.IO)
                .collect { result ->
                    _pullRefreshList.value = result.result
                }
        }
        refreshData()
    }

    private fun refreshData() {
        data = Pager(
            PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            )
        ) {
            ArticlePagingSource(articlesDao)
        }.flow.cachedIn(viewModelScope)
    }

    fun searchOnQuery(query: String) {
        _searchQuery.value = query
        refreshData()
    }

    fun navigateToDetail(articlesDataVO: ArticlesEntity) {
        _navigateToDetail.value = articlesDataVO
    }

    fun completeNavigation() {
        _navigateToDetail.value = null
    }
}
