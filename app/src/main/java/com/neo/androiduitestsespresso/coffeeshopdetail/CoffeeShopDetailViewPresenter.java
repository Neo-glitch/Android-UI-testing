package com.neo.androiduitestsespresso.coffeeshopdetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.neo.androiduitestsespresso.coffeeshoplist.CoffeeShop;

import static com.neo.androiduitestsespresso.coffeeshopdetail.CoffeeShopDetailActivity.COFFEE_SHOP_BUNDLE_KEY;

class CoffeeShopDetailViewPresenter {

    @NonNull
    private final CoffeeShopDetailView coffeeShopDetailView;

    @NonNull
    private final CoffeeShopDetailModel coffeeShopDetailModel;

    CoffeeShopDetailViewPresenter(@NonNull CoffeeShopDetailView coffeeShopDetailView) {
        this.coffeeShopDetailView = coffeeShopDetailView;
        coffeeShopDetailModel = new CoffeeShopDetailModel();
    }

    void onClickMapButton() {
        coffeeShopDetailView.sendMapsIntent(coffeeShopDetailModel);
    }

    void onCreate(@Nullable Intent intent) {
        if (null != intent && null != intent.getExtras()) {
            CoffeeShop coffeeShop = getCoffeeShopFromBundle(intent.getExtras());
            coffeeShopDetailModel.setCoffeeShop(coffeeShop);
            coffeeShopDetailView.displayCoffeeShop(coffeeShopDetailModel);
        }
    }

    void onClickWebsiteButton() {
        //todo: open webview
    }

    private CoffeeShop getCoffeeShopFromBundle(@NonNull Bundle extras) {
        return extras.getParcelable(COFFEE_SHOP_BUNDLE_KEY);
    }
}
