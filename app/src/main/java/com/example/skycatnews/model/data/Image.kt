package com.example.skycatnews.model.data

import com.example.skycatnews.model.json.ImageSerializer
import kotlinx.serialization.Serializable


@Serializable(with = ImageSerializer::class)
data class Image(
        val imageUrl: String,
        val accessText: String
)