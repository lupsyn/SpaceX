package com.challenge.data.mapper

import com.challenge.data.model.LaunchRepositoryModel
import com.challenge.domain.model.LaunchDomainModel
import com.challenge.domain.model.LinksDomainModel
import com.challenge.domain.model.RocketDomainModel

interface LaunchesRepositoryToDomainModelMapper {
    fun toDomainModel(launchesRepositoryModel: List<LaunchRepositoryModel>): List<LaunchDomainModel>
}

class LaunchesRepositoryToDomainModelMapperImpl :
    LaunchesRepositoryToDomainModelMapper {
    override fun toDomainModel(
        launchesRepositoryModel: List<LaunchRepositoryModel>
    ): List<LaunchDomainModel> =
        launchesRepositoryModel.map { launchRepositoryModel ->
            val linksDomainModel = LinksDomainModel(
                missionPatchSmall = launchRepositoryModel.links.missionPatchSmall,
                wikipedia = launchRepositoryModel.links.wikipedia,
                videoLink = launchRepositoryModel.links.videoLink,
                reeditLink = launchRepositoryModel.links.reeditLink
            )

            val rocketDomainModel = RocketDomainModel(
                rocketName = launchRepositoryModel.rocket.rocketName,
                rocketType = launchRepositoryModel.rocket.rocketType
            )

            LaunchDomainModel(
                missionName = launchRepositoryModel.missionName,
                launchDate = launchRepositoryModel.launchDateLocal,
                rocket = rocketDomainModel,
                links = linksDomainModel,
                launchSuccess = launchRepositoryModel.launchSuccess
            )
        }
}
