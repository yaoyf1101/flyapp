package com.example.yaoyifei.yaoyfapplication.View.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.View.Fragment.BlankFragment;
import com.example.yaoyifei.yaoyfapplication.View.Fragment.StudentFragment;
import com.example.yaoyifei.yaoyfapplication.View.Fragment.TeacherFragment;
import com.example.yaoyifei.yaoyfapplication.View.Fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener , BlankFragment.OnFragmentInteractionListener {

    LoginActivity loginActivity = new LoginActivity();
    private DrawerLayout mDrwerLayout;
    private ViewPager mViewPager;
    private List<LinearLayout> mLinearLayouts;
    private List<ImageView> mImageViews;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    private void initView() {
        // find view
        mViewPager = findViewById(R.id.fragment_vp);
        mViewPager.setOffscreenPageLimit(3);
        mDrwerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        //init actionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_reorder_black_24dp);
        }

        // init fragment
        mFragments = new ArrayList<>(4);
        mFragments.add(BlankFragment.newInstance("111","111"));
        mFragments.add(new StudentFragment());
        mFragments.add(new TeacherFragment());
        mFragments.add(new TestFragment());
        // init view pager

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);

        // register listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mLinearLayouts = new ArrayList<>(4);
        mLinearLayouts.add((LinearLayout) findViewById(R.id.zhuye));
        mLinearLayouts.add((LinearLayout) findViewById(R.id.kaoshi));
        mLinearLayouts.add((LinearLayout) findViewById(R.id.lianxi));
        mLinearLayouts.add((LinearLayout) findViewById(R.id.wode));
        mImageViews = new ArrayList<>(4);
        mImageViews.add((ImageView) findViewById(R.id.image0));
        mImageViews.get(0).setBackgroundResource(R.drawable.vector_drawable_home_dark);//初始状态下第一页被选中
        mImageViews.add((ImageView) findViewById(R.id.image1));
        mImageViews.add((ImageView) findViewById(R.id.image2));
        mImageViews.add((ImageView) findViewById(R.id.image3));
        for (int i=0;i<4;i++) {
            mLinearLayouts.get(i).setOnClickListener(this);
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.navigation_exit){
                    loginActivity.clearData();
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else if(menuItem.getItemId()==R.id.navigation_request_test){
                    mDrwerLayout.closeDrawers();
                }else {
                    mDrwerLayout.closeDrawers();
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        switch (position){
            case 0:
                mImageViews.get(0).setBackgroundResource(R.drawable.vector_drawable_home_dark);
                mImageViews.get(1).setBackgroundResource(R.drawable.vector_drawable_kaoshi);
                mImageViews.get(2).setBackgroundResource(R.drawable.vector_drawable_lianxi);
                mImageViews.get(3).setBackgroundResource(R.drawable.vector_drawable_wode);
                break;
            case 1:
                mImageViews.get(0).setBackgroundResource(R.drawable.vector_drawable_home);
                mImageViews.get(1).setBackgroundResource(R.drawable.vector_drawable_kaoshi_dark);
                mImageViews.get(2).setBackgroundResource(R.drawable.vector_drawable_lianxi);
                mImageViews.get(3).setBackgroundResource(R.drawable.vector_drawable_wode);
                break;
            case 2:
                mImageViews.get(0).setBackgroundResource(R.drawable.vector_drawable_home);
                mImageViews.get(1).setBackgroundResource(R.drawable.vector_drawable_kaoshi);
                mImageViews.get(2).setBackgroundResource(R.drawable.vector_drawable_lianxi_dark);
                mImageViews.get(3).setBackgroundResource(R.drawable.vector_drawable_wode);
                break;
            case 3:
                mImageViews.get(0).setBackgroundResource(R.drawable.vector_drawable_home);
                mImageViews.get(1).setBackgroundResource(R.drawable.vector_drawable_kaoshi);
                mImageViews.get(2).setBackgroundResource(R.drawable.vector_drawable_lianxi);
                mImageViews.get(3).setBackgroundResource(R.drawable.vector_drawable_wode_dark);
                break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zhuye:
                mImageViews.get(0).setBackgroundResource(R.drawable.vector_drawable_home_dark);
                mImageViews.get(1).setBackgroundResource(R.drawable.vector_drawable_kaoshi);
                mImageViews.get(2).setBackgroundResource(R.drawable.vector_drawable_lianxi);
                mImageViews.get(3).setBackgroundResource(R.drawable.vector_drawable_wode);
                mViewPager.setCurrentItem(0,true);
                break;
            case R.id.kaoshi:
                mImageViews.get(0).setBackgroundResource(R.drawable.vector_drawable_home);
                mImageViews.get(1).setBackgroundResource(R.drawable.vector_drawable_kaoshi_dark);
                mImageViews.get(2).setBackgroundResource(R.drawable.vector_drawable_lianxi);
                mImageViews.get(3).setBackgroundResource(R.drawable.vector_drawable_wode);
                mViewPager.setCurrentItem(1,true);
                break;
            case R.id.lianxi:
                mImageViews.get(0).setBackgroundResource(R.drawable.vector_drawable_home);
                mImageViews.get(1).setBackgroundResource(R.drawable.vector_drawable_kaoshi);
                mImageViews.get(2).setBackgroundResource(R.drawable.vector_drawable_lianxi_dark);
                mImageViews.get(3).setBackgroundResource(R.drawable.vector_drawable_wode);
                mViewPager.setCurrentItem(2,true);
                break;
            case R.id.wode:
                mImageViews.get(0).setBackgroundResource(R.drawable.vector_drawable_home);
                mImageViews.get(1).setBackgroundResource(R.drawable.vector_drawable_kaoshi);
                mImageViews.get(2).setBackgroundResource(R.drawable.vector_drawable_lianxi);
                mImageViews.get(3).setBackgroundResource(R.drawable.vector_drawable_wode_dark);
                mViewPager.setCurrentItem(3,true);
                break;
                default:
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.count){
            Toast.makeText(HomeActivity.this,"统计功能正在努力实现中，敬请期待",Toast.LENGTH_SHORT).show();
        }else{
            mDrwerLayout.openDrawer(Gravity.LEFT);
        }
        return true;
    }

}

