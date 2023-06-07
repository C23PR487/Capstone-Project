package io.github.c23pr487.lapakin.utils

import android.content.Context
import android.icu.number.NumberFormatter
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import java.text.NumberFormat
import java.util.Locale

fun Int.toIdr(): String {
    return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(this)
}

fun String.getLatLng(): LatLng {
    val slice = this.split("3d")[1].split("!4d")
    val lat = slice[0]
    val long = slice[1].split("!")[0]
    return LatLng(lat.toDouble(), long.toDouble())
}

fun ImageView.loadImageWithUrl(url: String, context: Context) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun ImageView.loadImageWithUri(uri: Uri, context: Context) {
    Glide.with(context)
        .load(uri)
        .into(this)
}