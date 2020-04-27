package net.laggedhero.doctorfinder.ui.doctor.list.data

import net.laggedhero.doctorfinder.consumable.Consumable
import net.laggedhero.doctorfinder.domain.Doctor
import net.laggedhero.doctorfinder.domain.DoctorPageKey

data class DoctorListState(
    val loading: Boolean,
    val nextPageToken: DoctorPageKey,
    val error: Consumable<String>?,
    val doctors: List<Doctor> = listOf(),
    val recentDoctors: List<Doctor> = listOf(),
    val doctorListItems: List<DoctorListItem> = listOf(),
    val destination: Consumable<DoctorListDestination>? = null
) {
    companion object {
        fun initial(): DoctorListState {
            return DoctorListState(
                false,
                DoctorPageKey.Initial,
                null
            )
        }
    }
}