package com.example.moviesandroidapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testShowListOfMoviesButtonExistsAndIsClickable() {
        // 1. Verify the "Show List of Movies" button is displayed on the main screen
        onView(withId(R.id.btnShowMovies))
            .check(matches(isDisplayed()))
            .check(matches(withText("Show List of Movies")))

        // 2. Perform click action to launch the Flutter module
        onView(withId(R.id.btnShowMovies))
            .perform(click())
    }
}
