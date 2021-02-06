package com.example.skycatnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skycatnews.di.DaggerSkyCatNewsDaggerComponent
import com.example.skycatnews.di.SkyCatNewsDaggerComponent
import com.example.skycatnews.model.retrofit.SkyCatNewsApiService
import com.example.skycatnews.model.retrofit.SkyCatNewsModule
import retrofit2.Retrofit
import javax.inject.Inject

class ViewModelFactory : ViewModelProvider.Factory {
    lateinit var apiComponent: SkyCatNewsDaggerComponent
    @Inject
    lateinit var retrofit: Retrofit


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        initDaggerComponent()
        if (modelClass.isAssignableFrom(CatNewsViewModel::class.java)) {
            return CatNewsViewModel(fetchApiService()) as T
        }
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            return StoryViewModel(fetchApiService()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
    fun fetchApiService(): SkyCatNewsApiService {
        return retrofit.create(SkyCatNewsApiService::class.java)
    }

    fun initDaggerComponent() {
        apiComponent = DaggerSkyCatNewsDaggerComponent
            .builder()
            .skyCatNewsModule(SkyCatNewsModule())
            .build()
        apiComponent.inject(this)
    }
}