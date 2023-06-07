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
    val buildingArea: String,
    val city: String,
    val subDistrict: String,
    val address: String,
    val sellerName: String?,
    val sellerPhoneNumber: String?,
    val label: String,
    ) : Parcelable