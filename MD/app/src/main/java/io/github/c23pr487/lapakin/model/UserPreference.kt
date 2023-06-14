package io.github.c23pr487.lapakin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPreference(
    var id: String = "anonymous",
    var label: String? = null,
    var city: String? = null,
    var subdistrict: String? = null,
    var maxPrice: Int? = null,
) : Parcelable {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "label" to label,
            "city" to city,
            "subdistrict" to subdistrict,
            "maxPrice" to maxPrice
        )
    }
}
