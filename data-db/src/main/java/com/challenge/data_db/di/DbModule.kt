package com.challenge.data_db.di

import android.content.Context
import com.challenge.data.SpaceXLocalSource
import com.challenge.data_db.SpaceXDb
import com.challenge.data_db.SpaceXdbManager
import com.challenge.data_db.mappers.*
import com.challenge.data_db.repository.SpaceXLocalSourceImpl
import kotlinx.coroutines.FlowPreview

@FlowPreview
object DbModule {

    fun getSpaceXLocalRepository(
        applicationContext: Context,
        companyInfoRepositoryToDomainModelMapper: CompanyInfoEntityToRepositoryModelMapper,
        launchesRepositoryToDomainModelMapper: LaunchModelEntitiesToRepositoryModelMapper
    ): SpaceXLocalSource = SpaceXLocalSourceImpl(
        getDb(applicationContext),
        getDateUtils(),
        companyInfoRepositoryToDomainModelMapper,
        launchesRepositoryToDomainModelMapper
    )

    fun getDb(context: Context): SpaceXDb = SpaceXdbManager(context).database

    fun clearAllTables(context: Context) =  SpaceXdbManager(context).database.clearAllTables()

    fun getCompanyInfoRepositoryToDomainModelMapper(): CompanyInfoEntityToRepositoryModelMapper =
        CompanyInfoEntityToRepositoryModelMapperImpl(getDateUtils())

    fun getLaunchesRepositoryToDomainModelMapper(): LaunchModelEntitiesToRepositoryModelMapper =
        LaunchModelEntitiesToRepositoryModelMapperImpl(DateUtilsImpl())

    fun getDateUtils(): DateUtils = DateUtilsImpl()
}