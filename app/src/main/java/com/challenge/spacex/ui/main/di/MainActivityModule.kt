package com.challenge.spacex.ui.main.di

import android.app.Activity
import android.content.Context
import androidx.navigation.Navigation
import com.challenge.core.di.FragmentBindingModule
import com.challenge.core.di.ViewModelBindingModule
import com.challenge.data.di.DataModule
import com.challenge.data_api.di.ApiModule
import com.challenge.data_db.di.DbModule
import com.challenge.domain.SpaceXRepository
import com.challenge.domain.di.DomainModule
import com.challenge.presentation.MainViewModel
import com.challenge.presentation.MainViewModelImpl
import com.challenge.presentation.di.PresentationModule
import com.challenge.spacex.R
import com.challenge.spacex.ui.main.fragments.MainFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object MainActivityModule {
    fun getNavigationController(activity: Activity) =
        Navigation.findNavController(activity, R.id.mainNavigation)

    fun getFragmentFactory(applicationContext: Context) =
        FragmentBindingModule.getBindingFragmentFactory(
            hashMapOf(
                MainFragment::class.java to MainFragment(getViewModelFactories(applicationContext))
            )
        )

    private fun getViewModelFactories(applicationContext: Context) =
        ViewModelBindingModule.getViewModelFactory(
            hashMapOf(
                MainViewModel::class.java to getMainViewModelImp(applicationContext)
            )
        )

    private fun getMainViewModelImp(applicationContext: Context): MainViewModelImpl =
        MainViewModelImpl(
            DomainModule.getLaunches(getRepository(applicationContext)),
            DomainModule.getCompanyInfo(getRepository(applicationContext)),
            PresentationModule.getCompanyInfoDomainToUiModelMapper(),
            PresentationModule.getLaunchesDomainToUiModelMapper(
                PresentationModule.getDateTransformer(PresentationModule.getDateTimeProvider())
            )
        )

    private fun getRepository(applicationContext: Context): SpaceXRepository =
        DataModule.getSpaceXRepository(
            ApiModule.getSpaceXRemoteSource(
                ApiModule.getApi(applicationContext),
                ApiModule.getCompanyInfoResponseToRepositoryModelMapper(),
                ApiModule.getLaunchesResponseToRepositoryModelMapper(ApiModule.getDateFormatter())
            ),
            DbModule.getSpaceXLocalRepository(
                applicationContext,
                DbModule.getCompanyInfoRepositoryToDomainModelMapper(),
                DbModule.getLaunchesRepositoryToDomainModelMapper()
            ),
            DataModule.getCompanyInfoRepositoryToDomainModelMapper(),
            DataModule.getLaunchesRepositoryToDomainModelMapper()
        )
}