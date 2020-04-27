package net.laggedhero.doctorfinder.ui.doctor.list

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import net.laggedhero.doctorfinder.domain.DoctorRepository
import net.laggedhero.doctorfinder.injection.fragment.FragmentKey
import net.laggedhero.doctorfinder.injection.viewmodel.ViewModelKey
import net.laggedhero.doctorfinder.provider.SchedulerProvider
import net.laggedhero.doctorfinder.provider.StringProvider

@Module
object DoctorListModule {

    @Provides
    @IntoMap
    @FragmentKey(DoctorListFragment::class)
    fun providesDoctorListFragment(
        viewModelFactory: ViewModelProvider.Factory,
        schedulerProvider: SchedulerProvider
    ): Fragment {
        return DoctorListFragment(viewModelFactory, schedulerProvider)
    }

    @Provides
    @IntoMap
    @ViewModelKey(DoctorListViewModel::class)
    fun providesDoctorListViewModel(
        doctorRepository: DoctorRepository,
        schedulerProvider: SchedulerProvider,
        stringProvider: StringProvider
    ): ViewModel {
        return DoctorListViewModel(doctorRepository, schedulerProvider, stringProvider)
    }
}