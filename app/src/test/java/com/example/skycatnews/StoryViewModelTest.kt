package com.example.skycatnews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.skycatnews.model.retrofit.SkyCatNewsApiService
import com.example.skycatnews.viewmodel.StoryViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: SkyCatNewsApiService


    lateinit var storyViewModel: StoryViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        storyViewModel = StoryViewModel(apiService)
    }

    @Test
    fun verifyApiInvoking() {
        storyViewModel.fetchStoryFromRepository("1")
        verify(apiService).getStory("1")
    }

}