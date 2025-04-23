package com.abhishek.dongle.newsarticlesapp.article

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleRaw(
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val subtitle: String?,
    @SerialName("content")
    val authorDesc: String?,
    @SerialName("urlToImage")
    val imageUrl: String?,
    @SerialName("url")
    val pageUrl: String?,
    @SerialName("author")
    val author: String?
)