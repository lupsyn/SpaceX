package com.challenge.data_db.mappers

import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.data_db.entities.CompanyInfoEntity

interface CompanyInfoEntityToRepositoryModelMapper {
    fun toRepositoryModel(companyInfoEntity: CompanyInfoEntity): CompanyInfoRepositoryModel
    fun modelToRepositoryEntity(companyInfoRepositoryModel: CompanyInfoRepositoryModel): CompanyInfoEntity
}

class CompanyInfoEntityToRepositoryModelMapperImpl(private val dateUtils: DateUtils) :
    CompanyInfoEntityToRepositoryModelMapper {
    override fun toRepositoryModel(companyInfoEntity: CompanyInfoEntity): CompanyInfoRepositoryModel =
        CompanyInfoRepositoryModel(
            name = companyInfoEntity.name,
            founder = companyInfoEntity.founder,
            founded = companyInfoEntity.founded,
            employees = companyInfoEntity.employees,
            launchSites = companyInfoEntity.launchSites,
            valuation = companyInfoEntity.valuation
        )

    override fun modelToRepositoryEntity(companyInfoRepositoryModel: CompanyInfoRepositoryModel): CompanyInfoEntity =
        CompanyInfoEntity(
            name = companyInfoRepositoryModel.name,
            founder = companyInfoRepositoryModel.founder,
            founded = companyInfoRepositoryModel.founded,
            employees = companyInfoRepositoryModel.employees,
            launchSites = companyInfoRepositoryModel.launchSites,
            valuation = companyInfoRepositoryModel.valuation,
            timeStamp = dateUtils.getCurrentTimestamp()
        )
}
