package com.challenge.data.di

import com.challenge.data.SpaceXLocalSource
import com.challenge.data.SpaceXRemoteSource
import com.challenge.data.mapper.CompanyInfoRepositoryToDomainModelMapper
import com.challenge.data.mapper.CompanyInfoRepositoryToDomainModelMapperImpl
import com.challenge.data.mapper.LaunchesRepositoryToDomainModelMapper
import com.challenge.data.mapper.LaunchesRepositoryToDomainModelMapperImpl
import com.challenge.data.repository.SpaceXRepositoryImpl
import com.challenge.domain.SpaceXRepository

object DataModule {

    fun getSpaceXRepository(
        spaceXRemoteSource: SpaceXRemoteSource,
        spaceXLocalSource: SpaceXLocalSource,
        companyInfoRepositoryToDomainModelMapper: CompanyInfoRepositoryToDomainModelMapper,
        launchesRepositoryToDomainModelMapper: LaunchesRepositoryToDomainModelMapper
    ): SpaceXRepository = SpaceXRepositoryImpl(
        spaceXRemoteSource,
        spaceXLocalSource,
        companyInfoRepositoryToDomainModelMapper,
        launchesRepositoryToDomainModelMapper
    )

    fun getCompanyInfoRepositoryToDomainModelMapper(): CompanyInfoRepositoryToDomainModelMapper =
        CompanyInfoRepositoryToDomainModelMapperImpl()

    fun getLaunchesRepositoryToDomainModelMapper(): LaunchesRepositoryToDomainModelMapper =
        LaunchesRepositoryToDomainModelMapperImpl()

}
