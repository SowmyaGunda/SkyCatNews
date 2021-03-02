package com.example.skycatnews.model.json

import com.example.skycatnews.model.data.News
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("CatNews")
internal data class NewsFeedJson(val title: String, val data: Embedded) {}

@Serializable
@SerialName("CatNewsList")
internal data class Embedded(val contents: List<News>)


@Serializable
@SerialName("Image")
internal data class NewsFeedTeaserImage(val _links: NewsFeedImageLinks, val accessibilityText: String)

@Serializable
internal data class NewsFeedImageLinks(val url: NewsFeedImageUrl)

@Serializable
internal data class NewsFeedImageUrl(val href: String, val templated: Boolean = true, val type: String = "image/jpeg")