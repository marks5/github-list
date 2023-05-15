package com.example.githublist

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githublist.helper.CustomMatchers.Companion.withItemAtPositionAndLogin
import com.example.githublist.helper.GetCurrentActivity.getCurrentActivity
import com.example.githublist.helper.IdlingResourceHelper
import com.example.githublist.helper.RecyclerViewItemCountAssertion
import com.example.githublist.mock.NetworkMockRule
import com.example.githublist.presentation.list.ListUsersActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class UITests {

    companion object {
        private const val IDLING_RESOURCE_NAME = "dataLoad"
    }
    private var idlingResource: IdlingResource? = null

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val networkMockRule = NetworkMockRule()

    @get:Rule
    val rule = activityScenarioRule<ListUsersActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        registerIdlingResourceToWaitItems(3)
    }

    @Test
    fun whenDataIsLoadedThenUsersAreShown() {
        onView(withId(R.id.list)).check(matches(isDisplayed()))
        onView(withId(R.id.list)).check(RecyclerViewItemCountAssertion(3))
        onView(withId(R.id.search)).check(matches(isDisplayed()))
        onView(withId(R.id.search)).check(matches(withHint("Search Users")))
    }

    @Test
    fun whenUserIsSelectedNavigatesToUserDetails() {
        // when
        onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    click()
                ))
    }

    @Test
    fun whenFilterIsUsedThenListIsUpdated() {
        // given
        onView(withId(R.id.list)).check(RecyclerViewItemCountAssertion(3))
        IdlingRegistry.getInstance().unregister(idlingResource)

        // when
        onView(withId(R.id.search)).perform(typeText("t"))

        registerIdlingResourceToWaitItems(2)
        // then
        onView(withId(R.id.list)).check(RecyclerViewItemCountAssertion(2))

        onView(withId(R.id.list))
            .check(matches(withItemAtPositionAndLogin(0, "defunkt")))
        onView(withId(R.id.list))
            .check(matches(withItemAtPositionAndLogin(1, "pjhyett")))
    }

    private fun registerIdlingResourceToWaitItems(itemCount: Int) {
        idlingResource = IdlingResourceHelper.createIdlingResource(IDLING_RESOURCE_NAME)
        { isIdle(itemCount) }
        IdlingRegistry.getInstance().register(idlingResource)
    }

    private fun isIdle(itemCount: Int) : Boolean {
        val activity = getCurrentActivity() ?: return false
        val recyclerView: RecyclerView? = activity.findViewById(R.id.list)
        return recyclerView?.adapter?.itemCount == itemCount
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}