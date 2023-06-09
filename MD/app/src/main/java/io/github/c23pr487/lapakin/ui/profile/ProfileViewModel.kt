package io.github.c23pr487.lapakin.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.github.c23pr487.lapakin.model.UserPreference

class ProfileViewModel(
    private val auth: FirebaseAuth,
    private val ref: DatabaseReference?
) : ViewModel() {

    init {
        val query = ref?.child("userPreference")?.orderByChild("id")?.equalTo(auth.currentUser?.uid)
        query?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (child in snapshot.children) {
                        val preference = child.getValue(UserPreference::class.java)
                        _selectedLabel.value = preference?.label
                        _selectedCity.value = preference?.city
                        _selectedSubdistrict.value = preference?.subdistrict
                        _maxPrice.value = preference?.maxPrice
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }

        })

    }


    private val _selectedLabel = MutableLiveData<String?>()
    val selectedLabel: LiveData<String?> = _selectedLabel

    private val _selectedCity = MutableLiveData<String?>()
    val selectedCity: LiveData<String?> = _selectedCity

    private val _selectedSubdistrict = MutableLiveData<String?>()
    val selectedSubdistrict: LiveData<String?> = _selectedSubdistrict

    private val _maxPrice = MutableLiveData<Int?>()
    val maxPrice: LiveData<Int?> = _maxPrice

    fun changeCity(label: String?) {
        _selectedCity.value = label
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val auth: FirebaseAuth, private val reference: DatabaseReference?) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                return ProfileViewModel(auth, reference) as T
            }
            throw IllegalArgumentException("modelClass not recognized: ${modelClass.simpleName}")
        }
    }

    companion object {
        const val TAG = "ProfileViewModel"
    }
}