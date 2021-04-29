package com.challenge.data_api.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.MainCoroutineRule
import com.challenge.data.SpaceXRemoteSource
import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.data.model.LaunchRepositoryModel
import com.challenge.data_api.ApiService
import com.challenge.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import com.challenge.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import com.challenge.data_api.model.CompanyInfoResponse
import com.challenge.data_api.model.LaunchesResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SpaceXRemoteSourceImplTest {
    private lateinit var underTest: SpaceXRemoteSource

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper

    @Mock
    lateinit var launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper

    @Mock
    lateinit var companyInfoResponse: CompanyInfoResponse

    @Mock
    lateinit var launches: List<LaunchesResponse>

    @Mock
    lateinit var companyInfoRepositoryModel: CompanyInfoRepositoryModel

    @Mock
    lateinit var launchesList: List<LaunchRepositoryModel>


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        underTest = SpaceXRemoteSourceImpl(
            apiService,
            companyInfoRepositoryMapper,
            launchesRepositoryMapper
        )
    }

    @Test
    fun `When getCompanyInfo then apiService invoked`() {
        runBlockingTest {

            given(apiService.getCompanyInfo()).willReturn(companyInfoResponse)
            given(companyInfoRepositoryMapper.toRepositoryModel(companyInfoResponse)).willReturn(
                companyInfoRepositoryModel
            )
            underTest.getCompanyInfo()
                .collect {
                    assertEquals(it, companyInfoRepositoryModel)
                }

            verify(apiService).getCompanyInfo()
        }
    }

    @Test
    fun `When getAllLaunches then apiService invoked`() {
        runBlockingTest {
            given(apiService.getAllLaunches()).willReturn(launches)
            given(launchesRepositoryMapper.toRepositoryModel(launches)).willReturn(launchesList)

            underTest.getAllLaunches()
                .collect {
                    assertEquals(it, launchesList)
                }

            verify(apiService).getAllLaunches()
        }
    }
}
