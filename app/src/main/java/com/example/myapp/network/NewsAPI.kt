package com.example.myapp.network

import com.example.myapp.modelData.Response
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


private const val BASE_URL = "https://newsapi.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



interface NewsAPI {

    @GET("v2/everything")
    suspend fun getNews(@Query("q")query: String,
    @Query("apiKey") apiKey: String): Response
    
}

object ApiNews {
    val retrofitService: NewsAPI by lazy { retrofit.create(NewsAPI::class.java) }
}