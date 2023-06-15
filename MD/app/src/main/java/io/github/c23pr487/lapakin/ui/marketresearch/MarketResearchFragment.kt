package io.github.c23pr487.lapakin.ui.marketresearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.databinding.FragmentMarketResearchBinding
import io.github.c23pr487.lapakin.model.ArticlesItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException

class MarketResearchFragment : Fragment() {

    private var _binding: FragmentMarketResearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding as FragmentMarketResearchBinding

    private val viewModel: MarketResearchViewModel by activityViewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketResearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = NewsAdapter(object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                try {
                    binding.circularProgressBar.visibility = View.GONE
                    adapter.submitData(pagingData)
                } catch (e: IOException) {
                    Snackbar.make(
                        requireContext(),
                        binding.root,
                        getString(R.string.end_of_list_news),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    binding.circularProgressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}