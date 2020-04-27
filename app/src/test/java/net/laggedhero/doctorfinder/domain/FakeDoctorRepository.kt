package net.laggedhero.doctorfinder.domain

import io.reactivex.Single

class FakeDoctorRepository(
    private val result: Single<DoctorPage>
) : DoctorRepository {
    override fun fetchDoctors(page: DoctorPageKey): Single<DoctorPage> {
        return result
    }
}