package io.github.c23pr487.lapakin.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.databinding.FragmentProfileBinding
import io.github.c23pr487.lapakin.ui.auth.AuthenticationActivity
import io.github.c23pr487.lapakin.utils.loadImageWithUri

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding as FragmentProfileBinding

    private val user: FirebaseUser? by lazy {
        Firebase.auth.currentUser
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

        // DELETE LATER
        // TEMP
        val city: String? = null
        // TEMP
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
}