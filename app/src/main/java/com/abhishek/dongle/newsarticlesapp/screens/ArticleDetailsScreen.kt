package com.abhishek.dongle.newsarticlesapp.screens

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.abhishek.dongle.newsarticlesapp.article.Article
import com.abhishek.dongle.newsarticlesapp.article.ArticlesViewModel
import kotlinx.coroutines.delay

@Composable
fun ArticleDetailsScreen(
    navController: NavHostController,
    articleViewModel: ArticlesViewModel
) {
    var isLoading by remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxSize()) {
        AppBar(navController, Screens.ARTICLE_DETAILS.screenName)

        LaunchedEffect(Unit) {
            withFrameNanos { }
            delay(800)
            isLoading = false
        }
        if (isLoading) Loader() else ArticleDetails(navController, articleViewModel)
    }
}

@Composable
fun ArticleDetails(
    navController: NavHostController,
    viewModel: ArticlesViewModel
) {
    val articleState by viewModel.articleState.collectAsState()
    val selectedArticle = remember(articleState) { articleState.selectedArticle }!!
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Text(
            text = selectedArticle.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        AsyncImage(
            model = selectedArticle.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(top = 6.dp)
                .background(Color.Black)
        )

        AuthorDetailsCard(selectedArticle)

        Text(
            text = selectedArticle.subtitle,
            maxLines = 3,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        if (selectedArticle.pageUrl != "Upcoming Link") {
            WebViewScreen(url = selectedArticle.pageUrl)
        }

        var isRendering by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            withFrameNanos { }
            delay(2000)
            isRendering = false
        }
        if (!isRendering) {
            ArticleTagList(
                viewModel = viewModel,
                navController = navController,
                tagList = selectedArticle.tag
            )
        }
    }
}

@Composable
fun AuthorDetailsCard(selectedArticle: Article) {
    Row(modifier = Modifier.padding(top = 10.dp)) {
        AsyncImage(
            model = selectedArticle.authorImageUrl,
            contentDescription = null,
            modifier = Modifier
                .height(60.dp)
                .background(Color.Black)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = selectedArticle.author,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = selectedArticle.authorDesc,
                fontWeight = FontWeight.Normal,
                maxLines = 2
            )
        }
    }
}

@Composable
fun WebViewScreen(url: String) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(top = 12.dp),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        }
    )
}

@Composable
fun ArticleTagList(
    viewModel: ArticlesViewModel,
    navController: NavHostController,
    tagList: List<String>
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(tagList) { item ->
            Card(
                modifier = Modifier.padding(5.dp),
                elevation = CardDefaults.cardElevation(3.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                onClick = {
                    viewModel.setSelectedTag(tag = item)
                    navController.navigate(Screens.TAGGED_ARTICLES.screenName)
                }
            ) {
                Box(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item, color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}