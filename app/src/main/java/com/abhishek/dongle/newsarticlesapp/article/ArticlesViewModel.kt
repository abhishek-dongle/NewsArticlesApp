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

    private fun getArticles() {
        scope.launch {
            val fetchedArticles = articlesUseCase.getArticles()
            updateFilteringList(fetchedArticles)
        }
    }

    private suspend fun updateFilteringList(articles: List<Article>) {
        val categories = articles.flatMap { it.category }.distinct()
        val tags = articles.flatMap { it.tag }.distinct()
        val articleTypes = articles.flatMap { it.articleType }.distinct()

        _articleState.emit(
            ArticlesState(
                articles = articles,
                authors = articles.map { it.author }.distinct(),
                categories = categories,
                articleTypes = articleTypes.map { it.first }.distinct(),
                tags = tags
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