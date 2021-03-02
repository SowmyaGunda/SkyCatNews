package com.example.skycatnews.model.data

import com.example.skycatnews.model.json.ContentTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ContentTypeSerializer::class)
abstract class StoryContentType() {
    abstract val type: String
}