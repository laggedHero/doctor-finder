package net.laggedhero.doctorfinder

import android.app.Application
import net.laggedhero.doctorfinder.injection.AppComponent
import net.laggedhero.doctorfinder.injection.DaggerAppComponent

class DoctorFinderApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}