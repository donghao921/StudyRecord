package com.dongh.funplus.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.dongh.baselib.mvp.BaseActivity;
import com.dongh.funplus.R;
import com.dongh.funplus.view.login.LoginActivity;
import com.dongh.funplus.view.main.adapter.FragmentAdapter;
import com.dongh.funplus.view.main.fragment.HomeFragment;
import com.dongh.funplus.view.main.fragment.ProjectFragment;
import com.dongh.funplus.view.main.fragment.SystemFragment;
import com.dongh.funplus.view.main.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vp_main)
    ViewPager mainViewPage;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bnv_bottom)
    BottomNavigationView bottomView;


    private List<Fragment> mList = new ArrayList<>();
    private HomeFragment mHomeFragment;
    private ProjectFragment mProjectFragment;
    private SystemFragment mSystemFragment;
    private UserFragment mUserFragment;
    private FragmentAdapter fragmentAdapter;
    private FragmentManager fm;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setPresenter(new MainPresenter(this));
        initData();
        initView();
    }

    private void initData() {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
    }

    private void initView() {
//        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //在布局文件中生命DrawerLayout后，即可从边缘滑出抽屉了
        //ActionBarDrawerToggle作用是在toolbar上创建一个点击弹出drawer的按钮而已
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance();
        }
        if (mProjectFragment == null) {
            mProjectFragment = ProjectFragment.newInstance();
        }
        if (mSystemFragment == null) {
            mSystemFragment = SystemFragment.newInstance();
        }
        if (mUserFragment == null) {
            mUserFragment = UserFragment.newInstance();
        }
        mList.add(mHomeFragment);
        mList.add(mProjectFragment);
        mList.add(mSystemFragment);
        mList.add(mUserFragment);

        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemInt = menuItem.getItemId();
                switch (itemInt) {
                    case R.id.bottom1:
                        mainViewPage.setCurrentItem(0);
                        break;
                    case R.id.bottom2:
                        mainViewPage.setCurrentItem(1);
                        break;
                    case R.id.bottom3:
                        mainViewPage.setCurrentItem(2);
                        break;
                    case R.id.bottom4:
                        mainViewPage.setCurrentItem(3);
                        break;
                }

                // true 会显示这个Item被选中的效果 false 则不会
                return true;
            }
        });

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mList);
        mainViewPage.setAdapter(fragmentAdapter);
        mainViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
//                当 ViewPager 滑动后设置BottomNavigationView 选中相应选项
                bottomView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Log.i("tag", "search");
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.i("tag", "onNavigationItemSelected");
        switch (menuItem.getItemId()) {
            case R.id.nav_login:
                Toast.makeText(MainActivity.this, "login", Toast.LENGTH_SHORT);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_regiest:

                break;
            case R.id.nav_collect:

                break;
            case R.id.nav_about:

                break;
            case R.id.nav_logout:

                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
