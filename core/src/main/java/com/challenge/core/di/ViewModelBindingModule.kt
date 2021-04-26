package com.challenge.core.di

import androidx.lifecycle.ViewModel
import com.challenge.core.android.InjectingViewModelFactory

object ViewModelBindingModule {
    fun getViewModelFactory(viewModelFactoryMap: MutableMap<Class<out ViewModel>, ViewModel>) =
        InjectingViewModelFactory(viewModelFactoryMap)
}
