package com.abhishek.dongle.newsarticlesapp.base.article

data class ArticlesState(
    val articles: List<Article> = listOf(),
    val loading: Boolean = false,
    val error: String? = null
)