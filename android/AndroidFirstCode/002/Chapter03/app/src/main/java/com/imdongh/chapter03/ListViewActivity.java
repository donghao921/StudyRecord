package com.imdongh.chapter03;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private String [] cityName = {"苏州", "南京", "无锡", "南通", "常州", "扬州", "泰州", "徐州",
            "宿迁", "盐城", "淮安", "连云港", "昆山", "江阴", "张家港", "太仓", "宜兴"};
    private String [] ranking = {"1", "2", "3", "4", "5", "6", "7", "8",
            "9", "10", "11", "12", "13", "14", "15", "16", "17"};
    private ArrayList<City> cityList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        listView = findViewById(R.id.lv_listview);
        initCityData();
        CityAdapter adapter = new CityAdapter(ListViewActivity.this, R.layout.list_city, cityList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    private void initCityData() {
        for (int i = 0; i < cityName.length; i++) {
            City city = new City();
            city.setCityName(cityName[i]);
            city.setRanking(ranking[i]);
            cityList.add(city);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        City city = cityList.get(position);
        Toast.makeText(this, "city:"+city.getCityName(), Toast.LENGTH_SHORT).show();
    }
}
