package io.github.c23pr487.lapakin.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.databinding.ActivityPreferencesBinding
import io.github.c23pr487.lapakin.model.UserPreference
import io.github.c23pr487.lapakin.ui.MainActivity

class PreferencesActivity : AppCompatActivity() {

    private val binding: ActivityPreferencesBinding by lazy {
        ActivityPreferencesBinding.inflate(layoutInflater)
    }

    private val viewModel: PreferenceViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    private val reference: DatabaseReference by lazy {
        Firebase.database(getString(R.string.database_url)).reference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        fillInDropdowns()
        viewModelListen()
        setUpButtonFunctionality()
    }

    private fun fillInDropdowns() {
        val labels = arrayOf(
            resources.getString(R.string.label_null),
            resources.getString(R.string.label_coffee),
            resources.getString(R.string.label_clothes),
        )
        (binding.textViewLabel as MaterialAutoCompleteTextView).setSimpleItems(labels)

        val cities = arrayOf(
            resources.getString(R.string.city_null),
            resources.getString(R.string.city_north_jakarta),
            resources.getString(R.string.city_south_jakarta)
        )
        (binding.textViewCity as MaterialAutoCompleteTextView).run {
            setSimpleItems(cities)
            doOnTextChanged { text, _, _, _ ->
                if (text == resources.getString(R.string.city_null)) {
                    viewModel.changeCity(null)
                } else {
                    viewModel.changeCity(text.toString())
                }
            }
        }

        val subdistricts = arrayOf(
            resources.getString(R.string.subdistrict_null),
        )

        (binding.textViewSubdistrict as MaterialAutoCompleteTextView).setSimpleItems(subdistricts)
    }

    private fun viewModelListen() {
        viewModel.selectedCity.observe(this) { city ->
            val subdistricts = when (city) {
                getString(R.string.city_north_jakarta) -> {
                    arrayOf(
                        resources.getString(R.string.subdistrict_null),
                        resources.getString(R.string.subdistrict_kelapa_gading),
                        resources.getString(R.string.subdistrict_pademangan),
                        resources.getString(R.string.subdistrict_penjaringan)
                    )
                }

                getString(R.string.city_south_jakarta) -> {
                    arrayOf(
                        resources.getString(R.string.subdistrict_null),
                        resources.getString(R.string.subdistrict_kebayoran_baru),
                        resources.getString(R.string.subdistrict_pancoran),
                        resources.getString(R.string.subdistrict_tebet),
                    )
                }

                else -> {
                    arrayOf(
                        resources.getString(R.string.subdistrict_null),
                    )
                }
            }

            (binding.textViewSubdistrict as MaterialAutoCompleteTextView).run {
                setSimpleItems(subdistricts)
                text = null
            }
        }
    }

    private fun setUpButtonFunctionality() {
        binding.buttonSave.setOnClickListener {
            if (!isInputsFilled()) {
                Snackbar.make(
                    this,
                    binding.root,
                    getString(R.string.input_empty_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            MaterialAlertDialogBuilder(this)
                .setMessage(getString(R.string.preference_save_message))
                .setNegativeButton(getString(R.string.logout_negative_button)) {dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(getString(R.string.save_buttom_message)) { dialog, _ ->
                    savePreference()
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun isInputsFilled(): Boolean {
        val label = binding.textViewLabel.text.toString()
        val city = binding.textViewCity.text.toString()
        val subdistrict = binding.textViewSubdistrict.text.toString()

        return label.isNotEmpty() &&
                city.isNotEmpty() &&
                subdistrict.isNotEmpty()
    }

    private fun savePreference() {
        val userPreference = getUserPreference()
        Log.i(TAG, userPreference.toString())

        reference.child("userPreference").push().setValue(userPreference)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun getUserPreference(): UserPreference {
        var label: String? = binding.textViewLabel.text.toString()
        if (label == getString(R.string.label_null)) {
            label = null
        }

        var city: String? = binding.textViewCity.text.toString()
        if (city == getString(R.string.city_null)) {
            city = null
        }

        var subdistrict: String? = binding.textViewSubdistrict.text.toString()
        if (subdistrict == getString(R.string.subdistrict_null)) {
            subdistrict = null
        }

        val maxPrice = binding.editTextMaxPrice.text.toString()

        val currentUser = Firebase.auth.currentUser

        return UserPreference(currentUser?.uid ?: "anonymous", label, city, subdistrict, maxPrice.toIntOrNull())
    }

    companion object {
        const val TAG = "PreferencesActivity"
    }
}