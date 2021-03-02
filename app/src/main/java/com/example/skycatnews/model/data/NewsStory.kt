package com.example.skycatnews.model.data

import kotlinx.serialization.Serializable
import kotlin.collections.ArrayList

@Serializable
data class NewsStory(val id: String, val headline: String, val heroImage: HeroImage, val creationDate: String, val modifiedDate: String, val contents: ArrayList<StoryContentType>)

@Serializable
data class HeroImage(val imageUrl: String, val accessibilityText: String)
