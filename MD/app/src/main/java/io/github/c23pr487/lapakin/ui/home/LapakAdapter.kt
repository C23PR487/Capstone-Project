package io.github.c23pr487.lapakin.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.c23pr487.lapakin.databinding.LapakItemBinding
import io.github.c23pr487.lapakin.model.LapakCard
import io.github.c23pr487.lapakin.utils.loadImageWithUrl
import io.github.c23pr487.lapakin.utils.styleLabel
import io.github.c23pr487.lapakin.utils.toIdr

class LapakAdapter(private val lapaks: List<LapakCard>, private val callback: (String?) -> Unit) :
    RecyclerView.Adapter<LapakAdapter.LapakViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LapakViewHolder {
        val binding = LapakItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LapakViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: LapakViewHolder, position: Int) {
        holder.bind(lapaks[position])
    }

    override fun getItemCount(): Int {
        return lapaks.size
    }

    class LapakViewHolder(
        private val binding: LapakItemBinding,
        callback: (String?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var lapak: LapakCard

        init {
            binding.root.setOnClickListener {
                binding.circularProgressBar.visibility = View.VISIBLE
                callback.invoke(lapak.id)
                binding.circularProgressBar.visibility = View.INVISIBLE
            }
        }

        fun bind(lapak: LapakCard) {
            binding.textViewPrice.text = lapak.price?.toIntOrNull()?.toIdr()
            binding.textViewArea.text = lapak.subdistrict
            binding.textViewAddress.text = lapak.address
            binding.textViewLapakType.styleLabel(
                lapak.label,
                binding.root.context,
                binding.cardViewLabel
            )
            lapak.thumbnailUrl?.let {
                binding.imageViewThumbnail.loadImageWithUrl(it, binding.root.context)
            }
            this.lapak = lapak

        }
    }
}