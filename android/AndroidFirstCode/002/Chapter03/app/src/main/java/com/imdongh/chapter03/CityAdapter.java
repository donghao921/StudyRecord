package com.imdongh.chapter03;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CityAdapter extends ArrayAdapter<City> {
    private int resourceId;

    public CityAdapter(Context context, int resource, ArrayList<City> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City city = (City) getItem(position);
        View view = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvCity = view.findViewById(R.id.tv_city);
            viewHolder.tvRanking = view.findViewById(R.id.tv_ranking);
            view.setTag(viewHolder); // 将viewholder存储到View中

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvCity.setText(city.getCityName());
        viewHolder.tvRanking.setText(city.getRanking());

        return view;
    }

    class ViewHolder {
        private TextView tvCity;
        private TextView tvRanking;

    }
}
