package com.abhishek.dongle.newsarticlesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.abhishek.dongle.newsarticlesapp.article.ArticlesViewModel
import com.abhishek.dongle.newsarticlesapp.ui.theme.NewsArticlesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val articlesViewModel: ArticlesViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            NewsArticlesAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = Color.White
                ) {
                    AppNavHost(viewModel = articlesViewModel)
                }
            }
        }
    }
}