package io.github.c23pr487.lapakin.model

import com.google.gson.annotations.SerializedName

data class Lapak(

	@field:SerializedName("kota")
	val city: String? = null,

	@field:SerializedName("maps")
	val gmapsUrl: String? = null,

	@field:SerializedName("nama_penjual")
	val sellerName: String? = null,

	@field:SerializedName("luas_bangunan")
	val buildingArea: String? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("kontak_penjual")
	val sellerPhoneNumber: String? = null,

	@field:SerializedName("alamat")
	val address: String? = null,

	@field:SerializedName("harga")
	val price: String? = null,

	@field:SerializedName("url_thumbnail")
	val pictureUrl: String? = null,

	@field:SerializedName("nama_lapak")
	val name: String? = null,

	@field:SerializedName("kecamatan")
	val subDistrict: String? = null,

	@field:SerializedName("deskripsi")
	val description: String? = null
)
