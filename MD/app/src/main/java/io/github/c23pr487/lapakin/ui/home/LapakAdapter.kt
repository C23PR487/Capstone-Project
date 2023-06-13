package io.github.c23pr487.lapakin.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.c23pr487.lapakin.databinding.LapakItemBinding
import io.github.c23pr487.lapakin.model.LapakCard
import io.github.c23pr487.lapakin.ui.details.LapakDetailsActivity
import io.github.c23pr487.lapakin.ui.details.LapakDetailsActivity.Companion.EXTRA_ID
import io.github.c23pr487.lapakin.utils.loadImageWithUrl
import io.github.c23pr487.lapakin.utils.toIdr

class LapakAdapter(private val lapaks: List<LapakCard>) : RecyclerView.Adapter<LapakAdapter.LapakViewHolder>() {

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

    class LapakViewHolder(private val binding: LapakItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lapak: LapakCard) {
            binding.textViewPrice.text = lapak.price?.toIntOrNull()?.toIdr()
            binding.textViewArea.text = lapak.subdistrict
            binding.textViewAddress.text = lapak.address
            binding.textViewLapakType.text = lapak.label
            lapak.thumbnailUrl?.let {
                binding.imageViewThumbnail.loadImageWithUrl(it, binding.root.context)
            }

            binding.root.setOnClickListener {
                binding.root.context.startActivity(
                    Intent(
                    binding.root.context,
                        LapakDetailsActivity::class.java
                    ).apply { putExtra(EXTRA_ID, lapak.id) }
                )
            }
        }
    }
}