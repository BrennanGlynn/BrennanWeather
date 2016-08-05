package com.brennanglynn.brennanweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brennanglynn.brennanweather.R;
import com.brennanglynn.brennanweather.weather.Hour;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private Context mContext;
    private Hour[] mHours;

    public HourAdapter(Context context, Hour[] hours) {
        mContext = context;
        mHours = hours;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_list_item, parent, false);
        HourViewHolder viewHolder = new HourViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHour(mHours[position]);

    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            timeLabel.setText(hour.getFormattedTime());
            iconImageView.setImageResource(hour.getIconId());
            temperatureLabel.setText(hour.getTemperature() + "");
            summaryLabel.setText(hour.getSummary());

        }


        @Override
        public void onClick(View view) {
            String time = timeLabel.getText().toString();
            String temperature = temperatureLabel.getText().toString();
            String summary = summaryLabel.getText().toString();
            String message = String.format("At %s it will be %s and %s",
                    time,
                    temperature,
                    summary);
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }
}
