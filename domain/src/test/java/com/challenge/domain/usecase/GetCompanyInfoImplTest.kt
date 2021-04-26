package com.challenge.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.MainCoroutineRule
import com.challenge.domain.SpaceXRepository
import com.challenge.domain.model.CompanyInfoDomainModel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class GetCompanyInfoImplTest {
    private lateinit var cut: GetCompanyInfoImpl

    @Mock
    lateinit var spaceXRepository: SpaceXRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        cut = GetCompanyInfoImpl(spaceXRepository)
    }

    @Test
    fun `When execute then returns expected CompanyInfoUiModel`() {
        runBlocking {
            // Given
            val companyInfoDomainModel = CompanyInfoDomainModel(
                "name",
                "founder",
                "foundedYear",
                "employees",
                1,
                30000
            )
            val channelCompanyInfo = ConflatedBroadcastChannel<CompanyInfoDomainModel>()

            channelCompanyInfo.offer(companyInfoDomainModel)

            val expected = CompanyInfoDomainModel(
                "name",
                "founder",
                "foundedYear",
                "employees",
                1,
                30000
            )
            given(spaceXRepository.getCompanyInfo()).willReturn(channelCompanyInfo.asFlow())

            val actualValue = cut.execute().first()

            verify(spaceXRepository).getCompanyInfo()

            assertEquals(expected, actualValue)
        }
    }
}
