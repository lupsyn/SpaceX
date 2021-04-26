package com.challenge.domain.di

import com.challenge.domain.SpaceXRepository
import com.challenge.domain.mapper.LaunchesDomainFilter
import com.challenge.domain.mapper.LaunchesDomainFilterImpl
import com.challenge.domain.usecase.GetCompanyInfo
import com.challenge.domain.usecase.GetCompanyInfoImpl
import com.challenge.domain.usecase.GetLaunches
import com.challenge.domain.usecase.GetLaunchesImpl


object DomainModule {
    fun getLaunches(spaceXRepository: SpaceXRepository): GetLaunches =
        GetLaunchesImpl(spaceXRepository, getLaunchDomainFilter())

    fun getCompanyInfo(spaceXRepository: SpaceXRepository): GetCompanyInfo =
        GetCompanyInfoImpl(spaceXRepository)

    fun getLaunchDomainFilter(): LaunchesDomainFilter = LaunchesDomainFilterImpl()
}