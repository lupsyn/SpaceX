package com.challenge.data_db.repository

import com.challenge.data.SpaceXLocalSource
import com.challenge.data.model.CompanyInfoRepositoryModel
import com.challenge.data.model.LaunchRepositoryModel
import com.challenge.data_db.SpaceXDb
import com.challenge.data_db.mappers.CompanyInfoEntityToRepositoryModelMapper
import com.challenge.data_db.mappers.DateUtils
import com.challenge.data_db.mappers.LaunchModelEntitiesToRepositoryModelMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@FlowPreview
@ExperimentalCoroutinesApi
class SpaceXLocalSourceImpl(
    private val db: SpaceXDb,
    private val dateUtilsImpl: DateUtils,
    private val companyInfoRepositoryMapper: CompanyInfoEntityToRepositoryModelMapper,
    private val launchesRepositoryMapper: LaunchModelEntitiesToRepositoryModelMapper
) : SpaceXLocalSource {

    override suspend fun getCompanyInfo(): Flow<CompanyInfoRepositoryModel?> =
        db.companyInfoDao().getCachedResponse()
            .map { infoEntity ->
                if (infoEntity == null)
                    null
                infoEntity?.run {
                    if (isExpired(timeStamp)) {
                        clearCompanyInfos()
                        null
                    } else {
                        companyInfoRepositoryMapper.toRepositoryModel(this)
                    }
                }
            }


    override suspend fun clearCompanyInfos() = db.companyInfoDao().clearTable()

    override suspend fun insertCompanyInfo(info: CompanyInfoRepositoryModel) =
        db.companyInfoDao()
            .insertCachedResponse(companyInfoRepositoryMapper.modelToRepositoryEntity(info))

    override suspend fun clearLaunches() {
        db.launchModelEntitiesDao().clearTable()
    }

    override suspend fun insertLaunches(list: List<LaunchRepositoryModel>) {
        db.launchModelEntitiesDao()
            .insertResponse(launchesRepositoryMapper.launchRepositoryModelToEntity(list))
    }

    override suspend fun getAllLaunches(): Flow<List<LaunchRepositoryModel>?> =
        db.launchModelEntitiesDao().getCachedResponses()
            .map { launchModelEntities ->
                if (launchModelEntities == null) {
                    null
                }
                launchModelEntities?.run {
                    if (isExpired(timeStamp)) {
                        clearLaunches()
                        null
                    } else {
                        launchesRepositoryMapper.toRepositoryModel(this)
                    }
                }
            }


    private fun isExpired(entityTimestamp: Long): Boolean {
        return (dateUtilsImpl.getCurrentTimestamp() - entityTimestamp) > EXPIRING_TIME
    }

    companion object {
        const val EXPIRING_TIME = 5 * 60 * 1000
    }
}
