package net.laggedhero.doctorfinder.extensions

import android.os.Bundle
import net.laggedhero.doctorfinder.domain.*

private const val KEY_DOCTOR_ID = "KEY_DOCTOR_ID"
private const val KEY_DOCTOR_NAME = "KEY_DOCTOR_NAME"
private const val KEY_DOCTOR_PHOTO = "KEY_DOCTOR_PHOTO"
private const val KEY_DOCTOR_ADDRESS = "KEY_DOCTOR_ADDRESS"
private const val KEY_DOCTOR_HIGHLIGHT = "KEY_DOCTOR_HIGHLIGHT"
private const val KEY_DOCTOR_PHONE = "KEY_DOCTOR_PHONE"
private const val KEY_DOCTOR_EMAIL = "KEY_DOCTOR_EMAIL"
private const val KEY_DOCTOR_WEBSITE = "KEY_DOCTOR_WEBSITE"
private const val KEY_DOCTOR_REVIEW_RATING = "KEY_DOCTOR_REVIEW_RATING"
private const val KEY_DOCTOR_REVIEW_COUNT = "KEY_DOCTOR_REVIEW_COUNT"
private const val KEY_DOCTOR_LOCATION_LATITUDE = "KEY_DOCTOR_LOCATION_LATITUDE"
private const val KEY_DOCTOR_LOCATION_LONGITUDE = "KEY_DOCTOR_LOCATION_LONGITUDE"

fun Doctor.toBundle(): Bundle {
    return Bundle().apply {
        putString(KEY_DOCTOR_ID, id.value)
        putString(KEY_DOCTOR_NAME, name.value)
        putString(KEY_DOCTOR_PHOTO, photo?.value)
        putString(KEY_DOCTOR_ADDRESS, address.value)
        putInt(KEY_DOCTOR_HIGHLIGHT, if (isHighlighted.value) 1 else 0)
        putString(KEY_DOCTOR_PHONE, phone.value)
        putString(KEY_DOCTOR_EMAIL, email?.value)
        putString(KEY_DOCTOR_WEBSITE, website?.value)
        putDouble(KEY_DOCTOR_REVIEW_RATING, review.rating.value)
        putInt(KEY_DOCTOR_REVIEW_COUNT, review.count.value)
        putDouble(KEY_DOCTOR_LOCATION_LATITUDE, location.latitude.value)
        putDouble(KEY_DOCTOR_LOCATION_LONGITUDE, location.longitude.value)
    }
}

fun Bundle.toDoctor(): Doctor {
    return Doctor(
        DoctorId(getString(KEY_DOCTOR_ID, "")),
        DoctorName(getString(KEY_DOCTOR_NAME, "")),
        getString(KEY_DOCTOR_PHOTO)?.let { DoctorPhoto(it) },
        DoctorAddress(getString(KEY_DOCTOR_ADDRESS, "")),
        DoctorHighlighted(getInt(KEY_DOCTOR_HIGHLIGHT) > 0),
        DoctorPhone(getString(KEY_DOCTOR_PHONE, "")),
        getString(KEY_DOCTOR_EMAIL)?.let { DoctorEmail(it) },
        getString(KEY_DOCTOR_WEBSITE)?.let { DoctorWebsite(it) },
        DoctorReview(
            DoctorRating(getDouble(KEY_DOCTOR_REVIEW_RATING)),
            DoctorReviewCount(getInt(KEY_DOCTOR_REVIEW_COUNT))
        ),
        DoctorLocation(
            Latitude(getDouble(KEY_DOCTOR_LOCATION_LATITUDE)),
            Longitude(getDouble(KEY_DOCTOR_LOCATION_LONGITUDE))
        )
    )
}