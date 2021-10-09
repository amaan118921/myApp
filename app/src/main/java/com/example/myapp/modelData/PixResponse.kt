package com.example.myapp.modelData

import com.squareup.moshi.Json

data class PixResponse(
    val hits: List<WeatherImages>
)

data class WeatherImages(
    @Json(name = "webformatURL") val webFormatURL: String, @Json(name="largeImageURL") val largeImageURL: String
)