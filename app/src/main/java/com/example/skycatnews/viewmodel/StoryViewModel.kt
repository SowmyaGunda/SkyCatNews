package com.example.skycatnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skycatnews.model.RestApiClient.SkyCatNewsRepository
import com.example.skycatnews.util.NewsStory

class StoryViewModel(retroFitRepository: SkyCatNewsRepository) : ViewModel() {
    lateinit var retrofitRepository: SkyCatNewsRepository
    var storyLiveData: LiveData<NewsStory> = MutableLiveData()

    init {
        this.retrofitRepository = retrofitRepository
        fetchStoryFromRepository()
    }

    fun fetchStoryFromRepository() {
        storyLiveData = retrofitRepository.fetchNewsStory()
    }
}