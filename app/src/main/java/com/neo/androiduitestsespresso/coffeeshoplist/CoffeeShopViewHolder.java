package com.neo.androiduitestsespresso.coffeeshoplist;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.androiduitestsespresso.R;


class CoffeeShopViewHolder extends RecyclerView.ViewHolder {

    @NonNull
    private TextView mNameTextView;

    @NonNull
    private TextView mDistanceTextView;

    @NonNull
    private RatingBar mRatingBar;

    @NonNull
    private TextView mIsClosedTextView;

    CoffeeShopViewHolder(@NonNull View itemView) {
        super(itemView);
        mNameTextView = (TextView) itemView.findViewById(R.id.name_text);
        mDistanceTextView = (TextView) itemView.findViewById(R.id.distance_text);
        mRatingBar = (RatingBar) itemView.findViewById(R.id.rating);
        mIsClosedTextView = (TextView) itemView.findViewById(R.id.is_closed_text);
    }

    void bind(@NonNull CoffeeShop coffeeShop) {
        Context context = itemView.getContext();

        mNameTextView.setText(coffeeShop.getName());
        mRatingBar.setRating((float)coffeeShop.getRating());

        String availabilityMessage;
        if (coffeeShop.isClosed()) {
            availabilityMessage = context.getString(R.string.closed);
        } else {
            availabilityMessage = context.getString(R.string.open);
        }
        mIsClosedTextView.setText(availabilityMessage);

        double distance = coffeeShop.getDistance();
        if (distance != 0.0d) {
            mDistanceTextView.setVisibility(View.VISIBLE);
            mDistanceTextView.setText(coffeeShop.getHumanReadableDistance());
        } else {
            mDistanceTextView.setVisibility(View.GONE);
        }
    }

}
