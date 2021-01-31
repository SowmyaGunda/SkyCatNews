package com.example.skycatnews.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skycatnews.R
import com.example.skycatnews.viewmodel.CatNewsViewModel

class CatNewsFragment : Fragment() {

    companion object {
        fun newInstance() = CatNewsFragment()
    }

    private lateinit var viewModel: CatNewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.cat_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CatNewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}