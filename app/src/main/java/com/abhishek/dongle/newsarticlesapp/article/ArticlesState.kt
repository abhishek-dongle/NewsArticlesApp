package com.abhishek.dongle.newsarticlesapp.article

data class ArticlesState(
    val articles: List<Article> = listOf(),
    val loading: Boolean = false,
    val error: String? = null,
    val selectedArticle: Article? = null,
    val authors: List<String> = listOf(),
    val categories: List<String> = listOf(),
    val articleTypes: List<String> = listOf(),
    val tags: List<String> = listOf()
)