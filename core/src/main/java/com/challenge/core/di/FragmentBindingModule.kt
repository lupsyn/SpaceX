package com.challenge.core.di

import androidx.fragment.app.Fragment
import com.challenge.core.android.InjectingFragmentFactory

object FragmentBindingModule {
    fun getBindingFragmentFactory(fragmentCreatorsMap: Map<Class<out Fragment>, @JvmSuppressWildcards Fragment>) =
        InjectingFragmentFactory(fragmentCreatorsMap)
}
