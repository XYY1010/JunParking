package com.example.xyy.junparking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xyy.adapter.ParkingLotAdapter;
import com.example.xyy.dbhelper.ParkingLot;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by XYY on 2018/4/27.
 */

public class ParkingLotActivity extends AppCompatActivity {

    private String CityName;
    private String CityCode;
    private List<ParkingLot> parkingLots;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private ParkingLotAdapter parkingLotAdapter;
    private TextView tvTip;

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
        setContentView(R.layout.parking_lot_layout);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvTip = (TextView) findViewById(R.id.tv_tip);
        Intent intent = getIntent();
        CityName = intent.getStringExtra("CityName");
        CityCode = intent.getStringExtra("CityCode");
        initParkingLotInfo();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        parkingLotAdapter = new ParkingLotAdapter(parkingLots);
        recyclerView.setAdapter(parkingLotAdapter);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshParkingLotInfo();
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
                finish();
                break;
            default:
        }
        return true;
    }

    private void initParkingLotInfo()
    {
        parkingLots = DataSupport.where("CityName = ?", CityName).find(ParkingLot.class);
        if (parkingLots.size() == 0){
            tvTip.setVisibility(View.VISIBLE);
        }
    }

    private void refreshParkingLotInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initParkingLotInfo();
                        parkingLotAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(ParkingLotActivity.this, "刷新成功^_^", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}
