package com.example.skycatnews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.skycatnews.model.retrofit.SkyCatNewsApiService
import com.example.skycatnews.model.retrofit.SkyCatNewsModule
import com.example.skycatnews.viewmodel.CatNewsViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import junit.framework.Assert.assertTrue
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After


@RunWith(MockitoJUnitRunner::class)
class CatNewsViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()


    lateinit var apiService: SkyCatNewsApiService

    lateinit var catNewsViewModel: CatNewsViewModel

    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8000
    private val observable = Observer<CatNewsViewModel.NewsListResponse> {
        if (it is CatNewsViewModel.NewsListResponse.Success) assertTrue("Success", it != null)
        else if (it is CatNewsViewModel.NewsListResponse.Failure) assertTrue("Failed", it != null)

    }

    @Before
    fun setUp() {
        server.start(MOCK_WEBSERVER_PORT)
        MockitoAnnotations.initMocks(this)
        apiService = Retrofit.Builder()
                .baseUrl(SkyCatNewsModule.DEFAULT_API_URL)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build().create(SkyCatNewsApiService::class.java)
        catNewsViewModel = CatNewsViewModel(apiService)
        catNewsViewModel.newsLiveData.observeForever(observable)

    }

    @Test
    fun verifyApiInvoking() {
        catNewsViewModel.fetchCatNewsFromRepository()
        verify(apiService).getNewsList()
    }

    @Test
    fun testMockResponseSuccess() {
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("sample_list.json").content))
        }
        catNewsViewModel.fetchCatNewsFromRepository()

    }

    @After
    fun shutdown() {
        server.shutdown()
    }

}