package com.abhishek.dongle.newsarticlesapp.base.article

import com.abhishek.dongle.newsarticlesapp.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArticlesViewModel : BaseViewModel() {
    private val _articleState = MutableStateFlow(ArticlesState())
    val articleState: StateFlow<ArticlesState> get() = _articleState

}