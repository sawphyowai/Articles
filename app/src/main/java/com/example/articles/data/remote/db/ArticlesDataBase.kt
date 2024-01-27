package com.example.articles.data.remote.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities =[ArticlesEntity::class] , version = 1, exportSchema = false)
abstract class ArticlesDataBase : RoomDatabase() {
        abstract fun bitCoinDao():ArticlesDao
}