package io.github.c23pr487.lapakin.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.c23pr487.lapakin.databinding.LapakItemBinding
import io.github.c23pr487.lapakin.model.Lapak
import io.github.c23pr487.lapakin.utils.toIdr

class LapakAdapter : RecyclerView.Adapter<LapakAdapter.LapakViewHolder>() {
    val lapaks = listOf(
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
        Lapak("", "", "Lapak", "Ini lapak bukan sekedar lapak.", 65000000, 200, "Jakarta Utara", "Koja", "Jalan in Koja nomor 23. Koja. Jakarta Utara, XXXXX.", "Salim", "081262964645", "Laundry"),
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
        }
    }
}