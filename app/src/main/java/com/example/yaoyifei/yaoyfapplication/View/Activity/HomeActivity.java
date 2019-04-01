package com.example.yaoyifei.yaoyfapplication.View.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.R;
import com.example.yaoyifei.yaoyfapplication.View.Fragment.BlankFragment;
import com.example.yaoyifei.yaoyfapplication.View.Fragment.BlankFragmentTeacher;
import com.example.yaoyifei.yaoyfapplication.View.Fragment.ChartFragment;
import com.example.yaoyifei.yaoyfapplication.View.Fragment.QuestionFragment;
import com.example.yaoyifei.yaoyfapplication.View.Fragment.UserGradeFragment;
import com.example.yaoyifei.yaoyfapplication.tools.SP;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener , BlankFragment.OnFragmentInteractionListener,BlankFragmentTeacher.OnFragmentInteractionListener {

    LoginActivity loginActivity = new LoginActivity();
    private DrawerLayout mDrwerLayout;
    private static ViewPager mViewPager;
    private List<LinearLayout> mLinearLayouts;
    private List<ImageView> mImageViews;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private NavigationView mNavigationView;
    private TextView mShowName;
    private String username;//从登录界面传过来的用户名字
    private SP mSp;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        mSp = new SP(this);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        username = intent.getStringExtra("name");
        initView();
    }

    public static ViewPager getmViewPager(){
        return mViewPager;
    }


    private void initView() {
        // find view
        mViewPager = findViewById(R.id.fragment_vp);
        mViewPager.setOffscreenPageLimit(3);
        mDrwerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        LayoutInflater factorys = LayoutInflater.from(HomeActivity.this);
        final View view = factorys.inflate(R.layout.nav_header, mNavigationView);
        mShowName =  view.findViewById(R.id.show_name);
        if (username.isEmpty()){
            Toast.makeText(this, "名字为空", Toast.LENGTH_SHORT).show();
        }else{
            mShowName.setText(username);
            mSp.write(username);
        }

        //init actionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_reorder_black_24dp);
        }

        // init fragment
        mFragments = new ArrayList<>(4);
        mFragments.add(new BlankFragment());//指导学生操作的界面
        mFragments.add(new QuestionFragment());//考试界面
        mFragments.add(new ChartFragment());//个人做题情况界面
        mFragments.add(new UserGradeFragment());//所有同学的成绩界面

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
                    //退出登录的开关
                    loginActivity.clearData();
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else if(menuItem.getItemId()==R.id.navigation_request_test){
                    mDrwerLayout.closeDrawers();
                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setIcon(R.drawable.ic_home_black_24dp);
                    dialog.setTitle("关于");
                    dialog.setMessage(
                            "作者:姚逸飞\n"+
                                    "\n"+
                                    "手机:13296542887\n" +
                                    "\n"+
                                    "邮箱:yaoyf1101@thundersoft.com\n" +
                                    "\n"+
                                    "小菊花--安卓在线课堂小测试APP\n" +
                                    "\n"+
                                    "版本号:v1.0.0\n"+
                                    "\n"+
                                    "有任何建议或者反馈可以随时联系作者，谢谢！");
                    dialog.show();
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
            mViewPager.setCurrentItem(3,true);
        }else{
            mDrwerLayout.openDrawer(Gravity.LEFT);
        }
        return true;
    }
}

