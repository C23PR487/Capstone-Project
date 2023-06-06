package io.github.c23pr487.lapakin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.c23pr487.lapakin.databinding.LapakItemBinding
import io.github.c23pr487.lapakin.model.Lapak
import io.github.c23pr487.lapakin.ui.details.LapakDetailsActivity
import io.github.c23pr487.lapakin.utils.toIdr

class LapakAdapter : RecyclerView.Adapter<LapakAdapter.LapakViewHolder>() {
    val lapaks = listOf(
        Lapak("", "https://www.google.com/maps/place/Fakultas+Kopi+Jakarta/data=!4m7!3m6!1s0x2e69f404f476b09d:0xad1e2ce92f97ab00!8m2!3d-6.2118378!4d106.828466!16s%2F", "Lapak Mangga Dua", "ini deskripsi", 950000, "40 m2", "Jakarta Utara", "Mangga Dua", "Jl. Karet Karya VII No.7, RW.7", "Budi", "02147483647", "Toko_Distro"),
        Lapak("", "https://www.google.com/maps/place/Fakultas+Kopi+Jakarta/data=!4m7!3m6!1s0x2e69f404f476b09d:0xad1e2ce92f97ab00!8m2!3d-6.2118378!4d106.828466!16s%2F", "Lapak Mangga Dua (LONG)", "ini deskripsi yang lebih panjang. Lapak ini direkomendasikan oleh orang. Lapak ini baru hanya disewakan ke satu orang sejak dibangun, tapi sayangnya orang tersebut meninggal. Nego available. Kalau sewa sebelum 7 Juli 2023, bakal saya kasih uang saku 20 juta per tahun juga. Ayo gas sewa di sini.", 120000000, "400 m2", "Jakarta Utara", "Mangga Dua", "Jl. Karet Karya VII No.7, RW.7. Di samping kali yang warna merah, seberang toko Roti WKWKWK tolol amat ini address.", "Budi", "02147483647", "Toko_Distro"),
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
        fun bind(lapak: Lapak) {
            binding.textViewPrice.text = lapak.price.toIdr()
            binding.textViewArea.text = lapak.subDistrict
            binding.textViewAddress.text = lapak.address
            binding.textViewLapakType.text = lapak.label

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, LapakDetailsActivity::class.java)
                intent.putExtra(LapakDetailsActivity.EXTRA_LAPAK, lapak)
                binding.root.context.startActivity(intent)
            }
        }
    }
}