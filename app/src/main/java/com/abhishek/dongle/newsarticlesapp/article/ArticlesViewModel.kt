package com.abhishek.dongle.newsarticlesapp.article

import com.abhishek.dongle.newsarticlesapp.base.BaseViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ArticlesViewModel : BaseViewModel() {
    private val _articleState = MutableStateFlow(ArticlesState(loading = true))
    val articleState: StateFlow<ArticlesState> get() = _articleState

    private var articlesUseCase: ArticlesUseCase

    init {
        val httpClient = getHttpClient()
        val articlesService = ArticlesService(httpClient)
        articlesUseCase = ArticlesUseCase(articlesService)
        getArticles()
    }

    fun setSelectedArticle(article: Article) {
        _articleState.value = _articleState.value.copy(selectedArticle = article)
    }

    fun setSelectedTag(tag: String) {
        _articleState.value = _articleState.value.copy(selectedArticleTag = tag)
    }

    fun getArticles(forceRefresh: Boolean = false) {
        scope.launch {
            val fetchedArticles = articlesUseCase.getArticles(forceRefresh)
            updateFilteringList(fetchedArticles)
        }
    }

    fun filterArticleList(
        author: String = ArticleFilterType.AUTHOR.text,
        category: String = ArticleFilterType.CATEGORY.text,
        articleType: String = ArticleFilterType.ARTICLE_TYPE.text,
        tag: String = ArticleFilterType.TAG.text,
        filterType: ArticleFilterType
    ) {
        val currentState = _articleState.value
        val filtered = currentState.articles.filter { article ->
            (author == ArticleFilterType.AUTHOR.text || article.author == author)
                    && (category == ArticleFilterType.CATEGORY.text || article.category.contains(
                category
            ))
                    && (articleType == ArticleFilterType.ARTICLE_TYPE.text || article.articleType.any { it.first == articleType })
                    && (tag == ArticleFilterType.TAG.text || article.tag.contains(tag))
        }

        val filteredState = when (filterType) {
            ArticleFilterType.AUTHOR ->
                ArticlesState(
                    selectedAuthor = author,
                    selectedCategory = ArticleFilterType.CATEGORY.text,
                    selectedArticleType = ArticleFilterType.ARTICLE_TYPE.text,
                    selectedTag = ArticleFilterType.TAG.text
                )

            ArticleFilterType.CATEGORY ->
                ArticlesState(
                    selectedAuthor = ArticleFilterType.AUTHOR.text,
                    selectedCategory = category,
                    selectedArticleType = ArticleFilterType.ARTICLE_TYPE.text,
                    selectedTag = ArticleFilterType.TAG.text
                )

            ArticleFilterType.ARTICLE_TYPE ->
                ArticlesState(
                    selectedAuthor = ArticleFilterType.AUTHOR.text,
                    selectedCategory = ArticleFilterType.CATEGORY.text,
                    selectedArticleType = articleType,
                    selectedTag = ArticleFilterType.TAG.text
                )

            ArticleFilterType.TAG ->
                ArticlesState(
                    selectedAuthor = ArticleFilterType.AUTHOR.text,
                    selectedCategory = ArticleFilterType.CATEGORY.text,
                    selectedArticleType = ArticleFilterType.ARTICLE_TYPE.text,
                    selectedTag = tag
                )
        }

        scope.launch {
            _articleState.emit(
                currentState.copy(
                    selectedAuthor = filteredState.selectedAuthor,
                    selectedCategory = filteredState.selectedCategory,
                    selectedArticleType = filteredState.selectedArticleType,
                    selectedTag = filteredState.selectedTag,
                    filteredArticles = filtered,
                    taggedArticles = filtered
                )
            )
        }
    }

    private suspend fun updateFilteringList(articles: List<Article>) {
        val categories = listOf(ArticleFilterType.CATEGORY.text) + articles
            .flatMap { it.category }
            .filterNot { it.equals(ArticleFilterType.CATEGORY.text, ignoreCase = true) }
            .distinct()

        val tags = listOf(ArticleFilterType.TAG.text) + articles
            .flatMap { it.tag }
            .filterNot { it.equals(ArticleFilterType.TAG.text, ignoreCase = true) }
            .distinct()

        val authors = listOf(ArticleFilterType.AUTHOR.text) + articles
            .map { it.author }
            .filterNot { it.equals(ArticleFilterType.AUTHOR.text, ignoreCase = true) }
            .distinct()

        val articleTypes = listOf(ArticleFilterType.ARTICLE_TYPE.text) + articles
            .flatMap { it.articleType.map { type -> type.first } }
            .filterNot { it.equals(ArticleFilterType.ARTICLE_TYPE.text, ignoreCase = true) }
            .distinct()

        _articleState.emit(
            ArticlesState(
                articles = articles,
                authors = authors,
                categories = categories,
                articleTypes = articleTypes,
                tags = tags,
                filteredArticles = articles
            )
        )
    }

    private fun getHttpClient(): HttpClient {
        val client = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        return client
    }
}