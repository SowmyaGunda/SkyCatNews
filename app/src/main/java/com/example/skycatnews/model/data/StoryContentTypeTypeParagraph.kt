package com.example.skycatnews.model.data

import kotlinx.serialization.Serializable

@Serializable
data class StoryContentTypeTypeParagraph(override val type: String, val text: String) : StoryContentType()