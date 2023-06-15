package io.github.c23pr487.lapakin.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.model.Lapak
import io.github.c23pr487.lapakin.repository.LapakDetailsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LapakDetailsViewModel(private val repository: LapakDetailsRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _lapak = MutableLiveData<Lapak>()
    val lapak: LiveData<Lapak> = _lapak

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    init {
        _isLoading.value = true
        repository.getLapakDetails().enqueue(object : Callback<List<Lapak>> {
            override fun onResponse(call: Call<List<Lapak>>, response: Response<List<Lapak>>) {
                if (!response.isSuccessful) {
                    _message.value = R.string.problem_encountered_home
                    _isLoading.value = false
                    return
                }
                val lapakResponse = response.body() ?: listOf()
                if (lapakResponse.isEmpty()) {
                    _message.value = R.string.no_lapak_found
                    _isLoading.value = false
                    return
                }
                _lapak.value = lapakResponse[0]
            }

            override fun onFailure(call: Call<List<Lapak>>, t: Throwable) {
                _message.value = R.string.problem_encountered_home
                _isLoading.value = false
            }

        })
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val id: String) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LapakDetailsViewModel::class.java)) {
                return LapakDetailsViewModel(LapakDetailsRepository(id)) as T
            }
            throw IllegalArgumentException("modelClass not recognized ${modelClass.simpleName}")
        }
    }
}