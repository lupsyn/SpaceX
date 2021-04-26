package com.challenge.domain.model

import org.joda.time.DateTime

data class LaunchDomainModel(
    val missionName: String,
    val launchDate: DateTime,
    val rocket: RocketDomainModel,
    val links: LinksDomainModel,
    val launchSuccess: Boolean
)