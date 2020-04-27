package net.laggedhero.doctorfinder.data

import io.reactivex.Single
import net.laggedhero.doctorfinder.data.dto.DoctorResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DoctorApi {

    @GET("interviews/challenges/android/doctors{page}.json")
    fun fetchDoctors(@Path("page") page: String): Single<DoctorResponseDto>
}