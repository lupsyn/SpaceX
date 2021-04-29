package com.challenge.data_api.mapper

import com.challenge.data.model.LaunchRepositoryModel
import com.challenge.data.model.LinksRepositoryModel
import com.challenge.data.model.RocketRepositoryModel
import com.challenge.data_api.model.LaunchesResponse
import com.challenge.data_api.model.LinksResponse
import com.challenge.data_api.model.RocketResponse
import org.joda.time.format.DateTimeFormat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mock
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class LaunchesResponseToRepositoryModelMapperImplTest(
    private val givenLaunches: List<LaunchesResponse>,
    private val expected: List<LaunchRepositoryModel>
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<List<Any>>> {
            return listOf(
                arrayOf(
                    listOf(
                        LaunchesResponse(
                            "missionName",
                            "2020-12-11T00:00:00.000Z",
                            RocketResponse("rocketId", "rocketName", "rocketType"),
                            LinksResponse(
                                "patchLink",
                                "wikipediaLink",
                                "videoLink",
                                "articleLink",
                                "pressKitLink",
                                ""
                            ),
                            upcoming = false,
                            tbd = false,
                            launchSuccess = false,
                            launchDetails = "details"
                        ),
                        LaunchesResponse(
                            "missionName2",
                            "2021-01-01T00:00:00.000Z",
                            RocketResponse("rocketId2", "rocketName2", "rocketType2"),
                            LinksResponse(
                                "patchLink2",
                                "wikipediaLink2",
                                "videoLink2",
                                "articleLink2",
                                "pressKitLink2",
                                ""
                            ),
                            upcoming = false,
                            tbd = false,
                            launchSuccess = false,
                            launchDetails = "details"

                        )
                    ),
                    listOf(
                        LaunchRepositoryModel(
                            "missionName",
                            buildDate("11-12-2020"),
                            RocketRepositoryModel("rocketName", "rocketType"),
                            LinksRepositoryModel(
                                "patchLink",
                                "wikipediaLink",
                                "videoLink",
                                ""
                            ),
                            false
                        ),
                        LaunchRepositoryModel(
                            "missionName2",
                            buildDate("01-01-2021"),
                            RocketRepositoryModel("rocketName2", "rocketType2"),
                            LinksRepositoryModel(
                                "patchLink2",
                                "wikipediaLink2",
                                "videoLink2",
                                ""
                            ),
                            false
                        )
                    )
                ),
                arrayOf(
                    listOf(
                        LaunchesResponse(
                            "missionName",
                            "1990-03-01T00:00:00.000Z",
                            RocketResponse("rocketId", "rocketName", "rocketType"),
                            LinksResponse(null, null, null, null, null, null),
                            upcoming = true,
                            tbd = false,
                            launchSuccess = true,
                            launchDetails = "details"
                        )
                    ),
                    listOf(
                        LaunchRepositoryModel(
                            "missionName",
                            buildDate("01-03-1990"),
                            RocketRepositoryModel("rocketName", "rocketType"),
                            LinksRepositoryModel(
                                "",
                                "",
                                "",
                                ""
                            ),
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

    private lateinit var underTest: LaunchesResponseToRepositoryModelMapperImpl

    @Mock
    lateinit var dateFormatter: DateFormatter

    @Before
    fun setUp() {
        dateFormatter = DateFormatterImpl()
        underTest = LaunchesResponseToRepositoryModelMapperImpl(dateFormatter)
    }

    @Test
    fun `Given launchResponseModels when toRepositoryModel then returns expected result`() {
        // When
        val actualValue = underTest.toRepositoryModel(givenLaunches)

        // Then
        assertEquals(expected, actualValue)
    }
}
