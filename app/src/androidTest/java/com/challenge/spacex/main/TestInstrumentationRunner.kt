package com.challenge.spacex.main

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.challenge.spacex.SpaceXTestApp

@Suppress("unused") // set as a testInstrumentationRunner by full class name
class TestInstrumentationRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, SpaceXTestApp::class.java.name, context)
    }
}