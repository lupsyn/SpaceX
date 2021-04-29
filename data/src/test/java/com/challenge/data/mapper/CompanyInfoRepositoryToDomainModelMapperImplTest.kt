package com.challenge.data.mapper

import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.domain.model.CompanyInfoDomainModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class CompanyInfoRepositoryToDomainModelMapperImplTest(
    private val givenCompanyInfo: CompanyInfoRepositoryModel,
    private val expected: CompanyInfoDomainModel
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    CompanyInfoRepositoryModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        1,
                        23
                    ), CompanyInfoDomainModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        1,
                        23
                    )
                ),
                arrayOf(
                    CompanyInfoRepositoryModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        3,
                        27500000000
                    ), CompanyInfoDomainModel(
                        "name",
                        "founder",
                        "founded",
                        "employees",
                        3,
                        27500000000
                    )
                ),
                arrayOf(
                    CompanyInfoRepositoryModel(
                        "",
                        "",
                        "",
                        "",
                        0,
                        0
                    ), CompanyInfoDomainModel(
                        "",
                        "",
                        "",
                        "",
                        0,
                        0
                    )
                )
            )
        }
    }

    private lateinit var underTest: CompanyInfoRepositoryToDomainModelMapperImpl

    @Before
    fun setUp() {
        underTest = CompanyInfoRepositoryToDomainModelMapperImpl()
    }

    @Test
    fun `Given companyInfos when toDomainModel then returns expected result`() {
        // When
        val actualValue = underTest.toDomainModel(givenCompanyInfo)

        // Then
        assertEquals(expected, actualValue)
    }
}
