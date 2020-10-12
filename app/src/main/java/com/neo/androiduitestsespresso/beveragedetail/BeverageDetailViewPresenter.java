package com.neo.androiduitestsespresso.beveragedetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.neo.androiduitestsespresso.common.Beverage;

import static com.neo.androiduitestsespresso.beveragedetail.BeverageDetailActivity.BEVERAGE_MODEL_BUNDLE_KEY;

class BeverageDetailViewPresenter {

    @NonNull
    private BeverageDetailView beverageDetailView;

    @NonNull
    private BeverageDetailModel beverageDetailModel;

    BeverageDetailViewPresenter(@NonNull BeverageDetailView beverageDetailView) {
        this.beverageDetailView = beverageDetailView;
        beverageDetailModel = new BeverageDetailModel();
    }

    void onCreate(@Nullable Intent intent) {
        if (null != intent && null != intent.getExtras()) {
            Beverage beverage = getBeverageFromBundle(intent.getExtras());
            beverageDetailModel.setBeverage(beverage);
            beverageDetailView.displayBeverage(beverageDetailModel);
        }
    }

    @Nullable
    private Beverage getBeverageFromBundle(@NonNull Bundle extras) {
        return extras.getParcelable(BEVERAGE_MODEL_BUNDLE_KEY);
    }

}
