package com.neo.androiduitestsespresso.coffeeshoplist;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

interface CoffeeShopListView {

    void showPermissionError();

    void sendShareIntent(@NonNull CoffeeShop coffeeShop);

    void displayPermissionRequest();

    void showMessage(@StringRes int message);

    void displayCoffeeShops(@NonNull CoffeeShopListModel coffeeShopListModel);

    void requestPermission(int requestCode, @NonNull String[] permissions);

    @NonNull
    Context getContext();

    void showShareButton();

    void hideShareButton();

    void openCoffeeShopDetailsActivity(CoffeeShop coffeeShop);

}
