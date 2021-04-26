package com.challenge.domain.usecase

import com.challenge.domain.model.CompanyInfoDomainModel
import kotlinx.coroutines.flow.Flow

interface GetCompanyInfo {
    suspend fun execute(): Flow<CompanyInfoDomainModel>
}

