package com.challenge.data_api.data

import com.challenge.data.SpaceXRemoteSource
import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.data.model.LaunchRepositoryModel
import com.challenge.data_api.ApiService
import com.challenge.data_api.mapper.CompanyInfoResponseToRepositoryModelMapper
import com.challenge.data_api.mapper.LaunchesResponseToRepositoryModelMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@FlowPreview
@ExperimentalCoroutinesApi
class SpaceXRemoteSourceImpl(
    private val apiService: ApiService,
    private val companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper,
    private val launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper
) : SpaceXRemoteSource {
    override suspend fun getCompanyInfo(): Flow<CompanyInfoRepositoryModel> = flow {
        val companyInfoResponse = apiService.getCompanyInfo()
        val companyInfoRepositoryModel =
            companyInfoRepositoryMapper.toRepositoryModel(companyInfoResponse)
        emit(companyInfoRepositoryModel)
    }


   override suspend fun getAllLaunches(): Flow<List<LaunchRepositoryModel>> = flow {
        val allLaunchesResponse = apiService.getAllLaunches()
        val launchesRepositoryModel =
            launchesRepositoryMapper.toRepositoryModel(allLaunchesResponse)
        emit(launchesRepositoryModel)
    }
}
