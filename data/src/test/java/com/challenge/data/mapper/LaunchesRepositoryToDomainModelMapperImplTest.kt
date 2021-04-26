package com.challenge.data.mapper

import com.challenge.data.model.LaunchRepositoryModel
import com.challenge.data.model.LinksRepositoryModel
import com.challenge.data.model.RocketRepositoryModel
import com.challenge.domain.model.LaunchDomainModel
import com.challenge.domain.model.LinksDomainModel
import com.challenge.domain.model.RocketDomainModel
import org.joda.time.format.DateTimeFormat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class LaunchesRepositoryToDomainModelMapperImplTest(
    private val givenLaunches: List<LaunchRepositoryModel>,
    private val expected: List<LaunchDomainModel>
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<List<Any>>> {
            return listOf(
                arrayOf(
                    listOf(
                        LaunchRepositoryModel(
                            "missionName",
                            buildDate("11-12-2019"),
                            RocketRepositoryModel("rocketName", "rocketType"),
                            LinksRepositoryModel("patchLink", "wikipediaLink", "videoLink"),
                            false
                        ),
                        LaunchRepositoryModel(
                            "missionName2",
                            buildDate("11-12-2020"),
                            RocketRepositoryModel("rocketName2", "rocketType2"),
                            LinksRepositoryModel("patchLink2", "wikipediaLink2", "videoLink2"),
                            false
                        )
                    )
                    ,
                    listOf(
                        LaunchDomainModel(
                            "missionName",
                            buildDate("11-12-2019"),
                            RocketDomainModel("rocketName", "rocketType"),
                            LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                            false
                        ),
                        LaunchDomainModel(
                            "missionName2",
                            buildDate("11-12-2020"),
                            RocketDomainModel("rocketName2", "rocketType2"),
                            LinksDomainModel("patchLink2", "wikipediaLink2", "videoLink2"),
                            false
                        )
                    )
                ),
                arrayOf(
                    listOf(
                        LaunchRepositoryModel(
                            "missionName",
                            buildDate("01-02-2010"),
                            RocketRepositoryModel("rocketName", "rocketType"),
                            LinksRepositoryModel("patchLink", "wikipediaLink", "videoLink"),
                            true
                        )
                    ),
                    listOf(
                        LaunchDomainModel(
                            "missionName",
                            buildDate("01-02-2010"),
                            RocketDomainModel("rocketName", "rocketType"),
                            LinksDomainModel("patchLink", "wikipediaLink", "videoLink"),
                            true
                        )
                    )
                ),
                arrayOf(emptyList(), emptyList())
            )
        }

        private fun buildDate(dateValue: String) =
            DateTimeFormat.forPattern("dd-MM-yyyy").parseDateTime(dateValue)
    }

    private lateinit var cut: LaunchesRepositoryToDomainModelMapperImpl

    @Before
    fun setUp() {
        cut = LaunchesRepositoryToDomainModelMapperImpl()
    }

    @Test
    fun `Given launchRepositoryModels when toDomainModel then returns expected result`() {
        // When
        val actualValue = cut.toDomainModel(givenLaunches)

        // Then
        assertEquals(expected, actualValue)
    }
}
