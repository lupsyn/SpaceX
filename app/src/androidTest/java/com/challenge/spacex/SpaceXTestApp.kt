package com.challenge.spacex

import com.challenge.data_api.ApiService
import com.challenge.data_api.di.ApiModule
import com.challenge.spacex.ui.main.SpaceXApp

class SpaceXTestApp : SpaceXApp() {

    private val testBaseUrl: String = "http://127.0.0.1:${BuildConfig.PORT}"

    override val apiService: ApiService
        get() =  ApiModule.getApi(applicationContext, testBaseUrl)
}