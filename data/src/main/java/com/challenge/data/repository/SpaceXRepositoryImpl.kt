package com.challenge.data.repository

import com.challenge.data.SpaceXRemoteSource
import com.challenge.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import com.challenge.data.mapper.LaunchesRepositoryToDomainModelMapper
import com.challenge.domain.SpaceXRepository
import com.challenge.domain.model.CompanyInfoDomainModel
import com.challenge.domain.model.LaunchDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SpaceXRepositoryImpl(
    private val spaceXRemoteSource: SpaceXRemoteSource,
    private val companyInfoDomainMapper: CompanyInfoRepositoryToDomainModelMapper,
    private val launchesDomainMapper: LaunchesRepositoryToDomainModelMapper
) : SpaceXRepository {
    override suspend fun getCompanyInfo(): Flow<CompanyInfoDomainModel> =
        spaceXRemoteSource.getCompanyInfo()
            .map { companyInfoRepositoryModel ->
                companyInfoDomainMapper.toDomainModel(companyInfoRepositoryModel)
            }

    override suspend fun getAllLaunches(): Flow<List<LaunchDomainModel>> =
        spaceXRemoteSource.getAllLaunches().map { allLaunchesRepositoryModel ->
            launchesDomainMapper.toDomainModel(allLaunchesRepositoryModel)
        }
}
