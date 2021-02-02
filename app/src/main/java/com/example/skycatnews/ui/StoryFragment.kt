package com.example.skycatnews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.skycatnews.R
import com.example.skycatnews.util.NewsStory
import com.example.skycatnews.viewmodel.StoryViewModel
import com.example.skycatnews.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import androidx.lifecycle.Observer

class StoryFragment : Fragment() {

    companion object {
        fun newInstance() = StoryFragment()
    }

    private lateinit var viewModel: StoryViewModel
    private lateinit var listAdapter: StoryListAdapter
    private lateinit var imageView: ImageView
    private lateinit var headLine: TextView
    private lateinit var storyContent: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.story_fragment, container, false)
        storyContent = view.findViewById(R.id.story_list)
        imageView = view.findViewById(R.id.main_story_image)
        headLine = view.findViewById(R.id.story_head_line)
        initAdapter()
        // initViewModel()
        // fetchRetroInfo()
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

    private fun fetchRetroInfo() {
        viewModel.storyLiveData.observe(this,
            Observer<NewsStory> { t ->
                t?.apply {
                    headLine.text = t.headline
                    listAdapter.setAdapterList(t.contents)
                    Picasso.get().load(t.hereImage.imageUrl)
                        .placeholder(R.drawable.placeholder).into(imageView)
                }
            })
    }


    private fun initAdapter() {
        listAdapter = StoryListAdapter()
        storyContent.adapter = listAdapter

    }

}