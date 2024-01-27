package com.example.articles.di.network

import com.example.articles.data.remote.repository.ArticlesRepositoryImpl
import com.example.articles.domain.ArticlesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryImplModule {
    @Binds
    fun bindArticles(impl: ArticlesRepositoryImpl): ArticlesRepository
}