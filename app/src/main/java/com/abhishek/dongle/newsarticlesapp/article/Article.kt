package com.abhishek.dongle.newsarticlesapp.article

data class Article(
    val title: String,
    val desc: String,
    val content: String,
    val imageUrl: String,
    val author: String,
    val category: String,
    val articleType: String,
    val tag: String
)