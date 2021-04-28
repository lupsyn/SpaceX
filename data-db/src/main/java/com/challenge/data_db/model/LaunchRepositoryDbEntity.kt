package com.challenge.data_db.model

import org.joda.time.DateTime

data class LaunchRepositoryDbEntity(
    val missionName: String,
    val launchDateLocal: String,
    val rocket: RocketRepositoryDbEntity,
    val links: LinksRepositoryDbEntity,
    val launchSuccess: Boolean
)