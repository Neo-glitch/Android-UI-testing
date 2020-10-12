package com.neo.androiduitestsespresso.beveragelist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.androiduitestsespresso.R;
import com.neo.androiduitestsespresso.common.Beverage;


class BeverageViewHolder extends RecyclerView.ViewHolder {

    @NonNull
    private TextView mTitleTextView;

    @NonNull
    private ImageView mIconImageView;

    BeverageViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitleTextView = (TextView) itemView.findViewById(R.id.text_title);
        mIconImageView = (ImageView) itemView.findViewById(R.id.image_icon);
    }

    void bind(@NonNull Beverage beverage) {
        mTitleTextView.setText(beverage.getName());
        Drawable icon = getDrawableByName(itemView.getContext(), beverage.getDrawableResourceName());
        mIconImageView.setImageDrawable(icon);
    }

    @Nullable
    private Drawable getDrawableByName(@NonNull Context context, @Nullable String name) {
        if (null == name) {
            return null;
        }

        Resources resources = context.getResources();
        if (null == resources) {
            return null;
        }

        final int resourceId = resources.getIdentifier(name, "drawable", context.getPackageName());
        return ResourcesCompat.getDrawable(resources, resourceId, null);
    }

}
