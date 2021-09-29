package com.example.myapp.network

import com.example.myapp.modelData.WeatherResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



interface WeatherAPI  {
    @GET("data/2.5/onecall")
    suspend fun getWeather(@Query("lat")latitude: String, @Query("lon")longitude: String,
    @Query("exclude") exclude: String, @Query("appid") api: String): WeatherResponse
}

object WApi {
    val retrofitService : WeatherAPI by lazy { retrofit.create(WeatherAPI::class.java) }
}