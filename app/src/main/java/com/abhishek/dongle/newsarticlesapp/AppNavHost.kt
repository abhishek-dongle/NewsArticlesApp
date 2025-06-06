package com.abhishek.dongle.newsarticlesapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhishek.dongle.newsarticlesapp.article.ArticlesViewModel
import com.abhishek.dongle.newsarticlesapp.screens.ArticleDetailsScreen
import com.abhishek.dongle.newsarticlesapp.screens.ArticlesScreen
import com.abhishek.dongle.newsarticlesapp.screens.Screens
import com.abhishek.dongle.newsarticlesapp.screens.TaggedArticlesScreen

@Composable
fun AppNavHost(
    viewModel: ArticlesViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.ARTICLES.screenName
    ) {
        composable(Screens.ARTICLES.screenName) {
            ArticlesScreen(navController, viewModel)
        }
        composable(Screens.ARTICLE_DETAILS.screenName) {
            ArticleDetailsScreen(navController, viewModel)
        }
        composable(Screens.TAGGED_ARTICLES.screenName) {
            TaggedArticlesScreen(navController, viewModel)
        }
    }
}