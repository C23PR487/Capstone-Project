package io.github.c23pr487.lapakin.utils

import android.content.Context
import android.content.res.Resources
import android.icu.number.NumberFormatter
import android.net.Uri
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import java.text.NumberFormat
import java.util.Locale

fun Int.toIdr(): String {
    return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(this)
}

fun String.getLatLng(): LatLng? {
    val slice = this.split("3d")[1].split("!4d")
    val lat = slice[0].toDoubleOrNull()
    val long = slice[1].split("!")[0].toDoubleOrNull()
    if (lat == null || long == null) {
        return null
    }
    return LatLng(lat, long)
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

val Number.toPx get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics)