package com.dongh.funplus.view.main;

import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;

import com.dongh.funplus.R;
import com.dongh.funplus.base.BaseActivity;
import com.dongh.funplus.service.bean.ArticleBean;
import com.dongh.funplus.view.adapt.ImageAdapt;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView{

    private ViewPager vpImage;
    private ImageAdapt imageAdapt;
//    private MainPresenter


    private List<ImageView> imageViewList;
    private int[] adImages = new int[] {R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic1, R.mipmap.pic2};

    private int previousPosition = 0;   // 前一个被选中的position
    private boolean isStop = false;//线程是否停止
    private static int PAGER_TIME = 3000;//间隔时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        autoPlayView();
        createPresenter().getArticle();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    private void initData() {
//        vpImage = findViewById(R.id.vp_image);
        imageViewList = new ArrayList<>();
        for(int i = 0; i < adImages.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(adImages[i]);
            imageViewList.add(iv);
        }


    }

    private void initView() {
        imageAdapt = new ImageAdapt(imageViewList, vpImage);
//        vpImage.setAdapter(imageAdapt);
//        vpImage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                int newPosition = position % imageViewList.size();
//                // 可以设置轮播点
//
//                previousPosition = newPosition;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
        
        setFirstLocation();

    }

    private void setFirstLocation() {
//        int m = (Integer.MAX_VALUE / 2) % imageViewList.size();
//        int currentPosition = Integer.MAX_VALUE / 2 - m;
//        vpImage.setCurrentItem(0);

    }

    private void autoPlayView() {
        // auto paly pic
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            vpImage.setCurrentItem(vpImage.getCurrentItem()+1);
                        }
                    });
                    SystemClock.sleep(PAGER_TIME);
                }

            }
        }).start();
    }

    @Override
    public void resultArticle(ArticleBean result) {

    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
