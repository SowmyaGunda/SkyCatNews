package com.example.skycatnews.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.skycatnews.R
import com.example.skycatnews.model.data.AdvertType
import com.example.skycatnews.model.data.News
import com.example.skycatnews.model.data.StoryType
import com.example.skycatnews.model.data.WebLinkType
import com.example.skycatnews.model.image.ImageLoader
import java.util.*


class NewsHeadLinesListAdapter(private var onItemClicked: OnItemClicked) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class NewsListItemType(val type: String) {
        STORY("story"),
        WEBLINK("webLink"),
        ADVERT("advert")
    }


    private var newsList: List<News> = emptyList()

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
        return when (newsList[position]) {
            is AdvertType -> {
                NewsListItemType.ADVERT.ordinal
            }
            is StoryType -> {
                NewsListItemType.STORY.ordinal
            }
            is WebLinkType -> {
                NewsListItemType.WEBLINK.ordinal
            }
            else -> -1
        }
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newsHeadline: News = newsList[position]
        when (holder) {
            is NewsTypeStory -> {
                holder.bind(newsHeadline/*, getTime((newsHeadline as StoryType).modifiedDate)*/)
            }
            is NewsTypeWebLink -> {
                holder.bind(newsHeadline/*, getTime((newsHeadline as StoryType).modifiedDate)*/)
            }
            is NewsTypeAdvert -> {
                holder.bind(newsHeadline)
            }
        }

    }

    fun setAdapterList(list: List<News>) {
        this.newsList = list
        notifyDataSetChanged()
    }

    private fun getTime(modifiedDate: String): String {
        val currentDate = Date()
        val diff: Long = currentDate.time - Date(modifiedDate).time
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
        fun bind(newsHeadLine: News/*, timeString: String*/) {
            val storyType: StoryType = newsHeadLine as StoryType
            header.text = storyType.headline
            // time.text = timeString
            desc.text = storyType.teaserText
            ImageLoader.loadImage(storyType.teaserImage.imageUrl, image)
            v.setOnClickListener {
                onItemClicked.onStoryType(storyType.id)
            }


        }
    }

    class NewsTypeWebLink(private var v: View, private var onItemClicked: OnItemClicked) : RecyclerView.ViewHolder(v) {
        private var header: TextView = v.findViewById(R.id.header)
        private var webLink: TextView = v.findViewById(R.id.weblink)
        private var time: TextView = v.findViewById(R.id.time)
        private var image: ImageView = v.findViewById(R.id.image)
        fun bind(newsHeadLine: News/*, timeString: String*/) {
            val weblinkType: WebLinkType = newsHeadLine as WebLinkType
            header.text = weblinkType.headline
            // time.text = timeString
            webLink.text = weblinkType.weblinkUrl
            ImageLoader.loadImage(weblinkType.teaserImage.imageUrl, image)
            v.setOnClickListener {
                onItemClicked.onWebLinkType(weblinkType.weblinkUrl)
            }


        }

    }

    class NewsTypeAdvert(private var v: View, private var onItemClicked: OnItemClicked) : RecyclerView.ViewHolder(v) {
        private var advert: TextView = v.findViewById(R.id.advert)

        fun bind(newsHeadLine: News) {
            val advertType: AdvertType = newsHeadLine as AdvertType
            advert.text = advertType.url
            v.setOnClickListener {
                onItemClicked.onAd(advertType.url)
            }
        }

    }

    interface OnItemClicked {
        fun onStoryType(id: String)
        fun onWebLinkType(url: String)
        fun onAd(url: String)
    }
}