package com.challenge.data_api.mapper

import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.data_api.model.CompanyInfoResponse

interface CompanyInfoResponseToRepositoryModelMapper {
    fun toRepositoryModel(companyInfoResponse: CompanyInfoResponse): CompanyInfoRepositoryModel
}

class CompanyInfoResponseToRepositoryModelMapperImpl :
    CompanyInfoResponseToRepositoryModelMapper {
    override fun toRepositoryModel(companyInfoResponse: CompanyInfoResponse): CompanyInfoRepositoryModel =
        CompanyInfoRepositoryModel(
            name = companyInfoResponse.name,
            founder = companyInfoResponse.founder,
            founded = companyInfoResponse.founded,
            employees = companyInfoResponse.employees,
            launchSites = companyInfoResponse.launchSites,
            valuation = companyInfoResponse.valuation
        )
}
