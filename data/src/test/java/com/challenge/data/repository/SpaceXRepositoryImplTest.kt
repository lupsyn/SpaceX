package com.challenge.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.MainCoroutineRule
import com.challenge.data.SpaceXRemoteSource
import com.challenge.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import com.challenge.data.mapper.LaunchesRepositoryToDomainModelMapper
import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.data.model.LaunchRepositoryModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SpaceXRepositoryImplTest {
    private lateinit var cut: SpaceXRepositoryImpl

    @Mock
    lateinit var spaceXRemoteSource: SpaceXRemoteSource

    @Mock
    lateinit var companyInfoDomainMapper: CompanyInfoRepositoryToDomainModelMapper

    @Mock
    lateinit var launchesDomainMapper: LaunchesRepositoryToDomainModelMapper

    @Mock
    lateinit var companyInfoModelFlows: Flow<CompanyInfoRepositoryModel>

    @Mock
    lateinit var launcheRepositoriesFlows: Flow<List<LaunchRepositoryModel>>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        cut =
            SpaceXRepositoryImpl(spaceXRemoteSource, companyInfoDomainMapper, launchesDomainMapper)
    }

    @Test
    fun `When getCompanyInfo then spaceXRemoteSource invoked`() {
        runBlockingTest {
            // When
            given(spaceXRemoteSource.getCompanyInfo()).willReturn(companyInfoModelFlows)

            cut.getCompanyInfo()

            // Then
            verify(spaceXRemoteSource).getCompanyInfo()
        }
    }

    @Test
    fun `When getAllLaunches then spaceXRemoteSource invoked`() {
        runBlockingTest {
            // When
            given(spaceXRemoteSource.getAllLaunches()).willReturn(launcheRepositoriesFlows)

            cut.getAllLaunches()

            // Then
            verify(spaceXRemoteSource).getAllLaunches()
        }
    }
}
