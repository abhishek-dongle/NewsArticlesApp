package com.abhishek.dongle.newsarticlesapp.article

import com.abhishek.dongle.newsarticlesapp.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticlesViewModel : BaseViewModel() {
    private val _articleState = MutableStateFlow(ArticlesState(loading = true))
    val articleState: StateFlow<ArticlesState> get() = _articleState

    init {
        getArticles()
    }

    fun setSelectedArticle(article: Article) {
        _articleState.value = _articleState.value.copy(selectedArticle = article)
    }

    private fun getArticles() {
        scope.launch {
            val fetchedArticles = fetchArticles()
            delay(1000)
            _articleState.emit(ArticlesState(error = "Something went wrong."))

            delay(1000)
            _articleState.emit(ArticlesState(articles = fetchedArticles))
        }
    }

    private suspend fun fetchArticles() = mockArticle

    private val mockArticle = listOf(
        Article(
            title = "Gold Powers to Record as Trump’s Fed Broadside Rattles Markets - Bloomberg.com",
            desc = "Gold extended a blistering rally to rise above $3,500 an ounce for the first time, as concern that President Donald Trump could fire Federal Reserve Chair Jerome Powell triggered a flight from US stocks, bonds and the dollar.",
            author = "Yihui Xie",
            imageUrl = "https://assets.bwbx.io/images/users/iqjWHBFdfxIU/iEkChbGht5xE/v0/1200x800.jpg",
            content = "Gold extended a blistering rally to rise above $3,500 an ounce for the first time, as concern that President Donald Trump could fire Federal Reserve Chair Jerome Powell triggered a flight from US sto…",
            category = "Finance",
            articleType = "News",
            tag = "Gold"
        ),
        Article(
            title = "Walgreens to pay up to $350 million in US opioid settlement - CNN",
            desc = "Walgreens has agreed to pay up to $350 million in a settlement with the U.S. Department of Justice, who accused the pharmacy of illegally filling millions of prescriptions in the last decade for opioids and other controlled substances.",
            author = "Associated Press",
            imageUrl = "https://media.cnn.com/api/v1/images/stellar/prod/ap25072097339624.jpg?c=16x9&q=w_800,c_fill",
            content = "Walgreens has agreed to pay up to $350 million in a settlement with the US Department of Justice, who accused the pharmacy of illegally filling millions of prescriptions in the last decade for opioid…",
            category = "Health",
            articleType = "News",
            tag = "Opioids"
        ),
        Article(
            title = "Dow Headed for Worst April Since 1932 as Investors Send ‘No Confidence’ Signal - WSJ",
            desc = "Investors are sending a ‘no confidence’ signal to the Federal Reserve, and the Dow Jones Industrial Average is headed for its worst April since 1932.",
            author = "WSJ",
            imageUrl = "https://images.wsj.net/im-1234567/social",
            content = "The Dow Jones Industrial Average is headed for its worst April since 1932, as investors send a ‘no confidence’ signal to the Federal Reserve. The index is down more than 4% this month, and the S&P 500 is down about 3%.",
            category = "Finance",
            articleType = "News",
            tag = "Dow Jones"
        ),
        Article(
            title = "Scoop: Key House Republican defends Fed against Trump interference - Axios",
            desc = "Rep. Frank Lucas said a task force has discussed how to build \"the walls higher and stronger and taller.\"",
            author = "Courtenay Brown",
            imageUrl = "https://images.axios.com/yK0ou0iC-EqG0M0k5kSQSMQ2CXM=/0x24:4256x2418/1366x768/2025/04/21/1745277080148.jpg",
            content = "The Republican leader of a congressional task force focused on the Federal Reserve tells Axios in an exclusive interview there is bipartisan interest in stronger guardrails around the central bank's …",
            category = "Politics",
            articleType = "News",
            tag = "Federal Reserve"
        ),
        Article(
            title = "Justice Dept. asks judge to ‘thaw’ Google’s search monopoly by forcing Chrome sale - The Washington Post",
            desc = "A federal trial over what steps Google needs to take to remedy its internet search monopoly opened in Washington.",
            author = "Julian Mark",
            imageUrl = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/M2W4S35O4IDMKHTNO6OXHDY4RU.jpg&w=1440",
            content = "The Justice Department on Monday argued before a federal judge in Washington that Google should be forced to divest its Chrome web browser and make other major changes to its business to break up wha…",
            category = "Technology",
            articleType = "News",
            tag = "Google"
        ),
        Article(
            title = "Gold Powers to Record as Trump’s Fed Broadside Rattles Markets - Bloomberg.com",
            desc = "Gold extended a blistering rally to rise above $3,500 an ounce for the first time, as concern that President Donald Trump could fire Federal Reserve Chair Jerome Powell triggered a flight from US stocks, bonds and the dollar.",
            author = "Yihui Xie",
            imageUrl = "https://assets.bwbx.io/images/users/iqjWHBFdfxIU/iEkChbGht5xE/v0/1200x800.jpg",
            content = "Gold extended a blistering rally to rise above $3,500 an ounce for the first time, as concern that President Donald Trump could fire Federal Reserve Chair Jerome Powell triggered a flight from US sto…",
            category = "Finance",
            articleType = "News",
            tag = "Gold"
        ),
        Article(
            title = "Walgreens to pay up to $350 million in US opioid settlement - CNN",
            desc = "Walgreens has agreed to pay up to $350 million in a settlement with the U.S. Department of Justice, who accused the pharmacy of illegally filling millions of prescriptions in the last decade for opioids and other controlled substances.",
            author = "Associated Press",
            imageUrl = "https://media.cnn.com/api/v1/images/stellar/prod/ap25072097339624.jpg?c=16x9&q=w_800,c_fill",
            content = "Walgreens has agreed to pay up to $350 million in a settlement with the US Department of Justice, who accused the pharmacy of illegally filling millions of prescriptions in the last decade for opioid…",
            category = "Health",
            articleType = "News",
            tag = "Opioids"
        ),
        Article(
            title = "Dow Headed for Worst April Since 1932 as Investors Send ‘No Confidence’ Signal - WSJ",
            desc = "Investors are sending a ‘no confidence’ signal to the Federal Reserve, and the Dow Jones Industrial Average is headed for its worst April since 1932.",
            author = "WSJ",
            imageUrl = "https://images.wsj.net/im-1234567/social",
            content = "The Dow Jones Industrial Average is headed for its worst April since 1932, as investors send a ‘no confidence’ signal to the Federal Reserve. The index is down more than 4% this month, and the S&P 500 is down about 3%.",
            category = "Finance",
            articleType = "News",
            tag = "Dow Jones"
        ),
        Article(
            title = "Scoop: Key House Republican defends Fed against Trump interference - Axios",
            desc = "Rep. Frank Lucas said a task force has discussed how to build \"the walls higher and stronger and taller.\"",
            author = "Courtenay Brown",
            imageUrl = "https://images.axios.com/yK0ou0iC-EqG0M0k5kSQSMQ2CXM=/0x24:4256x2418/1366x768/2025/04/21/1745277080148.jpg",
            content = "The Republican leader of a congressional task force focused on the Federal Reserve tells Axios in an exclusive interview there is bipartisan interest in stronger guardrails around the central bank's …",
            category = "Politics",
            articleType = "News",
            tag = "Federal Reserve"
        ),
        Article(
            title = "Justice Dept. asks judge to ‘thaw’ Google’s search monopoly by forcing Chrome sale - The Washington Post",
            desc = "A federal trial over what steps Google needs to take to remedy its internet search monopoly opened in Washington.",
            author = "Julian Mark",
            imageUrl = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/M2W4S35O4IDMKHTNO6OXHDY4RU.jpg&w=1440",
            content = "The Justice Department on Monday argued before a federal judge in Washington that Google should be forced to divest its Chrome web browser and make other major changes to its business to break up wha…",
            category = "Technology",
            articleType = "News",
            tag = "Google"
        )
    )
}