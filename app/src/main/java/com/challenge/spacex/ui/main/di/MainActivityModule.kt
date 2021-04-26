package com.challenge.spacex.ui.main.di

import android.app.Activity
import androidx.navigation.Navigation
import com.challenge.core.di.FragmentBindingModule
import com.challenge.core.di.ViewModelBindingModule
import com.challenge.spacex.R
import com.challenge.spacex.ui.main.MainViewModel
import com.challenge.spacex.ui.main.fragments.MainFragment

object MainActivityModule {
    fun getNavigationController(activity: Activity) = Navigation.findNavController(activity, R.id.mainNavigation)

    fun getFragmentFactory() = FragmentBindingModule.getBindingFragmentFactory(
        hashMapOf(
            MainFragment::class.java to MainFragment(getViewModelFactories())
        )
    )

    private fun getViewModelFactories() = ViewModelBindingModule.getViewModelFactory(
        hashMapOf(
            MainViewModel::class.java to MainViewModel()
        )
    )
}