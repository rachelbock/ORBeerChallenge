package com.rage.oregonbeerchallenge;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests display of brewery detail page when brewery row is selected.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BreweryDetailPageTest {

    @Rule
    public ActivityTestRule<MainActivity> actionBarActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void breweryDetailPageTestFromHomeFragment(){
        onView(withId(R.id.home_fragment_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText(R.string.beer_list)).check(matches(isDisplayed()));
    }

    @Test
    public void breweryDetailPageTestFromBreweryFragment() throws InterruptedException {
        onView(withId(R.id.home_fragment_view_breweries_fab)).perform(click());
        onView(withText(R.string.oregon_breweries)).check(matches(isDisplayed()));
        Thread.sleep(5000);
        onView(withId(R.id.breweries_fragment_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText(R.string.beer_list)).check(matches(isDisplayed()));
    }
}
