package com.challenge.data_api.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.MainCoroutineRule
import com.challenge.data.SpaceXRemoteSource
import com.challenge.data_api.ApiService
import com.challenge.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import com.challenge.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import com.challenge.data_api.model.CompanyInfoResponse
import com.challenge.data_api.model.LaunchesResponse
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


@RunWith(MockitoJUnitRunner::class)
class SpaceXRemoteSourceImplTest {
    private lateinit var cut: SpaceXRemoteSource

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


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        cut = SpaceXRemoteSourceImpl(
            apiService,
            companyInfoRepositoryMapper,
            launchesRepositoryMapper
        )
    }

    @Test
    fun `When getCompanyInfo then apiService invoked`() {
        runBlockingTest {
            // When
            given(apiService.getCompanyInfo()).willReturn(companyInfoResponse)

            cut.getCompanyInfo()

            // Then
            verify(apiService).getCompanyInfo()
        }
    }

    @Test
    fun `When getAllLaunches then apiService invoked`() {
        runBlockingTest {
            // When
            given(apiService.getAllLaunches()).willReturn(launches)

            cut.getAllLaunches()

            // Then
            verify(apiService).getAllLaunches()
        }
    }
}
