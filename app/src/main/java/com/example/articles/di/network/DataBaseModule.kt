package com.example.articles.di.network

import android.content.Context
import androidx.room.Room
import com.example.articles.data.remote.db.ArticlesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideBitConDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ArticlesDataBase::class.java, "BitCoinDataBase")
            .build()

    @Provides
    @Singleton
    fun provideBitCoinDao(db: ArticlesDataBase) = db.bitCoinDao()
}