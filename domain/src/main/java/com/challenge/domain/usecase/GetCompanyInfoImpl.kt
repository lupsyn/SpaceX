package com.challenge.domain.usecase

import com.challenge.domain.SpaceXRepository
import com.challenge.domain.model.CompanyInfoDomainModel
import kotlinx.coroutines.flow.Flow

class GetCompanyInfoImpl(private val spaceXRepository: SpaceXRepository) :
    GetCompanyInfo {
    override suspend fun execute(): Flow<CompanyInfoDomainModel> = spaceXRepository.getCompanyInfo()
}
