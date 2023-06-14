package io.github.c23pr487.lapakin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import android.widget.RadioButton
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.databinding.FragmentFilterBottomSheetBinding
import io.github.c23pr487.lapakin.model.UserPreference

class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding as FragmentFilterBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioButtonCustom.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setUpMenu()
                binding.buttonAddField.visibility = View.VISIBLE
                binding.linearLayout.visibility = View.VISIBLE
                return@setOnCheckedChangeListener
            }
            binding.buttonAddField.visibility = View.GONE
            binding.linearLayout.visibility = View.GONE
        }

        binding.buttonFilter.setOnClickListener {
            setFragmentResult(MY_REQUEST_KEY, Bundle().apply {
                putInt(EXTRA_RADIO_BUTTON_ID, binding.radioGroup.checkedRadioButtonId)
                if (binding.radioButtonCustom.isChecked) {
                    this.putParcelable(EXTRA_USER_PREFERENCE, getUserPreferenceFromInputs())
                    this.putStringArrayList(EXTRA_PRIORITIES, ArrayList(mutableListOf(
                        binding.textViewCity,
                        binding.textViewSubdistrict,
                        binding.editTextMaxPrice,
                    ).apply {
                        sortBy {
                            binding.linearLayout.indexOfChild(it)
                        }
                    }.map {
                        when (it.id) {
                            R.id.text_view_city -> "kota"
                            R.id.text_view_subdistrict -> "kecamatan"
                            R.id.edit_text_max_price -> "harga"
                            else -> ""
                        }
                    })
                    )
                }
            })
            dismiss()
        }

        val id = arguments?.getInt(EXTRA_RADIO_BUTTON_ID)
        id?.let {
            view.findViewById<RadioButton>(it).isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getUserPreferenceFromInputs(): UserPreference {
        val label =
            if (binding.textViewLabel.text.toString() == "null" || binding.textViewLabel.text.toString() == getString(
                    R.string.label_null
                )
            ) null else binding.textViewLabel.text.toString()
        val city =
            if (binding.textViewCity.text.toString() == "null" || binding.textViewCity.text.toString() == getString(
                    R.string.city_null
                )
            ) null else binding.textViewCity.text.toString()

        val subdistrict =
            if (binding.textViewSubdistrict.text.toString() == "null" || binding.textViewSubdistrict.text.toString() == getString(
                    R.string.subdistrict_null
                )
            ) null else binding.textViewSubdistrict.text.toString()

        return UserPreference(
            label = label,
            city = city,
            subdistrict = subdistrict,
            maxPrice = binding.editTextMaxPrice.text.toString().toIntOrNull()
        )
    }

    private fun setUpMenu() {
        val listPopUpWindow = ListPopupWindow(
            requireContext(),
            null,
            com.google.android.material.R.attr.listPopupWindowStyle
        )
        listPopUpWindow.anchorView = binding.buttonAddField

        val fields = mutableListOf(
            getString(R.string.label_title),
            getString(R.string.city_title),
            getString(R.string.subdistrict_title),
            getString(R.string.max_price_title),
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.popup_item, fields)
        listPopUpWindow.setAdapter(adapter)

        listPopUpWindow.setOnItemClickListener { _, _, pos, _ ->
            showField(fields[pos])
            fields.removeAt(pos)
            listPopUpWindow.setAdapter(ArrayAdapter(requireContext(), R.layout.popup_item, fields))
            listPopUpWindow.dismiss()
        }

        binding.buttonAddField.setOnClickListener {
            listPopUpWindow.show()
        }
    }

    private fun showField(name: String) {
        when (name) {
            getString(R.string.label_title) -> {
                val labels = arrayOf(
                    resources.getString(R.string.label_null),
                    resources.getString(R.string.label_coffee),
                    resources.getString(R.string.label_food),
                )
                binding.textInputLayoutLabel.visibility = View.VISIBLE
                binding.textViewLabelTitle.visibility = View.VISIBLE
                binding.textViewLabel.setSimpleItems(labels)
            }

            getString(R.string.city_title) -> {
                val cities = arrayOf(
                    resources.getString(R.string.city_null),
                    resources.getString(R.string.city_north_jakarta),
                    resources.getString(R.string.city_south_jakarta)
                )
                binding.textInputLayoutCity.visibility = View.VISIBLE
                binding.textViewCityTitle.visibility = View.VISIBLE
                binding.textViewCity.setSimpleItems(cities)
            }

            getString(R.string.subdistrict_title) -> {
                val subdistricts = arrayOf(
                    resources.getString(R.string.subdistrict_null),
                    resources.getString(R.string.subdistrict_kelapa_gading),
                    resources.getString(R.string.subdistrict_pademangan),
                    resources.getString(R.string.subdistrict_penjaringan),
                    resources.getString(R.string.subdistrict_kebayoran_baru),
                    resources.getString(R.string.subdistrict_pancoran),
                    resources.getString(R.string.subdistrict_tebet),
                )
                binding.textInputLayoutSubdistrict.visibility = View.VISIBLE
                binding.textViewSubdistrictTitle.visibility = View.VISIBLE
                binding.textViewSubdistrict.setSimpleItems(subdistricts)
            }

            getString(R.string.max_price_title) -> {
                binding.textViewMaxPriceTitle.visibility = View.VISIBLE
                binding.textInputLayoutMaxPrice.visibility = View.VISIBLE
            }

            else -> Unit
        }
    }

    companion object {
        const val EXTRA_RADIO_BUTTON_ID = "extra_radio_button_id"
        const val MY_REQUEST_KEY = "bottom sheet request key"
        const val EXTRA_USER_PREFERENCE = "extra user preference"
        const val EXTRA_PRIORITIES = "extra priorities"
    }
}