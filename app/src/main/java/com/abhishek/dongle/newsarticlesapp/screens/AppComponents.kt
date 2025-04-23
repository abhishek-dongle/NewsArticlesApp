package com.abhishek.dongle.newsarticlesapp.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(name: String) {
    TopAppBar(
        title = { Text(text = name, fontWeight = FontWeight.Bold) }
    )
}