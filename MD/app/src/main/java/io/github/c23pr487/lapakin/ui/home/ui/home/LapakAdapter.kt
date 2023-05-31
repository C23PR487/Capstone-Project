package io.github.c23pr487.lapakin.ui.home.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.c23pr487.lapakin.databinding.LapakItemBinding
import io.github.c23pr487.lapakin.model.LapakCard
import io.github.c23pr487.lapakin.utils.toIdr

class LapakAdapter : RecyclerView.Adapter<LapakAdapter.LapakViewHolder>() {
    val lapaks: List<LapakCard> = listOf(
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
        LapakCard("", 1_200_000, "Jakarta Barat", "Jalan di Jakarta Barat. RW sekian RT sekian. 11620", "Laundry"),
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LapakViewHolder {
        val binding = LapakItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LapakViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LapakViewHolder, position: Int) {
        holder.bind(lapaks[position])
    }

    override fun getItemCount(): Int {
        return lapaks.size
    }

    class LapakViewHolder(val binding: LapakItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lapak: LapakCard) {
            binding.textViewPrice.text = lapak.price.toIdr()
            binding.textViewArea.text = lapak.area
            binding.textViewAddress.text = lapak.address
            binding.textViewLapakType.text = lapak.lapakType
        }
    }
}