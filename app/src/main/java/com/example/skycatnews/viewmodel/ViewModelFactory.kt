package com.example.skycatnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skycatnews.SkyCatNewsApplication
import com.example.skycatnews.di.DaggerSkyCatNewsDaggerComponent
import com.example.skycatnews.di.SkyCatNewsDaggerComponent
import com.example.skycatnews.model.RestApiClient.SkyCatNewsModule
import com.example.skycatnews.model.RestApiClient.SkyCatNewsRepository
import javax.inject.Inject

class ViewModelFactory : ViewModelProvider.Factory {
    lateinit var apiComponent: SkyCatNewsDaggerComponent
    @Inject
    lateinit var retrofitRepository: SkyCatNewsRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        initDaggerComponent()
        apiComponent.inject(this)
        if (modelClass.isAssignableFrom(CatNewsViewModel::class.java)) {
            return CatNewsViewModel(retrofitRepository) as T
        }
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            return StoryViewModel(retrofitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    fun initDaggerComponent() {
        apiComponent = DaggerSkyCatNewsDaggerComponent
            .builder()
            .skyCatNewsModule(SkyCatNewsModule())
            .build()
        apiComponent.inject(this)
    }
}