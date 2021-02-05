package com.example.skycatnews

import android.app.Application
import android.content.Context
import com.example.skycatnews.di.DaggerSkyCatNewsDaggerComponent
import com.example.skycatnews.di.SkyCatNewsDaggerComponent
import com.example.skycatnews.model.restApiClient.SkyCatNewsModule

class SkyCatNewsApplication : Application(){
    companion object {
        var ctx: Context? = null
        lateinit var apiComponent:SkyCatNewsDaggerComponent
    }
    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        apiComponent = initDaggerComponent()

    }

    fun getDaggerComponent(): SkyCatNewsDaggerComponent {
        return apiComponent
    }

    fun initDaggerComponent():SkyCatNewsDaggerComponent{
        apiComponent =   DaggerSkyCatNewsDaggerComponent
            .builder()
            .skyCatNewsModule(SkyCatNewsModule())
            .build()
        return  apiComponent

    }
}