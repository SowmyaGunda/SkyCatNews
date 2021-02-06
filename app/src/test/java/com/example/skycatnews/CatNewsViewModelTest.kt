package com.example.skycatnews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.skycatnews.model.retrofit.SkyCatNewsApiService
import com.example.skycatnews.model.retrofit.SkyCatNewsRepository
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
    @Mock
    lateinit var retrofit: Retrofit
    @Mock
    @InjectMocks
    lateinit var skyCatNewsRepository: SkyCatNewsRepository




    lateinit var catNewsViewModel: CatNewsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        catNewsViewModel = CatNewsViewModel(skyCatNewsRepository)
    }

    @Test
    fun verifyApiInvoking(){

        Mockito.`when`(skyCatNewsRepository.fetchApiService()).thenReturn(apiService)
        catNewsViewModel.fetchCatNewsFromRepository()
        verify(apiService).getNewsList()
    }

}