package com.challenge.data_db.model

import org.joda.time.DateTime

data class LaunchRepositoryModelEntity(
    val missionName: String,
    val launchDateLocal: DateTime,
    val rocket: RocketRepositoryModelEntity,
    val links: LinksRepositoryModelEntity,
    val launchSuccess: Boolean
)

data class RocketRepositoryModelEntity(
    val rocketName: String,
    val rocketType: String
)

data class LinksRepositoryModelEntity(
    val missionPatchSmall: String,
    val wikipedia: String,
    val videoLink: String
)
