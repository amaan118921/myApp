package com.example.myapp.modelData

import com.squareup.moshi.Json

data class WeatherResponse (
    val daily: List<Daily>
        )

data class Daily (
 @Json(name="temp")  val dailyTemp: DailyTemp
        )


data class DailyTemp (
    @Json(name="day")  val dayTemp: String,
        )