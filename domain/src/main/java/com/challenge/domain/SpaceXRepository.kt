package com.challenge.domain

import com.challenge.domain.model.CompanyInfoDomainModel
import com.challenge.domain.model.LaunchDomainModel
import kotlinx.coroutines.flow.Flow

interface SpaceXRepository {
    suspend fun getCompanyInfo(): Flow<CompanyInfoDomainModel>
    suspend fun getAllLaunches(): Flow<List<LaunchDomainModel>>
}