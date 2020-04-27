package net.laggedhero.doctorfinder.domain

import io.reactivex.Single

interface DoctorRepository {
    fun fetchDoctors(page: DoctorPageKey): Single<DoctorPage>
}