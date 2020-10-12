package com.neo.androiduitestsespresso.beveragelist;

import android.app.Instrumentation.ActivityMonitor;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.rule.ActivityTestRule;

import com.neo.androiduitestsespresso.R;
import com.neo.androiduitestsespresso.beveragedetail.BeverageDetailActivity;

import org.junit.Rule;
import org.junit.Test;


import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertNotNull;

public class NonEspressoBeverageListActivityTest {

    @Rule
    public ActivityTestRule<BeverageListActivity> activityTestRule = new ActivityTestRule<>(BeverageListActivity.class);

    @Test
    public void testBeverageClick_without_Espresso_shouldOpenBeverageDetailActivity() throws Throwable {
        // Setup
        ActivityMonitor activityMonitor = getInstrumentation()
                .addMonitor(BeverageDetailActivity.class.getName(), null, false);
        BeverageListActivity activity = activityTestRule.getActivity();
        final RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.beverages_recycler);

        // Act
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.getChildAt(0).performClick();
            }
        });

        // Verify
        BeverageDetailActivity beverageDetailActivity = (BeverageDetailActivity) getInstrumentation()
                .waitForMonitorWithTimeout(activityMonitor, 2000);
        assertNotNull(beverageDetailActivity);
        beverageDetailActivity.finish();
    }

}
