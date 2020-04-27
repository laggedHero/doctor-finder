package net.laggedhero.doctorfinder.ui.doctor.list

import io.reactivex.Single
import net.laggedhero.doctorfinder.consumable.Consumable
import net.laggedhero.doctorfinder.domain.*
import net.laggedhero.doctorfinder.provider.FakeSchedulerProvider
import net.laggedhero.doctorfinder.provider.FakeStringProvider
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListAction
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListDestination
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListItem
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListState
import org.junit.Test

class DoctorListViewModelTest {

    @Test
    fun `given no action is sent when subscribing then returns an empty state`() {
        // given
        val doctorRepository = FakeDoctorRepository(Single.error(Throwable("no-op")))
        val schedulerProvider = FakeSchedulerProvider()
        val stringProvider = FakeStringProvider { "no-op" }

        // when
        val sut = DoctorListViewModel(
            doctorRepository, schedulerProvider, stringProvider
        )

        // then
        sut.state
            .test()
            .assertValue(DoctorListState.initial())
    }

    @Test
    fun `given load action is sent when repository fails then returns an error state`() {
        // given
        val doctorRepository = FakeDoctorRepository(Single.error(Throwable("ignored")))
        val schedulerProvider = FakeSchedulerProvider()
        val stringProvider = FakeStringProvider { "Error message" }

        // when
        val sut = DoctorListViewModel(
            doctorRepository, schedulerProvider, stringProvider
        )
        sut.onAction(DoctorListAction.Load(DoctorPageKey.Initial))

        // then
        val expected = DoctorListState.initial()
            .copy(error = Consumable("Error message"))
        sut.state
            .test()
            .assertValue(expected)
    }

    @Test
    fun `given load action is sent when repository succeeds then returns a content state`() {
        // given
        val resultPage = Factory.createDoctorPage()
        val doctorRepository = FakeDoctorRepository(Single.just(resultPage))
        val schedulerProvider = FakeSchedulerProvider()
        val stringProvider = FakeStringProvider { "ignored" }

        // when
        val sut = DoctorListViewModel(
            doctorRepository, schedulerProvider, stringProvider
        )
        sut.onAction(DoctorListAction.Load(DoctorPageKey.Initial))

        // then
        val expected = DoctorListState.initial()
            .copy(
                nextPageToken = DoctorPageKey.Keyed("next-page-key"),
                doctors = listOf(),
                recentDoctors = listOf(),
                doctorListItems = listOf(
                    DoctorListItem.NextPageItem(DoctorPageKey.Keyed("next-page-key"))
                )
            )
        sut.state
            .test()
            .assertValue(expected)
    }

    @Test
    fun `given open details action is sent when content already exists then the recent list contains the elected doctor`() {
        // given
        val resultPage = Factory.createDoctorPage(7)
        val doctorRepository = FakeDoctorRepository(Single.just(resultPage))
        val schedulerProvider = FakeSchedulerProvider()
        val stringProvider = FakeStringProvider { "header" }

        // when
        val sut = DoctorListViewModel(
            doctorRepository, schedulerProvider, stringProvider
        )
        sut.onAction(DoctorListAction.Load(DoctorPageKey.Initial))

        sut.onAction(
            DoctorListAction.OpenDetails(
                Factory.createDoctor(2)
            )
        )

        // then
        val expected = DoctorListState.initial()
            .copy(
                nextPageToken = DoctorPageKey.Keyed("next-page-key"),
                doctors = List(7) { Factory.createDoctor(it) },
                recentDoctors = listOf(Factory.createDoctor(2)),
                doctorListItems = listOf(
                    DoctorListItem.HeaderItem("header"),
                    DoctorListItem.DoctorItem(Factory.createDoctor(2)),
                    DoctorListItem.HeaderItem("header"),
                    DoctorListItem.DoctorItem(Factory.createDoctor(0)),
                    DoctorListItem.DoctorItem(Factory.createDoctor(1)),
                    DoctorListItem.DoctorItem(Factory.createDoctor(3)),
                    DoctorListItem.DoctorItem(Factory.createDoctor(4)),
                    DoctorListItem.DoctorItem(Factory.createDoctor(5)),
                    DoctorListItem.DoctorItem(Factory.createDoctor(6)),
                    DoctorListItem.NextPageItem(DoctorPageKey.Keyed("next-page-key"))
                ),
                destination = Consumable(
                    DoctorListDestination.DoctorDetails(
                        Factory.createDoctor(2)
                    )
                )
            )
        sut.state
            .test()
            .assertValue(expected)
    }

    @Test
    fun `given open details action is sent more than 3 times when content already exists then the recent list contains most recent the selected doctors`() {
        // given
        val resultPage = Factory.createDoctorPage(7)
        val doctorRepository = FakeDoctorRepository(Single.just(resultPage))
        val schedulerProvider = FakeSchedulerProvider()
        val stringProvider = FakeStringProvider { "header" }

        // when
        val sut = DoctorListViewModel(
            doctorRepository, schedulerProvider, stringProvider
        )
        sut.onAction(DoctorListAction.Load(DoctorPageKey.Initial))

        sut.onAction(
            DoctorListAction.OpenDetails(
                Factory.createDoctor(2)
            )
        )
        sut.onAction(
            DoctorListAction.OpenDetails(
                Factory.createDoctor(4)
            )
        )
        sut.onAction(
            DoctorListAction.OpenDetails(
                Factory.createDoctor(1)
            )
        )
        sut.onAction(
            DoctorListAction.OpenDetails(
                Factory.createDoctor(6)
            )
        )

        // then
        val expected = DoctorListState.initial()
            .copy(
                nextPageToken = DoctorPageKey.Keyed("next-page-key"),
                doctors = List(7) { Factory.createDoctor(it) },
                recentDoctors = listOf(
                    Factory.createDoctor(6),
                    Factory.createDoctor(1),
                    Factory.createDoctor(4)
                ),
                doctorListItems = listOf(
                    DoctorListItem.HeaderItem("header"),
                    DoctorListItem.DoctorItem(Factory.createDoctor(6)),
                    DoctorListItem.DoctorItem(Factory.createDoctor(1)),
                    DoctorListItem.DoctorItem(Factory.createDoctor(4)),
                    DoctorListItem.HeaderItem("header"),
                    DoctorListItem.DoctorItem(Factory.createDoctor(0)),
                    DoctorListItem.DoctorItem(Factory.createDoctor(2)),
                    DoctorListItem.DoctorItem(Factory.createDoctor(3)),
                    DoctorListItem.DoctorItem(Factory.createDoctor(5)),
                    DoctorListItem.NextPageItem(DoctorPageKey.Keyed("next-page-key"))
                ),
                destination = Consumable(
                    DoctorListDestination.DoctorDetails(
                        Factory.createDoctor(6)
                    )
                )
            )
        sut.state
            .test()
            .assertValue(expected)
    }

    object Factory {
        fun createDoctorPage(doctorQtd: Int = 0): DoctorPage {
            return DoctorPage(
                DoctorList(
                    List(doctorQtd) {
                        createDoctor(it)
                    }
                ),
                DoctorPageKey.Keyed("next-page-key")
            )
        }

        fun createDoctor(idx: Int): Doctor {
            return Doctor(
                DoctorId("id-$idx"),
                DoctorName("name $idx"),
                null,
                DoctorAddress("address $idx"),
                DoctorHighlighted(false),
                DoctorPhone("phone $idx"),
                null,
                null,
                DoctorReview(
                    DoctorRating(3.5),
                    DoctorReviewCount(20)
                ),
                DoctorLocation(
                    Latitude(52.00),
                    Longitude(53.00)
                )
            )
        }
    }
}