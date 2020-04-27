package net.laggedhero.doctorfinder.domain

data class Doctor(
    val id: DoctorId,
    val name: DoctorName,
    val photo: DoctorPhoto?,
    val address: DoctorAddress,
    val isHighlighted: DoctorHighlighted,
    val phone: DoctorPhone,
    val email: DoctorEmail?,
    val website: DoctorWebsite?,
    val review: DoctorReview,
    val location: DoctorLocation
)

inline class DoctorId(val value: String)
inline class DoctorName(val value: String)
inline class DoctorPhoto(val value: String)
inline class DoctorAddress(val value: String)
inline class DoctorHighlighted(val value: Boolean)
inline class DoctorPhone(val value: String)
inline class DoctorEmail(val value: String)
inline class DoctorWebsite(val value: String)

data class DoctorReview(
    val rating: DoctorRating,
    val count: DoctorReviewCount
)

inline class DoctorRating(val value: Double)
inline class DoctorReviewCount(val value: Int)

data class DoctorLocation(
    val latitude: Latitude,
    val longitude: Longitude
)

inline class Latitude(val value: Double)
inline class Longitude(val value: Double)