package com.imdongh.chapter03;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder> {
    private List<City> cityList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View cityView;
        TextView tvCityName;
        TextView tvRanking;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityView = itemView;
            tvCityName = itemView.findViewById(R.id.tv_city);
            tvRanking = itemView.findViewById(R.id.tv_ranking);
        }
    }

    public CityRecyclerAdapter(List<City> cityList) {
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_city, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                City city = cityList.get(position);
                Toast.makeText(v.getContext(), city.getCityName(), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.tvRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                City city = cityList.get(position);
                Toast.makeText(v.getContext(), city.getRanking(), Toast.LENGTH_SHORT).show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        City city = cityList.get(position);
        viewHolder.tvCityName.setText(city.getCityName());
        viewHolder.tvRanking.setText(city.getRanking());
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }




}
