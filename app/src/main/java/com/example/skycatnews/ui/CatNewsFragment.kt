package com.example.skycatnews.ui

import android.app.AlertDialog
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
    private lateinit var imageView: ImageView
    private lateinit var time: TextView

    private val observable = Observer<CatNewsViewModel.NewsListResponse> {
        when(it){
            it as CatNewsViewModel.NewsListResponse.Success -> updateUI(it.catNews)
            it as CatNewsViewModel.NewsListResponse.Failure -> showErrorDialog()
        }
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this.context)
        builder.setTitle("Warning!!")
        builder.setMessage("Something went wrong try again later")
        builder.setNeutralButton("OK") { dialog, which ->

        }
        builder.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

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
        fetchRetroInfo()
        return view
    }

    private fun initViewModel() {
        val retroViewModelFactory = ViewModelFactory()
        viewModel = ViewModelProviders.of(this, retroViewModelFactory).get(CatNewsViewModel::class.java)
    }

    private fun fetchRetroInfo() {
        viewModel.newsLiveData.observe(this, observable)
        viewModel.fetchCatNewsFromRepository()
    }

    private fun updateUI(catNews: CatNews) {
        newsheadLine.text = catNews.title
        latestheadLine.text = catNews.data[0].headline
        latestTeaser.text = catNews.data[0].teaserText
        time.text = getTime(catNews.data[0].modifiedDate)
        listAdapter?.setAdapterList(catNews.data)
        /*Picasso.get().load(catNews.newsHeadline[0].teaserImage._links.url.href)
            .placeholder(R.drawable.placeholder).into(imageView)*/
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

    private fun initAdapter() {
        listAdapter = NewsHeadLinesListAdapter()
        storyList.adapter = listAdapter
    }


}