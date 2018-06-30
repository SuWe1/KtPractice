package com.swy.ktpractice.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.swy.ktpractice.R
import com.swy.ktpractice.adapter.common.ViewType
import com.swy.ktpractice.adapter.common.ViewTypeDelegateAdapter
import com.swy.ktpractice.bean.NewsItem
import com.swy.ktpractice.extension.getFriendlyTime
import com.swy.ktpractice.extension.inflate
import com.swy.ktpractice.extension.loadingImage
import kotlinx.android.synthetic.main.news_item.*
import kotlinx.android.synthetic.main.news_item.view.*

@Suppress("UNREACHABLE_CODE")
class NewsDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = NewsViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as NewsViewHolder
        holder.bind(item as NewsItem)
    }

    class NewsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.news_item)) {
        private val img_thumbnail = itemView.img_thumbnail
        private val description = itemView.description
        private val author = itemView.author
        private val comments = itemView.comments
        private val time = itemView.time

        @SuppressLint("SetTextI18n")
        fun bind(item: NewsItem) {
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"
            time.text = item.created.getFriendlyTime()
            img_thumbnail.loadingImage(item.url.toString())
        }
    }
}