package com.challenge.data

import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.data.model.LaunchRepositoryModel
import kotlinx.coroutines.flow.Flow

interface SpaceXRemoteSource {
    suspend fun getCompanyInfo(): Flow<CompanyInfoRepositoryModel>
    suspend fun getAllLaunches(): Flow<List<LaunchRepositoryModel>>
}