package com.abhishek.dongle.newsarticlesapp.screens

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.abhishek.dongle.newsarticlesapp.article.Article
import com.abhishek.dongle.newsarticlesapp.article.ArticlesViewModel

@Composable
fun ArticleDetailsScreen(articleViewModel: ArticlesViewModel) {
    val articleState = articleViewModel.articleState.collectAsState()
    val selectedArticle = articleState.value.selectedArticle
    Column(modifier = Modifier.fillMaxSize()) {
        AppBar("Article Details")
        ArticleDetails(selectedArticle)
    }
}

@Composable
fun WebViewScreen(url: String) {
    AndroidView(
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
fun ArticleDetails(article: Article?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = article?.title ?: "Article Title",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        AsyncImage(
            model = article?.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(top = 6.dp)
                .background(Color.Black)
        )

        Row(modifier = Modifier.padding(top = 10.dp)) {
            AsyncImage(
                model = article?.imageUrl,
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
                    text = article?.author ?: "Author Name",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = article?.desc ?: "Author Description",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal
                    ),
                    maxLines = 2
                )
            }
        }

        Text(
            text = article?.desc ?: "No Content",
            style = TextStyle(
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        WebViewScreen(url = article?.articleType ?: "https://developer.android.com")

        val sampleItems = listOf("One", "Two", "Three", "Four", "Five")
        HorizontalList(items = sampleItems)
    }
}

@Composable
fun HorizontalList(items: List<String>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(items) { item ->
            Card(
                modifier = Modifier.padding(5.dp),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}