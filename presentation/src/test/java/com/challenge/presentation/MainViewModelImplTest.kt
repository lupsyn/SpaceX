package com.challenge.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.challenge.domain.model.CompanyInfoDomainModel
import com.challenge.domain.model.LaunchDomainModel
import com.challenge.domain.usecase.GetCompanyInfo
import com.challenge.domain.usecase.GetLaunches
import com.challenge.presentation.mapper.CompanyInfoDomainToUiModelMapper
import com.challenge.presentation.mapper.LaunchesDomainToUiModelMapper
import com.challenge.presentation.model.CompanyInfoUiModel
import com.challenge.presentation.model.LaunchUiModel
import com.challenge.presentation.state.TransientUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.inOrder
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    private lateinit var underTest: MainViewModel

    @Mock
    lateinit var getLaunches: GetLaunches

    @Mock
    lateinit var getCompanyInfo: GetCompanyInfo

    @Mock
    lateinit var companyInfoMapper: CompanyInfoDomainToUiModelMapper

    @Mock
    lateinit var launchesMapper: LaunchesDomainToUiModelMapper

    @Mock
    lateinit var launchesObserver: Observer<List<LaunchUiModel>>

    @Mock
    lateinit var companyInfoObserver: Observer<CompanyInfoUiModel>

    @Mock
    lateinit var headerTransientUiDataObserver: Observer<TransientUIState>

    @Mock
    lateinit var bodyTransientUiDataObserver: Observer<TransientUIState>

    @Mock
    lateinit var launchDomainModels: List<LaunchDomainModel>

    @Mock
    lateinit var companyInforDomainModel: CompanyInfoDomainModel

    @Mock
    lateinit var companyInfoUiModel: CompanyInfoUiModel

    @Mock
    lateinit var launchUiModels: List<LaunchUiModel>

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        underTest =
            MainViewModelImpl(getLaunches, getCompanyInfo, companyInfoMapper, launchesMapper)
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `once start observing launches and company info should be retrieved`() {
        runBlockingTest {

            val domainModelFlows: Flow<List<LaunchDomainModel>> = flow { emit(launchDomainModels) }
            val companyInfoFlow: Flow<CompanyInfoDomainModel> =
                flow { emit(companyInforDomainModel) }

            given(getLaunches.execute(-1, false)).willReturn(domainModelFlows)
            given(launchesMapper.toUiModel(launchDomainModels)).willReturn(launchUiModels)

            given(getCompanyInfo.execute()).willReturn(companyInfoFlow)
            given(companyInfoMapper.toUiModel(companyInforDomainModel)).willReturn(
                companyInfoUiModel
            )

            startObserving()

            verify(getLaunches).execute(-1, false)

            with(inOrder(bodyTransientUiDataObserver)) {
                verify(bodyTransientUiDataObserver).onChanged(TransientUIState.LoadingUiState)
                verify(bodyTransientUiDataObserver).onChanged(TransientUIState.DisplayDataUIState)
            }

            with(inOrder(headerTransientUiDataObserver)) {
                verify(headerTransientUiDataObserver).onChanged(TransientUIState.LoadingUiState)
                verify(headerTransientUiDataObserver).onChanged(TransientUIState.DisplayDataUIState)
            }

            verify(launchesObserver).onChanged(launchUiModels)
            verify(companyInfoObserver).onChanged(companyInfoUiModel)
        }
    }

    @Test
    fun `once start observing launches will emit error in case of error, and error in case of company info error`() {
        runBlockingTest {
            val anError = Throwable("an error")
            val domainModelFlows: Flow<List<LaunchDomainModel>> = flow { throw (anError) }
            val companyInfoFlow: Flow<CompanyInfoDomainModel> =
                flow { throw (anError) }

            given(getLaunches.execute(-1, false)).willReturn(domainModelFlows)

            given(getCompanyInfo.execute()).willReturn(companyInfoFlow)

            startObserving()

            verify(getLaunches).execute(-1, false)

            with(inOrder(bodyTransientUiDataObserver)) {
                verify(bodyTransientUiDataObserver).onChanged(TransientUIState.LoadingUiState)
                verify(bodyTransientUiDataObserver).onChanged(TransientUIState.ErrorUiState(anError.message.orEmpty()))
            }

            with(inOrder(headerTransientUiDataObserver)) {
                verify(headerTransientUiDataObserver).onChanged(TransientUIState.LoadingUiState)
                verify(headerTransientUiDataObserver).onChanged(
                    TransientUIState.ErrorUiState(
                        anError.message.orEmpty()
                    )
                )
            }
        }
    }

    @Test
    fun `once start observing launches will emit error in case of noContent, and error in case of company info error`() {
        runBlockingTest {
            val anError = Throwable("empty state")

            val domainModelFlows: Flow<List<LaunchDomainModel>> = flow { emit(launchDomainModels) }
            val companyInfoFlow: Flow<CompanyInfoDomainModel> =
                flow { throw (anError) }

            given(getLaunches.execute(-1, false)).willReturn(domainModelFlows)
            given(launchesMapper.toUiModel(launchDomainModels)).willReturn(launchUiModels)
            given(launchUiModels.isEmpty()).willReturn(true)

            given(getCompanyInfo.execute()).willReturn(companyInfoFlow)

            startObserving()

            verify(getLaunches).execute(-1, false)

            with(inOrder(bodyTransientUiDataObserver)) {
                verify(bodyTransientUiDataObserver).onChanged(TransientUIState.LoadingUiState)
                verify(bodyTransientUiDataObserver).onChanged(TransientUIState.ErrorUiState(anError.message.orEmpty()))
            }

            with(inOrder(headerTransientUiDataObserver)) {
                verify(headerTransientUiDataObserver).onChanged(TransientUIState.LoadingUiState)
                verify(headerTransientUiDataObserver).onChanged(
                    TransientUIState.ErrorUiState(
                        anError.message.orEmpty()
                    )
                )
            }
        }
    }

    @After
    fun cleanUp() {
        with(underTest) {
            launches.removeObserver(launchesObserver)
            companyInfo.removeObserver(companyInfoObserver)
            bodyTransientUiState.removeObserver(bodyTransientUiDataObserver)
            headerTransientUiState.removeObserver(headerTransientUiDataObserver)
        }
    }

    private fun startObserving() {
        underTest.bodyTransientUiState.observeForever(bodyTransientUiDataObserver)
        underTest.headerTransientUiState.observeForever(headerTransientUiDataObserver)
        underTest.launches.observeForever(launchesObserver)
        underTest.companyInfo.observeForever(companyInfoObserver)
    }
}