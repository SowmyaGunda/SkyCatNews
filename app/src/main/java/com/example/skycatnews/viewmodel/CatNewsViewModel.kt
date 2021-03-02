package com.example.skycatnews.viewmodel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.example.skycatnews.model.data.CatNews
import com.example.skycatnews.model.retrofit.SkyCatNewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

class CatNewsViewModel(apiService: SkyCatNewsApiService) : ViewModel() {
    @VisibleForTesting
    var skyCatNewsApiService: SkyCatNewsApiService = apiService
    val newsLiveData: MutableLiveData<NewsListResponse> = MutableLiveData()

    fun fetchCatNewsFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            val newsListInfo: Call<CatNews> = skyCatNewsApiService.getNewsList()
            newsListInfo.enqueue(object : Callback<CatNews> {
                override fun onFailure(call: Call<CatNews>, t: Throwable) {
                    Log.d("CatNewsViewModel", "Failed:::" + t.message)
                    newsLiveData.postValue(NewsListResponse.Failure(t as Exception))
                }

                override fun onResponse(call: Call<CatNews>, response: Response<CatNews>) {
                    if (response.isSuccessful) {
                        newsLiveData.postValue(NewsListResponse.Success(response.body()!!))


                    } else {
                        newsLiveData.postValue(NewsListResponse.Failure(HttpException(response)))
                    }

                }
            })
        }
    }

    sealed class NewsListResponse {
        data class Success(val catNews: CatNews) : NewsListResponse()
        data class Failure(val exception: Exception) : NewsListResponse()
    }
}