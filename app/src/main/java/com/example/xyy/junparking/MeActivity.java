package com.example.xyy.junparking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xyy.dbhelper.AccountInfo;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MeActivity extends AppCompatActivity {

    private ImageView imgtabhome;
    private ImageView imgtabcheck;
    private ImageView imgtabmsg;
    private ImageView imgtabme;
    private ImageView imgIcon;
    private TextView tvUserName;
    private TextView tvAccountBalanceNum;
    private TextView tvCouponNum;
    private LinearLayout llcode;
    private LinearLayout llshare;
    private LinearLayout llhelp;
    private LinearLayout llfeedback;

    private List<AccountInfo> accountInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_layout);

        tvUserName = (TextView) findViewById(R.id.username);
        tvCouponNum = (TextView) findViewById(R.id.num1);
        tvAccountBalanceNum = (TextView) findViewById(R.id.num2);
        imgtabhome = (ImageView) findViewById(R.id.imgtabhome4);
        imgtabcheck = (ImageView) findViewById(R.id.imgtabcheck4);
        imgtabmsg = (ImageView) findViewById(R.id.imgtabmsg4);
        imgtabme = (ImageView) findViewById(R.id.imgtabme4);
        imgIcon = (ImageView) findViewById(R.id.icon_image);
        llcode = (LinearLayout) findViewById(R.id.ll_code);
        llshare = (LinearLayout) findViewById(R.id.ll_share);
        llhelp = (LinearLayout) findViewById(R.id.ll_help);
        llfeedback = (LinearLayout) findViewById(R.id.ll_feedback);

        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        accountInfoList = DataSupport.findAll(AccountInfo.class);
        if (accountInfoList.size() != 0){
            imgIcon.setImageResource(accountInfoList.get(0).getHeadImgSrc());
            tvUserName.setText(accountInfoList.get(0).getUserName());
            tvCouponNum.setText(String.valueOf(accountInfoList.get(0).getCouponNum()));
            tvAccountBalanceNum.setText(String.valueOf(accountInfoList.get(0).getAccountBalance()));
        }

        imgtabhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, ParkActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        imgtabcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, CheckActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        imgtabmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, MsgActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, MeInfoActivity.class);
                startActivity(intent);
            }
        });

        llcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, CouponCodeActivity.class);
                startActivity(intent);
            }
        });

        llshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, ShareActivity.class);
                startActivity(intent);
            }
        });

        llhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, HelpCenterActivity.class);
                startActivity(intent);
            }
        });

        llfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, FeedBackActivity.class);
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
            default:
        }
        return true;
    }
}
