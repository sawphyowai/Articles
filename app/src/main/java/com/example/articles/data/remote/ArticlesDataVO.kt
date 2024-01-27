package com.example.articles.data.remote

import android.os.Parcelable
import com.example.articles.data.remote.db.ArticlesEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ArticlesDataVO(
    @Json(name = "title")
    val title:String?=null,
    @Json(name = "author")
    val author:String?=null,
    @Json(name = "description")
    val description:String?=null,
    @Json(name = "url")
    val url:String?=null,
    @Json(name = "urlToImage")
    val urlToImage:String?=null,
    @Json(name = "publishedAt")
    val publishDate:String?=null
):Parcelable

fun List<ArticlesEntity>.toDomain():List<ArticlesDataVO>{
    return map {
        ArticlesDataVO(
            title = it.title,
            author = it.author,
            description = it.description,
            urlToImage = it.imageUrl,
            publishDate = "Published ${it.publishDate}"
        )
    }
}

fun List<ArticlesEntity>.toResponse():ArticleResponseDataVO{
    return ArticleResponseDataVO(
        articles=toDomain()
    )
}
