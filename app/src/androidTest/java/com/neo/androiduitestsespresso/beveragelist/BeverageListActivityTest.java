package com.neo.androiduitestsespresso.beveragelist;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.BoundedMatcher;

import com.neo.androiduitestsespresso.R;
import com.neo.androiduitestsespresso.beveragedetail.BeverageDetailActivity;
import com.neo.androiduitestsespresso.coffeeshoplist.CoffeeShopListActivity;
import com.neo.androiduitestsespresso.common.Beverage;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class BeverageListActivityTest {

    @Rule
    // intents test Rule
    public IntentsTestRule<BeverageListActivity> intentsTestRule = new IntentsTestRule<>(BeverageListActivity.class);

    @Before
    public void stubCoffeeShopListActivityIntent() {
        Intent intent = new Intent();
        ActivityResult coffeeShopListActivityResult = new ActivityResult(Activity.RESULT_OK, intent);
        // setup capture intent used to start TheActivity, and pass onActivity result as ok, since it's required
        intending(hasComponent(CoffeeShopListActivity.class.getName())).respondWith(coffeeShopListActivityResult);
    }


    @Test
    public void testMapFabClick_shouldOpenCoffeeShopListActivity() {
        onView(withId(R.id.map_fab)).perform(click());

        // verifies that an intent is sent by this Activity to the activity specified, an true when only one intent is sent
        intended(hasComponent(CoffeeShopListActivity.class.getName()));
    }


    @Test
    public void testBeverageClick_shouldOpenBeverageListActivity() {
        onView(withId(R.id.beverages_recycler))
                .perform(RecyclerViewActions.scrollToPosition(7));

        onView(withId(R.id.beverages_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));

        intended(hasComponent(BeverageDetailActivity.class.getName()));  // to verify that beverageDetailActivity is open now

        pressBack();
    }


    @Test
    public void testMapFloatingActionButton_shouldBeDisplayed() {
        onView(withId(R.id.map_fab)).check(matches(isDisplayed()));    // checks that fab is displayed
    }


    @Test
    public void testToolbarImageView_shouldHaveContentDescription() {
        // checks ImageView ContentDescription
        onView(withId(R.id.toolbar_image))
                .check(matches(withContentDescription(R.string.content_fox_mug)));
    }


    /**
     * The below test is trying to find a single AppCompatTextView, but since there are
     * multiple AppCompatTextViews in our view hierarchy, our test fails with an
     * AmbiguousViewMatcherException. We'll have to have to use a different ViewMatcher :-)
     */
    @Test
    public void testToolbarTitleText_shouldHaveCorrectText_fails() {
        onView(isAssignableFrom(AppCompatTextView.class))
                .check(matches(withText(R.string.app_name)));
    }


    /**
     * There we go! The AppCompatTextView we're looking for is a child of our Toolbar.
     * By combining matchers to specify this, Espresso is able to find our view and
     * perform our test.
     */
    @Test
    public void testToolbarTitleText_shouldHaveCorrectText() {
        // find View whose parent is toolbar and of type AppCompatTextView and match them for check
        onView(allOf(withParent(isAssignableFrom(Toolbar.class)), isAssignableFrom(AppCompatTextView.class)))
                .check(matches(withText(R.string.app_name)));
    }


    @Test
    public void testBeveragesRecyclerViewItem_shouldHaveBeverageData() {
        Beverage beverage = new Beverage(
                "Café au lait",
                "A drink made with French-pressed " +
                    "coffee and an added touch of hot milk." +
                    " This drink originates from - you guessed" +
                    " it! - France.",
                "R.drawable.ic_cafe_au_lait"
        );

        // does check using a custom matcher where checks if beverage is same as item index 0 of rv
        onView(withId(R.id.beverages_recycler))
                .check(matches(hasBeverageDataForPosition(0, beverage)));
    }


    //// custom Matcher that ret matcher of type View////
    private static Matcher<View> hasBeverageDataForPosition(final int position,
                                                            @NonNull final Beverage beverage) {
        // BoundedMatcher that match a view of type rv
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                // shown when match fails
                description.appendText("UH OH! Item has beverage data at position: " + position + " : ");
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                // ret true if view matches criteria else ret false
                if (null == recyclerView) {
                    return false;
                }

                // gets the ViewHolder for pos passed
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);

                if (null == viewHolder) {
                    return false;
                }

                // if viewholder retrieved has same name as beverage.getName() then rt true, else false
                return withChild(withText(beverage.getName())).matches(viewHolder.itemView);
            }
        };
    }




}