package com.challenge.presentation.di

import com.challenge.presentation.mapper.*

object PresentationModule {

    fun getLaunchesDomainToUiModelMapper(
        dateTransformer: DateTransformer
    ): LaunchesDomainToUiModelMapper = LaunchesDomainToUiModelMapperImpl(dateTransformer)

    fun getDateTransformer(dateTimeProvider: DateTimeProvider): DateTransformer =
        DateTransformerImpl(dateTimeProvider)

    fun getDateTimeProvider(): DateTimeProvider = DateTimeProvider()

    fun getCompanyInfoDomainToUiModelMapper(): CompanyInfoDomainToUiModelMapper =
        CompanyInfoDomainToUiModelMapperImpl()
}