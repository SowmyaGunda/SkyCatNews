package com.example.skycatnews.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.skycatnews.R
import com.example.skycatnews.util.NewsHeadline
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class NewsHeadLinesListAdapter() : RecyclerView.Adapter<NewsHeadLinesListAdapter.NewsHolder>() {


    private var newsList: List<NewsHeadline> = emptyList<NewsHeadline>()

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflatedView = parent.inflate(R.layout.cat_news_item_view)
        return NewsHolder(inflatedView)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val newsHeadline: NewsHeadline = newsList[position]
        holder.header.text = newsHeadline.headline
        holder.time.text = getTime(newsHeadline.ModifiedDate)
        holder.desc.text = newsHeadline.teaserText
        if (newsHeadline.teaserImage._links.url.href.isNotEmpty()) {
            Picasso.get().load(newsHeadline.teaserImage._links.url.href)
                .placeholder(R.drawable.placeholder).into(holder.image)
        }
    }

    fun setAdapterList(list: List<NewsHeadline>) {
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

    class NewsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var header: TextView = v.findViewById(R.id.header)
        var desc: TextView = v.findViewById(R.id.desc)
        var time: TextView = v.findViewById(R.id.desc)
        var image: ImageView = v.findViewById(R.id.image)
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

    }
}