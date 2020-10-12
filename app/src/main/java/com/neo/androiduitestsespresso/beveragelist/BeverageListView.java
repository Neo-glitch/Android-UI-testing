package com.neo.androiduitestsespresso.beveragelist;

import android.content.Context;

import androidx.annotation.NonNull;

import com.neo.androiduitestsespresso.common.Beverage;

interface BeverageListView {

    void startCoffeeShopListActivity();

    void startBeverageActivity(@NonNull Beverage beverage);

    void displayBeverages(@NonNull BeverageListModel beverageListModel);

    @NonNull
    Context getContext();

}
