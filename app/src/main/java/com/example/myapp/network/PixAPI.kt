package com.example.myapp.network

import com.example.myapp.modelData.PixResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://pixabay.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface PixAPI  {

    @GET("api")
    suspend fun getImages(@Query("key")key: String, @Query("q") query: String,
                          @Query("image_type") imageType: String) : PixResponse

}

object PIX {
    val retrofitService : PixAPI by lazy { retrofit.create(PixAPI::class.java) }
}