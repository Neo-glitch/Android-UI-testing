package com.neo.androiduitestsespresso.coffeeshopdetail;

import android.content.Intent;
import android.net.Uri;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.web.webdriver.Locator;

import com.neo.androiduitestsespresso.R;
import com.neo.androiduitestsespresso.coffeeshoplist.CoffeeShop;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

public class CoffeeShopDetailActivityTest {

    public static final CoffeeShop fakeCoffeeShop = CoffeeShop.fake(0);

    @Rule
    public IntentsTestRule<CoffeeShopDetailActivity> intentsTestRule = new IntentsTestRule<CoffeeShopDetailActivity>(CoffeeShopDetailActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent intent = super.getActivityIntent();

            CoffeeShopDetailModel fakeModel = new CoffeeShopDetailModel();
            fakeModel.setCoffeeShop(fakeCoffeeShop);

            intent.putExtra(CoffeeShopDetailActivity.COFFEE_SHOP_BUNDLE_KEY, fakeCoffeeShop);
            return intent;
        }

        @Override
        protected void afterActivityLaunched() {
            super.afterActivityLaunched();
            // init js for webView tests, just for study and test will fail since no webView present with that id
            onWebView(withId(R.id.webView)).forceJavascriptEnabled();
        }
    };

    @Test
    public void testMapButton_SendsMapViewLocationIntent() {
        Uri desiredUri = Uri.parse("geo:0,0?q=" + fakeCoffeeShop.getAddress());

        onView(withId(R.id.button_map)).perform(click());

        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(desiredUri))
        );
    }

    @Test
    // will fail to, just for study purposes
    public void testClickWebsiteButton_OpenWebViewCoffeeShopUrl() {
        onView(withId(R.id.button_website)).perform(click());   // opens up the webView

        onWebView()
                .withElement(findElement(Locator.ID, "fake Header id"))  // find header with that id
                .check(webMatches(getText(), containsString("Coffee shop")));   // checks text of header contains coffee shop

        onWebView().reset();

        onWebView()
                .withElement(findElement(Locator.ID, "fake Header id"))  // find header with that id
                .perform(webClick());    // sim click on header

        onView(withText(R.string.app_name));

    }

}