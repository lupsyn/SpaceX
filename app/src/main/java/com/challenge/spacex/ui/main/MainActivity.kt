package com.challenge.spacex.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.challenge.spacex.R
import com.challenge.spacex.ui.main.di.MainActivityModule

class MainActivity : AppCompatActivity() {

    private val fragmentFactory by lazy { MainActivityModule.getFragmentFactory() }
    private val navigationController by lazy { MainActivityModule.getNavigationController(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.fragmentFactory = fragmentFactory

        setContentView(R.layout.main_activity)
    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()
}