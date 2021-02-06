package com.example.skycatnews.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.skycatnews.R
import com.example.skycatnews.model.data.NewsHeadline
import com.squareup.picasso.Picasso
import java.util.*


class NewsHeadLinesListAdapter(private var onItemClicked: OnItemClicked) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class NewsListItemType(val type: String) {
        STORY("story"),
        WEBLINK("webLink"),
        ADVERT("advert")
    }


    private var newsList: List<NewsHeadline> = emptyList<NewsHeadline>()

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NewsListItemType.ADVERT.ordinal -> {
                val inflatedView = parent.inflate(R.layout.cat_news_item_advert)
                NewsTypeAdvert(inflatedView, onItemClicked)
            }
            NewsListItemType.STORY.ordinal -> {
                val inflatedView = parent.inflate(R.layout.cat_news_item_story)
                NewsTypeStory(inflatedView, onItemClicked)
            }
            else -> {
                val inflatedView = parent.inflate(R.layout.cat_news_item_weblink)
                NewsTypeWebLink(inflatedView, onItemClicked)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (newsList[position].type) {
            NewsListItemType.ADVERT.type -> {
                NewsListItemType.ADVERT.ordinal
            }
            NewsListItemType.STORY.type -> {
                NewsListItemType.STORY.ordinal
            }
            else -> {
                NewsListItemType.WEBLINK.ordinal
            }
        }
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newsHeadline: NewsHeadline = newsList[position]
        when (holder) {
            is NewsTypeStory -> {
                holder.bind(newsHeadline, getTime(newsHeadline.modifiedDate))
            }
            is NewsTypeWebLink -> {
                holder.bind(newsHeadline, getTime(newsHeadline.modifiedDate))
            }
            is NewsTypeAdvert -> {
                holder.bind(newsHeadline)
            }
        }

    }

    fun setAdapterList(list: List<NewsHeadline>) {
        this.newsList = list
        notifyDataSetChanged()
    }

    private fun getTime(modifiedDate: Date): String {
        val currentDate = Date()
        val diff: Long = currentDate.time - modifiedDate.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return when {
            days > 0 -> "$days days"
            hours > 0 -> "$hours hrs"
            minutes > 0 -> "$minutes mins"
            else -> "now"
        }

    }

    class NewsTypeStory(private var v: View, private var onItemClicked: OnItemClicked) : RecyclerView.ViewHolder(v) {
        private var header: TextView = v.findViewById(R.id.header)
        private var desc: TextView = v.findViewById(R.id.desc)
        private var time: TextView = v.findViewById(R.id.time)
        private var image: ImageView = v.findViewById(R.id.image)
        fun bind(newsHeadLine: NewsHeadline, timeString: String) {
            header.text = newsHeadLine.headline
            time.text = timeString
            desc.text = newsHeadLine.teaserText
            if (newsHeadLine.teaserImage._links.url.href.isNotEmpty()) {
                Picasso.get().load(newsHeadLine.teaserImage._links.url.href)
                        .placeholder(R.drawable.placeholder).into(image)
            }
            v.setOnClickListener {
                onItemClicked.onStoryType(newsHeadLine.id)
            }


        }
    }

    class NewsTypeWebLink(private var v: View, private var onItemClicked: OnItemClicked) : RecyclerView.ViewHolder(v) {
        private var header: TextView = v.findViewById(R.id.header)
        private var webLink: TextView = v.findViewById(R.id.weblink)
        private var time: TextView = v.findViewById(R.id.time)
        private var image: ImageView = v.findViewById(R.id.image)
        fun bind(newsHeadLine: NewsHeadline, timeString: String) {
            header.text = newsHeadLine.headline
            time.text = timeString
            webLink.text = newsHeadLine.weblinkUrl
            if (newsHeadLine.teaserImage._links.url.href.isNotEmpty()) {
                Picasso.get().load(newsHeadLine.teaserImage._links.url.href)
                        .placeholder(R.drawable.placeholder).into(image)
            }
            v.setOnClickListener {
                onItemClicked.onWebLinkType(newsHeadLine.weblinkUrl)
            }


        }

    }

    class NewsTypeAdvert(private var v: View, private var onItemClicked: OnItemClicked) : RecyclerView.ViewHolder(v) {
        private var advert: TextView = v.findViewById(R.id.advert)

        fun bind(newsHeadLine: NewsHeadline) {
            advert.text = newsHeadLine.url
            v.setOnClickListener {
                onItemClicked.onAd(newsHeadLine.url)
            }
        }

    }

    interface OnItemClicked {
        fun onStoryType(id: String)
        fun onWebLinkType(url: String)
        fun onAd(url: String)
    }
}