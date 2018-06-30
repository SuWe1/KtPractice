package com.swy.ktpractice.model.interfaze

import com.swy.ktpractice.bean.NewsItem
import com.swy.ktpractice.model.RedditNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {
    @GET("top.json")
    fun getTopNews(@Query("after") after: String, @Query("limit") limit: Int): Call<RedditNewsResponse>
}