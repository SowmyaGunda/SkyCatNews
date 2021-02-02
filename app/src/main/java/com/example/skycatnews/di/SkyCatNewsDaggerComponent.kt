package com.example.skycatnews.di

import com.example.skycatnews.AppModule
import com.example.skycatnews.model.RestApiClient.SkyCatNewsModule
import com.example.skycatnews.model.RestApiClient.SkyCatNewsRepository
import com.example.skycatnews.ui.CatNewsFragment
import com.example.skycatnews.ui.StoryFragment
import com.example.skycatnews.viewmodel.CatNewsViewModel
import com.example.skycatnews.viewmodel.ViewModelFactory
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class,SkyCatNewsModule::class])
interface SkyCatNewsDaggerComponent {
    fun inject(retrofitRepository: SkyCatNewsRepository)
    fun inject(retroViewModel: CatNewsViewModel)
    fun inject(catNewsFragment: CatNewsFragment)
    fun inject(storyFragment: StoryFragment)
    fun inject(viewModelFactory: ViewModelFactory)
}