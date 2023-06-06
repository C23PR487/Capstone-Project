package io.github.c23pr487.lapakin.model

data class Lapak(
    val pictureUrls: List<String>,
    val description: String,
    val price: Int,
    val details: LapakDetails,
    val seller: Seller,
    val locationUrl: String,
)