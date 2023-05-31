package io.github.c23pr487.lapakin.utils

import android.icu.number.NumberFormatter
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.util.Locale

fun Int.toIdr(): String {
    return NumberFormat.getCurrencyInstance(Locale("in-ID")).format(this)
}