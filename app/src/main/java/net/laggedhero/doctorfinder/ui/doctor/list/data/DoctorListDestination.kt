package net.laggedhero.doctorfinder.ui.doctor.list.data

import net.laggedhero.doctorfinder.domain.Doctor

sealed class DoctorListDestination {
    data class DoctorDetails(val doctor: Doctor) : DoctorListDestination()
}