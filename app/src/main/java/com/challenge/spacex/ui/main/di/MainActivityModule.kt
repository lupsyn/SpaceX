package com.challenge.spacex.ui.main.di

import android.app.Activity
import android.content.Context
import androidx.navigation.Navigation
import com.challenge.core.di.FragmentBindingModule
import com.challenge.core.di.ViewModelBindingModule
import com.challenge.data.di.DataModule
import com.challenge.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import com.challenge.data.mapper.LaunchesRepositoryToDomainModelMapper
import com.challenge.data_api.di.ApiModule
import com.challenge.data_db.di.DbModule
import com.challenge.domain.SpaceXRepository
import com.challenge.domain.di.DomainModule
import com.challenge.domain.usecase.GetCompanyInfo
import com.challenge.domain.usecase.GetLaunches
import com.challenge.presentation.MainViewModel
import com.challenge.presentation.MainViewModelImpl
import com.challenge.presentation.di.PresentationModule
import com.challenge.presentation.mapper.CompanyInfoDomainToUiModelMapper
import com.challenge.presentation.mapper.LaunchesDomainToUiModelMapper
import com.challenge.spacex.R
import com.challenge.spacex.ui.main.SpaceXApp
import com.challenge.spacex.ui.main.fragments.MainFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
object MainActivityModule {
    private fun getApiService(applicationContext: SpaceXApp) = applicationContext.apiService

    fun getNavigationController(activity: Activity) =
        Navigation.findNavController(activity, R.id.mainNavigation)

    fun getFragmentFactory(applicationContext: SpaceXApp) =
        FragmentBindingModule.getBindingFragmentFactory(
            hashMapOf(
                MainFragment::class.java to MainFragment(getViewModelFactories(applicationContext))
            )
        )

    private fun getViewModelFactories(applicationContext: SpaceXApp) =
        ViewModelBindingModule.getViewModelFactory(
            hashMapOf(
                MainViewModel::class.java to getMainViewModelImp(applicationContext)
            )
        )

    private fun getMainViewModelImp(applicationContext: SpaceXApp): MainViewModelImpl =
        MainViewModelImpl(
            getLaunches(applicationContext),
            getCompanyInfo(applicationContext),
            companyInfoDomainToUiModelMapper(),
            launchesDomainToUiModelMapper()
        )

    private fun getRepository(applicationContext: SpaceXApp): SpaceXRepository =
        DataModule.getSpaceXRepository(
            getSpaceXremoteSource(applicationContext),
            getSpaceXLocalSource(applicationContext),
            getCompanyInfoRepositoryToDomainModelMapper(),
            getLaunchesRepositoryToDomainModelMapper()
        )

    private fun getSpaceXremoteSource(applicationContext: SpaceXApp) =
        ApiModule.getSpaceXRemoteSource(
            getApiService(applicationContext),
            ApiModule.getCompanyInfoResponseToRepositoryModelMapper(),
            ApiModule.getLaunchesResponseToRepositoryModelMapper(ApiModule.getDateFormatter())
        )

    private fun getSpaceXLocalSource(applicationContext: Context) =
        DbModule.getSpaceXLocalRepository(
            applicationContext,
            DbModule.getCompanyInfoRepositoryToDomainModelMapper(),
            DbModule.getLaunchesRepositoryToDomainModelMapper()
        )

    private fun getLaunches(applicationContext: SpaceXApp): GetLaunches =
        DomainModule.getLaunches(getRepository(applicationContext))

    private fun getCompanyInfo(applicationContext: SpaceXApp): GetCompanyInfo =
        DomainModule.getCompanyInfo(getRepository(applicationContext))

    private fun companyInfoDomainToUiModelMapper(): CompanyInfoDomainToUiModelMapper =
        PresentationModule.getCompanyInfoDomainToUiModelMapper()

    private fun launchesDomainToUiModelMapper(): LaunchesDomainToUiModelMapper =
        PresentationModule.getLaunchesDomainToUiModelMapper(
            PresentationModule.getDateTransformer(PresentationModule.getDateTimeProvider())
        )

    private fun getCompanyInfoRepositoryToDomainModelMapper(): CompanyInfoRepositoryToDomainModelMapper =
        DataModule.getCompanyInfoRepositoryToDomainModelMapper()

    private fun getLaunchesRepositoryToDomainModelMapper(): LaunchesRepositoryToDomainModelMapper =
        DataModule.getLaunchesRepositoryToDomainModelMapper()

}