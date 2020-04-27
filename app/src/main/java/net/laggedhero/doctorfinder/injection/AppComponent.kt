package net.laggedhero.doctorfinder.injection

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import net.laggedhero.doctorfinder.MainActivity
import net.laggedhero.doctorfinder.data.DataModule
import net.laggedhero.doctorfinder.provider.ProviderModule
import net.laggedhero.doctorfinder.ui.doctor.list.DoctorListModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        DoctorListModule::class,
        ProviderModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}