package com.example.skycatnews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.skycatnews.model.retrofit.SkyCatNewsApiService
import com.example.skycatnews.viewmodel.CatNewsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit


@RunWith(MockitoJUnitRunner::class)
class CatNewsViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: SkyCatNewsApiService




    lateinit var catNewsViewModel: CatNewsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        catNewsViewModel = CatNewsViewModel(apiService)
    }

    @Test
    fun verifyApiInvoking(){
        catNewsViewModel.fetchCatNewsFromRepository()
        verify(apiService).getNewsList()
    }

}