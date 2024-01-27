package com.example.articles.data.remote.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertAll(result: List<ArticlesEntity>)

    @Query("SELECT * FROM Article ORDER BY id DESC LIMIT :limit OFFSET :offset")
    suspend fun getWithPageList(limit: Int, offset: Int): List<ArticlesEntity>

    @Query("SELECT * FROM Article ORDER BY id DESC")
    suspend fun getAllPage():List<ArticlesEntity>

}