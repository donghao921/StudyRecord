package com.dongh.funplus.view.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private List<Fragment> mList = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.fm = fm;
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
