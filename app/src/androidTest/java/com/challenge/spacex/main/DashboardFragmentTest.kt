package com.challenge.spacex.main

import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.challenge.data_db.di.DbModule
import com.challenge.spacex.BuildConfig
import com.challenge.spacex.R
import com.challenge.spacex.ui.main.MainActivity
import com.challenge.spacex.utils.TestConfigurationRule
import com.challenge.spacex.utils.ViewVisibilityIdlingResource
import com.challenge.spacex.webmock.ErrorDispatcher
import com.challenge.spacex.webmock.SuccessDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@FlowPreview
@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @get:Rule
    val espressoRule = TestConfigurationRule()

    private val mockWebServer = MockWebServer()

    private var progressBarGoneIdlingResource: ViewVisibilityIdlingResource? = null

    @Before
    fun setup() {
        DbModule.clearAllTables(ApplicationProvider.getApplicationContext())
        mockWebServer.start(BuildConfig.PORT)
    }

    @After
    fun teardown() {
        //In perfect world this should be extrapolated as Base test, and all
        //cleaning should be done with a rule.
        mockWebServer.shutdown()
        DbModule.clearAllTables(ApplicationProvider.getApplicationContext())
        IdlingRegistry.getInstance().unregister(progressBarGoneIdlingResource)
    }

    @Test
    fun elementsVisibilityAfterOpeningTheScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()

        dashboardFragmentRobot {
            assertProgressBarBodyIsDisplayed()
            assertToolbarIsDisplayed()
            assertFilterButtonIsDisplayed()
        }
    }

    @Test
    fun itemsListed() {
        mockWebServer.dispatcher = SuccessDispatcher()
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar_body),
                View.GONE
            )

        dashboardFragmentRobot {
            waitForCondition(progressBarGoneIdlingResource)
            assertRecyclerViewIsDisplayed()
            assertItemsSize()
        }
    }

    @Test
    fun clickItemAndShowBottomSheet() {
        mockWebServer.dispatcher = SuccessDispatcher()
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar_body),
                View.GONE
            )

        dashboardFragmentRobot {
            waitForCondition(progressBarGoneIdlingResource)
            assertRecyclerViewIsDisplayed()
            clickItem(2)
            youtubeLayoutViewMatcher()
        }
    }

    @Test
    fun clickItemAndShowDialog() {
        mockWebServer.dispatcher = SuccessDispatcher()
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar_body),
                View.GONE
            )

        dashboardFragmentRobot {
            waitForCondition(progressBarGoneIdlingResource)
            assertRecyclerViewIsDisplayed()
            clickFilter()
            dialogYearViewMatcher()
        }
    }

    @Test
    fun displayBodyError() {
        mockWebServer.dispatcher = ErrorDispatcher()
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar_body),
                View.GONE
            )

        dashboardFragmentRobot {
            waitForCondition(progressBarGoneIdlingResource)
            assertBodyErrorDisplayed()
        }
    }

    @Test
    fun displayHeaderError() {
        mockWebServer.dispatcher = ErrorDispatcher()
        progressBarGoneIdlingResource =
            ViewVisibilityIdlingResource(
                activityTestRule.activity.findViewById(R.id.progress_bar_body),
                View.GONE
            )

        dashboardFragmentRobot {
            waitForCondition(progressBarGoneIdlingResource)
            assertHeaderErrorDisplayed()
        }
    }
}