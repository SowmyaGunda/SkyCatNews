package com.example.skycatnews.view

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skycatnews.R
import com.example.skycatnews.model.data.CatNews
import com.example.skycatnews.model.data.StoryType
import com.example.skycatnews.model.image.ImageLoader
import com.example.skycatnews.viewmodel.CatNewsViewModel
import com.example.skycatnews.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import java.util.*


class CatNewsFragment : Fragment(), NewsHeadLinesListAdapter.OnItemClicked {

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
    private lateinit var loadingDialog: AlertDialog;

    private val observable = Observer<CatNewsViewModel.NewsListResponse> {
        if (it is CatNewsViewModel.NewsListResponse.Success) updateUI(it.catNews)
        else if (it is CatNewsViewModel.NewsListResponse.Failure) showErrorDialog()
        cancelLoading()
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this.context)
        builder.setTitle("Warning!!")
        builder.setMessage("Something went wrong try again later")
        builder.setNeutralButton("OK") { dialog, which ->

        }
        builder.show()
    }

    private fun cancelLoading() {
        loadingDialog.dismiss()
    }

    private fun showLoading() {
        loadingDialog = ProgressDialog(context)
        loadingDialog.setMessage("Loding..")
        loadingDialog.setCancelable(false)
        loadingDialog.setInverseBackgroundForced(false)
        loadingDialog.show()
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
        showLoading()
        viewModel.newsLiveData.observe(this, observable)
        viewModel.fetchCatNewsFromRepository()
    }

    private fun updateUI(catNews: CatNews) {
        newsheadLine.text = catNews.title
        val layoutManager = LinearLayoutManager(context)
        storyList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(context,
                layoutManager.orientation)
        storyList.addItemDecoration(dividerItemDecoration)
        listAdapter.setAdapterList(catNews.data)
        val storyContent = catNews.data[0] as StoryType
        latestheadLine.text = storyContent.headline
        latestTeaser.text = storyContent.teaserText
        //time.text = getTime(storyContent.modifiedDate)
        ImageLoader.loadImage(storyContent.teaserImage.imageUrl, imageView)
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
        listAdapter = NewsHeadLinesListAdapter(this)
        storyList.adapter = listAdapter
    }

    override fun onStoryType(id: String) {
        var storyFragment: StoryFragment = StoryFragment.newInstance()
        val args = Bundle()
        args.putString("id", id)
        storyFragment.setArguments(args)
        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, storyFragment)?.addToBackStack("crop_type")
                ?.commit()
    }

    override fun onWebLinkType(url: String) {
        var webUrl = url
        if (webUrl.isEmpty()) {
            webUrl = "https://www.google.com/"
        } else if (!webUrl.startsWith("http://") && !webUrl.startsWith("https://"))
            webUrl = "http://$webUrl"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
        context?.startActivity(browserIntent)
    }

    override fun onAd(url: String) {
        var adUrl = url
        if (adUrl.isEmpty()) {
            adUrl = "https://www.google.com/"
        } else if (!adUrl.startsWith("http://") && !adUrl.startsWith("https://"))
            adUrl = "http://$adUrl"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(adUrl))
        context?.startActivity(browserIntent)
    }

}