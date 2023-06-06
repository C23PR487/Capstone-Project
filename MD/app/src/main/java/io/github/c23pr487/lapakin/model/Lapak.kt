package io.github.c23pr487.lapakin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lapak(
    val pictureUrl: String,
    val gmapsUrl: String,
    val name: String,
    val description: String,
    val price: Int,
    val buildingArea: Int,
    val city: String,
    val subDistrict: String,
    val address: String,
    val sellerName: String?,
    val contact: String,
    val label: String,
    ) : Parcelable