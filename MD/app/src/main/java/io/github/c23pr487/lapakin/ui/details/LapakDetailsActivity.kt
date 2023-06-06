package io.github.c23pr487.lapakin.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.databinding.ActivityLapakDetailsBinding
import io.github.c23pr487.lapakin.model.Lapak
import io.github.c23pr487.lapakin.utils.toIdr

class LapakDetailsActivity : AppCompatActivity() {
    private val binding: ActivityLapakDetailsBinding by lazy {
        ActivityLapakDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.run {
            title = resources.getString(R.string.lapak_details_title)
            setDisplayHomeAsUpEnabled(true)
        }

        val lapak = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_LAPAK, Lapak::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_LAPAK)
        }

        updateUI(lapak as Lapak)

        setShowMoreListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(lapak: Lapak) {
        binding.textViewDescriptionContent.text = lapak.description
        binding.imageViewThumbnail.setImageResource(R.drawable.page_background)
        binding.textViewInfoPrice.text = resources.getString(R.string.price, lapak.price.toIdr())
        binding.textViewInfoBody.text = resources.getString(R.string.lapak_sale_info, lapak.buildingArea, lapak.address)
        binding.textViewInfoLabel.text = lapak.label
        binding.textViewSellerName.text = lapak.sellerName
        binding.textViewSellerPhone.text = lapak.sellerPhoneNumber

        if (lapak.sellerName != null && lapak.sellerPhoneNumber != null) {
            binding.cardViewSeller.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${lapak.sellerPhoneNumber}")
                }
                startActivity(intent)
            }
        }
    }

    private fun setShowMoreListener() {
        val textView = binding.textViewDescriptionContent
        textView.post {
            if (textView.lineCount > 5) {
                binding.textViewShowMore.visibility = View.VISIBLE
                binding.cardViewDescription.setOnClickListener {
                    toggleComponents()
                }
                binding.textViewDescriptionContent.maxLines = 5
            }
        }
    }

    private fun toggleComponents() {
        binding.textViewShowMore.visibility = when (binding.textViewShowMore.visibility) {
            View.GONE -> {
                binding.textViewDescriptionContent.maxLines = 5
                View.VISIBLE
            }
            View.VISIBLE -> {
                binding.textViewDescriptionContent.maxLines = Integer.MAX_VALUE
                View.GONE
            }
            else -> View.GONE
        }


    }

    companion object {
        const val EXTRA_LAPAK = "extra_lapak"
    }
}