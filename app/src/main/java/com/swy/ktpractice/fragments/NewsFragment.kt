package com.swy.ktpractice.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swy.ktpractice.NewsManager
import com.swy.ktpractice.R
import com.swy.ktpractice.adapter.NewsAdapter
import com.swy.ktpractice.bean.NewsItem
import com.swy.ktpractice.bean.RedditNews
import com.swy.ktpractice.extension.inflate
import com.swy.ktpractice.extension.showSnackbar
import kotlinx.android.synthetic.main.news_fragment_layout.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class NewsFragment : BaseFragment() {


    private var redditNews: RedditNews? = null

    companion object {
        private var KEY_REDDIT_NEWS = "redditNews"
    }

    private val newsManager by lazy {
        NewsManager()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.news_fragment_layout)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        news_list.apply {
            news_list.setHasFixedSize(true)
            news_list.layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestData() }, news_list.layoutManager as LinearLayoutManager))
            adapter = NewsAdapter()
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
            var redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
            var news = redditNews!!.news
            (news_list.adapter as NewsAdapter).clearAndAddNews(news)
        } else {
            requestData()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var news = (news_list.adapter as NewsAdapter).getNews()
        if (redditNews != null && news.isNotEmpty()) {
            outState?.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = news))
        }
    }

    private fun requestData() {
        val subscribe = newsManager.getNews(redditNews?.after ?: "", 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ retrievedNews ->
                    redditNews  = retrievedNews
                    (news_list.adapter as NewsAdapter).addNews(redditNews!!.news)
                }, { error ->
                    showSnackbar(news_list, error.message ?: "")
                })
        subscriptions.add(subscribe)


    }
}