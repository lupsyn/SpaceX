package com.challenge.domain.usecase

import com.challenge.domain.SpaceXRepository
import com.challenge.domain.mapper.LaunchesDomainFilter
import com.challenge.domain.model.LaunchDomainModel
import com.challenge.domain.model.LinksDomainModel
import com.challenge.domain.model.RocketDomainModel
import com.challenge.utils.buildDate
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class GetLaunchesImplTest {
    private lateinit var underTest: GetLaunchesImpl

    @Mock
    lateinit var spaceXRepository: SpaceXRepository


    @Mock
    lateinit var launchesDomainFilter: LaunchesDomainFilter

    @Before
    fun setUp() {
        underTest = GetLaunchesImpl(spaceXRepository, launchesDomainFilter)
    }

    @Test
    fun `When execute then returns expected launchUiModels`() {
        runBlocking {
            // Given
            val launchDomainModels = listOf(
                LaunchDomainModel(
                    "missionName",
                    buildDate("2019-12-11T12:00:00.000Z"),
                    RocketDomainModel("rocketName", "rocketType"),
                    LinksDomainModel("patchLink", "wikipediaLink", "videoLink","redditLink"),
                    false
                ),
                LaunchDomainModel(
                    "missionName2",
                    buildDate("2020-12-07T12:00:00.000Z"),
                    RocketDomainModel("rocketName2", "rocketType2"),
                    LinksDomainModel("patchLink2", "wikipediaLink2", "videoLink2","redditLink"),
                    false
                )
            )
            val launchesChannel = ConflatedBroadcastChannel<List<LaunchDomainModel>>()
            launchesChannel.offer(launchDomainModels)
            val expected = listOf(
                LaunchDomainModel(
                    "missionName",
                    buildDate("2019-12-11T12:00:00.000Z"),
                    RocketDomainModel("rocketName", "rocketType"),
                    LinksDomainModel("patchLink", "wikipediaLink", "videoLink","redditLink"),
                    false
                ),
                LaunchDomainModel(
                    "missionName2",
                    buildDate("2020-12-07T12:00:00.000Z"),
                    RocketDomainModel("rocketName2", "rocketType2"),
                    LinksDomainModel("patchLink2", "wikipediaLink2", "videoLink2","redditLink"),
                    false
                )
            )

            given(spaceXRepository.getAllLaunches()).willReturn(launchesChannel.asFlow())
            given(launchesDomainFilter.filter(launchDomainModels, -1, false)).willReturn(
                launchDomainModels
            )

            // When
            val actualValue = underTest.execute(-1, false).first()

            // Then
            verify(spaceXRepository).getAllLaunches()
            assertEquals(expected, actualValue)
        }
    }
}
