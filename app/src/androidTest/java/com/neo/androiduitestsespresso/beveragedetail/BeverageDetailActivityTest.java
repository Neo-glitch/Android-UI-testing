package com.neo.androiduitestsespresso.beveragedetail;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.neo.androiduitestsespresso.R;
import com.neo.androiduitestsespresso.common.Beverage;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.LayoutAssertions.noEllipsizedText;
import static androidx.test.espresso.assertion.LayoutAssertions.noOverlaps;
import static androidx.test.espresso.assertion.PositionAssertions.isAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.neo.androiduitestsespresso.beveragedetail.BeverageDetailActivity.BEVERAGE_MODEL_BUNDLE_KEY;

public class BeverageDetailActivityTest {

    @NonNull
    private static final Beverage beverage = Beverage.fake();   // mock beverage object

    @Rule
    public ActivityTestRule<BeverageDetailActivity> activityTestRule =
        new ActivityTestRule<BeverageDetailActivity>(BeverageDetailActivity.class) {
            @Override
            protected Intent getActivityIntent() {
                Context targetContext = InstrumentationRegistry
                        .getInstrumentation()
                        .getTargetContext();
                // modify intent to startActivity by adding extras(Beverage obj)
                Intent result = new Intent(targetContext, BeverageDetailActivity.class);
                result.putExtra(BEVERAGE_MODEL_BUNDLE_KEY, beverage);
                return result;
            }
        };


    @Test
    public void testTitle_shouldBePositionedAboveBeverageImageView() {
        onView(withId(R.id.text_title)).check(isCompletelyAbove(withId(R.id.image_beverage)));
    }


    @Test
    public void testDescription_shouldBeSetToCorrectBeverageDescriptionText() {
        onView(withId(R.id.text_description)).check(matches(withText(beverage.getDescription())));
    }


    @Test
    public void testImage_shouldBeDisplayed() {
        onView(withId(R.id.image_beverage)).check(matches(isDisplayed()));
    }


    @Test
    public void testDescription_shouldNotContainEllipsizedText() {
        // ensures the View hierarchy has no cutOff TextViews.
        onView(withId(R.id.text_description)).check(noEllipsizedText());
    }


    @Test
    public void testBeverageDetailActivity_shouldNotHaveAnyOverlaps() {
        // ensures View passed has no tv or imageView that overlap oneAnother
        onView(withId(R.id.activity_beverage)).check(noOverlaps());
    }


}