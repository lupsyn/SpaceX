package com.challenge.presentation.mapper

import com.challenge.domain.model.LaunchDomainModel
import com.challenge.presentation.model.LaunchUiModel
import com.challenge.presentation.model.LinksUiModel
import com.challenge.presentation.model.RocketUiModel

interface LaunchesDomainToUiModelMapper {
    fun toUiModel(
        launchesDomainModel: List<LaunchDomainModel>
    ): List<LaunchUiModel>
}

class LaunchesDomainToUiModelMapperImpl(
    private val dateTransformer: DateTransformer
) : LaunchesDomainToUiModelMapper {
    override fun toUiModel(
        launchesDomainModel: List<LaunchDomainModel>
    ): List<LaunchUiModel> = launchesDomainModel.map { launchDomainModel ->
        val linksUiModel = LinksUiModel(
            missionPatchSmall = launchDomainModel.links.missionPatchSmall,
            wikipedia = launchDomainModel.links.wikipedia,
            videoLink = launchDomainModel.links.videoLink,
            reeditLink = launchDomainModel.links.reeditLink
        )

        val rocketUiModel = RocketUiModel(
            rocketName = launchDomainModel.rocket.rocketName,
            rocketType = launchDomainModel.rocket.rocketType
        )

        LaunchUiModel(
            missionName = launchDomainModel.missionName,
            launchDate = dateTransformer.dateToDateString(launchDomainModel.launchDate),
            rocket = rocketUiModel,
            links = linksUiModel,
            launchSuccess = launchDomainModel.launchSuccess,
            isPastLaunch = dateTransformer.isPast(launchDomainModel.launchDate),
            differenceOfDays = dateTransformer.getDifferenceOfDays(launchDomainModel.launchDate)
        )
    }
}
