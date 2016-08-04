package com.brennanglynn.brennanweather.weather;

import com.brennanglynn.brennanweather.R;

public class Forecast {
    private Current mCurrentForecast;
    private Hour[] mHourlyForecast;
    private Day[] mDailyForecast;

    public Current getCurrentForecast() {
        return mCurrentForecast;
    }

    public void setCurrentForecast(Current currentForecast) {
        mCurrentForecast = currentForecast;
    }

    public Hour[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(Hour[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public Day[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Day[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }

    public static int getIconId(String iconString) {
        // clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night.
        String iconId = iconString;

        switch (iconId) {
            case "clear-day":
                return R.drawable.clear_day;
            case "clear-night":
                return R.drawable.clear_night;
            case "rain":
                return R.drawable.rain;
            case "snow":
                return R.drawable.snow;
            case "sleet":
                return R.drawable.sleet;
            case "wind":
                return R.drawable.wind;
            case "fog":
                return R.drawable.fog;
            case "cloudy":
                return R.drawable.cloudy;
            case "partly-cloudy-day":
                return R.drawable.partly_cloudy;
            case "partly cloudy-night":
                return R.drawable.cloudy_night;
            default:
                return R.drawable.clear_day;
        }

    }
}
