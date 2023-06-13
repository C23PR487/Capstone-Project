package io.github.c23pr487.lapakin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.c23pr487.lapakin.databinding.FragmentFilterBottomSheetBinding

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
        val id = arguments?.getInt(EXTRA_RADIO_BUTTON_ID)
        id?.let {
            view.findViewById<RadioButton>(it).isChecked = true
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            setFragmentResult(MY_REQUEST_KEY, Bundle().apply {
                putInt(EXTRA_RADIO_BUTTON_ID, checkedId)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_RADIO_BUTTON_ID = "extra_radio_button_id"
        const val MY_REQUEST_KEY = "bottom sheet request key"
    }
}