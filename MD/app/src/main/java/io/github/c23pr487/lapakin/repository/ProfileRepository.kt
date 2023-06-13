package io.github.c23pr487.lapakin.repository

import android.content.Context
import android.provider.SyncStateContract.Helpers.update
import com.bumptech.glide.disklrucache.DiskLruCache.Value
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.model.UserPreference

class ProfileRepository(context: Context? = null) {
    private val db = Firebase.database((context?.resources?.getString(R.string.database_url)) ?: "https://lapakin-d5982-default-rtdb.asia-southeast1.firebasedatabase.app").reference

    fun getUserPreference(listener: ValueEventListener) {
        val id = Firebase.auth.currentUser?.uid
        val query = db.child("userPreference").orderByChild("id").equalTo(id ?: "anonymous")
        query.addValueEventListener(listener)
    }

    fun updateUserPreference(userPreference: UserPreference) {
        val id = Firebase.auth.currentUser?.uid
        val query = db.child("userPreference").orderByChild("id").equalTo(id ?: "anonymous")
        query.addListenerForSingleValueEvent(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        val key = childSnapshot.key
                        updatePreference(userPreference, key)
                        query.removeEventListener(this)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }

        })
    }

    private fun updatePreference(userPreference: UserPreference, key: String?) {
        val childUpdates = hashMapOf<String, Any?>(
            "/userPreference/$key" to userPreference.toMap()
        )
        db.updateChildren(childUpdates)
    }
}