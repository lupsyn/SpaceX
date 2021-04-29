package com.challenge.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.MainCoroutineRule
import com.challenge.data.SpaceXLocalSource
import com.challenge.data.SpaceXRemoteSource
import com.challenge.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import com.challenge.data.mapper.LaunchesRepositoryToDomainModelMapper
import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.domain.model.CompanyInfoDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SpaceXRepositoryImplTest {
    private lateinit var underTest: SpaceXRepositoryImpl

    @Mock
    lateinit var spaceXRemoteSource: SpaceXRemoteSource

    @Mock
    lateinit var spaceXLocalSource: SpaceXLocalSource

    @Mock
    lateinit var companyInfoDomainMapper: CompanyInfoRepositoryToDomainModelMapper

    @Mock
    lateinit var launchesDomainMapper: LaunchesRepositoryToDomainModelMapper

    @Mock
    lateinit var companyInfoModel: CompanyInfoRepositoryModel

    @Mock
    lateinit var companyInfoDomainModel: CompanyInfoDomainModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        underTest =
            SpaceXRepositoryImpl(
                spaceXRemoteSource,
                spaceXLocalSource,
                companyInfoDomainMapper,
                launchesDomainMapper
            )
    }

    @Test
    fun `emit local company info response if present`() {
        runBlockingTest {
            val emitMockFlow = flow { emit(companyInfoModel) }

            given(spaceXLocalSource.getCompanyInfo()).willReturn(emitMockFlow)
            given(companyInfoDomainMapper.toDomainModel(companyInfoModel)).willReturn(
                companyInfoDomainModel
            )

            underTest.getCompanyInfo().collect { assertEquals(it, companyInfoDomainModel) }

            verify(spaceXLocalSource).getCompanyInfo()
            verifyNoInteractions(spaceXRemoteSource)
        }
    }

    @Test
    fun `will hit remote company repository if cached response is null and persist response`() {
        runBlockingTest {
            val emitMockFlow = flow { emit(companyInfoModel) }
            val emitNull = flow { emit(null) }

            given(spaceXLocalSource.getCompanyInfo()).willReturn(emitNull)
            given(spaceXRemoteSource.getCompanyInfo()).willReturn(emitMockFlow)

            given(companyInfoDomainMapper.toDomainModel(companyInfoModel)).willReturn(
                companyInfoDomainModel
            )

            underTest.getCompanyInfo().collect {
                assertEquals(it, companyInfoDomainModel)
            }
            verify(spaceXLocalSource).insertCompanyInfo(companyInfoModel)
            verify(spaceXRemoteSource).getCompanyInfo()
        }
    }
}
