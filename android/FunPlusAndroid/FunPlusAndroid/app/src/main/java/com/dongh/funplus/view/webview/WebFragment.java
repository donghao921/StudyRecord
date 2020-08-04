package com.dongh.funplus.view.webview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dongh.baselib.mvp.BaseFragment;
import com.dongh.funplus.R;
import com.dongh.funplus.view.main.fragment.HomeFragment;
import com.just.agentweb.AgentWeb;

public class WebFragment extends BaseFragment {
    private String url;
    private AgentWeb mAgentWeb;

    public WebFragment() {
    }

    public static WebFragment newInstance() {
        WebFragment webFragment = new WebFragment();
//        Bundle args = new Bundle();
//        args.putString("url", url);
//        webFragment.setArguments(args);
        return webFragment;
    }

    public void setUrl(String url) {
        this.url =url;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString("url");
        } else {
            url = "http://www.baidu.com";
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        initData();

        return view;
    }

    private void initData() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout)view, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);

    }
}
