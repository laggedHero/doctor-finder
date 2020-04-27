package net.laggedhero.doctorfinder.injection

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import net.laggedhero.doctorfinder.injection.fragment.DaggerFragmentFactory
import net.laggedhero.doctorfinder.injection.viewmodel.DaggerViewModelFactory
import javax.inject.Provider

@Module
object AppModule {
    @Provides
    fun providesDaggerFragmentFactory(creator: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>): FragmentFactory {
        return DaggerFragmentFactory(creator)
    }

    @Provides
    fun providesDaggerViewModelFactory(creator: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {
        return DaggerViewModelFactory(creator)
    }
}