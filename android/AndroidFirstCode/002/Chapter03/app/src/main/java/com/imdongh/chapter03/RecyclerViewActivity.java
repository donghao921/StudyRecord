package com.imdongh.chapter03;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private String [] cityName = {"苏州", "南京", "无锡", "南通", "常州", "扬州", "泰州", "徐州",
            "宿迁", "盐城", "淮安", "连云港", "昆山", "江阴", "张家港", "太仓", "宜兴"};
    private String [] ranking = {"1", "2", "3", "4", "5", "6", "7", "8",
            "9", "10", "11", "12", "13", "14", "15", "16", "17"};
    private ArrayList<City> cityList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initCityData();
        recyclerView = findViewById(R.id.rv_recyclerView);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        CityRecyclerAdapter cityRecyclerAdapter= new CityRecyclerAdapter(cityList);
        recyclerView.setAdapter(cityRecyclerAdapter);

    }

    private void initCityData() {
        for (int i = 0; i < cityName.length; i++) {
            City city = new City();
            city.setCityName(cityName[i]);
            city.setRanking(ranking[i]);
            cityList.add(city);
        }
    }
}
