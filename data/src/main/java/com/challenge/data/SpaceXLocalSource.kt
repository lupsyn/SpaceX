package com.challenge.data

import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.data.model.LaunchRepositoryModel
import kotlinx.coroutines.flow.Flow

interface SpaceXLocalSource {
    suspend fun getCompanyInfo(): Flow<CompanyInfoRepositoryModel?>
    suspend fun clearCompanyInfos()
    suspend fun insertCompanyInfo(info: CompanyInfoRepositoryModel)

    suspend fun clearLaunches()
    suspend fun insertLaunches(list: List<LaunchRepositoryModel>)
    suspend fun getAllLaunches(): Flow<List<LaunchRepositoryModel>?>
}