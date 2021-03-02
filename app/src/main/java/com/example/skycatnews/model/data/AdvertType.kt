package com.example.skycatnews.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("advert")
data class AdvertType(override val type: String, val url: String) : News() {
}