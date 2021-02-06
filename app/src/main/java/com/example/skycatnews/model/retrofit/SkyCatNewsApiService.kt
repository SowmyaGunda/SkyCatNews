package com.example.skycatnews.model.retrofit

import com.example.skycatnews.model.data.CatNews
import com.example.skycatnews.model.data.NewsStory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SkyCatNewsApiService {
    @GET("news-list")
    fun getNewsList(): Call<CatNews>

    @GET("story/{id}")
    fun getStory(@Path("id") id:String): Call<NewsStory>
}