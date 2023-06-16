package io.github.c23pr487.lapakin.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import io.github.c23pr487.lapakin.R
import io.github.c23pr487.lapakin.databinding.ActivityLapakDetailsBinding
import io.github.c23pr487.lapakin.model.Lapak
import io.github.c23pr487.lapakin.utils.getLatLng
import io.github.c23pr487.lapakin.utils.loadImageWithUrl
import io.github.c23pr487.lapakin.utils.styleLabel
import io.github.c23pr487.lapakin.utils.toIdr

class LapakDetailsActivity : AppCompatActivity() {
    private val binding: ActivityLapakDetailsBinding by lazy {
        ActivityLapakDetailsBinding.inflate(layoutInflater)
    }

    private val id by lazy {
        intent.getStringExtra(EXTRA_ID) ?: ""
    }

    private val viewModel: LapakDetailsViewModel by viewModels {
        LapakDetailsViewModel.Factory(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.run {
            title = resources.getString(R.string.lapak_details_title)
            setDisplayHomeAsUpEnabled(true)
        }

        listenToViewModel()
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

    private fun listenToViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.circularProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.message.observe(this) { resourceId ->
            val length = when (resourceId) {
                R.string.problem_encountered_home, R.string.no_lapak_found -> Snackbar.LENGTH_INDEFINITE
                else -> Snackbar.LENGTH_SHORT
            }
            Snackbar.make(binding.root, resourceId, length).show()
        }
        viewModel.lapak.observe(this) { lapak ->
            updateUI(lapak)
            makeMap(lapak)
        }
    }

    private fun updateUI(lapak: Lapak) {
        binding.textViewDescriptionContent.text = lapak.description
        setShowMoreListener()
        lapak.pictureUrl?.let {
            binding.imageViewThumbnail.loadImageWithUrl(it, this)
        }
        binding.textViewInfoPrice.text =
            resources.getString(R.string.price, lapak.price?.toIntOrNull()?.toIdr())
        binding.textViewInfoBody.text =
            resources.getString(R.string.lapak_sale_info, lapak.buildingArea, lapak.address)
        binding.textViewInfoLabel.styleLabel(lapak.label, this, binding.cardViewLabel)
        binding.textViewSellerName.text = lapak.sellerName ?: "-"
        binding.textViewSellerPhone.text = lapak.sellerPhoneNumber ?: "-"

        if (lapak.sellerName != null && lapak.sellerPhoneNumber != null && lapak.sellerName != "null" && lapak.sellerPhoneNumber != "null") {
            binding.cardViewSeller.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${lapak.sellerPhoneNumber}")
                }
                startActivity(intent)
            }
        }
    }

    private fun makeMap(lapak: Lapak) {
        val callback = OnMapReadyCallback { googleMap ->
            val url = lapak.gmapsUrl
            googleMap.setOnMapLoadedCallback {
                val latLng = url?.getLatLng()
                latLng?.let {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 16F))
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(it)
                            .title(lapak.name)
                    )
                }
                binding.circularProgressBar.visibility = View.GONE
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
                val latLng = lapak.gmapsUrl?.getLatLng()
                latLng?.let {
                    val lat = it.latitude
                    val long = it.longitude
                    val uri = Uri.parse("geo:${lat},${long}?z=20&q=$lat, $long(${lapak.name})")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.resolveActivity(packageManager)?.let {
                        startActivity(intent)
                    }
                }

            }

        }

        val fragment = binding.fragmentMapsView.getFragment() as SupportMapFragment?
        fragment?.getMapAsync(callback)
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
        const val EXTRA_ID = "extra_id"
    }
}