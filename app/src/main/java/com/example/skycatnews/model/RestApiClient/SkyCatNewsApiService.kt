package com.example.skycatnews.model.RestApiClient

import com.example.skycatnews.util.CatNews
import com.example.skycatnews.util.NewsStory
import retrofit2.Call
import retrofit2.http.GET

interface SkyCatNewsApiService {
    @GET("news-list")
    fun getNewsList(): Call<CatNews>

    @GET("story/<id>")
    fun getStory(): Call<NewsStory>
}