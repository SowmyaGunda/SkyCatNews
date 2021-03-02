package com.example.skycatnews.model.data

import kotlinx.serialization.SerialName
import java.util.*
import kotlinx.serialization.Serializable


@Serializable
@SerialName("weblink")
data class WebLinkType(val id: String, override val type: String, val headline: String, val weblinkUrl: String, val creationDate: String, val modifiedDate: String, val teaserImage: Image) : News() {
}