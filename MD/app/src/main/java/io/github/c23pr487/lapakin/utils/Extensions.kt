package io.github.c23pr487.lapakin.utils

import android.icu.number.NumberFormatter
import androidx.annotation.RequiresApi
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