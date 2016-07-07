package com.rage.oregonbeerchallenge;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

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

    @Test
    public void testHomePageLaunched() {
        onView(withText("Welcome")).check(matches(isDisplayed()));
        onView(withId(R.id.home_fragment_view_breweries_button)).perform(click());
        onView(withText("Cascade")).check(matches(isDisplayed()));
    }
}
