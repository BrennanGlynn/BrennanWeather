    package com.brennanglynn.brennanweather.ui;

    import android.app.Activity;
    import android.content.Context;
    import android.content.Intent;
    import android.graphics.drawable.Drawable;
    import android.graphics.drawable.GradientDrawable;
    import android.location.Location;
    import android.net.ConnectivityManager;
    import android.net.NetworkInfo;
    import android.os.Bundle;
    import android.support.v4.content.res.ResourcesCompat;
    import android.util.Log;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.RelativeLayout;
    import android.widget.TextView;

    import com.brennanglynn.brennanweather.GoogleLocationService;
    import com.brennanglynn.brennanweather.LocationUpdateListener;
    import com.brennanglynn.brennanweather.R;
    import com.brennanglynn.brennanweather.weather.Current;
    import com.brennanglynn.brennanweather.weather.Day;
    import com.brennanglynn.brennanweather.weather.Forecast;
    import com.brennanglynn.brennanweather.weather.Hour;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.IOException;

    import butterknife.BindView;
    import butterknife.ButterKnife;
    import butterknife.OnClick;
    import okhttp3.Call;
    import okhttp3.Callback;
    import okhttp3.OkHttpClient;
    import okhttp3.Request;
    import okhttp3.Response;


    public class MainActivity extends Activity {

        public static final String TAG = MainActivity.class.getSimpleName();
        public static final String DAILY_FORECAST = "DAILY_FORECAST";
        public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
        public static final String BG_GRADIENT = "BG_GRADIENT";

        private Forecast mForecast;
        private ColorWheel mColorWheel;
        private int[] mBackground;

        protected Location mCurrentLocation;

        private GoogleLocationService mGoogleLocationService;

        private double mLatitude;
        private double mLongitude;// = -116.2296;

        @BindView(R.id.layoutBackground)
        RelativeLayout mLayoutBackground;
        @BindView(R.id.timeLabel)
        TextView mTimeLabel;
        @BindView(R.id.temperatureLabel)
        TextView mTemperatureLabel;
        @BindView(R.id.humidityValue)
        TextView mHumidityValue;
        @BindView(R.id.precipValue)
        TextView mPrecipValue;
        @BindView(R.id.summaryLabel)
        TextView mSummaryLabel;
        @BindView(R.id.iconImageView)
        ImageView mIconImageView;
        @BindView(R.id.refreshImageView)
        ImageView mRefreshImageView;
        @BindView(R.id.progressBar)
        ProgressBar mProgressBar;
        @BindView(R.id.locationLabel)
        TextView mLocationLabel;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            mGoogleLocationService = new GoogleLocationService(this, new LocationUpdateListener() {
                @Override
                public void canReceiveLocationUpdates() {
                    Log.i(TAG, "Can receiveLocationUpdates");
                }

                @Override
                public void cannotReceiveLocationUpdates() {

                }

                @Override
                public void updateLocation(Location location) {
                    if (location != null) {
                        Log.i(TAG, "Updating location");
                        mLatitude = location.getLatitude();
                        mLongitude = location.getLongitude();
                        getForecast(mLatitude, mLongitude);
                    }
                }

                @Override
                public void updateLocationName(String localityName, Location location) {
                    mGoogleLocationService.stopLocationUpdates();
                }
            });
            mGoogleLocationService.startUpdates();

            mColorWheel = new ColorWheel();
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        private void setBackgroundGradient() {
            mBackground = mColorWheel.getColors();

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{mBackground[0], mBackground[1]});
            gd.setCornerRadius(0f);

            mLayoutBackground.setBackground(gd);
        }

        private void getForecast(double latitude, double longitude) {
            String apiKey = "50e826df5889d0d215cdcbae50d182e3";
            String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
                    "/" + latitude + "," + longitude;

            if (isNetworkAvailable()) {
                toggleRefresh();

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(forecastUrl)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toggleRefresh();
                            }
                        });
                        alertUserAboutError();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toggleRefresh();
                            }
                        });
                        try {
                            String jsonData = response.body().string();
                            Log.v(TAG, jsonData);
                            if (response.isSuccessful()) {
                                mForecast = parseForecastDetails(jsonData);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateDisplay();
                                    }
                                });
                            } else {
                                alertUserAboutError();
                            }
                        } catch (IOException | JSONException e) {
                            Log.e(TAG, "Exception caught: ", e);
                        }
                    }
                });
            } else {
                alertUserAboutError();
            }
            Log.i(TAG, forecastUrl);
        }

        private void toggleRefresh() {
            if (mProgressBar.getVisibility() == View.INVISIBLE) {
                mProgressBar.setVisibility(View.VISIBLE);
                mRefreshImageView.setVisibility(View.INVISIBLE);
            } else {
                mProgressBar.setVisibility(View.INVISIBLE);
                mRefreshImageView.setVisibility(View.VISIBLE);
            }
        }

        private Forecast parseForecastDetails(String jsonData) throws JSONException {
            Forecast forecast = new Forecast();

            forecast.setCurrentForecast(getCurrentDetails(jsonData));
            forecast.setHourlyForecast(getHourlyForecast(jsonData));
            forecast.setDailyForecast(getDailyForecast(jsonData));

            return forecast;
        }

        private Day[] getDailyForecast(String jsonData) throws JSONException {
            JSONObject forecast = new JSONObject(jsonData);
            String timezone = forecast.getString("timezone");
            JSONObject daily = forecast.getJSONObject("daily");
            JSONArray data = daily.getJSONArray("data");

            Day[] days = new Day[data.length()];

            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonHour = data.getJSONObject(i);
                Day day = new Day();
                day.setTime(jsonHour.getLong("time"));
                day.setSummary(jsonHour.getString("summary"));
                day.setTemperatureMax(jsonHour.getInt("temperatureMax"));
                day.setTimezone(timezone);
                day.setIcon(jsonHour.getString("icon"));

                days[i] = day;
            }

            return days;
        }

        private Hour[] getHourlyForecast(String jsonData) throws JSONException {
            JSONObject forecast = new JSONObject(jsonData);
            String timezone = forecast.getString("timezone");
            JSONObject hourly = forecast.getJSONObject("hourly");
            JSONArray data = hourly.getJSONArray("data");

            Hour[] hours = new Hour[data.length()];

            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonHour = data.getJSONObject(i);
                Hour hour = new Hour();
                hour.setTime(jsonHour.getLong("time"));
                hour.setSummary(jsonHour.getString("summary"));
                hour.setTemperature(jsonHour.getInt("temperature"));
                hour.setTimezone(timezone);
                hour.setIcon(jsonHour.getString("icon"));

                hours[i] = hour;
            }

            return hours;
        }

        private Current getCurrentDetails(String jsonData) throws JSONException {
            JSONObject forecast = new JSONObject(jsonData);
            String timezone = forecast.getString("timezone");

            JSONObject currently = forecast.getJSONObject("currently");

            Current current = new Current(
                    currently.getString("icon"),
                    currently.getLong("time"),
                    currently.getDouble("temperature"),
                    currently.getDouble("humidity"),
                    currently.getDouble("precipProbability"),
                    currently.getString("summary"),
                    forecast.getString("timezone")
            );
            Log.i(TAG, current.toString());
            return current;
        }

        private boolean isNetworkAvailable() {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            boolean isAvailable = false;
            if (networkInfo != null && networkInfo.isConnected()) {
                isAvailable = true;
            }
            return isAvailable;
        }

        private void alertUserAboutError() {
            AlertDialogFragment dialog = new AlertDialogFragment();
            dialog.show(getFragmentManager(), "error_dialog");
        }

        @OnClick(R.id.refreshImageView)
        public void refreshPage(View view) {
            getForecast(mLatitude, mLongitude);
            setBackgroundGradient();

        }

        @OnClick(R.id.dailyButton)
        public void startDailyActivity(View view) {
            Intent intent = new Intent(this, DailyForecastActivity.class);
            intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
            if (mBackground != null) {
                intent.putExtra(BG_GRADIENT, mBackground);
            }
            startActivity(intent);
        }

        @OnClick(R.id.hourButton)
        public void startHourlyActivity(View view) {
            Intent intent = new Intent(this, HourlyForecastActivity.class);
            intent.putExtra(HOURLY_FORECAST, mForecast.getHourlyForecast());
            if (mBackground != null) {
                intent.putExtra(BG_GRADIENT, mBackground);
            }
            startActivity(intent);
        }

        private void updateDisplay() {
            Current current = mForecast.getCurrentForecast();
            mTemperatureLabel.setText(current.getTemperature() + "");
            mTimeLabel.setText("The time is " + current.getFormattedTime());
            mHumidityValue.setText(current.getHumidity() + "");
            mPrecipValue.setText(current.getPrecipChance() + "%");
            mSummaryLabel.setText(current.getSummary());
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), current.getIconId(), null);
            mIconImageView.setImageDrawable(drawable);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (mGoogleLocationService != null) {
                mGoogleLocationService.stopLocationUpdates();
            }
        }

        @Override
        protected void onStart() {
            mGoogleLocationService.startGoogleApi();
            super.onStart();
        }

        @Override
        protected void onStop() {
            mGoogleLocationService.closeGoogleApi();
            super.onStop();
        }
    }


