package com.dongh.funplus.view.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongh.baselib.mvp.BaseFragment;
import com.dongh.funplus.R;

public class SystemFragment extends BaseFragment {

    public SystemFragment() {

    }

    public static SystemFragment newInstance() {
        return new SystemFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system, container, false);
        return view;
    }
}
