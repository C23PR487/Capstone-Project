package io.github.c23pr487.lapakin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.databinding.FragmentHomeBinding

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

        }

        listenToViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listenToViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            binding.circularProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.message.observe(viewLifecycleOwner) {resourceId ->
            val length = when (resourceId) {
                R.string.problem_encountered_home, R.string.no_lapak_found -> Snackbar.LENGTH_LONG
                else -> Snackbar.LENGTH_SHORT
            }
            if (resourceId != null) {
                Snackbar.make(binding.root, resourceId, length).show()
            }
        }
        viewModel.lapaks.observe(viewLifecycleOwner) {lapaks ->
            binding.recyclerView.adapter = LapakAdapter(lapaks)
        }
    }
}