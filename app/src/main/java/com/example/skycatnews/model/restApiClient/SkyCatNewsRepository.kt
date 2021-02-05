package com.example.skycatnews.model.restApiClient

import com.example.skycatnews.SkyCatNewsApplication
import com.example.skycatnews.di.SkyCatNewsDaggerComponent
import retrofit2.*
import javax.inject.Inject

class SkyCatNewsRepository {
    private val apiComponent: SkyCatNewsDaggerComponent = SkyCatNewsApplication.apiComponent

    @Inject
    lateinit var retrofit: Retrofit

    init {
        apiComponent.inject(this)
    }

    fun fetchApiService(): SkyCatNewsApiService {
        return retrofit.create(SkyCatNewsApiService::class.java)
    }
}
