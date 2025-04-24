package com.abhishek.dongle.newsarticlesapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import com.abhishek.dongle.newsarticlesapp.article.ArticleFilterType
import com.abhishek.dongle.newsarticlesapp.article.ArticlesViewModel

@Composable
fun ArticlesScreen(
    navController: NavHostController,
    articleViewModel: ArticlesViewModel
) {
    val articleState = articleViewModel.articleState.collectAsState()

    Column {
        AppBar(navController, Screens.ARTICLES.screenName)
        if (articleState.value.loading)
            Loader()
        if (articleState.value.error != null)
            ErrorMessage(articleState.value.error!!)
        if (articleState.value.articles.isNotEmpty()) {
            ArticleDropdown(articleViewModel)
            ArticleListView(navController, articleViewModel)
        }
    }
}

@Composable
private fun ArticleDropdown(
    viewModel: ArticlesViewModel
) {
    val articleState by viewModel.articleState.collectAsState()

    Row(modifier = Modifier.fillMaxWidth()) {
        ArticleDropdownMenu(
            filterType = articleState.selectedAuthor,
            items = articleState.authors,
            onItemSelected = { item ->
                viewModel.filterArticleList(author = item, filterType = ArticleFilterType.AUTHOR)
            },
            modifier = Modifier.weight(1f)
        )
        ArticleDropdownMenu(
            filterType = articleState.selectedCategory,
            items = articleState.categories,
            onItemSelected = { item ->
                viewModel.filterArticleList(
                    category = item,
                    filterType = ArticleFilterType.CATEGORY
                )
            },
            modifier = Modifier.weight(1f)
        )
        ArticleDropdownMenu(
            filterType = articleState.selectedArticleType,
            items = articleState.articleTypes,
            onItemSelected = { item ->
                viewModel.filterArticleList(
                    articleType = item,
                    filterType = ArticleFilterType.ARTICLE_TYPE
                )
            },
            modifier = Modifier.weight(1f)
        )
        ArticleDropdownMenu(
            filterType = articleState.selectedTag,
            items = articleState.tags,
            onItemSelected = { item ->
                viewModel.filterArticleList(tag = item, filterType = ArticleFilterType.TAG)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleListView(
    navController: NavHostController,
    viewModel: ArticlesViewModel
) {
    val articleState by viewModel.articleState.collectAsState()
    val state = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = articleState.loading,
        onRefresh = { viewModel.getArticles(true) },
        state = state
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(articleState.filteredArticles) { article ->
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
                        navController.navigate(Screens.ARTICLE_DETAILS.screenName)
                    }
                ) {
                    ArticleItemView(article)
                }
            }
        }
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

@Composable
private fun ArticleDropdownMenu(
    filterType: String,
    items: List<String>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(filterType) }
    selectedItem = filterType
    Box(
        modifier = modifier
            .padding(4.dp)
            .background(Color.White, shape = MaterialTheme.shapes.small)
            .clickable { expanded = true }
            .padding(horizontal = 8.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = selectedItem,
                color = Color.Black,
                modifier = Modifier.weight(1f),
                maxLines = 1
            )
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedItem = item
                        expanded = false
                        onItemSelected(item)
                    }
                )
            }
        }
    }
}