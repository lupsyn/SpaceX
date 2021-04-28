package com.challenge.spacex.ui.main

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.challenge.spacex.R
import com.challenge.spacex.ui.main.di.MainActivityModule

class MainActivity : AppCompatActivity() {

    private val fragmentFactory by lazy { MainActivityModule.getFragmentFactory(application) }
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