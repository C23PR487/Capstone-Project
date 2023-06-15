package io.github.c23pr487.lapakin.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(): ApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hapi-fiv3monpwa-et.a.run.app")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun getNewsApiService(): NewsApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder().addHeader(
                    "Authorization", "Bearer f627882ea9524928b63c96cb32de2721"
                ).build()
                chain.proceed(newRequest)
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NewsApiService::class.java)
    }
}