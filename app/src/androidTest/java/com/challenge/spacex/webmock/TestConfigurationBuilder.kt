package com.challenge.spacex.webmock

import androidx.test.platform.app.InstrumentationRegistry
import com.challenge.spacex.SpaceXTestApp

private fun requireTestedApplication() =
    (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as SpaceXTestApp)