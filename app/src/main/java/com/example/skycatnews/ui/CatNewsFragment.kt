package com.example.skycatnews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.skycatnews.R
import com.example.skycatnews.util.CatNews
import com.example.skycatnews.viewmodel.CatNewsViewModel
import com.example.skycatnews.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import java.util.*

class CatNewsFragment : Fragment() {

    companion object {
        fun newInstance() = CatNewsFragment()
    }

    private lateinit var viewModel: CatNewsViewModel
    private lateinit var listAdapter: NewsHeadLinesListAdapter
    private lateinit var storyList: RecyclerView
    private lateinit var newsheadLine: TextView
    private lateinit var latestheadLine: TextView
    private lateinit var latestTeaser: TextView
    private lateinit var imageView : ImageView
    private lateinit var time: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.cat_news_fragment, container, false)
        storyList = view.findViewById(R.id.news_list)
        newsheadLine = view.findViewById(R.id.news_head_line)
        latestTeaser = view.findViewById(R.id.latest_headline_teaser_text)
        latestheadLine = view.findViewById(R.id.latest_headline)
        time = view.findViewById(R.id.time)
        imageView = view.findViewById(R.id.main_story_image)
        initAdapter()
        // initViewModel()
       // fetchRetroInfo()
        return view
    }

    private fun initViewModel() {
        val retroViewModelFactory = ViewModelFactory()
        viewModel = ViewModelProviders.of(this, retroViewModelFactory).get(CatNewsViewModel::class.java)
    }

    private fun fetchRetroInfo() {
        viewModel.newsLiveData.observe(this,
            Observer<CatNews> { t ->
                t?.apply {
                    newsheadLine.text = t.title
                    latestheadLine.text = t.newsHeadline[0].headline
                    latestTeaser.text = t.newsHeadline[0].teaserText
                    time.text = getTime(t.newsHeadline[0].ModifiedDate)
                    listAdapter?.setAdapterList(t.newsHeadline)
                    Picasso.get().load(t.newsHeadline[0].teaserImage._links.url.href)
                        .placeholder(R.drawable.placeholder).into(imageView)
                }
            })
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

    private fun initAdapter() {
        listAdapter = NewsHeadLinesListAdapter()
        storyList.adapter = listAdapter
    }


}