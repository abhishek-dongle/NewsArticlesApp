package com.abhishek.dongle.newsarticlesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.abhishek.dongle.newsarticlesapp.article.ArticlesViewModel
import com.abhishek.dongle.newsarticlesapp.screens.ArticlesScreen
import com.abhishek.dongle.newsarticlesapp.ui.theme.NewsArticlesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val articlesViewModel: ArticlesViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            NewsArticlesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArticlesScreen(articleViewModel = articlesViewModel)
                }
            }
        }
    }
}