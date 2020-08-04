package com.dongh.funplus.view.main;

import com.dongh.baselib.mvp.BaseView;
import com.dongh.funplus.service.bean.ArticleListBean;
import com.dongh.funplus.service.bean.BannerBean;

import java.util.List;

public interface MainView extends BaseView<MainPresenter> {
    // 获取首页文章列表
    void resultTopArticle(ArticleListBean result);
    // 获取更多文章列表
    void resultMoreArticle(ArticleListBean result);
    // 获取首页Banner
    void resultBanner(List<BannerBean> result);
    // 结束加载
    boolean onFinishLoad();
    // 收藏文章列表
    void resultCollectArticle();
    // 取消收藏文章列表
    void resultUncollectArticle();
}
