package com.challenge.data_db.mappers

import com.challenge.data.model.LaunchRepositoryModel
import com.challenge.data.model.LinksRepositoryModel
import com.challenge.data.model.RocketRepositoryModel
import com.challenge.data_db.entities.LaunchModelEntities
import com.challenge.data_db.model.LaunchRepositoryDbEntity
import com.challenge.data_db.model.LinksRepositoryDbEntity
import com.challenge.data_db.model.RocketRepositoryDbEntity

interface LaunchModelEntitiesToRepositoryModelMapper {
    fun toRepositoryModel(launchModelEntities: LaunchModelEntities): List<LaunchRepositoryModel>

    fun launchRepositoryModelToEntity(model: List<LaunchRepositoryModel>): LaunchModelEntities
}

class LaunchModelEntitiesToRepositoryModelMapperImpl(
    private val dateFormatter: DateUtils
) :
    LaunchModelEntitiesToRepositoryModelMapper {
    override fun toRepositoryModel(
        launchModelEntities: LaunchModelEntities
    ): List<LaunchRepositoryModel> {
        return launchModelEntities.launches.map { dbEntity ->

            val linksRepositoryModel = LinksRepositoryModel(
                missionPatchSmall = dbEntity.links.missionPatchSmall,
                wikipedia = dbEntity.links.wikipedia,
                videoLink = dbEntity.links.videoLink,
                reeditLink = dbEntity.links.redditLink
            )

            val rocketRepositoryModel = RocketRepositoryModel(
                rocketName = dbEntity.rocket.rocketName,
                rocketType = dbEntity.rocket.rocketType
            )

            LaunchRepositoryModel(
                missionName = dbEntity.missionName,
                launchDateLocal = dateFormatter.format(dbEntity.launchDateLocal),
                rocket = rocketRepositoryModel,
                links = linksRepositoryModel,
                launchSuccess = dbEntity.launchSuccess
            )
        }
    }

    override fun launchRepositoryModelToEntity(model: List<LaunchRepositoryModel>): LaunchModelEntities =
        LaunchModelEntities(timeStamp = dateFormatter.getCurrentTimestamp(),
            model.map {
                val rocketRepositoryDbEntity = RocketRepositoryDbEntity(
                    it.rocket.rocketName,
                    it.rocket.rocketType,
                )

                val linksRepositoryDbEntity = LinksRepositoryDbEntity(
                    it.links.missionPatchSmall,
                    it.links.wikipedia,
                    it.links.videoLink,
                    it.links.reeditLink
                )

                LaunchRepositoryDbEntity(
                    it.missionName,
                    dateFormatter.dateTimeToString(it.launchDateLocal),
                    rocketRepositoryDbEntity,
                    linksRepositoryDbEntity,
                    it.launchSuccess
                )
            })
}
