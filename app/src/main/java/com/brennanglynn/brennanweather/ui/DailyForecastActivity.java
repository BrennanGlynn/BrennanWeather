package com.brennanglynn.brennanweather.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.brennanglynn.brennanweather.R;
import com.brennanglynn.brennanweather.adapter.DayAdapter;
import com.brennanglynn.brennanweather.weather.Day;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity {

    private Day[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);

        DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String dayOfTheWeek = mDays[position].getDayOfTheWeek();
        String conditions = mDays[position].getSummary().toLowerCase();
        String maxTemp = mDays[position].getTemperatureMax() + "";

        String message = String.format("On %s, the high will be %s and it will be %s",
                dayOfTheWeek,
                maxTemp,
                conditions);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}