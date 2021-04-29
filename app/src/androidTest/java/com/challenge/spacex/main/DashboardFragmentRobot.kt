package com.challenge.spacex.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.challenge.spacex.R
import com.challenge.spacex.utils.RecyclerViewItemCountAssertion
import com.challenge.spacex.utils.RecyclerViewMatcher
import org.hamcrest.CoreMatchers.not

fun dashboardFragmentRobot(func: DashboardFragmentRobot.() -> Unit) =
    DashboardFragmentRobot().apply { func() }

class DashboardFragmentRobot {

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    fun assertRecyclerViewIsDisplayed() = apply {
        onView(recyclerViewMatcher).check(matches(isDisplayed()))
    }

    fun assertItemsSize() = apply {
        onView(recyclerViewMatcher).check(
            RecyclerViewItemCountAssertion(104)
        )
    }

    fun assertProgressBarBodyIsDisplayed() = apply {
        onView(progressBarBodyViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertToolbarIsDisplayed() = apply {
        onView(toolbarViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertFilterButtonIsDisplayed() = apply {
        onView(filterButtonViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun assertBodyErrorDisplayed() = apply {
        onView(bodyErrorViewMatcher).check(matches(isDisplayed()))
    }

    fun assertHeaderErrorDisplayed() = apply {
        onView(headerErrorViewMatcher).check(matches(isDisplayed()))
    }

    fun dialogYearViewMatcher() = apply {
        onView(dialogYearViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun youtubeLayoutViewMatcher() = apply {
        onView(youtubeIconViewMatcher).check(
            matches(
                isDisplayed()
            )
        )
    }

    fun clickItem(position: Int) = apply {
        val itemMatcher = RecyclerViewMatcher(recyclerViewId).atPosition(position)
        onView(itemMatcher).perform(ViewActions.click())
    }

    fun clickFilter() = apply {
        onView(filterButtonViewMatcher).perform(ViewActions.click())
    }

    companion object {

        private const val recyclerViewId = R.id.launches_recycler_view

        private val recyclerViewMatcher = withId(R.id.launches_recycler_view)

        private val progressBarBodyViewMatcher = withId(R.id.progress_bar_body)

        private val toolbarViewMatcher = withId(R.id.toolbar)

        private val filterButtonViewMatcher = withId(R.id.filter_button)

        private val dialogYearViewMatcher = withId(R.id.dialog_year)

        private val youtubeIconViewMatcher = withId(R.id.youtube)

        private val bodyErrorViewMatcher = withId(R.id.body_error_description)

        private val headerErrorViewMatcher = withId(R.id.header_error_description)
    }
}