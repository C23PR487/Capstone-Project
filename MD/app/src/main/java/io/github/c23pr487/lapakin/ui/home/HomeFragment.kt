package io.github.c23pr487.lapakin.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.databinding.FragmentHomeBinding
import io.github.c23pr487.lapakin.model.UserPreference
import io.github.c23pr487.lapakin.ui.details.LapakDetailsActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding as FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(MyItemDecorator())

        binding.buttonFilter.setOnClickListener {
            if (parentFragmentManager.findFragmentByTag(FilterBottomSheetFragment::class.java.simpleName) != null) {
                return@setOnClickListener
            }

            val bottomSheet = FilterBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    viewModel.filterMode.value?.let { notNullFilterMode ->
                        putInt(
                            FilterBottomSheetFragment.EXTRA_RADIO_BUTTON_ID,
                            notNullFilterMode
                        )
                    }
                }
            }
            bottomSheet.show(parentFragmentManager, bottomSheet.javaClass.simpleName)
            bottomSheet.setFragmentResultListener(FilterBottomSheetFragment.MY_REQUEST_KEY) { _, result ->
                viewModel.changeFilterMode(result.getInt(FilterBottomSheetFragment.EXTRA_RADIO_BUTTON_ID))
                viewModel.updateLapaks(
                    if (Build.VERSION.SDK_INT < 33) {
                        result.getParcelable<UserPreference>(FilterBottomSheetFragment.EXTRA_USER_PREFERENCE)
                    } else {
                        result.getParcelable(
                            FilterBottomSheetFragment.EXTRA_USER_PREFERENCE,
                            UserPreference::class.java
                        )
                    },
                    priors = ArrayList(
                        result.getStringArrayList(FilterBottomSheetFragment.EXTRA_PRIORITIES)
                            ?: ArrayList()
                    )
                )
            }
        }

        listenToViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listenToViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.circularProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.message.observe(viewLifecycleOwner) { resourceId ->
            val length = when (resourceId) {
                R.string.problem_encountered_home, R.string.no_lapak_found -> Snackbar.LENGTH_LONG
                else -> Snackbar.LENGTH_SHORT
            }
            if (resourceId != null) {
                Snackbar.make(binding.root, resourceId, length).show()
            }
        }
        viewModel.lapaks.observe(viewLifecycleOwner) { lapaks ->
            binding.recyclerView.adapter = LapakAdapter(lapaks) { id ->
                startActivity(
                    Intent(
                        binding.root.context,
                        LapakDetailsActivity::class.java
                    ).apply { putExtra(LapakDetailsActivity.EXTRA_ID, id) }
                )
            }
        }
        viewModel.filterMode.observe(viewLifecycleOwner) { filterMode ->
            binding.buttonFilter.text = when (filterMode) {
                R.id.radio_button_preference -> {
                    getString(R.string.filter_preference)
                }

                R.id.radio_button_all -> {
                    getString(R.string.filter_all)
                }

                R.id.radio_button_custom -> {
                    getString(R.string.filter_custom)
                }

                else -> null
            }
        }
    }
}