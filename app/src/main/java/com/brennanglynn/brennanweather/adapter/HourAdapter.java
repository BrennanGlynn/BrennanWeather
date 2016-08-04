package com.brennanglynn.brennanweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brennanglynn.brennanweather.R;
import com.brennanglynn.brennanweather.weather.Hour;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder {
        public TextView timeLabel;
        public ImageView iconImageView;
        public TextView temperatureLabel;
        public TextView summaryLabel;

        public HourViewHolder (View itemView) {
            super(itemView);
            timeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            iconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
            temperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            summaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);

        }

        public void bindHour(Hour hour) {

        }
    }

//    private Context mContext;
//    private Hour[] mHours;
//
//    @Override
//    public int getCount() {
//        return mHours.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mHours[position];
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup viewGroup) {
//        RecyclerView.ViewHolder holder;
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.hourly_list_item, null);\
//            holder = new ViewHolder();
//
//        }
//        return null;
//    }
//
//    private static class ViewHolder {
//        TextView timeLabel;
//        ImageView iconImageView;
//        TextView temperatureLabel;
//        TextView summaryLabel;
//    }
}
