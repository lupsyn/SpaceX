package com.challenge.spacex.ui.main

import android.app.Activity
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.challenge.spacex.R
import com.challenge.spacex.ui.main.di.MainActivityModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val fragmentFactory by lazy { MainActivityModule.getFragmentFactory(applicationContext as SpaceXApp) }
    private val navigationController by lazy { MainActivityModule.getNavigationController(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.fragmentFactory = fragmentFactory

        setContentView(R.layout.main_activity)
        setStatusBarColor(R.color.colorStatusBar)
    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()

}

fun Activity.setStatusBarColor(@ColorRes colorResId: Int) {
    window.statusBarColor = ContextCompat.getColor(this, colorResId)
}