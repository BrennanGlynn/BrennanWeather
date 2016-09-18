package com.brennanglynn.brennanweather.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.brennanglynn.brennanweather.R;
import com.brennanglynn.brennanweather.adapter.HourAdapter;
import com.brennanglynn.brennanweather.weather.Hour;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HourlyForecastActivity extends Activity {

    private Hour[] mHours;

    @BindView(R.id.relativeLayout) RelativeLayout mLayoutBackground;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);
        HourAdapter adapter = new HourAdapter(mHours);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        if (intent.hasExtra(MainActivity.BG_GRADIENT)) {
            int[] mBackground = intent.getIntArrayExtra(MainActivity.BG_GRADIENT);

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{mBackground[0], mBackground[1]});
            gd.setCornerRadius(0f);

            mLayoutBackground.setBackground(gd);
        }
    }

}