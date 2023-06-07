package io.github.c23pr487.lapakin.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.databinding.ActivityLapakDetailsBinding
import io.github.c23pr487.lapakin.model.Lapak
import io.github.c23pr487.lapakin.utils.getLatLng
import io.github.c23pr487.lapakin.utils.toIdr

class LapakDetailsActivity : AppCompatActivity() {
    private val binding: ActivityLapakDetailsBinding by lazy {
        ActivityLapakDetailsBinding.inflate(layoutInflater)
    }

    private val lapak by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_LAPAK, Lapak::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_LAPAK)
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val url = lapak?.gmapsUrl
        googleMap.setOnMapLoadedCallback {
            val latLng = url?.getLatLng() as LatLng
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F))
            googleMap.addMarker(MarkerOptions()
                .position(latLng)
                .title(lapak?.name)
            )
        }
        googleMap.uiSettings.apply {
            isMapToolbarEnabled = false
            isRotateGesturesEnabled = false
            isScrollGesturesEnabled = false
            isTiltGesturesEnabled = false
            isZoomGesturesEnabled = false
            isScrollGesturesEnabledDuringRotateOrZoom = false
        }

        googleMap.setOnMapClickListener {
            val latLng = lapak?.gmapsUrl?.getLatLng()
            val lat = latLng?.latitude
            val long = latLng?.longitude
            val uri = Uri.parse("geo:${lat},${long}?z=20&q=$lat, $long(${lapak?.name})")
            Log.d("A", uri.toString())
            val intent = Intent(Intent.ACTION_VIEW, uri)
            Log.d("Details", "${intent.resolveActivity(packageManager)}")
            intent.resolveActivity(packageManager)?.let {
                startActivity(intent)
            }
        }

    }

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.run {
            title = resources.getString(R.string.lapak_details_title)
            setDisplayHomeAsUpEnabled(true)
        }

        makeMap()

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

    private fun makeMap() {
        val fragment = binding.fragmentMapsView.getFragment() as SupportMapFragment?
        fragment?.getMapAsync(callback)
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