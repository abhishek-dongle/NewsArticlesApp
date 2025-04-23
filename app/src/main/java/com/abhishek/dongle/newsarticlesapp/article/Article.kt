package com.abhishek.dongle.newsarticlesapp.article

data class Article(
    val title: String,
    val subtitle: String,
    val authorDesc: String,
    val imageUrl: String,
    val authorImageUrl: String,
    val pageUrl: String,
    val author: String,
    val category: List<String>,
    val articleType: List<Pair<String, Map<String, Any>>>,
    val tag: List<String>
)