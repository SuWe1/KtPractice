package com.swy.ktpractice.model.interfaze

import com.swy.ktpractice.bean.NewsItem
import com.swy.ktpractice.model.RedditNewsResponse
import retrofit2.Call

interface NewsApi {
    fun getNews(after: String, limit: Int): Call<RedditNewsResponse>
}