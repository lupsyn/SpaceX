package com.challenge.data.repository

import com.challenge.data.SpaceXLocalSource
import com.challenge.data.SpaceXRemoteSource
import com.challenge.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import com.challenge.data.mapper.LaunchesRepositoryToDomainModelMapper
import com.challenge.domain.SpaceXRepository
import com.challenge.domain.model.CompanyInfoDomainModel
import com.challenge.domain.model.LaunchDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SpaceXRepositoryImpl(
    private val spaceXRemoteSource: SpaceXRemoteSource,
    private val spaceXLocalSource: SpaceXLocalSource,
    private val companyInfoDomainMapper: CompanyInfoRepositoryToDomainModelMapper,
    private val launchesDomainMapper: LaunchesRepositoryToDomainModelMapper
) : SpaceXRepository {
    override suspend fun getCompanyInfo(): Flow<CompanyInfoDomainModel> =
        spaceXLocalSource.getCompanyInfo().flatMapConcat { companyInfoLocalModel ->
            (if (companyInfoLocalModel != null) {
                flow {
                    emit(companyInfoLocalModel)
                }
            } else {
                spaceXRemoteSource.getCompanyInfo()
                    .map {
                        spaceXLocalSource.insertCompanyInfo(it)
                        it
                    }
            }).map {
                companyInfoDomainMapper.toDomainModel(it)
            }
        }

    override suspend fun getAllLaunches(): Flow<List<LaunchDomainModel>> =
        spaceXLocalSource.getAllLaunches().flatMapConcat { allLaunchesCached ->
            if (allLaunchesCached != null) {
                flow {
                    emit(allLaunchesCached)
                }
            } else {
                spaceXRemoteSource.getAllLaunches()
                    .map {
                        spaceXLocalSource.insertLaunches(it)
                        it
                    }
            }.map { launchesDomainMapper.toDomainModel(it) }
        }
}
