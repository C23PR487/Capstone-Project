package io.github.c23pr487.lapakin.repository

import io.github.c23pr487.lapakin.model.Lapak
import retrofit2.Call

class LapakDetailsRepository(private val id: String) {
    private val service = ApiConfig.getApiService()

    fun getLapakDetails(): Call<List<Lapak>> {
        return service.getLapakDetails(id)
    }
}