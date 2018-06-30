package com.swy.ktpractice.model

import com.swy.ktpractice.bean.NewsItem
import com.swy.ktpractice.model.interfaze.NewsApi
import com.swy.ktpractice.model.interfaze.RedditApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestApi :NewsApi {

    private val redditApi: RedditApi
    val baseUrl: String = "https://www.reddit.com"

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        redditApi = retrofit.create(RedditApi::class.java)
    }


    override fun getNews(after: String, limit: Int): Call<RedditNewsResponse> {
        return  redditApi.getTopNews(after,limit)
    }
}