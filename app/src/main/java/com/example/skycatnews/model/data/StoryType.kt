package com.example.skycatnews.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("story")
data class StoryType(val id: String, override val type: String, val headline: String, val teaserText: String, val creationDate: String, val modifiedDate: String, val teaserImage: Image) : News() {
}