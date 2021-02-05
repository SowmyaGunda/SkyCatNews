package com.example.skycatnews.util

import java.util.*
import kotlin.collections.ArrayList

data class CatNews(val title: String, val data: ArrayList<NewsHeadline>)

data class TeaserImageUrl(val href: String, val templated: Boolean, val type: String)

data class TeaserImageLinks(val url: TeaserImageUrl)

data class TeaserImage(val _links: TeaserImageLinks, val accessibilityText: String)

data class NewsHeadline(val id: Int, val type: String, val headline: String, val teaserText: String, val creationDate: Date, val modifiedDate: Date, val teaserImage: TeaserImage, val weblinkUrl: String,val url:String)