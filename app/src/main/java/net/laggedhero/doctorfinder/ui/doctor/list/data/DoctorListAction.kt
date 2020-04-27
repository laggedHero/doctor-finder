package net.laggedhero.doctorfinder.ui.doctor.list.data

import net.laggedhero.doctorfinder.domain.Doctor
import net.laggedhero.doctorfinder.domain.DoctorPageKey

sealed class DoctorListAction {
    data class Load(val doctorPageKey: DoctorPageKey) : DoctorListAction()
    data class OpenDetails(val doctor: Doctor) : DoctorListAction()
}