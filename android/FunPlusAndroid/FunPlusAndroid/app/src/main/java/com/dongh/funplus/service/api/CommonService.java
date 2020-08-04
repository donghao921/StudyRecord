package com.dongh.funplus.service.api;

import com.dongh.funplus.service.bean.ArticleListBean;
import com.dongh.funplus.service.bean.BannerBean;
import com.dongh.funplus.service.bean.BaseData;
import com.dongh.funplus.service.bean.LoginBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommonService {
    /**
     * 登陆
     * 登录后会在cookie中返回账号密码
     * 只要在客户端做cookie持久化存储即可自动登录验证
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseData<LoginBean>> login(@Field("username") String username,
                                          @Field("password") String password);

    /**
     * 注册
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<BaseData<LoginBean>> register(@Field("username") String username,
                                             @Field("password") String password,
                                             @Field("repassword") String repassword);

    /**
     * 退出
     * 服务端会让客户端清除 Cookie（即cookie max-Age=0）
     * 如果客户端 Cookie 实现合理，可以实现自动清理
     * 如果本地做了用户账号密码和保存，及时清理
     * @return
     */
    @GET("user/logout/json")
    Observable<BaseData<LoginBean>> logout();



    @GET("article/list/{id}/json")
    Observable<BaseData<ArticleListBean>> getArticleList(@Path("id") String id);

    @GET("lg/collect/list/{page}/json")
    Observable<BaseData<ArticleListBean>> getCollectList(@Path("page") String page);

    @GET("banner/json")
    Observable<BaseData<List<BannerBean>>> getBanner();

    @POST("lg/collect/{id}/json")
    Observable<BaseData<String>> collectArticle(@Path("id") String id);

    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseData<String>> uncollectArticle(@Path("id") String id);
}
