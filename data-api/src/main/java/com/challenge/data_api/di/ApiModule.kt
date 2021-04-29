package com.challenge.data_api.di

import android.content.Context
import com.challenge.data.SpaceXRemoteSource
import com.challenge.data_api.ApiService
import com.challenge.data_api.data.SpaceXRemoteSourceImpl
import com.challenge.data_api.mapper.*
import retrofit2.Retrofit


object ApiModule {
    fun getSpaceXRemoteSource(
        apiService: ApiService,
        companyInfoRepositoryMapper: CompanyInfoResponseToRepositoryModelMapper,
        launchesRepositoryMapper: LaunchesResponseToRepositoryModelMapper
    ): SpaceXRemoteSource =
        SpaceXRemoteSourceImpl(apiService, companyInfoRepositoryMapper, launchesRepositoryMapper)

    fun getCompanyInfoResponseToRepositoryModelMapper(): CompanyInfoResponseToRepositoryModelMapper =
        CompanyInfoResponseToRepositoryModelMapperImpl()

    fun getLaunchesResponseToRepositoryModelMapper(
        dateFormatter: DateFormatter
    ): LaunchesResponseToRepositoryModelMapper =
        LaunchesResponseToRepositoryModelMapperImpl(dateFormatter)

    fun getDateFormatter(): DateFormatter =
        DateFormatterImpl()

    fun getApi(
        applicationContext: Context,
        apiUrl: String
    ): ApiService =
        getRetrofit(applicationContext, apiUrl)
            .create(ApiService::class.java)

    private fun getRetrofit(
        applicationContext: Context,
        apiUrl: String
    ): Retrofit =
        NetworkModule.getRetrofitBuilder(baseUrl = apiUrl)
            .client(NetworkModule.getHttpBuilder(applicationContext).build())
            .build()

}