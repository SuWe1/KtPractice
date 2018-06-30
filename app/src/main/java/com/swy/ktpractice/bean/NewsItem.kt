package com.swy.ktpractice.bean

import android.os.Parcel
import android.os.Parcelable
import com.swy.ktpractice.adapter.common.AdapterConstants
import com.swy.ktpractice.adapter.common.ViewType

data class NewsItem(val author: String,
                    val title: String,
                    val numComments: Int,
                    val created: Long,
                    val thumbnail: String,
                    val url: String?) : ViewType, Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun getViewType(): Int = AdapterConstants.NEWS

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeInt(numComments)
        parcel.writeLong(created)
        parcel.writeString(thumbnail)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsItem> {
        override fun createFromParcel(parcel: Parcel): NewsItem {
            return NewsItem(parcel)
        }

        override fun newArray(size: Int): Array<NewsItem?> {
            return arrayOfNulls(size)
        }
    }


}