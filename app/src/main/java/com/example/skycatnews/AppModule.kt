package com.example.skycatnews

import dagger.Module
import dagger.Provides

@Module
class AppModule constructor(myRetroApplication: SkyCatNewsApplication){

    var myRetroApplication:SkyCatNewsApplication

    init {
        this.myRetroApplication = myRetroApplication
    }

    @Provides
    fun provideMyRetroApplication():SkyCatNewsApplication{
        return myRetroApplication
    }
}