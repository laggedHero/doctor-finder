package net.laggedhero.doctorfinder.data

import io.reactivex.Single
import net.laggedhero.doctorfinder.data.dto.DoctorDto
import net.laggedhero.doctorfinder.data.dto.DoctorResponseDto
import net.laggedhero.doctorfinder.domain.*

// TODO: potentially extract a mapper
class DoctorRepositoryImpl(
    private val doctorApi: DoctorApi
) : DoctorRepository {

    override fun fetchDoctors(page: DoctorPageKey): Single<DoctorPage> {
        return Single.fromCallable { page.getRequestPage() }
            .flatMap { doctorApi.fetchDoctors(it) }
            .map { it.toDoctorPage() }
    }

    private fun DoctorPageKey.getRequestPage(): String {
        return when (this) {
            is DoctorPageKey.Initial -> ""
            is DoctorPageKey.Keyed -> "-${value}"
            else -> throw IllegalArgumentException("Cannot go over the last page")
        }
    }

    private fun DoctorResponseDto.toDoctorPage(): DoctorPage {
        return DoctorPage(
            doctors = doctors.toDoctorList(),
            page = lastKey.toDoctorPageKey()
        )
    }

    private fun List<DoctorDto>.toDoctorList(): DoctorList {
        val list = this.map { doctorDto ->
            Doctor(
                id = DoctorId(doctorDto.id),
                name = DoctorName(doctorDto.name),
                photo = doctorDto.toDoctorPhoto(),
                address = DoctorAddress(doctorDto.address),
                isHighlighted = DoctorHighlighted(doctorDto.highlighted),
                phone = DoctorPhone(doctorDto.phoneNumber),
                email = doctorDto.toDoctorEmail(),
                website = doctorDto.toDoctorWebsite(),
                review = doctorDto.toDoctorReview(),
                location = doctorDto.toDoctorLocation()
            )
        }
        return DoctorList(list)
    }

    private fun DoctorDto.toDoctorPhoto(): DoctorPhoto? {
        return photoId?.let { DoctorPhoto(it) }
    }

    private fun DoctorDto.toDoctorEmail(): DoctorEmail? {
        return email?.let { DoctorEmail(it) }
    }

    private fun DoctorDto.toDoctorWebsite(): DoctorWebsite? {
        return website?.let { DoctorWebsite(it) }
    }

    private fun DoctorDto.toDoctorReview(): DoctorReview {
        return DoctorReview(DoctorRating(rating), DoctorReviewCount(reviewCount))
    }

    private fun DoctorDto.toDoctorLocation(): DoctorLocation {
        return DoctorLocation(Latitude(lat), Longitude(lng))
    }

    private fun String?.toDoctorPageKey(): DoctorPageKey {
        return this?.let { DoctorPageKey.Keyed(it) } ?: DoctorPageKey.Last
    }
}