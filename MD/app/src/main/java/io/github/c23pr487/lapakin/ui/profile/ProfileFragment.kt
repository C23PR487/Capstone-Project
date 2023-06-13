package io.github.c23pr487.lapakin.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
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
        ProfileViewModel.Factory(requireContext())
    }

    private val labels by lazy {
        arrayOf(
            resources.getString(R.string.label_null),
            resources.getString(R.string.label_clothing),
            resources.getString(R.string.label_coffee),
            resources.getString(R.string.label_food),
            resources.getString(R.string.label_fotocopy),
            resources.getString(R.string.label_laundry)
        )
    }

    private val cities by lazy {
        arrayOf(
            resources.getString(R.string.city_null),
            resources.getString(R.string.city_north_jakarta),
            resources.getString(R.string.city_south_jakarta)
        )
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
        listenToUserUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpDropdown(subdistricts: Array<String>? = arrayOf(
        resources.getString(R.string.subdistrict_null),
    )) {
        binding.textViewLabel.setSimpleItems(labels)
        binding.textViewCity.setSimpleItems(cities)
        subdistricts?.let{
            binding.textViewSubdistrict.setSimpleItems(it)
        }
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

            binding.textViewCity.setText((city ?: getString(R.string.city_null)), false)
            if (subdistricts.indexOf(binding.textViewSubdistrict.text.toString()) < 0) {
                binding.textViewSubdistrict.text = null
            }
            setUpDropdown(subdistricts)
        }

        viewModel.maxPrice.observe(viewLifecycleOwner) {maxPrice ->
            binding.editTextMaxPrice.setText((maxPrice ?: "").toString())
        }

        viewModel.selectedLabel.observe(viewLifecycleOwner) {label ->
            binding.textViewLabel.setText(label ?: getString(R.string.label_null), false)
            setUpDropdown(null)
        }

        viewModel.selectedSubdistrict.observe(viewLifecycleOwner) {subdistrict ->
            binding.textViewSubdistrict.setText(subdistrict ?: getString(R.string.subdistrict_null), false)
            setUpDropdown(null)
        }

        viewModel.snackBarMessage.observe(viewLifecycleOwner) {message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun listenToUserUpdates() {
        binding.textViewLabel.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val label = if (p0.toString() == getString(R.string.label_null)) {
                    null
                } else {
                    p0.toString()
                }
                viewModel.changeLabel(label)
            }

        })

        binding.textViewCity.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val city = if (p0.toString() == getString(R.string.city_null)) {
                    null
                } else {
                    p0.toString()
                }
                viewModel.changeCity(city)
            }

        })

        binding.textViewSubdistrict.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val subdistrict = if (p0.toString() == getString(R.string.subdistrict_null) ||
                        p0.toString().isEmpty()) {
                    null
                } else {
                    p0.toString()
                }
                viewModel.changeSubdistrict(subdistrict)
            }

        })

        binding.editTextMaxPrice.setOnFocusChangeListener { _, isFocused ->
            if (isFocused) return@setOnFocusChangeListener

            val input = binding.editTextMaxPrice.text.toString()
            val maxPrice = if (input.isEmpty()) null else input.toString().toIntOrNull()
            viewModel.changeMaxPrice(maxPrice)
        }
    }
}