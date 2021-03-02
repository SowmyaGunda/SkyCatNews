package com.example.skycatnews.model.data

import com.example.skycatnews.model.json.NewsFeedSerializer
import kotlinx.serialization.Serializable

@Serializable(with = NewsFeedSerializer::class)
data class CatNews(val title: String, val data: List<News>)