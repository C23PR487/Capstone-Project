package io.github.c23pr487.lapakin.ui.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import io.github.c23pr487.lapakin.model.UserPreference
import io.github.c23pr487.lapakin.repository.ProfileRepository

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    private val _selectedLabel = MutableLiveData<String?>()
    val selectedLabel: LiveData<String?> = _selectedLabel

    private val _selectedCity = MutableLiveData<String?>()
    val selectedCity: LiveData<String?> = _selectedCity

    private val _selectedSubdistrict = MutableLiveData<String?>()
    val selectedSubdistrict: LiveData<String?> = _selectedSubdistrict

    private val _maxPrice = MutableLiveData<Int?>()
    val maxPrice: LiveData<Int?> = _maxPrice

    private val _snackBarMessage = MutableLiveData<String>()
    val snackBarMessage: LiveData<String> = _snackBarMessage

    init {
        _selectedCity.value = null
        repository.getUserPreference(getListener())
    }

    private fun getListener() = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                for (childSnapshot in snapshot.children) {
                    val userPreference = childSnapshot.getValue(UserPreference::class.java)
                    _selectedLabel.value = userPreference?.label
                    if (selectedCity.value != userPreference?.city) {
                        _selectedCity.value = userPreference?.city
                    }
                    _selectedSubdistrict.value = userPreference?.subdistrict
                    _maxPrice.value = userPreference?.maxPrice
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            _snackBarMessage.value = error.message
        }

    }

    fun changeLabel(label: String?) {
        if (_selectedLabel.value == label) {
            return
        }
        _selectedLabel.value = label
        updatePreference()
    }

    fun changeCity(city: String?) {
        if (_selectedCity.value == city) {
            return
        }
        _selectedCity.value = city
        updatePreference()
    }

    fun changeSubdistrict(subdistrict: String?) {
        if (_selectedSubdistrict.value == subdistrict) {
            return
        }
        _selectedSubdistrict.value = subdistrict
        updatePreference()
    }

    fun changeMaxPrice(maxPrice: Int?) {
        if (_maxPrice.value == maxPrice) {
            return
        }
        _maxPrice.value = maxPrice
        updatePreference()
    }

    private fun updatePreference() {
        val userPreference = UserPreference(
            Firebase.auth.currentUser?.uid ?: "anonymous",
            _selectedLabel.value,
            _selectedCity.value,
            _selectedSubdistrict.value,
            _maxPrice.value
        )
        repository.updateUserPreference(userPreference)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                return ProfileViewModel(ProfileRepository(context)) as T
            }
            throw IllegalArgumentException("modelClass invalid: ${modelClass.simpleName}")
        }
    }

    companion object {
        const val TAG = "ProfileViewModel"
    }
}