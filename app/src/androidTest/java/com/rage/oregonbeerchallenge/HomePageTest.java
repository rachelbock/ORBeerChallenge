package com.rage.oregonbeerchallenge;

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
 * Tests that the home page is launched at application start.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomePageTest {
    @Rule
    public ActivityTestRule<MainActivity> actionBarActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    @Test
    public void testHomePageLaunched() {
        onView(withText(R.string.welcome_to_the_oregon_beer_challenge)).check(matches(isDisplayed()));
        onView(withId(R.id.home_fragment_view_breweries_fab)).perform(click());
        onView(withText(R.string.oregon_breweries)).check(matches(isDisplayed()));
    }
}
