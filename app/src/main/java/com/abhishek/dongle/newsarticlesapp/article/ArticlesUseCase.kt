package com.abhishek.dongle.newsarticlesapp.article

import kotlin.random.Random

class ArticlesUseCase(private val articlesService: ArticlesService) {

    suspend fun getArticles(): List<Article> {
        val articlesRaw = articlesService.fetchArticles()
        return mapArticles(articlesRaw)
    }

    private fun mapArticles(articlesRaw: List<ArticleRaw>) = articlesRaw.map { raw ->
        Article(
            title = raw.title,
            subtitle = raw.subtitle ?: "Upcoming details",
            authorDesc = raw.authorDesc ?: "Upcoming Author details",
            imageUrl = raw.imageUrl
                ?: "https://techcrunch.com/wp-content/uploads/2024/05/Minecraft-keyart.jpg?resize=1200,720",
            pageUrl = raw.pageUrl ?: "Upcoming Link",
            authorImageUrl = "https://media2.dev.to/dynamic/image/width=800%2Cheight=%2Cfit=scale-down%2Cgravity=auto%2Cformat=auto/https%3A%2F%2Fwww.gravatar.com%2Favatar%2F2c7d99fe281ecd3bcd65ab915bac6dd5%3Fs%3D250",
            author = raw.author ?: "Unknown",
            category = setCategories(raw.title),
            articleType = setArticleType(raw.title),
            tag = setArticleTags()
        )
    }

    private fun setArticleTags(): List<String> {
        val validTags = sampleTags.filter { tag -> tag.none { it.isDigit() } }
        return if (validTags.size < 3) {
            validTags
        } else {
            validTags.shuffled(Random).take(Random.nextInt(1, 4))
        }
    }

    private fun setArticleType(source: String): List<Pair<String, Map<String, Any>>> {
        val articleTypes = sampleArticleTypes.filter {
            it.first.startsWith(
                source.firstOrNull() ?: ' ',
                ignoreCase = true
            )
        }
        return if (articleTypes.isNotEmpty()) articleTypes else emptyList()
    }

    private fun setCategories(source: String): List<String> {
        val categories = sampleCategories.filter {
            it.startsWith(
                source.firstOrNull() ?: ' ',
                ignoreCase = true
            )
        }
        return if (categories.isNotEmpty()) categories else listOf("Uncategorized")
    }
}