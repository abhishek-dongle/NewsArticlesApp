package com.abhishek.dongle.newsarticlesapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.abhishek.dongle.newsarticlesapp.article.ArticleFilterType
import com.abhishek.dongle.newsarticlesapp.article.ArticlesViewModel

@Composable
fun TaggedArticlesScreen(
    navController: NavHostController,
    articleViewModel: ArticlesViewModel
) {
    val articleState = articleViewModel.articleState.collectAsState()
    val tagName = articleState.value.selectedArticleTag!!
    articleViewModel.filterArticleList(tag = tagName, filterType = ArticleFilterType.TAG)
    Column {
        AppBar(navController, tagName)
        TaggedArticleListView(articleViewModel)
    }
}

@Composable
private fun TaggedArticleListView(viewModel: ArticlesViewModel) {
    val articleState by viewModel.articleState.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(articleState.taggedArticles) { article ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
                shape = RectangleShape
            ) {
                ArticleItemView(article)
            }
        }
    }
}