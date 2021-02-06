package com.example.skycatnews.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skycatnews.model.data.NewsStory
import com.example.skycatnews.model.retrofit.SkyCatNewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

class StoryViewModel(apiService: SkyCatNewsApiService) : ViewModel() {
    private val skyCatNewsApiService: SkyCatNewsApiService = apiService
    val storyLiveData: MutableLiveData<NewsStoryResponse> = MutableLiveData()

    fun fetchStoryFromRepository(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val story: Call<NewsStory> = skyCatNewsApiService.getStory(id)
            story.enqueue(object : Callback<NewsStory> {
                override fun onFailure(call: Call<NewsStory>, t: Throwable) {
                    Log.d("SkyCatNewsRepository", "Failed:::" + t.message)
                    storyLiveData.postValue(NewsStoryResponse.Failure(t as Exception))
                }

                override fun onResponse(call: Call<NewsStory>, response: Response<NewsStory>) {
                    if (response.isSuccessful) {
                        storyLiveData.postValue(NewsStoryResponse.Success(response.body()!!))

                    } else {
                        storyLiveData.postValue(NewsStoryResponse.Failure(HttpException(response)))
                    }

                }
            })
        }
    }

    sealed class NewsStoryResponse {
        data class Success(val newsStory: NewsStory) : NewsStoryResponse()
        data class Failure(val exception: Exception) : NewsStoryResponse()
    }
}