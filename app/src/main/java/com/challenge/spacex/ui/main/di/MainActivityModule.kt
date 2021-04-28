package com.challenge.spacex.ui.main.di

import android.app.Activity
import androidx.navigation.Navigation
import com.challenge.core.di.FragmentBindingModule
import com.challenge.core.di.ViewModelBindingModule
import com.challenge.data.di.DataModule
import com.challenge.data_api.di.ApiModule
import com.challenge.domain.SpaceXRepository
import com.challenge.domain.di.DomainModule
import com.challenge.presentation.MainViewModel
import com.challenge.presentation.MainViewModelImpl
import com.challenge.presentation.di.PresentationModule
import com.challenge.spacex.R
import com.challenge.spacex.ui.main.fragments.MainFragment

object MainActivityModule {
    fun getNavigationController(activity: Activity) =
        Navigation.findNavController(activity, R.id.mainNavigation)

    fun getFragmentFactory() = FragmentBindingModule.getBindingFragmentFactory(
        hashMapOf(
            MainFragment::class.java to MainFragment(getViewModelFactories())
        )
    )

    private fun getViewModelFactories() = ViewModelBindingModule.getViewModelFactory(
        hashMapOf(
            MainViewModel::class.java to getMainViewModelImp()
        )
    )

    private fun getMainViewModelImp(): MainViewModelImpl = MainViewModelImpl(
        DomainModule.getLaunches(getRepository()),
        DomainModule.getCompanyInfo(getRepository()),
        PresentationModule.getCompanyInfoDomainToUiModelMapper(),
        PresentationModule.getLaunchesDomainToUiModelMapper(
            PresentationModule.getDateTransformer(PresentationModule.getDateTimeProvider())
        )
    )

    private fun getRepository(): SpaceXRepository = DataModule.getSpaceXRepository(
        ApiModule.getSpaceXRemoteSource(
            ApiModule.getApi(),
            ApiModule.getCompanyInfoResponseToRepositoryModelMapper(),
            ApiModule.getLaunchesResponseToRepositoryModelMapper(ApiModule.getDateFormatter())
        ),
        DataModule.getCompanyInfoRepositoryToDomainModelMapper(),
        DataModule.getLaunchesRepositoryToDomainModelMapper()
    )
}