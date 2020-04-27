package net.laggedhero.doctorfinder.data

import io.reactivex.Single
import net.laggedhero.doctorfinder.data.dto.DoctorResponseDto

class FakeDoctorApi(
    private val result: Single<DoctorResponseDto>
) : DoctorApi {

    var page: String? = null

    override fun fetchDoctors(page: String): Single<DoctorResponseDto> {
        this.page = page
        return result
    }
}