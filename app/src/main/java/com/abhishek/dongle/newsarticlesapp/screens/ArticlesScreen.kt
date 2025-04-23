package com.abhishek.dongle.newsarticlesapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.abhishek.dongle.newsarticlesapp.article.Article
import com.abhishek.dongle.newsarticlesapp.article.ArticleFilterType
import com.abhishek.dongle.newsarticlesapp.article.ArticlesViewModel

@Composable
fun ArticlesScreen(
    navController: NavHostController,
    articleViewModel: ArticlesViewModel
) {
    val articleState = articleViewModel.articleState.collectAsState()

    Column {
        AppBar("Articles")
        if (articleState.value.loading)
            Loader()
        if (articleState.value.error != null)
            ErrorMessage(articleState.value.error!!)
        if (articleState.value.articles.isNotEmpty()) {
            ArticleDropdown(articleViewModel)
            ArticleListView(articleState.value.articles, navController, articleViewModel)
        }
    }
}

@Composable
private fun ArticleDropdown(viewModel: ArticlesViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val state = viewModel.articleState.value
        item { ArticleDropdownMenu(ArticleFilterType.AUTHOR.text, state.authors) }
        item { ArticleDropdownMenu(ArticleFilterType.CATEGORY.text, state.categories) }
        item { ArticleDropdownMenu(ArticleFilterType.ARTICLE_TYPE.text, state.articleTypes) }
        item { ArticleDropdownMenu(ArticleFilterType.TAG.text, state.tags) }
    }
}

@Composable
fun ArticleDropdownMenu(
    label: String,
    items: List<String?>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items.firstOrNull() ?: "Select") }

    Column(modifier = Modifier.padding(4.dp)) {
        Text(
            text = label, style = MaterialTheme.typography.labelSmall,
            maxLines = 1
        )

        Box {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items.distinct().forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            selectedItem = item ?: "Unknown"
                            expanded = false
                        },
                        text = {
                            Text(
                                text = item ?: "Unknown",
                                maxLines = 1
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ArticleListView(
    articles: List<Article>,
    navController: NavHostController,
    viewModel: ArticlesViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(articles) { article ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                ),
                shape = RectangleShape,
                onClick = {
                    viewModel.setSelectedArticle(article)
                    navController.navigate(Screens.ARTICLEDETAILS.screenName)
                }
            ) {
                ArticleItemView(article)
            }
        }
    }
}

@Composable
private fun ArticleItemView(article: Article) {
    Row {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .background(Color.Black)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            Text(
                text = article.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun Loader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = TextStyle(fontWeight = FontWeight.Medium)
        )
    }
}