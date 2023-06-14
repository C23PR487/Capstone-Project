package io.github.c23pr487.lapakin.model

import com.google.gson.annotations.SerializedName

data class LapakCard(

	@field:SerializedName("kota")
	val city: String? = null,

	@field:SerializedName("harga")
	val price: String? = null,

	@field:SerializedName("kecamatan")
	val subdistrict: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("alamat")
	val address: String? = null,

	@field:SerializedName("url_thumbnail")
	val thumbnailUrl: String? = null,
)
