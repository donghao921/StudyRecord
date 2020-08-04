package com.dongh.funplus.view.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dongh.baselib.mvp.BaseFragment;
import com.dongh.funplus.R;
import com.dongh.funplus.view.webview.WebActivity;
import com.dongh.funplus.service.bean.ArticleBean;
import com.dongh.funplus.service.bean.ArticleListBean;
import com.dongh.funplus.service.bean.BannerBean;
import com.dongh.funplus.utils.GlideImageLoader;
import com.dongh.funplus.view.main.MainPresenter;
import com.dongh.funplus.view.main.MainView;
import com.dongh.funplus.view.main.adapter.ArticleListAdapt;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.apache.commons.lang3.StringEscapeUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment<MainPresenter> implements MainView, ArticleListAdapt.OnItemViewClick {
    @Nullable
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @BindView(R.id.top_banner)
    Banner imgBanner;
    @Nullable
    @BindView(R.id.refresh_home)
    SmartRefreshLayout refreshLayout;

    private View mView;
    private ArticleListAdapt mAdapt;
    private List<ArticleBean> articleBeans;
    private List<BannerBean> bannerBeans;
    private List<String> bannerPaths;
    private List<String> bannerTitles;
    private int page = 0;   // 页数
    private ArticleBean collcetArticle;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mView = view;
        ButterKnife.bind(this, view);
        setPresenter(new MainPresenter(this));
        initData();
        initView();
        return view;
    }

    private void initData() {
        articleBeans = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapt = new ArticleListAdapt(mView.getContext(), articleBeans);
        recyclerView.setAdapter(mAdapt);
        refreshLayout.setDragRate(0.5f);    //显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setReboundDuration(300);  //回弹动画时长（毫秒）
        refreshLayout.setEnableRefresh(true);   //是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getmBasePresenter().getBanner();
                getmBasePresenter().getTopArticleList();
                if (onFinishLoad()) {
                    refreshLayout.finishRefresh();
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getmBasePresenter().getMoreArticleList(String.valueOf(page));
                if (onFinishLoad()) {
                    refreshLayout.finishLoadMore();
                }
            }
        });

    }

    private void initView() {
        getmBasePresenter().getTopArticleList();
        getmBasePresenter().getBanner();
    }

    @Override
    public void resultTopArticle(ArticleListBean result) {
        if (articleBeans != null && articleBeans.size() > 0) {
            articleBeans.clear();
        }
        articleBeans = result.getDatas();
        mAdapt = new ArticleListAdapt(mView.getContext(), articleBeans);
        mAdapt.setOnItemClick(this);
        recyclerView.setAdapter(mAdapt);

    }

    @Override
    public void resultMoreArticle(ArticleListBean result) {
        if (articleBeans != null && articleBeans.size() > 0) {
            if (result.getDatas() != null && result.getDatas().size() > 0) {
                List<ArticleBean> temps = result.getDatas();
                for (int i = 0; i < temps.size(); i++) {
                    articleBeans.add(temps.get(i));
                }
            }
        } else {
            articleBeans = result.getDatas();
        }
        mAdapt = new ArticleListAdapt(mView.getContext(), articleBeans);
        mAdapt.setOnItemClick(this);
        recyclerView.setAdapter(mAdapt);
    }

    @Override
    public void resultBanner(List<BannerBean> result) {
        if (bannerBeans != null && bannerBeans.size() > 0) {
            bannerBeans.clear();
        }
        bannerBeans = result;
        if (result.size() > 0) {
            bannerPaths = new ArrayList<>();
            bannerTitles = new ArrayList<>();
            for (BannerBean bannerBean : result) {
                bannerPaths.add(bannerBean.getImagePath());
                bannerTitles.add(StringEscapeUtils.unescapeHtml4(bannerBean.getTitle()));
            }
        }
        createHeaderBanner();
    }

    @Override
    public boolean onFinishLoad() {
        return true;
    }

    @Override
    public void resultCollectArticle() {
        collcetArticle.setCollect(true);
        mAdapt.updataList(collcetArticle);
        mAdapt.notifyDataSetChanged();
    }

    @Override
    public void resultUncollectArticle() {
        collcetArticle.setCollect(false);
        mAdapt.updataList(collcetArticle);
        mAdapt.notifyDataSetChanged();
    }

    private void createHeaderBanner() {
        //设置banner样式
        imgBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        imgBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        imgBanner.setImages(bannerPaths);
        //设置banner动画效果
        imgBanner.setBannerAnimation(Transformer.Default);
//        imgBanner.setradiu
        //设置标题集合（当banner样式有显示title时）
        imgBanner.setBannerTitles(bannerTitles);
        //设置轮播时间
        imgBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        imgBanner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置banner点击事件监听
        imgBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bannerBean = bannerBeans.get(position);
                Toast.makeText(mView.getContext(), "path:"+ bannerBean.getUrl(), Toast.LENGTH_SHORT).show();
            }
        });
        //banner设置方法全部调用完毕时最后调用
        imgBanner.start();
    }


    @Override
    public void onCollectClick(ArticleBean articleBean) {
        collcetArticle = articleBean;
        if (articleBean.isCollect()) {
            getmBasePresenter().uncollectArticle(String.valueOf(articleBean.getId()));
        } else {
            getmBasePresenter().collectArticle(String.valueOf(articleBean.getId()));
        }
    }

    @Override
    public void onLinkClick(String url) {
        Intent intent = new Intent(mView.getContext(), WebActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }

    @thisTest(age = 10, name="ssd")
    public void fight() {

    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface thisTest {
        int age() default 12;
        String name() default "asd";
    }
}
