package com.dongh.sample.http.cache;

import com.dongh.sample.mvp.model.bean.BannerListBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc
 */
public interface CacheProvider {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<BannerListBean>> getBannerWithCache(Observable<BannerListBean> o, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);

}