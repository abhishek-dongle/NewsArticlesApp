package com.abhishek.dongle.newsarticlesapp.article

data class ArticlesState(
    val articles: List<Article> = listOf(),
    val loading: Boolean = false,
    val error: String? = null,
    val selectedArticle: Article? = null,
    val authors: List<String> = listOf(),
    val categories: List<String> = listOf(),
    val articleTypes: List<String> = listOf(),
    val tags: List<String> = listOf(),
    val selectedAuthor: String = ArticleFilterType.AUTHOR.text,
    val selectedCategory: String = ArticleFilterType.CATEGORY.text,
    val selectedArticleType: String = ArticleFilterType.ARTICLE_TYPE.text,
    val selectedTag: String = ArticleFilterType.TAG.text,
    val filteredArticles: List<Article> = listOf()
)