package com.challenge.data_db.data

import com.challenge.data.SpaceXRemoteSource
import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.data.model.LaunchRepositoryModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
class SpaceXRemoteSourceImpl() : SpaceXRemoteSource {

    private val companyInfoChannel = ConflatedBroadcastChannel<CompanyInfoRepositoryModel>()
    private val launchesChannel = ConflatedBroadcastChannel<List<LaunchRepositoryModel>>()

    override suspend fun getCompanyInfo(): Flow<CompanyInfoRepositoryModel> {
        //TODO : Retrieve from db
        return companyInfoChannel.asFlow()

    }

    override suspend fun getAllLaunches(): Flow<List<LaunchRepositoryModel>> {
        //TODO: Retrieve from db
        return launchesChannel.asFlow()
    }
}
