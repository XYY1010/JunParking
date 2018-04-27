package com.example.xyy.junparking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xyy.adapter.MyPagerAdapter;
import com.example.xyy.bean.News;
import com.example.xyy.dbhelper.Airport;
import com.example.xyy.dbhelper.HighSpeedRialStation;
import com.example.xyy.dbhelper.ParkingLot;
import com.example.xyy.publicclass.HttpUtil;
import com.example.xyy.publicclass.VerticalTextview;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class ParkActivity extends AppCompatActivity {

    private static final int NEXT_PAGE = 20;
    ArrayList<News> newsList = new ArrayList<News>();
    ArrayList<String> titleList;
    private DrawerLayout mDrawerLayout;
    private ImageView bingPicImg;
    private ViewPager viewPager;
    private LinearLayout ll_dots;
    private static final int FIRST_PAGE = 1;
    private int currentPosition;
    private boolean isLooper = false;
    private VerticalTextview mTextView;
    private Handler mHandler;
    private ImageView imgtabhome1;
    private ImageView imgtabcheck1;
    private ImageView imgtabmsg1;
    private ImageView imgtabme1;
    private LinearLayout ll_airport;
    private LinearLayout ll_highrail;

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

        LitePal.getDatabase();
        initDB();

        imgtabhome1 = (ImageView) findViewById(R.id.imgtabhome1);
        imgtabcheck1 = (ImageView) findViewById(R.id.imgtabcheck1);
        imgtabmsg1 = (ImageView) findViewById(R.id.imgtabmsg1);
        imgtabme1 = (ImageView) findViewById(R.id.imgtabme1);
        ll_airport = (LinearLayout) findViewById(R.id.ll_airport);
        ll_highrail = (LinearLayout) findViewById(R.id.ll_highrail);

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

        init();
        //初始化ViewPager数据
        initData();

        //开启一个线程，用于线程
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                isLooper = true;
                while (isLooper){
                    try {
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //这里是设置当前的下一页
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).run();*/

        //初始化小点
        initDots();

        //设置数据适配器
        viewPager.setAdapter(new MyPagerAdapter(this, newsList));

        //设置当前页码值-一开始就在某个位置
        viewPager.setCurrentItem(10000*newsList.size());
        //设置当前展示的位置为1
  //      viewPager.setCurrentItem(FIRST_PAGE);

        //mHandler = new Handler();
        //mHandler.postDelayed(new TimerRunnable(), 5000);
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

        imgtabcheck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkActivity.this, CheckActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        imgtabmsg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkActivity.this, MsgActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        imgtabme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkActivity.this, MeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        ll_airport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkActivity.this, NationalAirportActivity.class);
                startActivity(intent);
            }
        });

        ll_highrail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkActivity.this, HighSpeedRailStationActivity.class);
                startActivity(intent);
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

//    @SuppressLint("HandlerLeak")
//    Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == NEXT_PAGE){
//                currentPosition = viewPager.getCurrentItem();
//                currentPosition = (currentPosition + 1) % newsList.size();
//                viewPager.setCurrentItem(currentPosition);
//                mHandler.sendEmptyMessageDelayed(NEXT_PAGE, 2000);
//            }
//        }
//    };

    class TimerRunnable implements Runnable{
        @Override
        public void run() {
            currentPosition = viewPager.getCurrentItem();
            currentPosition = (currentPosition + 1) % newsList.size();
            viewPager.setCurrentItem(currentPosition);
            if (mHandler != null){
                mHandler.postDelayed(this, 5000);
            }
        }
    }

    private void init(){
        mTextView = (VerticalTextview) findViewById(R.id.scollTxtView);
        titleList = new ArrayList<String>();
        titleList.add("欢迎使用机场停车APP，车位预定指南！");
        titleList.add("特大优惠：新用户停车送代金券！");
        titleList.add("全国各省市机场停车位等你来预定！");
        mTextView.setTextList(titleList);
        mTextView.setText(18, 5, 0xfffdb700);
        mTextView.setTextStillTime(3000);
        mTextView.setAnimTime(300);
        mTextView.setOnItemClickListener(new VerticalTextview.OnItemClickListener(){
            @Override
            public void onItemClick(int position) {
                Toast.makeText(ParkActivity.this, "点击了："+titleList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTextView.startAutoScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTextView.stopAutoScroll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler = null;
    }

    private void initDB(){
        int[] airportId = {1001, 1002, 1003, 1014, 1015, 1017, 1016, 1017,1025, 1035, 1044, 1045,
                1046, 1054, 1055, 1056, 1057, 1058, 1065, 1066, 1077, 1084, 1085, 1086, 1087, 1094,
                1095, 1096, 1097, 2001, 2011, 2012, 2013, 2014, 2015, 2016, 2021, 2022, 2023, 2033,
                2034, 2035, 2036, 2041, 2042, 2043, 2051, 2052, 2053, 2054, 2055, 2056, 2061, 2062, 2063,
                2064};
        String[] airportName = {"包头二里半", "北京南苑", "北京首都", "常州奔牛", "长春龙嘉", "重庆江北",
                "长沙黄花","成都双流", "大连周水子", "福州长乐", "桂林两江", "贵阳龙洞堡", "广州白云",
                "呼和浩特白塔", "海口美兰", "合肥新桥", "哈尔滨太平", "杭州萧山", "揭阳潮汕", "济南遥墙",
                "昆明长水", "兰州中川", "拉萨贡嘎", "丽江三义", "临沂沭埠岭", "宁波栎社", "南宁吴圩", "南昌昌北",
                "南京禄口", "青岛流亭", "石家庄正定", "上海迪士尼", "沈阳仙桃", "深圳宝安", "上海虹桥", "上海浦东",
                "台州路桥", "天津滨海", "太原武宿", "温州龙湾", "乌鲁木齐地窝堡", "无锡硕放", "武汉天河", "西宁曹家堡",
                "厦门高崎", "西安咸阳", "银川河东", "义乌机场", "烟台蓬莱", "宜昌三峡", "延吉机场","扬州泰州机场", "珠海金湾",
                "遵义新舟", "张家界荷花", "郑州新郑"};
        for (int i = 0; i<airportId.length; i++) {
            Airport airports = new Airport();
            airports.setAirportId(airportId[i]);
            airports.setAirportName(airportName[i]);
            airports.save();
        }

        int[] stationId = {1001, 1011, 1021, 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1043, 1051, 1061,
                1071, 1072, 1073, 1081, 1082, 1091, 1092, 1093, 2001, 2002, 2003, 2004, 2011, 2012, 2013,
                2014, 2015, 2016, 2025, 2026};
        String[] stationName = {"北京南站", "长春西站", "广州南站", "海口站", "汉口站", "合肥南站", "哈尔滨西站",
                "衡阳东站", "杭州东站", "杭州城站", "济南西站", "昆明站", "娄底南站", "宁波站", "南昌西站",
                "南京南站", "深圳北站", "上海虹桥站", "太原南站", "天津南站", "天津站", "无锡新区站", "武汉站",
                "厦门北站", "西宁站", "烟台站", "义乌站", "永康南站", "营口东站", "宜兴站", "宜昌东站", "珠海高铁站",
                "珠海拱北口岸"
        };
        for (int i = 0; i<stationId.length; i++) {
            HighSpeedRialStation station = new HighSpeedRialStation();
            station.setStationId(stationId[i]);
            station.setStationName(stationName[i]);
            station.save();
        }

        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setParkingLotId(10001);
        parkingLot1.setCityName("北京机场新星亮点停车场");
        parkingLot1.setImageSrc("park1.png");
        parkingLot1.setRatingScore(5);
        parkingLot1.setSales(119);
        parkingLot1.setIsInRoom("室外");
        parkingLot1.setDistance("距离机场直线距离4.2公里");
        parkingLot1.setRemarks("温馨提醒：六环以外外地牌照车辆过来停车无需办理进京证。");
        parkingLot1.setAvgPrice(20);
        parkingLot1.setCityName("北京首都");
        parkingLot1.setService1(true);
        parkingLot1.setService2(true);
        parkingLot1.setService3(true);
        parkingLot1.setService4(true);
        parkingLot1.setDiscount("预付20元");
        parkingLot1.save();

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setParkingLotId(10002);
        parkingLot2.setCityName("首都泊安飞正元停车场");
        parkingLot2.setImageSrc("park1.png");
        parkingLot2.setRatingScore(5);
        parkingLot2.setSales(221);
        parkingLot2.setIsInRoom("室外");
        parkingLot2.setDistance("距离机场T3直线距离2.5公里");
        parkingLot2.setRemarks("新场开业，室内车位，特价促销，距航站楼最近的大型地下停车场。");
        parkingLot2.setAvgPrice(20);
        parkingLot2.setCityName("北京首都");
        parkingLot2.setService1(true);
        parkingLot2.setService2(true);
        parkingLot2.setService3(false);
        parkingLot2.setService4(true);
        parkingLot2.setDiscount("预付20元");
        parkingLot2.save();

        ParkingLot parkingLot3 = new ParkingLot();
        parkingLot3.setParkingLotId(10003);
        parkingLot3.setCityName("萧山航杰停车场");
        parkingLot3.setImageSrc("park1.png");
        parkingLot3.setRatingScore(4.8);
        parkingLot3.setSales(1890);
        parkingLot3.setIsInRoom("室内/室外");
        parkingLot3.setDistance("距机场直线4公里");
        parkingLot3.setRemarks("不含政府收取的过路费(国家法定节假日免收)；室外车位带遮阳棚。");
        parkingLot3.setAvgPrice(15);
        parkingLot3.setCityName("杭州萧山");
        parkingLot3.setService1(false);
        parkingLot3.setService2(true);
        parkingLot3.setService3(false);
        parkingLot3.setService4(true);
        parkingLot3.setDiscount("预付20元抵30元");
        parkingLot3.save();
    }
}
