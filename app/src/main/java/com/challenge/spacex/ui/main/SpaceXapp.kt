package com.challenge.spacex.ui.main

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.challenge.data_api.ApiService
import com.challenge.data_api.di.ApiModule

open class SpaceXApp : Application() {

    @VisibleForTesting
    open val apiService: ApiService
        get() {
            return ApiModule.getApi(this, BASE_URL)
        }

    companion object {
        const val BASE_URL = "https://api.spacexdata.com/v3/"
    }
}