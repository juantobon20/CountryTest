package com.juantobon20.countrytest.data.common.converters

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.juantobon20.countrytest.data.models.network.CurrencyResponse
import com.juantobon20.countrytest.data.models.network.ImageResponse
import java.lang.reflect.Type

class ImageUrlDeserialize : JsonDeserializer<String?> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String? {
        if (json == null || !json.isJsonObject) {
            return null
        }
        val jsonObject = json.asJsonObject
        if (jsonObject.isEmpty) {
            return null
        }

        val imageResponse : ImageResponse? = Gson().fromJson(jsonObject, ImageResponse::class.java)
        return imageResponse?.png ?: imageResponse?.svg
    }
}