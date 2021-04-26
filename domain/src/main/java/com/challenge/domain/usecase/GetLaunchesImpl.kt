package com.challenge.domain.usecase

import com.challenge.domain.SpaceXRepository
import com.challenge.domain.mapper.LaunchesDomainFilter
import com.challenge.domain.model.LaunchDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLaunchesImpl(
    private val spaceXRepository: SpaceXRepository,
    private val launchesDomainFilter: LaunchesDomainFilter
) : GetLaunches {
    override suspend fun execute(
        yearFilterCriteria: Int,
        ascendantOrder: Boolean
    ): Flow<List<LaunchDomainModel>> =
        spaceXRepository.getAllLaunches().map { allLaunchesDomainModel ->
            launchesDomainFilter.filter(
                allLaunchesDomainModel,
                yearFilterCriteria,
                ascendantOrder
            )
        }
}
