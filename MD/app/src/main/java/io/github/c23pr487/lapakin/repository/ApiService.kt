package io.github.c23pr487.lapakin.repository

import io.github.c23pr487.lapakin.model.Lapak
import io.github.c23pr487.lapakin.model.LapakCard
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/stalls/all")
    fun getAllLapak() : Call<List<LapakCard>>

    @GET("/stalls/filtered")
    fun getFilteredLapak(
        @Query("label") label: String? = null,
        @Query("prior1") prior1: String? = null,
        @Query("value1") value1: String? = null,
        @Query("prior2") prior2: String? = null,
        @Query("value2") value2: String? = null,
        @Query("prior3") prior3: String? = null,
        @Query("value3") value3: String? = null,
    ): Call<List<LapakCard>>

    @GET("/stalls/{id}/details")
    fun getLapakDetails(@Path("id") id: String) : Call<List<Lapak>>
}