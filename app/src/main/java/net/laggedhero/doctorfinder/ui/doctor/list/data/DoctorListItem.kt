package net.laggedhero.doctorfinder.ui.doctor.list.data

import net.laggedhero.doctorfinder.domain.Doctor
import net.laggedhero.doctorfinder.domain.DoctorPageKey

sealed class DoctorListItem {
    data class HeaderItem(val title: String) : DoctorListItem()
    data class DoctorItem(val doctor: Doctor) : DoctorListItem()
    data class NextPageItem(val pageKey: DoctorPageKey.Keyed) : DoctorListItem()
}