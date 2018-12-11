package com.example.rikis.footballmatchscheduleapp

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.rikis.footballmatchscheduleapp.R.id.*
import org.hamcrest.Matchers.anything
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FootballAppsTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)


    @Test
    fun fotballAppsTest(){
        addFavMatch()
        addFavTeam()
        favorite()
    }

    //@Test
    fun addFavMatch() {
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withId(cmbLeagueLast)).perform(click())
        onData(anything()).atPosition(35).perform(click())
        onView(withId(listEventLast)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withId(listEventLast)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(listEventLast)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5,click()))
        Thread.sleep(10000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(ViewAssertions.matches(isDisplayed()))
        Espresso.pressBack()
        onView(withId(R.id.viewPager)).perform(swipeLeft())
        Thread.sleep(10000)
        onView(withId(cmbLeagueNext)).perform(click())
        onData(anything()).atPosition(24).perform(click())
        onView(withId(cmbLeagueNext)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withId(listEventNext)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(listEventNext)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5,click()))
        Thread.sleep(10000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(ViewAssertions.matches(isDisplayed()))
        Espresso.pressBack()
    }

    fun addFavTeam() {
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withId(teams)).perform(ViewActions.click())
        Thread.sleep(10000)
        onView(withId(R.id.spinnerLeague)).perform(click())
        onData(anything()).atPosition(40).perform(click())
        Thread.sleep(10000)
        onView(withId(list_team)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, ViewActions.click()))
        Thread.sleep(10000)
        onView(withId(R.id.viewPagerTeam)).check(matches(isDisplayed()))
        onView(withId(R.id.viewPagerTeam)).perform(swipeLeft())
        Thread.sleep(10000)
        onView(withId(listPlayers)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))
        onView(withId(listPlayers)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(8,click()))
        Thread.sleep(10000)
        Espresso.pressBack()
        Thread.sleep(10000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(ViewAssertions.matches(isDisplayed()))
        Espresso.pressBack()
        Thread.sleep(10000)
    }

    fun favorite(){
        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withId(favMatch)).perform(ViewActions.click())
        Thread.sleep(10000)
        removeFavMatch()
        removeFavTeam()
    }
    fun removeFavTeam(){

        onView(withId(R.id.viewPagerFav)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(R.id.viewPagerFav)).perform(swipeLeft())
        onView(withId(listFavTeam)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(listFavTeam)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(listFavTeam)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed to favorite"))
                .check(ViewAssertions.matches(isDisplayed()))
        Espresso.pressBack()
        onView(ViewMatchers.withId(swipeFavTeam)).perform(ViewActions.swipeDown())

    }

    fun removeFavMatch(){
        onView(withId(listFav)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(listFav)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(listFav)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed to favorite"))
                .check(ViewAssertions.matches(isDisplayed()))
        Espresso.pressBack()
        onView(ViewMatchers.withId(swipeId)).perform(ViewActions.swipeDown())
        onView(withId(listFav)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(listFav)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(listFav)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed to favorite"))
                .check(ViewAssertions.matches(isDisplayed()))
        Espresso.pressBack()
        onView(ViewMatchers.withId(swipeId)).perform(ViewActions.swipeDown())
    }

}