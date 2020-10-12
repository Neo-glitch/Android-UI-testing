package com.neo.androiduitestsespresso.beveragedetail;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.neo.androiduitestsespresso.R;
import com.neo.androiduitestsespresso.common.Beverage;

public class BeverageDetailActivity extends AppCompatActivity implements BeverageDetailView {

    @NonNull
    public static final String BEVERAGE_MODEL_BUNDLE_KEY = "BEVERAGE_MODEL_BUNDLE_KEY";

    @Nullable
    TextView titleTextView;

    @Nullable
    TextView descriptionTextView;

    @Nullable
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverage);
        initializeViews();

        BeverageDetailViewPresenter presenter = new BeverageDetailViewPresenter(this);
        presenter.onCreate(getIntent());
    }

    public void initializeViews() {
        titleTextView = (TextView) findViewById(R.id.text_title);
        descriptionTextView = (TextView) findViewById(R.id.text_description);
        imageView = (ImageView) findViewById(R.id.image_beverage);
    }

    void showErrorText() {
        if (null == titleTextView) {
            return;
        }
        titleTextView.setText(getString(R.string.error_unable_to_load_beverage));
    }

    void setTitle(@NonNull String name) {
        if (null == titleTextView) {
            return;
        }
        titleTextView.setText(name);
    }

    void setDescription(@NonNull String description) {
        if (null == descriptionTextView) {
            return;
        }
        descriptionTextView.setText(description);
    }

    void setImage(@NonNull String drawableResourceName) {
        if (null == imageView) {
            return;
        }
        Drawable image = getDrawableByName(drawableResourceName);
        imageView.setImageDrawable(image);
    }

    @Override
    public void displayBeverage(@Nullable BeverageDetailModel beverageDetailModel) {
        if (null == beverageDetailModel) {
            showErrorText();
        } else {
            Beverage beverage = beverageDetailModel.getBeverage();
            if (null == beverage) {
                return;
            }
            setTitle(beverage.getName());
            setDescription(beverage.getDescription());
            setImage(beverage.getDrawableResourceName());
        }
    }

    @Nullable
    private Drawable getDrawableByName(@Nullable String name) {
        if (null == name) {
            return null;
        }

        Resources resources = getResources();
        if (null == resources) {
            return null;
        }

        final int resourceId = resources.getIdentifier(name, "drawable", getPackageName());
        return ResourcesCompat.getDrawable(resources, resourceId, null);
    }

}
