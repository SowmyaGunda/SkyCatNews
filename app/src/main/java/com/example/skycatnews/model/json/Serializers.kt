package com.example.skycatnews.model.json

import com.example.skycatnews.model.data.*
import kotlinx.serialization.*
import kotlinx.serialization.json.JsonInput
import kotlinx.serialization.json.JsonObject


object NewsFeedSerializer : KSerializer<CatNews> {
    override val descriptor: SerialDescriptor = NewsFeedJson.serializer().descriptor

    override fun deserialize(decoder: Decoder): CatNews {
        val surrogate = decoder.decodeSerializableValue(NewsFeedJson.serializer())

        return CatNews(
            surrogate.title,
            surrogate.data.contents
        )
    }

    override fun serialize(encoder: Encoder, value: CatNews) {
        val surrogate = NewsFeedJson(
            value.title,
            Embedded(value.data)
        )

        encoder.encodeSerializableValue(NewsFeedJson.serializer(), surrogate)
    }
}

object CatNewsListSerializer : KSerializer<News> {
    override val descriptor: SerialDescriptor
        get() = SerialDescriptor("type")

    override fun deserialize(decoder: Decoder): News {
        val input = decoder as? JsonInput
                ?: throw SerializationException("Expected JsonInput for ${decoder::class}")
        val jsonObject = input.decodeJson() as? JsonObject
                ?: throw SerializationException("Expected JsonObject for ${input.decodeJson()::class}")

        val type = jsonObject.getPrimitive("type").content

        return when (type) {
            "story" -> decoder.json.parse(StoryType.serializer(), jsonObject.toString())
            "weblink" -> decoder.json.parse(WebLinkType.serializer(), jsonObject.toString())
            "advert" -> decoder.json.parse(AdvertType.serializer(), jsonObject.toString())
            else -> throw SerializationException("Type $type not supported")
        }
    }


    override fun serialize(encoder: Encoder, value: News) {
        when (value) {
            is StoryType -> encoder.encode(StoryType.serializer(), value)
            is WebLinkType -> encoder.encode(WebLinkType.serializer(), value)
            is AdvertType -> encoder.encode(AdvertType.serializer(), value)
        }
    }

}

object ContentTypeSerializer : KSerializer<StoryContentType> {
    override val descriptor: SerialDescriptor
        get() = SerialDescriptor("type")

    override fun deserialize(decoder: Decoder): StoryContentType {
        val input = decoder as? JsonInput
                ?: throw SerializationException("Expected JsonInput for ${decoder::class}")
        val jsonObject = input.decodeJson() as? JsonObject
                ?: throw SerializationException("Expected JsonObject for ${input.decodeJson()::class}")

        val type = jsonObject.getPrimitive("type").content

        return when (type) {
            "paragraph" -> decoder.json.parse(StoryContentTypeTypeParagraph.serializer(), jsonObject.toString())
            "image" -> decoder.json.parse(StoryContentTypeTypeImage.serializer(), jsonObject.toString())
            else -> throw SerializationException("Type $type not supported")
        }
    }


    override fun serialize(encoder: Encoder, value: StoryContentType) {
        when (value) {
            is StoryContentTypeTypeParagraph -> encoder.encode(StoryContentTypeTypeParagraph.serializer(), value)
            is StoryContentTypeTypeImage -> encoder.encode(StoryContentTypeTypeImage.serializer(), value)
        }
    }

}

object ImageSerializer : KSerializer<Image> {
    override val descriptor: SerialDescriptor = NewsFeedTeaserImage.serializer().descriptor

    override fun deserialize(decoder: Decoder): Image {
        val surrogate = decoder.decodeSerializableValue(NewsFeedTeaserImage.serializer())

        return Image(
            surrogate._links.url.href,
            surrogate.accessibilityText
        )
    }

    override fun serialize(encoder: Encoder, value: Image) {
        val links = NewsFeedImageLinks(
            NewsFeedImageUrl(value.imageUrl)
        )
        val surrogate = NewsFeedTeaserImage(
            links,
            value.accessText
        )
        encoder.encodeSerializableValue(NewsFeedTeaserImage.serializer(), surrogate)
    }
}