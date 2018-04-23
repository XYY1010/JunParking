package com.example.xyy.junparking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.xyy.adapter.NoticeAdapter;
import com.example.xyy.bean.Notice;

import java.util.ArrayList;
import java.util.List;

public class MsgActivity extends AppCompatActivity {

    private ImageView imgtabhome;
    private ImageView imgtabcheck;
    private ImageView imgtabmsg;
    private ImageView imgtabme;
    private RecyclerView recyclerView;
    private List<Notice> noticeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_layout);

        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNotice();

        imgtabhome = (ImageView) findViewById(R.id.imgtabhome3);
        imgtabcheck = (ImageView) findViewById(R.id.imgtabcheck3);
        imgtabmsg = (ImageView) findViewById(R.id.imgtabmsg3);
        imgtabme = (ImageView) findViewById(R.id.imgtabme3);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        imgtabhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MsgActivity.this, ParkActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        imgtabcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MsgActivity.this, CheckActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        imgtabme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MsgActivity.this, MeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NoticeAdapter noticeAdapter = new NoticeAdapter(noticeList);
        recyclerView.setAdapter(noticeAdapter);
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
            default:
        }
        return true;
    }

    private void initNotice(){
        for (int i = 0; i<10; i++) {
            Notice notice1 = new Notice("欢迎使用机场停车APP，车位预定指南！", R.mipmap.laba2, "2016-11-19 15:17:45");
            noticeList.add(notice1);
            Notice notice2 = new Notice("五一优惠提前到，最高金额免单，单单有奖！！！", R.mipmap.laba2, "2018-04-23 20:59:44");
            noticeList.add(notice2);
            Notice notice3 = new Notice("欢迎使用机场停车APP，车位预定指南！", R.mipmap.laba2, "2016-11-19 15:17:45");
            noticeList.add(notice3);
            Notice notice4 = new Notice("五一优惠提前到，最高金额免单，单单有奖！！！", R.mipmap.laba2, "2018-04-23 20:59:44");
            noticeList.add(notice4);
            Notice notice5 = new Notice("欢迎使用机场停车APP，车位预定指南！", R.mipmap.laba2, "2016-11-19 15:17:45");
            noticeList.add(notice5);
            Notice notice6 = new Notice("五一优惠提前到，最高金额免单，单单有奖！！！", R.mipmap.laba2, "2018-04-23 20:59:44");
            noticeList.add(notice6);
            Notice notice7 = new Notice("欢迎使用机场停车APP，车位预定指南！", R.mipmap.laba2, "2016-11-19 15:17:45");
            noticeList.add(notice7);
            Notice notice8 = new Notice("五一优惠提前到，最高金额免单，单单有奖！！！", R.mipmap.laba2, "2018-04-23 20:59:44");
            noticeList.add(notice8);
        }
    }
}
