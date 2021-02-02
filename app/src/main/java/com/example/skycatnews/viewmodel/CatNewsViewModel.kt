package com.example.skycatnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skycatnews.model.RestApiClient.SkyCatNewsRepository
import com.example.skycatnews.util.CatNews

class CatNewsViewModel(retroFitRepository: SkyCatNewsRepository) : ViewModel() {
    lateinit var retrofitRepository:SkyCatNewsRepository
    var newsLiveData: LiveData<CatNews> = MutableLiveData()

    init {
        this.retrofitRepository  = retrofitRepository
        fetchCatNewsFromRepository()
    }

    fun fetchCatNewsFromRepository(){
       // newsLiveData =  retrofitRepository.fetchCatNews()
    }
}