package com.neo.androiduitestsespresso.beveragelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.neo.androiduitestsespresso.R;
import com.neo.androiduitestsespresso.beveragedetail.BeverageDetailActivity;
import com.neo.androiduitestsespresso.coffeeshoplist.CoffeeShopListActivity;
import com.neo.androiduitestsespresso.common.Beverage;

public class BeverageListActivity extends AppCompatActivity implements BeverageListView {

    @NonNull
    public static final String BEVERAGE_LIST_MODEL_BUNDLE_KEY = "BEVERAGE_LIST_MODEL_BUNDLE_KEY";

    @NonNull
    @SuppressWarnings("NullableProblems")
    private BeverageListViewPresenter presenter;

    @Nullable
    private RecyclerView beverageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        presenter = new BeverageListViewPresenter(this);
        presenter.onCreate(savedInstanceState);
    }

    public void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        beverageRecyclerView = (RecyclerView) findViewById(R.id.beverages_recycler);

        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.map_fab);
        floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickFloatingActionButton();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable(BEVERAGE_LIST_MODEL_BUNDLE_KEY, presenter.getBeverageListModel());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void startBeverageActivity(@NonNull Beverage beverage) {
        Intent beverageActivityIntent = new Intent(BeverageListActivity.this, BeverageDetailActivity.class);
        beverageActivityIntent.putExtra(BeverageDetailActivity.BEVERAGE_MODEL_BUNDLE_KEY, beverage);
        startActivity(beverageActivityIntent);
    }

    @Override
    public void startCoffeeShopListActivity() {
        Intent coffeeShopListActivityIntent = new Intent(this, CoffeeShopListActivity.class);
        startActivity(coffeeShopListActivityIntent);
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void displayBeverages(@NonNull BeverageListModel beverageListModel) {
        if (null != beverageRecyclerView) {
            BeverageAdapter adapter = new BeverageAdapter(presenter, beverageListModel.getBeverages());
            beverageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            beverageRecyclerView.setAdapter(adapter);
        }
    }
}
