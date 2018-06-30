package com.swy.ktpractice.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.swy.ktpractice.R
import com.swy.ktpractice.adapter.common.ViewType
import com.swy.ktpractice.adapter.common.ViewTypeDelegateAdapter
import com.swy.ktpractice.extension.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = FooterHolderView(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class FooterHolderView(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.news_loading_item))
}