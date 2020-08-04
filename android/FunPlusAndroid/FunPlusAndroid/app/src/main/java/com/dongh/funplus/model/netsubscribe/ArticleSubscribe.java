package com.dongh.funplus.model.netsubscribe;

import android.content.Context;

import com.dongh.funplus.http.RetrofitFactory;
import com.dongh.funplus.service.bean.ArticleListBean;
import com.dongh.funplus.service.bean.BannerBean;
import com.dongh.funplus.service.bean.BaseData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class ArticleSubscribe{
    /**
     *
     * @param id
     * @param subscribe
     */
    public static void getArticleList(String id, DisposableObserver<BaseData<ArticleListBean>> subscribe) {
        Observable<BaseData<ArticleListBean>> observable = RetrofitFactory.getInstance().getCommService().getArticleList(id);
        RetrofitFactory.getInstance().toSubscribe(observable, subscribe);
    }

    public static void getCollectList(String page, DisposableObserver<BaseData<ArticleListBean>> subscribe) {
        Observable<BaseData<ArticleListBean>> observable = RetrofitFactory.getInstance().getCommService().getCollectList(page);
        RetrofitFactory.getInstance().toSubscribe(observable, subscribe);
    }

    public static void getBanner(DisposableObserver<BaseData<List<BannerBean>>> subscribe) {
        Observable<BaseData<List<BannerBean>>> observable = RetrofitFactory.getInstance().getCommService().getBanner();
        RetrofitFactory.getInstance().toSubscribe(observable, subscribe);
    }

    public static void collectArticle(String id, DisposableObserver<BaseData<String>> subscribe) {
        Observable<BaseData<String>> observable = RetrofitFactory.getInstance().getCommService().collectArticle(id);
        RetrofitFactory.getInstance().toSubscribe(observable, subscribe);
    }

    public static void uncollectArticle(String id, DisposableObserver<BaseData<String>> subscribe) {
        Observable<BaseData<String>> observable = RetrofitFactory.getInstance().getCommService().uncollectArticle(id);
        RetrofitFactory.getInstance().toSubscribe(observable, subscribe);
    }
}
