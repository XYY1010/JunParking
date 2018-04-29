package com.example.xyy.junparking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyy.dbhelper.ParkingLot;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by XYY on 2018/4/28.
 */

public class DetailShowActivity extends AppCompatActivity {

    private final int[] imgR= {R.mipmap.ic_launcher, R.mipmap.park1, R.mipmap.park2, R.mipmap.park3, R.mipmap.park4};
    private String ParkingLotName;
    private List<ParkingLot> parkingLotList;

    private ImageView img_parkinglot;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppCompatRatingBar ratingBar;
    private TextView tvRatingScore;
    private TextView tvSales;
    private TextView tvIsInRoom;
    private TextView tvDiscount;
    private TextView tvService1;
    private TextView tvService2;
    private TextView tvService3;
    private TextView tvService4;
    private TextView tvTips;
    private Button btnBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        Intent intent = getIntent();
        ParkingLotName = intent.getStringExtra("ParkingLotName");
        LitePal.getDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        img_parkinglot = (ImageView) findViewById(R.id.detail_img);
        ratingBar = (AppCompatRatingBar) findViewById(R.id.ratingbar);
        tvRatingScore = (TextView) findViewById(R.id.tv_rating_score);
        tvSales = (TextView) findViewById(R.id.tv_sales);
        tvIsInRoom = (TextView) findViewById(R.id.tv_isinroom1);
        tvDiscount = (TextView) findViewById(R.id.tv_discount);
        tvService1 = (TextView) findViewById(R.id.tv_service1);
        tvService2 = (TextView) findViewById(R.id.tv_service2);
        tvService3 = (TextView) findViewById(R.id.tv_service3);
        tvService4 = (TextView) findViewById(R.id.tv_service4);
        tvTips = (TextView) findViewById(R.id.tv_tips);
        btnBook = (Button) findViewById(R.id.btn_book);

        parkingLotList = DataSupport.where("ParkingLotName = ?", ParkingLotName).find(ParkingLot.class);

        collapsingToolbarLayout.setTitle(ParkingLotName);
        Glide.with(this).load(parkingLotList.get(0).getImageSrc()).into(img_parkinglot);
        ratingBar.setRating((float) (parkingLotList.get(0).getRatingScore()));
        tvRatingScore.setText(String.valueOf(parkingLotList.get(0).getRatingScore())+"分");
        tvSales.setText("月销"+String.valueOf(parkingLotList.get(0).getSales())+"单");
        tvIsInRoom.setText(parkingLotList.get(0).getIsInRoom()+"总价(元)");
        tvDiscount.setText("2、"+parkingLotList.get(0).getDiscount()+"。");
        if (parkingLotList.get(0).isService1())
        {
            tvService1.setVisibility(View.VISIBLE);
        }
        if (parkingLotList.get(0).isService2())
        {
            tvService2.setVisibility(View.VISIBLE);
        }
        if (parkingLotList.get(0).isService3())
        {
            tvService3.setVisibility(View.VISIBLE);
        }
        if (parkingLotList.get(0).isService4())
        {
            tvService4.setVisibility(View.VISIBLE);
        }
        tvTips.setText(parkingLotList.get(0).getRemarks());

    }
}
