package net.laggedhero.doctorfinder.data

import io.reactivex.Single
import net.laggedhero.doctorfinder.data.dto.DoctorDto
import net.laggedhero.doctorfinder.data.dto.DoctorResponseDto
import net.laggedhero.doctorfinder.domain.*
import org.junit.Assert.assertEquals
import org.junit.Test

class DoctorRepositoryImplTest {

    @Test
    fun `given a doctor api when requesting the initial page then api receives an empty string`() {
        // given
        val doctorApi = FakeDoctorApi(
            Single.error(Throwable("ignored"))
        )

        // when
        val sut = DoctorRepositoryImpl(doctorApi)

        sut.fetchDoctors(DoctorPageKey.Initial)
            .test()

        // then
        assertEquals("", doctorApi.page)
    }

    @Test
    fun `given a doctor api when requesting a page then api receives a page string`() {
        // given
        val doctorApi = FakeDoctorApi(
            Single.error(Throwable("ignored"))
        )

        // when
        val sut = DoctorRepositoryImpl(doctorApi)

        sut.fetchDoctors(DoctorPageKey.Keyed("page"))
            .test()

        // then
        assertEquals("-page", doctorApi.page)
    }

    @Test
    fun `given a doctor api when requesting over the last page then api is not called`() {
        // given
        val doctorApi = FakeDoctorApi(
            Single.error(Throwable("ignored"))
        )

        // when
        val sut = DoctorRepositoryImpl(doctorApi)

        sut.fetchDoctors(DoctorPageKey.Last)
            .test()

        // then
        assertEquals(null, doctorApi.page)
    }

    @Test
    fun `given a doctor api when requesting over the last page then returns error`() {
        // given
        val doctorApi = FakeDoctorApi(
            Single.error(Throwable("ignored"))
        )

        // when
        val sut = DoctorRepositoryImpl(doctorApi)

        val test = sut.fetchDoctors(DoctorPageKey.Last)
            .test()

        // then
        test
            .assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun `given an doctor api error when requesting a page then returns error`() {
        // given
        val error = Throwable("Error")
        val doctorApi = FakeDoctorApi(
            Single.error(error)
        )

        // when
        val sut = DoctorRepositoryImpl(doctorApi)

        val test = sut.fetchDoctors(DoctorPageKey.Initial)
            .test()

        // then
        test
            .assertError(error)
    }

    @Test
    fun `given an doctor api success when requesting a page then returns teh content`() {
        // given
        val responseDto = Factory.createTestDoctorResponseDto()
        val doctorApi = FakeDoctorApi(
            Single.just(responseDto)
        )

        // when
        val sut = DoctorRepositoryImpl(doctorApi)

        val test = sut.fetchDoctors(DoctorPageKey.Initial)
            .test()

        // then
        val expected = Factory.createTestDoctorPage()
        test.assertValue(expected)
    }

    object Factory {
        fun createTestDoctorResponseDto(): DoctorResponseDto {
            return DoctorResponseDto(
                doctors = listOf(
                    DoctorDto(
                        "id",
                        "name",
                        "photo-id",
                        4.5,
                        "address",
                        52.00,
                        53.00,
                        false,
                        20,
                        "google",
                        "phone-number",
                        "email",
                        "website",
                        listOf()
                    )
                ),
                lastKey = "some-key"
            )
        }

        fun createTestDoctorPage(): DoctorPage {
            return DoctorPage(
                DoctorList(
                    listOf(
                        Doctor(
                            DoctorId("id"),
                            DoctorName("name"),
                            DoctorPhoto("photo-id"),
                            DoctorAddress("address"),
                            DoctorHighlighted(false),
                            DoctorPhone("phone-number"),
                            DoctorEmail("email"),
                            DoctorWebsite("website"),
                            DoctorReview(
                                DoctorRating(4.5),
                                DoctorReviewCount(20)
                            ),
                            DoctorLocation(
                                Latitude(52.00),
                                Longitude(53.00)
                            )
                        )
                    )
                ),
                DoctorPageKey.Keyed("some-key")
            )
        }
    }
}