package com.challenge.data.mapper

import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.domain.model.CompanyInfoDomainModel

interface CompanyInfoRepositoryToDomainModelMapper {
    fun toDomainModel(companyInfoRepositoryModel: CompanyInfoRepositoryModel): CompanyInfoDomainModel
}

class CompanyInfoRepositoryToDomainModelMapperImpl :
    CompanyInfoRepositoryToDomainModelMapper {
    override fun toDomainModel(companyInfoRepositoryModel: CompanyInfoRepositoryModel) =
        CompanyInfoDomainModel(
            name = companyInfoRepositoryModel.name,
            founder = companyInfoRepositoryModel.founder,
            founded = companyInfoRepositoryModel.founded,
            employees = companyInfoRepositoryModel.employees,
            launchSites = companyInfoRepositoryModel.launchSites,
            valuation = companyInfoRepositoryModel.valuation
        )
}
