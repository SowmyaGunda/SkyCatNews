package com.example.skycatnews.util

import java.util.*
import kotlin.collections.ArrayList

data class NewsStory(val id: String, val headline: String, val hereImage: HeroImage, val creationDate: Date, val modifiedDateDate: Date, val contents: ArrayList<StoryContent>)

data class HeroImage(val imageUrl: String, val accessbilityText: String)

data class StoryContent(val type: String, val text: String, val url: String, val accesibilityText: String)