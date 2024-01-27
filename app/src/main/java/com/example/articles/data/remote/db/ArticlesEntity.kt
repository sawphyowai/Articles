package com.example.articles.data.remote.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.articles.data.remote.ArticlesDataVO
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Article")
data class ArticlesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val description: String,
    val imageUrl: String,
    val publishDate: String
): Parcelable

fun List<ArticlesDataVO>.toEntity(): List<ArticlesEntity> {
    return map {
        ArticlesEntity(
            title = it.title ?: "",
            author = it.author ?: "",
            description = it.description ?: "",
            imageUrl = it.urlToImage ?: "",
            publishDate = it.publishDate ?: ""
        )
    }
}


