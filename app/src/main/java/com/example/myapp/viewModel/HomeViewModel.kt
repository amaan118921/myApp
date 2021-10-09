package com.example.myapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.modelData.Daily
import com.example.myapp.modelData.News
import com.example.myapp.modelData.WeatherImages
import com.example.myapp.network.ApiNews
import com.example.myapp.network.PIX
import com.example.myapp.network.WApi
import com.example.myapp.room.Dao
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {


    private val apiKey = "a76d9eafc96f47babd26b44d3f9936e7"
    private val weatherApiKey = "a28cd1bb0b8123d19b0e13e9964f379d"
    private val pixApiKey = "21841907-55025c06f5ffcb57a84b8e7d5"
    private lateinit var uri: String
    private lateinit var title: String
    private lateinit var timePublished: String
    private lateinit var source: String
    private lateinit var content: String

    var newsData  = MutableLiveData<List<News>>()
    var weatherData = MutableLiveData<List<Daily>>()
    var weatherImages = MutableLiveData<List<WeatherImages>>()
    private lateinit var lat: String
    private lateinit var lon: String

    fun setLat(lat: String) {
        this.lat = lat
    }
    fun setLon(lon: String) {
        this.lon = lon
    }

    fun getWeatherImages() {
        viewModelScope.launch {
            try {
                weatherImages.value = PIX.retrofitService.getImages(pixApiKey, "weather", "photo").hits
            }
            catch (e: Exception) {

            }
        }
    }

    fun getNewsResponse(query: String) {

        viewModelScope.launch {

            try {
                newsData.value =  ApiNews.retrofitService.getNews(query,  apiKey).articles
            }
            catch (e: Exception) {

            }

        }

    }




    fun getWeatherResponse(lat: String, lon: String) {
        viewModelScope.launch {

            try {
                weatherData.value = WApi.retrofitService.getWeather(lat, lon, "hourly", weatherApiKey).daily
            }
            catch (e: Exception) {

            }
        }
    }


    fun setTime(time: String) {
        this.timePublished = time
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun setSource(source: String) {
        this.source = source
    }

    fun setUri(uri: String) {
        this.uri = uri
    }

    fun setTitle(title: String) {
        this.title = title
    }


    fun getSource(): String {
        return source
    }
    fun getTitle(): String {
        return title
    }
    fun getTime(): String {
        return timePublished
    }

    fun getContent(): String {
        return content
    }

    fun getUri(): String {
        return uri
    }
}

