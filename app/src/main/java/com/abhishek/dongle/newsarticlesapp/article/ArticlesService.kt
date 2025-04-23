package com.abhishek.dongle.newsarticlesapp.article

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ArticlesService(private val httpClient: HttpClient) {
    private val country = "us"
    private val category = "business"
    private val apiKey = "12f442d4bc124238b1cad08bd525e95b"

    suspend fun fetchArticles(): List<ArticleRaw> {
        println("Test: fetchArticles")
        val response =
            httpClient.get("https://newsapi.org/v2/top-headlines?country=$country&category=$category&apiKey=$apiKey")
        val articleResponse: ArticlesResponse = response.body()
        println("Test: ${articleResponse.articles}")
        return articleResponse.articles
    }
}