package com.challenge.data_api.model

import com.google.gson.annotations.SerializedName

data class CompanyInfoResponse(
    val name: String,
    val founder: String,
    val founded: String,
    val employees: String,
    @SerializedName("test_sites")
    val testSites: Int,
    @SerializedName("launch_sites")
    val launchSites: Int,
    val valuation: Long
)
