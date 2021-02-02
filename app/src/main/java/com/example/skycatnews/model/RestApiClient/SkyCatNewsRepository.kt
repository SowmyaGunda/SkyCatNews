package com.example.skycatnews.model.RestApiClient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.skycatnews.SkyCatNewsApplication
import com.example.skycatnews.di.SkyCatNewsDaggerComponent
import com.example.skycatnews.util.CatNews
import com.example.skycatnews.util.NewsStory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class SkyCatNewsRepository {
    lateinit var apiComponent: SkyCatNewsDaggerComponent
    var catNewsMutableList: MutableLiveData<CatNews> = MutableLiveData()
    var storyMutableList: MutableLiveData<NewsStory> = MutableLiveData()

    @Inject
    lateinit var retrofit: Retrofit

    init {
        var apiComponent: SkyCatNewsDaggerComponent = SkyCatNewsApplication.apiComponent
        apiComponent.inject(this)
    }


    fun fetchCatNews(): LiveData<CatNews> {

        var apiService: SkyCatNewsApiService = retrofit.create(SkyCatNewsApiService::class.java)
        var newsListInfo: Call<CatNews> = apiService.getNewsList()
        newsListInfo.enqueue(object : Callback<CatNews> {
            override fun onFailure(call: Call<CatNews>, t: Throwable) {
                Log.d("SkyCatNewsRepository", "Failed:::" + t.message)
            }

            override fun onResponse(call: Call<CatNews>, response: Response<CatNews>) {
                var newsInfoList = response.body()
                catNewsMutableList.value = newsInfoList

            }
        })
        return catNewsMutableList
    }

    fun fetchNewsStory(): LiveData<NewsStory> {

        var apiService: SkyCatNewsApiService = retrofit.create(SkyCatNewsApiService::class.java)
        var story: Call<NewsStory> = apiService.getStory()
        story.enqueue(object : Callback<NewsStory> {
            override fun onFailure(call: Call<NewsStory>, t: Throwable) {
                Log.d("SkyCatNewsRepository", "Failed:::" + t.message)
            }

            override fun onResponse(call: Call<NewsStory>, response: Response<NewsStory>) {
                var newsInfoList = response.body()
                storyMutableList.value = newsInfoList

            }
        })
        return storyMutableList
    }
}
