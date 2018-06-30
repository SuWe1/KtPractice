package com.swy.ktpractice

import com.swy.ktpractice.bean.NewsItem
import com.swy.ktpractice.bean.RedditNews
import com.swy.ktpractice.model.RestApi
import com.swy.ktpractice.model.interfaze.NewsApi
import rx.Observable
import rx.observers.Observers

class NewsManager(private var api: NewsApi = RestApi()) {

    fun getNews(after: String, limit: Int = 10): Observable<RedditNews> {
        return Observable.create { subscriber ->
            val execute = api.getNews(after, limit).execute()
            var response = execute.body().data
            if (execute.isSuccessful) {
                var items = response.children.map {
                    var item = it.data
                    NewsItem(item.author, item.title, item.num_comments,
                            item.created, item.thumbnail, item.url)
                }
                var news = RedditNews(response.after ?: "", response.before ?: "", items)
                subscriber.onNext(news)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(execute.message()))
            }
        }
    }
}