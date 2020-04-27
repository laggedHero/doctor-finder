package net.laggedhero.doctorfinder.data.dto

data class DoctorDto(
    val id: String,
    val name: String,
    val photoId: String?,
    val rating: Double,
    val address: String,
    val lat: Double,
    val lng: Double,
    val highlighted: Boolean,
    val reviewCount: Int,
    val source: String,
    val phoneNumber: String,
    val email: String?,
    val website: String?,
    val openingHours: List<String>
)