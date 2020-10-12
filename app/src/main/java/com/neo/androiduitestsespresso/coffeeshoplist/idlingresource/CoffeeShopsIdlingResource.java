package com.neo.androiduitestsespresso.coffeeshoplist.idlingresource;


import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;


public class CoffeeShopsIdlingResource implements IdlingResource {

    @Nullable
    private ResourceCallback resourceCallback;

    private boolean isIdle = false;

    @Override
    public String getName() {
        return CoffeeShopsIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(@Nullable ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public void onSearchCompleted() {
        isIdle = true;
        notifyResourceCallback();   // notify resource call back that the resource is now idle(ready for interaction)
    }

    private void notifyResourceCallback() {
        if (resourceCallback != null && isIdle) {
            resourceCallback.onTransitionToIdle();   // what actually does the notification of idling resource
        }
    }
}
