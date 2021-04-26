package com.challenge.data_api.model

import com.google.gson.annotations.SerializedName

class LaunchesResponse(
    @SerializedName("mission_name")
    val missionName: String,
    @SerializedName("launch_date_utc")
    val launchDate: String,
    val rocket: RocketResponse,
    val links: LinksResponse,
    val upcoming: Boolean,
    val tbd: Boolean,
    @SerializedName("launch_success")
    val launchSuccess: Boolean,
    @SerializedName("details")
    val launchDetails: String?
)

data class RocketResponse(
    @SerializedName("rocket_id")
    val rockedId: String,
    @SerializedName("rocket_name")
    val rocketName: String,
    @SerializedName("rocket_type")
    val rocketType: String
)

data class LinksResponse(
    @SerializedName("mission_patch_small")
    val missionPatchSmall: String?,
    val wikipedia: String?,
    @SerializedName("video_link")
    val videoLink: String?,
    @SerializedName("article_link")
    val articleLink: String?,
    @SerializedName("presskit")
    val pressKitLink: String?,
    @SerializedName("reddit")
    val redditLink: String?
)
