package com.challenge.presentation.mapper

import com.challenge.domain.model.LaunchDomainModel
import com.challenge.domain.model.LinksDomainModel
import com.challenge.domain.model.RocketDomainModel
import com.challenge.presentation.model.LaunchUiModel
import com.challenge.presentation.model.LinksUiModel
import com.challenge.presentation.model.RocketUiModel
import com.challenge.utils.buildDate
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.MethodRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class LaunchesDomainToUiModelMapperTest {

    private lateinit var cut: LaunchesDomainToUiModelMapperImpl

    @Mock
    lateinit var dateTransformer: DateTransformer

    @get:Rule
    var rule: MethodRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        cut = LaunchesDomainToUiModelMapperImpl(dateTransformer)
    }

    @Test
    fun `Given 2 launches when toUiModel then return expected result`() {
        // Given
        val launches = listOf(
            LaunchDomainModel(
                "missionName",
                buildDate("2019-12-11T12:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                false
            ),
            LaunchDomainModel(
                "missionName2",
                buildDate("2020-12-07T12:00:00.000Z"),
                RocketDomainModel("rocketName2", "rocketType2"),
                LinksDomainModel("patchLink2", "wikipediaLink2", "videoLink2"),
                false
            )
        )

        val expected = listOf(
            LaunchUiModel(
                "missionName",
                "11-12-2019 at 12:00",
                true,
                "0",
                RocketUiModel("rocketName", "rocketType"),
                LinksUiModel("patchLink", "wikipediaLink", "videoLink"),
                false
            ),
            LaunchUiModel(
                "missionName2",
                "07-12-2020 at 12:00",
                false,
                "361",
                RocketUiModel("rocketName2", "rocketType2"),
                LinksUiModel("patchLink2", "wikipediaLink2", "videoLink2"),
                false
            )
        )

        given(dateTransformer.dateToDateString(buildDate("2019-12-11T12:00:00.000Z")))
            .willReturn("11-12-2019 at 12:00")
        given(dateTransformer.dateToDateString(buildDate("2020-12-07T12:00:00.000Z")))
            .willReturn("07-12-2020 at 12:00")

        given(dateTransformer.getDifferenceOfDays(buildDate("2019-12-11T12:00:00.000Z")))
            .willReturn("0")
        given(dateTransformer.getDifferenceOfDays(buildDate("2020-12-07T12:00:00.000Z")))
            .willReturn("361")

        given(dateTransformer.isPast(buildDate("2019-12-11T12:00:00.000Z")))
            .willReturn(true)
        given(dateTransformer.isPast(buildDate("2020-12-07T12:00:00.000Z")))
            .willReturn(false)

        // When
        val actualValue = cut.toUiModel(launches)

        // Then
        assertEquals(expected, actualValue)

    }

    @Test
    fun `Given launch when toUiModel then return expected result`() {
        // Given
        val launches = listOf(
            LaunchDomainModel(
                "missionName",
                buildDate("2019-12-13T13:00:00.000Z"),
                RocketDomainModel("rocketName", "rocketType"),
                LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                true
            )
        )
        val expected = listOf(
            LaunchUiModel(
                "missionName",
                "13-12-2019 at 13:00",
                false,
                "1",
                RocketUiModel("rocketName", "rocketType"),
                LinksUiModel("patchLink", "wikipediaLink", "videoLink"),
                true
            )
        )

        given(dateTransformer.dateToDateString(buildDate("2019-12-13T13:00:00.000Z")))
            .willReturn("13-12-2019 at 13:00")

        given(dateTransformer.getDifferenceOfDays(buildDate("2019-12-13T13:00:00.000Z")))
            .willReturn("1")

        // When
        val actualValue = cut.toUiModel(launches)

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Given no launches when toUiModel then return expected result`() {
        // Given
        val launches = emptyList<LaunchDomainModel>()
        val expected = emptyList<LaunchUiModel>()

        // When
        val actualValue = cut.toUiModel(launches)

        // Then
        assertEquals(expected, actualValue)
    }
}
