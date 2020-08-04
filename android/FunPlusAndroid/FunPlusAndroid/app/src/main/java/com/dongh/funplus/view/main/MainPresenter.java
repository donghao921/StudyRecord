package com.dongh.funplus.view.main;

import com.dongh.baselib.mvp.BasePresenterImpl;
import com.dongh.funplus.http.OnSuccAndFaultListener;
import com.dongh.funplus.http.OnSuccAndFaultSub;
import com.dongh.funplus.model.netsubscribe.ArticleSubscribe;
import com.dongh.funplus.service.bean.ArticleListBean;
import com.dongh.funplus.service.bean.BannerBean;
import com.dongh.funplus.service.bean.BaseData;

import java.util.List;

public class MainPresenter extends BasePresenterImpl<MainView> {


    public MainPresenter(MainView mView) {
        super(mView);
    }

    public void getMoreArticleList(String id) {
        ArticleSubscribe.getArticleList(id, new OnSuccAndFaultSub<ArticleListBean>(new OnSuccAndFaultListener() {
            @Override
            public void onSuccess(BaseData result) {
                ArticleListBean articleListBean = (ArticleListBean) result.getData();
                mView.resultMoreArticle(articleListBean);
                mView.onFinishLoad();
            }

            @Override
            public void onFault(String errorMsg) {
                mView.onFinishLoad();
            }

        }));

    }

    public void getTopArticleList() {
        ArticleSubscribe.getArticleList("0", new OnSuccAndFaultSub<ArticleListBean>(new OnSuccAndFaultListener() {
            @Override
            public void onSuccess(BaseData result) {
                ArticleListBean articleListBean = (ArticleListBean) result.getData();
                mView.resultTopArticle(articleListBean);
                mView.onFinishLoad();
            }

            @Override
            public void onFault(String errorMsg) {
                mView.onFinishLoad();
            }

        }));

    }

//    public void getCollectList(String page) {
//        ArticleSubscribe.getCollectList(page, new OnSuccAndFaultSub<ArticleListBean>(new OnSuccAndFaultListener() {
//            @Override
//            public void onSuccess(BaseData result) {
//                ArticleListBean articleListBean = (ArticleListBean) result.getData();
//                mView.resultArticle(articleListBean);
//            }
//
//            @Override
//            public void onFault(String errorMsg) {
//
//            }
//        }));
//    }

    public void getBanner() {
        ArticleSubscribe.getBanner(new OnSuccAndFaultSub<List<BannerBean>>(new OnSuccAndFaultListener() {
            @Override
            public void onSuccess(BaseData result) {
                List<BannerBean> bannerBeans = (List<BannerBean>) result.getData();
                mView.resultBanner(bannerBeans);
                mView.onFinishLoad();
            }

            @Override
            public void onFault(String errorMsg) {
                mView.onFinishLoad();
            }

        }));
    }

    public void collectArticle(String id) {
        ArticleSubscribe.collectArticle(id, new OnSuccAndFaultSub<String>(new OnSuccAndFaultListener() {
            @Override
            public void onSuccess(BaseData result) {
                mView.resultCollectArticle();
            }

            @Override
            public void onFault(String errorMsg) {
                mView.resultUncollectArticle();
            }
        }));
    }

    public void uncollectArticle(String id) {
        ArticleSubscribe.uncollectArticle(id, new OnSuccAndFaultSub<String>(new OnSuccAndFaultListener() {
            @Override
            public void onSuccess(BaseData result) {
                mView.resultUncollectArticle();
            }

            @Override
            public void onFault(String errorMsg) {
                mView.resultCollectArticle();
            }
        }));
    }


}
