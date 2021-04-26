package com.challenge.data_api.data

import com.challenge.data.SpaceXRemoteSource
import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.data.model.LaunchRepositoryModel
import com.challenge.data_api.ApiService
import com.challenge.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import com.challenge.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
class SpaceXRemoteSourceImpl(
    private val apiService: ApiService,
    private val companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper,
    private val launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper
) : SpaceXRemoteSource {

    private val companyInfoChannel = ConflatedBroadcastChannel<CompanyInfoRepositoryModel>()
    private val launchesChannel = ConflatedBroadcastChannel<List<LaunchRepositoryModel>>()

    override suspend fun getCompanyInfo(): Flow<CompanyInfoRepositoryModel> {
        val companyInfoResponse = apiService.getCompanyInfo()
        val companyInfoRepositoryModel =
            companyInfoRepositoryMapper.toRepositoryModel(companyInfoResponse)
        companyInfoChannel.offer(companyInfoRepositoryModel)
        return companyInfoChannel.asFlow()

    }

    override suspend fun getAllLaunches(): Flow<List<LaunchRepositoryModel>> {
        val allLaunchesResponse = apiService.getAllLaunches()
        val launchesRepositoryModel =
            launchesRepositoryMapper.toRepositoryModel(allLaunchesResponse)
        launchesChannel.offer(launchesRepositoryModel)
        return launchesChannel.asFlow()
    }
}
