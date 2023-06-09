package io.github.c23pr487.lapakin.model

data class UserPreference(
    var id: String = "anonymous",
    var label: String? = null,
    var city: String? = null,
    var subdistrict: String? = null,
    var maxPrice: Int? = null
)
