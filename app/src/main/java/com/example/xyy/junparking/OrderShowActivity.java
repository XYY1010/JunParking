package com.example.xyy.junparking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xyy.dbhelper.OrderInfo;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by XYY on 2018/5/3.
 */

public class OrderShowActivity extends AppCompatActivity {

    private TextView tvOrderNum;
    private TextView tvParkingLotName;
    private TextView tvIsInRoom;
    private TextView tvParkingTime;
    private TextView tvPickingTime;
    private TextView tvPrice;
    private TextView tvPaymentTime;
    private TextView tvOrderState;
    private TextView tvPhoneNum;

    private String OrderNum;
    private List<OrderInfo> orderInfoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.order_show_layout);

        Intent intent = getIntent();
        OrderNum = intent.getStringExtra("OrderNum");

        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvOrderNum = (TextView) findViewById(R.id.tv_order_num);
        tvParkingLotName = (TextView) findViewById(R.id.tv_parkinglot_name);
        tvIsInRoom = (TextView) findViewById(R.id.tv_isinroom);
        tvParkingTime = (TextView) findViewById(R.id.tv_parking_time);
        tvPickingTime = (TextView) findViewById(R.id.tv_picking_time);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPaymentTime = (TextView) findViewById(R.id.tv_payment_time);
        tvOrderState = (TextView) findViewById(R.id.tv_order_state);
        tvPhoneNum = (TextView) findViewById(R.id.tv_phone_num);

        orderInfoList = DataSupport.where("OrderNum = ?", OrderNum).find(OrderInfo.class);
        tvOrderNum.setText(OrderNum);
        tvParkingLotName.setText(orderInfoList.get(0).getParkingLotName());
        tvIsInRoom.setText(orderInfoList.get(0).getParkingLotName());
        tvParkingTime.setText(orderInfoList.get(0).getStartTime());
        tvPickingTime.setText(orderInfoList.get(0).getEndTime());
        tvPrice.setText(String.valueOf(orderInfoList.get(0).getPrice())+"元");
        tvPaymentTime.setText(orderInfoList.get(0).getPaymentTime());
        tvOrderState.setText(orderInfoList.get(0).getOrderState());
        tvPhoneNum.setText(orderInfoList.get(0).getPhoneNum());
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
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
