package net.laggedhero.doctorfinder.domain

data class DoctorPage(
    val doctors: DoctorList,
    val page: DoctorPageKey
)

data class DoctorList(val value: List<Doctor>) : List<Doctor> by value

sealed class DoctorPageKey {
    object Initial : DoctorPageKey()
    data class Keyed(val value: String) : DoctorPageKey()
    object Last : DoctorPageKey()
}