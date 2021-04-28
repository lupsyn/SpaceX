package com.challenge.presentation.mapper

import com.challenge.domain.model.CompanyInfoDomainModel
import com.challenge.presentation.model.CompanyInfoUiModel

interface CompanyInfoDomainToUiModelMapper {
    fun toUiModel(companyInfoDomainModel: CompanyInfoDomainModel): CompanyInfoUiModel
}

class CompanyInfoDomainToUiModelMapperImpl : CompanyInfoDomainToUiModelMapper {
    override fun toUiModel(companyInfoDomainModel: CompanyInfoDomainModel): CompanyInfoUiModel =
        CompanyInfoUiModel(
            name = companyInfoDomainModel.name,
            founder = companyInfoDomainModel.founder,
            foundedYear = companyInfoDomainModel.founded,
            employees = companyInfoDomainModel.employees,
            launchSites = companyInfoDomainModel.launchSites,
            valuation = companyInfoDomainModel.valuation
        )
}
