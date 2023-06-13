package io.github.c23pr487.lapakin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.model.LapakCard
import io.github.c23pr487.lapakin.model.UserPreference
import io.github.c23pr487.lapakin.repository.LapakCardRepository
import io.github.c23pr487.lapakin.repository.ProfileRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: LapakCardRepository, private val profileRepository: ProfileRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _lapaks = MutableLiveData<List<LapakCard>>()
    val lapaks: LiveData<List<LapakCard>> = _lapaks

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    init {
        getPreferenceLapak()
    }

    private fun getPreferenceLapak() {
        _isLoading.value = true
        profileRepository.getUserPreference(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        val userPreference = childSnapshot.getValue(UserPreference::class.java)
                        repository.getFilteredLapakByPreference(
                            formatLabel(userPreference?.label),
                            "kecamatan",
                            userPreference?.subdistrict,
                            "kota",
                            userPreference?.city,
                            "harga",
                            if (userPreference?.maxPrice == null) null else userPreference.maxPrice.toString()
                        ).enqueue(object: Callback<List<LapakCard>> {
                            override fun onResponse(
                                call: Call<List<LapakCard>>,
                                response: Response<List<LapakCard>>
                            ) {
                                if (!response.isSuccessful) {
                                    _message.value = R.string.problem_encountered_home
                                    _isLoading.value = false
                                    return
                                }
                                val lapaksResponse = response.body() ?: listOf()
                                if (lapaksResponse.isEmpty()) {
                                    _message.value = R.string.no_lapak_found
                                    _isLoading.value = false
                                    return
                                }
                                _lapaks.value = lapaksResponse
                                _isLoading.value = false
                            }

                            override fun onFailure(call: Call<List<LapakCard>>, t: Throwable) {
                                _message.value = R.string.problem_encountered_home
                            }

                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _message.value = R.string.problem_encountered_home
            }

        })
    }

    private fun getAllLapak() {
        _isLoading.value = true
        repository.getAllLapak().enqueue(object : Callback<List<LapakCard>> {
            override fun onResponse(call: Call<List<LapakCard>>, response: Response<List<LapakCard>>) {
                if (!response.isSuccessful) {
                    _message.value = R.string.problem_encountered_home
                    _isLoading.value = false
                    return
                }
                val lapaksResponse = response.body() ?: listOf()
                if (lapaksResponse.isEmpty()) {
                    _message.value = R.string.no_lapak_found
                    _isLoading.value = false
                    return
                }
                _lapaks.value = lapaksResponse
                _isLoading.value = false
            }

            override fun onFailure(call: Call<List<LapakCard>>, t: Throwable) {
                _message.value = R.string.problem_encountered_home
                _isLoading.value = false
            }
        })
    }

    private fun formatLabel(label: String): String? {
        return when (label) {
            "Belum Menentukan" -> null
            "Toko Kopi" ->
            else -> null

        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(LapakCardRepository(), ProfileRepository()) as T
            }
            throw IllegalArgumentException("modelClass not recognized ${modelClass.simpleName}")
        }
    }
}