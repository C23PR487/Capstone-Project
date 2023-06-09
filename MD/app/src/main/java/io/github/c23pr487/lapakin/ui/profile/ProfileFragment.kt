package io.github.c23pr487.lapakin.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.databinding.FragmentProfileBinding
import io.github.c23pr487.lapakin.ui.auth.AuthenticationActivity
import io.github.c23pr487.lapakin.ui.auth.PreferenceViewModel
import io.github.c23pr487.lapakin.utils.loadImageWithUri

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding as FragmentProfileBinding

    private val user by lazy {
        Firebase.auth.currentUser
    }

    private val viewModel: ProfileViewModel by viewModels {
        try {
            ProfileViewModel.Factory(
                Firebase.auth,
                Firebase.database(getString(R.string.database_url)).reference
            )
        } catch (e: DatabaseException) {
            ProfileViewModel.Factory(
                Firebase.auth,
                null
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDropdown()
        updateUI()
        setUpLogoutButton()
        viewModelListen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpDropdown() {
        val labels = arrayOf(
            resources.getString(R.string.label_null),
            resources.getString(R.string.label_clothing),
            resources.getString(R.string.label_coffee),
            resources.getString(R.string.label_food),
            resources.getString(R.string.label_fotocopy),
            resources.getString(R.string.label_laundry)
        )
        (binding.textViewLabel as MaterialAutoCompleteTextView).setSimpleItems(labels)

        val cities = arrayOf(
            resources.getString(R.string.city_null),
            resources.getString(R.string.city_north_jakarta),
            resources.getString(R.string.city_south_jakarta)
        )
        (binding.textViewCity as MaterialAutoCompleteTextView).setSimpleItems(cities)

        val subdistricts = arrayOf(
            resources.getString(R.string.subdistrict_null),
        )

        (binding.textViewSubdistrict as MaterialAutoCompleteTextView).setSimpleItems(subdistricts)

    }

    private fun updateUI() {
        if (user == null) {
            requireActivity().startActivity(Intent(requireActivity(), AuthenticationActivity::class.java))
            requireActivity().finish()
        }

        user?.let {
            if (it.photoUrl == null) {
                binding.imageViewProfilePicture.setImageResource(R.drawable.baseline_person_24)
            } else {
                binding.imageViewProfilePicture.loadImageWithUri(it.photoUrl as Uri, requireActivity())
            }

            it.displayName?.let {name ->
                binding.textViewName.text = name
            }

            it.email?.let {email ->
                binding.textViewEmailContent.text = email
            }
        }
    }

    private fun setUpLogoutButton() {
        binding.buttonLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity())
                .setMessage(getString(R.string.logout_message))
                .setNegativeButton(getString(R.string.logout_negative_button)) {dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(getString(R.string.logout_positive_button)) {dialog, _ ->
                    signOut()
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun signOut() {
        Firebase.auth.signOut()
        requireActivity().finish()
        requireActivity().startActivity(
            Intent(requireActivity(),
                AuthenticationActivity::class.java)
        )
    }

    private fun viewModelListen() {
        viewModel.selectedCity.observe(viewLifecycleOwner) { city ->
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

            binding.textViewCity.text = Editable.Factory.getInstance().newEditable(city ?: getString(R.string.city_null))
        }

        viewModel.maxPrice.observe(viewLifecycleOwner) {maxPrice ->
            binding.editTextMaxPrice.setText((maxPrice ?: 0).toString())
        }

        viewModel.selectedLabel.observe(viewLifecycleOwner) {label ->
            (binding.textViewLabel as MaterialAutoCompleteTextView).setText(label ?: getString(R.string.label_null))

        }

        viewModel.selectedSubdistrict.observe(viewLifecycleOwner) {subdistrict ->
            binding.textViewSubdistrict.setText(subdistrict ?: getString(R.string.subdistrict_null))
        }
    }
}