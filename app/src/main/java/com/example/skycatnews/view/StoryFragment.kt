package com.example.skycatnews.view

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skycatnews.R
import com.example.skycatnews.model.data.NewsStory
import com.example.skycatnews.viewmodel.StoryViewModel
import com.example.skycatnews.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso


class StoryFragment : Fragment() {

    companion object {
        fun newInstance() = StoryFragment()
    }

    private lateinit var viewModel: StoryViewModel
    private lateinit var listAdapter: StoryListAdapter
    private lateinit var imageView: ImageView
    private lateinit var headLine: TextView
    private lateinit var storyContent: RecyclerView
    private lateinit var loadingDialog: AlertDialog;
    private val observable = Observer<StoryViewModel.NewsStoryResponse> {
        if (it is StoryViewModel.NewsStoryResponse.Success) {
            updateUI(it.newsStory)
        } else if (it is StoryViewModel.NewsStoryResponse.Failure) showErrorDialog()
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

    private fun showLoading() {
        loadingDialog = ProgressDialog(context)
        loadingDialog.setMessage("Loading..")
        loadingDialog.setCancelable(false)
        loadingDialog.setInverseBackgroundForced(false)
        loadingDialog.show()
    }

    private fun cancelLoading() {
        loadingDialog.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.story_fragment, container, false)
        storyContent = view.findViewById(R.id.story_list)
        imageView = view.findViewById(R.id.main_story_image)
        headLine = view.findViewById(R.id.story_head_line)
        initAdapter()
        arguments?.getString("id")?.let { fetchRetroInfo(it) }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initViewModel() {
        val retroViewModelFactory = ViewModelFactory()
        viewModel =
                ViewModelProviders.of(this, retroViewModelFactory).get(StoryViewModel::class.java)
    }

    private fun fetchRetroInfo(id: String) {
        showLoading()
        viewModel.storyLiveData.observe(this, observable)
        viewModel.fetchStoryFromRepository(id)
    }

    private fun updateUI(newsStory: NewsStory) {
        headLine.text = newsStory.headline
        val layoutManager = LinearLayoutManager(context)
        storyContent.layoutManager = layoutManager
        listAdapter.setAdapterList(newsStory.contents)
        if (newsStory.heroImage.imageUrl.isNotEmpty()) {
            Picasso.get().load(newsStory.heroImage.imageUrl)
                    .placeholder(R.drawable.placeholder).into(imageView)
        }
    }


    private fun initAdapter() {
        listAdapter = StoryListAdapter()
        storyContent.adapter = listAdapter
    }

}