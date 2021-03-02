package com.example.skycatnews.model.data

import com.example.skycatnews.model.json.CatNewsListSerializer
import kotlinx.serialization.*


@Serializable(with = CatNewsListSerializer::class)
abstract class News {
    abstract val type: String
}
