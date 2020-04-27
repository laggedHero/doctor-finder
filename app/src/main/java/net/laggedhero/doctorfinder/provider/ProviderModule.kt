package net.laggedhero.doctorfinder.provider

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ProviderModule {

    @Provides
    @Singleton
    fun providesStringProvider(application: Application): StringProvider {
        return StringProviderImpl(application)
    }

    @Provides
    @Singleton
    fun providesSchedulerProvider(): SchedulerProvider {
        return SchedulerProviderImpl()
    }
}