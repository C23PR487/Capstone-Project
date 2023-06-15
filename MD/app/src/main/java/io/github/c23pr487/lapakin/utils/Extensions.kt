package io.github.c23pr487.lapakin.utils

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import io.github.c23pr487.lapakin.R
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

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )

fun String?.encloseWithSingleQuotes(): String? {
    if (this == null) return null
    return "'$this'"
}

fun TextView.styleLabel(label: String?, context: Context, parent: CardView) {
    this.run {
        when (label) {
            "usaha_baju" -> {
                text = context.resources.getString(R.string.label_clothes)
                parent.setCardBackgroundColor(
                    ResourcesCompat.getColor(
                        context.resources,
                        R.color.clothes_orange,
                        context.theme
                    )
                )
                setCompoundDrawablesWithIntrinsicBounds(
                    ResourcesCompat.getDrawable(
                        context.resources,
                        R.drawable.baseline_discount_24,
                        context.theme
                    ),
                    null,
                    null,
                    null
                )
            }

            "toko_kopi" -> {
                text = context.resources.getString(R.string.label_coffee)
                parent.setCardBackgroundColor(
                    ResourcesCompat.getColor(
                        context.resources,
                        R.color.coffee_brown,
                        context.theme
                    )
                )
                setCompoundDrawablesWithIntrinsicBounds(
                    ResourcesCompat.getDrawable(
                        context.resources,
                        R.drawable.baseline_coffee_24,
                        context.theme
                    ),
                    null,
                    null,
                    null
                )
            }

            else -> {

            }
        }
    }
}