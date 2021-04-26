package com.challenge.domain.usecase

import com.challenge.domain.model.LaunchDomainModel
import kotlinx.coroutines.flow.Flow

interface GetLaunches {
    suspend fun execute(
        yearFilterCriteria: Int,
        ascendantOrder: Boolean
    ): Flow<List<LaunchDomainModel>>
}

