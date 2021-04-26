package com.challenge.data_api

import com.challenge.data_api.model.CompanyInfoResponse
import com.challenge.data_api.model.LaunchesResponse
import retrofit2.http.GET

interface ApiService {
    @GET("info")
    suspend fun getCompanyInfo(): CompanyInfoResponse

    @GET("launches")
    suspend fun getAllLaunches(): List<LaunchesResponse>
}
