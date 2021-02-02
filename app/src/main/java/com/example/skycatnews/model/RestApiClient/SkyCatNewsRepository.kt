package com.example.skycatnews.model.RestApiClient

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.skycatnews.SkyCatNewsApplication
import com.example.skycatnews.di.SkyCatNewsDaggerComponent
import com.example.skycatnews.util.CatNews
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class SkyCatNewsRepository {
    lateinit var apiComponent: SkyCatNewsDaggerComponent
    var postInfoMutableList: MutableLiveData<CatNews> = MutableLiveData()

    @Inject
    lateinit var retrofit: Retrofit

    init {
        /* apiComponent =   DaggerAPIComponent
             .builder()
             .aPIModule(APIModule(APIURL.BASE_URL))
             .build()
         apiComponent.inject(this)*/

        var apiComponent: SkyCatNewsDaggerComponent = SkyCatNewsApplication.apiComponent
        apiComponent.inject(this)
    }


    fun fetchPostInfoList(): LiveData<CatNews> {

        var apiService: SkyCatNewsApiService = retrofit.create(SkyCatNewsApiService::class.java)
        var newsListInfo: Call<CatNews> = apiService.getNewsList()
        newsListInfo.enqueue(object : Callback<CatNews> {
            override fun onFailure(call: Call<CatNews>, t: Throwable) {
                Log.d("SkyCatNewsRepository", "Failed:::" + t.message)
            }

            override fun onResponse(call: Call<CatNews>, response: Response<CatNews>) {
                var newsInfoList = response.body()
                postInfoMutableList.value = newsInfoList

            }
        })

        return postInfoMutableList

    }
}
