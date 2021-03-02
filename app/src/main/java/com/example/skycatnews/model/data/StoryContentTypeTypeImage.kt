package com.example.skycatnews.model.data

import kotlinx.serialization.Serializable

@Serializable
data class StoryContentTypeTypeImage(override val type: String, val url: String, val accessibilityText: String) : StoryContentType()