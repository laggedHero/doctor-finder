package net.laggedhero.doctorfinder.ui.doctor.list

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import net.laggedhero.doctorfinder.R
import net.laggedhero.doctorfinder.consumable.Consumable
import net.laggedhero.doctorfinder.domain.Doctor
import net.laggedhero.doctorfinder.domain.DoctorPageKey
import net.laggedhero.doctorfinder.domain.DoctorRepository
import net.laggedhero.doctorfinder.provider.SchedulerProvider
import net.laggedhero.doctorfinder.provider.StringProvider
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListAction
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListDestination
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListItem
import net.laggedhero.doctorfinder.ui.doctor.list.data.DoctorListState

class DoctorListViewModel(
    private val doctorRepository: DoctorRepository,
    private val schedulerProvider: SchedulerProvider,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val behaviorSubject = BehaviorSubject.createDefault(DoctorListState.initial())
    val state: Observable<DoctorListState>
        get() = behaviorSubject
    private val currentState: DoctorListState
        get() = behaviorSubject.value ?: DoctorListState.initial()

    private var disposable: Disposable? = null

    fun onAction(action: DoctorListAction) {
        when (action) {
            is DoctorListAction.Load -> onActionLoad(action)
            is DoctorListAction.OpenDetails -> onOpenDetails(action)
        }
    }

    private fun onActionLoad(action: DoctorListAction.Load) {
        disposable?.dispose()

        behaviorSubject.onNext(currentState.copy(loading = true))

        disposable = doctorRepository.fetchDoctors(action.doctorPageKey)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.computation())
            .map { doctorPage ->
                currentState.copy(
                    loading = false,
                    nextPageToken = doctorPage.page,
                    doctors = (currentState.doctors + doctorPage.doctors).sortedBy { it.review.rating.value },
                    error = null
                )
            }
            .map(::withDoctorListItems)
            .onErrorReturn {
                currentState.copy(
                    loading = false,
                    error = Consumable(stringProvider.getString(R.string.generic_error))
                )
            }
            .subscribe { state ->
                behaviorSubject.onNext(state)
            }
    }

    private fun withDoctorListItems(state: DoctorListState): DoctorListState {
        val doctorListItems = mutableListOf<DoctorListItem>()

        if (state.recentDoctors.isNotEmpty()) {
            doctorListItems.add(
                DoctorListItem.HeaderItem(
                    stringProvider.getString(R.string.doctor_list_recent_doctors)
                )
            )
            doctorListItems.addAll(
                state.recentDoctors.map {
                    DoctorListItem.DoctorItem(it)
                }
            )
            doctorListItems.add(
                DoctorListItem.HeaderItem(
                    stringProvider.getString(R.string.doctor_list_doctors)
                )
            )
        }
        doctorListItems.addAll(
            (state.doctors - state.recentDoctors).map {
                DoctorListItem.DoctorItem(it)
            }
        )

        if (state.nextPageToken is DoctorPageKey.Keyed) {
            doctorListItems.add(
                DoctorListItem.NextPageItem(state.nextPageToken)
            )
        }

        return state.copy(
            doctorListItems = doctorListItems.toList()
        )
    }

    private fun onOpenDetails(action: DoctorListAction.OpenDetails) {
        val state = currentState
            .copy(
                recentDoctors = currentState.updatedRecentList(action.doctor),
                destination = Consumable(DoctorListDestination.DoctorDetails(action.doctor))
            )
            .let(::withDoctorListItems)
        behaviorSubject.onNext(state)
    }

    private fun DoctorListState.updatedRecentList(doctor: Doctor): List<Doctor> {
        return recentDoctors.toMutableList().apply {
            remove(doctor)
            add(0, doctor)
        }.take(3)
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}

