package io.github.c23pr487.lapakin.repository

import io.github.c23pr487.lapakin.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("/v2/top-headlines?country=id&category=business")
    suspend fun getNews(
        @Query("page") page: Int
    ): NewsResponse
}