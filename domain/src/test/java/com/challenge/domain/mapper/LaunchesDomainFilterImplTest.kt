package com.challenge.domain.mapper

import com.challenge.domain.model.LaunchDomainModel
import com.challenge.domain.model.LinksDomainModel
import com.challenge.domain.model.RocketDomainModel
import com.challenge.utils.buildDate
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class LaunchesDomainFilterImplTest {
    private lateinit var cut: LaunchesDomainFilterImpl

    @Before
    fun setUp() {
        cut = LaunchesDomainFilterImpl()
    }

    @Test
    fun `Given launches and filter Descendant flag when filter then return expected result`() {
        // Given
        val launchesDomainModel = listOf(
            LaunchDomainModel(
                "missionName0",
                buildDate("2019-12-12T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                false
            ),
            LaunchDomainModel(
                "missionName1",
                buildDate("2018-12-16T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                true
            ),
            LaunchDomainModel(
                "missionName2",
                buildDate("2019-12-11T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                true
            ),
            LaunchDomainModel(
                "missionName3",
                buildDate("2019-11-13T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                true
            )
        )
        val expected = listOf(
            LaunchDomainModel(
                "missionName3",
                buildDate("2019-11-13T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                true
            ),
            LaunchDomainModel(
                "missionName2",
                buildDate("2019-12-11T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                true
            )
        )
        val filterYear = 2019
        val ascendantOrder = false

        // When
        val actualValue = cut.filter(launchesDomainModel, filterYear, ascendantOrder)

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Given launches and filter Ascendant flag when filter then return expected result`() {
        // Given
        val launchesDomainModel = listOf(
            LaunchDomainModel(
                "missionName0",
                buildDate("2019-12-12T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                false
            ),
            LaunchDomainModel(
                "missionName1",
                buildDate("2018-12-16T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                true
            ),
            LaunchDomainModel(
                "missionName2",
                buildDate("2019-12-11T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                true
            ),
            LaunchDomainModel(
                "missionName3",
                buildDate("2019-11-13T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                true
            )
        )
        val expected = listOf(
            LaunchDomainModel(
                "missionName2",
                buildDate("2019-12-11T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                true
            ),
            LaunchDomainModel(
                "missionName3",
                buildDate("2019-11-13T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink", "redditLink"),
                true
            )
        )
        val filterYear = 2019
        val ascendantOrder = true

        // When
        val actualValue = cut.filter(launchesDomainModel, filterYear, ascendantOrder)

        // Then
        assertEquals(expected, actualValue)
    }
}
