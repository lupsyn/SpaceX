package com.challenge.data_api.mapper

import com.challenge.data_api.model.LaunchesResponse
import com.challenge.data.model.LaunchRepositoryModel
import com.challenge.data.model.LinksRepositoryModel
import com.challenge.data.model.RocketRepositoryModel

interface LaunchesResponseToRepositoryModelMapper {
    fun toRepositoryModel(launchesResponse: List<LaunchesResponse>): List<LaunchRepositoryModel>
}

const val DEFAULT_PATCH = "https://images2.imgbox.com/b0/fd/86Jem1C5_o.png"

class LaunchesResponseToRepositoryModelMapperImpl(
    private val dateFormatter: DateFormatter
) :
    LaunchesResponseToRepositoryModelMapper {
    override fun toRepositoryModel(
        launchesResponse: List<LaunchesResponse>
    ): List<LaunchRepositoryModel> {
        return launchesResponse.map { launchResponse ->

            val linksRepositoryModel = LinksRepositoryModel(
                missionPatchSmall = launchResponse.links.missionPatchSmall ?: DEFAULT_PATCH,
                wikipedia = launchResponse.links.wikipedia.orEmpty(),
                videoLink = launchResponse.links.videoLink.orEmpty()
            )

            val rocketRepositoryModel = RocketRepositoryModel(
                rocketName = launchResponse.rocket.rocketName,
                rocketType = launchResponse.rocket.rocketType
            )

            LaunchRepositoryModel(
                missionName = launchResponse.missionName,
                launchDateLocal = dateFormatter.format(launchResponse.launchDate),
                rocket = rocketRepositoryModel,
                links = linksRepositoryModel,
                launchSuccess = launchResponse.launchSuccess
            )
        }
    }
}
