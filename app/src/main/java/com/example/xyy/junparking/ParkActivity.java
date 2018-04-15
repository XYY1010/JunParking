package com.example.xyy.junparking;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xyy.adapter.MyPagerAdapter;
import com.example.xyy.bean.News;
import com.example.xyy.publicclass.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


public class ParkActivity extends AppCompatActivity {

    ArrayList<News> newsList = new ArrayList<News>();
    private DrawerLayout mDrawerLayout;
    private ImageView bingPicImg;
    private ViewPager viewPager;
    private LinearLayout ll_dots;
    private static final int FIRST_PAGE = 1;
    private int currentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.jun_park_layout);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
        //bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }

        //loadBingPic();

        navView.setCheckedItem(R.id.nav_pocket);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                switch (item.getItemId()){
                    case R.id.nav_pocket:
                        Toast.makeText(ParkActivity.this, "You checked your pocket", Toast.LENGTH_SHORT);
                        break;
                    case R.id.nav_wallet:
                        Toast.makeText(ParkActivity.this, "You checked your wallet", Toast.LENGTH_SHORT);
                        break;
                    case R.id.nav_setting:
                        Toast.makeText(ParkActivity.this, "You checked setting", Toast.LENGTH_SHORT);
                        break;
                    case R.id.nav_service:
                        Toast.makeText(ParkActivity.this, "You checked service", Toast.LENGTH_SHORT);
                        break;
                    default:
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        //初始化ViewPager数据
        initData();
        //初始化小点
        initDots();

        //设置数据适配器
        viewPager.setAdapter(new MyPagerAdapter(this, newsList));

        //设置当前页码值-一开始就在某个位置
        viewPager.setCurrentItem(10000*newsList.size());
        //设置当前展示的位置为1
  //      viewPager.setCurrentItem(FIRST_PAGE);

        //设置viewPager的监听事件
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动过程中的方法 position 索引值
            //positionOffset 滑动偏移量 0~1
            //positionOffsetPixels 偏移像素值
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //选中某一页的监听
            //@Override
            public void onPageSelected(int position) {
                //if (position == newsList.size()-1) {
                   //currentPosition = FIRST_PAGE;
               // }else if (position == 0){
               //     currentPosition = newsList.size()-2;
                //}else{
 //                   currentPosition=position;
                //}
                int dotPosition = position % newsList.size();
                //设置小点监听
                for (int i = 0; i<ll_dots.getChildCount(); i++){
                    //从线性布局中取出对应小点
                    ImageView imgView = (ImageView) ll_dots.getChildAt(i);
                    if (i == dotPosition) {
                        imgView.setImageResource(R.mipmap.selectdots);
                    }else {
                        imgView.setImageResource(R.mipmap.dots);
                    }
                }


            }

            //滑动状态改变的方法 state:dragging 拖拽 idle 静止 settling 惯性过程
        //           @Override
              public void onPageScrollStateChanged(int state) {
                  //如果是静止状态，将当前页进行替换
//                if (state == viewPager.SCROLL_STATE_IDLE) {
                    //设置当前页，smoothScroll 平稳滑动
//                    viewPager.setCurrentItem(currentPosition, false);
//                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.phone:
                Toast.makeText(this, "You made a call", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    private void loadBingPic(){
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ParkActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(ParkActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

    private void initDots() {
        for (int i = 0; i<newsList.size(); i++){
            //实例化ImageView
            ImageView imageView = new ImageView(this);
            //如果是第一页，设置选中图片状态
            if (i==0){
                imageView.setImageResource(R.mipmap.selectdots);
            }else{
                imageView.setImageResource(R.mipmap.dots);
            }
            //代码中设置大小是指相对像素——px
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
            params.setMargins(5, 5, 5, 5);
            //添加到线性布局中
            ll_dots.addView(imageView, params);
        }
    }

    private void initData(){
        //newsList.add(new News("邀好友，得大奖", R.mipmap.ad3));    //0

        newsList.add(new News("战略合作伙伴", R.mipmap.ad1));      //1
        newsList.add(new News("安全·省钱·便捷", R.mipmap.ad2));  //2
        newsList.add(new News("邀好友，得大奖", R.mipmap.ad3));    //3

        //newsList.add(new News("战略合作伙伴", R.mipmap.ad1));      //4
    }
}
