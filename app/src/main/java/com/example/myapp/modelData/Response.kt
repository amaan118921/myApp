package com.example.myapp.modelData

data class Response(
    val articles: List<News>
)

data class News(
  val source: Source,  val title: String,  val description: String,  val url: String,  val urlToImage: String,
  val publishedAt: String, val content: String
)

data class Source (
    val name: String
        )