package com.challenge.spacex.utils

import androidx.test.core.app.launchActivity
import com.challenge.spacex.ui.main.MainActivity
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class TestConfigurationRule : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)

        launchActivity<MainActivity>()
    }
}