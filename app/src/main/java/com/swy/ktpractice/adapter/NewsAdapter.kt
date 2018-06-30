package com.swy.ktpractice.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.swy.ktpractice.adapter.common.AdapterConstants
import com.swy.ktpractice.adapter.common.ViewType
import com.swy.ktpractice.adapter.common.ViewTypeDelegateAdapter
import com.swy.ktpractice.bean.NewsItem

@Suppress("UNREACHABLE_CODE")
class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>

    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private val loadingItem = object : ViewType {
        override fun getViewType(): Int = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.NEWS, NewsDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items.get(position).getViewType()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    fun addNews(news: List<NewsItem>) {
        var oldSize = items.size - 1
        //先移除loading
        items.removeAt(oldSize)
        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeChanged(oldSize, items.size + 1)
    }

    fun addNews(news: NewsItem) {
        var oldSize = items.size - 1
        //先移除loading
        items.removeAt(oldSize)
        items.add(news)
        items.add(loadingItem)
        notifyItemRangeChanged(oldSize, items.size + 1)
    }

    fun getNews(): List<NewsItem> {
        return items.filter {
            it.getViewType() == AdapterConstants.NEWS
        }.map { it as NewsItem }
    }

    fun clearAndAddNews(news: List<NewsItem>, isClear: Boolean = true) {
        if (!isClear) {
            addNews(news)
            return
        }
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())
        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}