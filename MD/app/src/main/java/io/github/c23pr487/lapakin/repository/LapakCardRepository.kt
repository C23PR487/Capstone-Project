package io.github.c23pr487.lapakin.repository

import io.github.c23pr487.lapakin.model.LapakCard
import retrofit2.Call

class LapakCardRepository {
    private val service: ApiService = ApiConfig.getApiService()

    fun getAllLapak(): Call<List<LapakCard>> {
        return service.getAllLapak()
    }

    fun getFilteredLapakByPreference(
        label: String? = null,
        prior1: String? = null,
        value1: String? = null,
        prior2: String? = null,
        value2: String? = null,
        prior3: String? = null,
        value3: String? = null,
    ): Call<List<LapakCard>> {
        return service.getFilteredLapak(
            label, prior1, value1, prior2, value2, prior3, value3
        )
    }
}