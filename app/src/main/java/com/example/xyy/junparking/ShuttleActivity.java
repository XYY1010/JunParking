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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.xyy.junparking.R.mipmap.flight;

/**
 * Created by XYY on 2018/5/9.
 */

public class ShuttleActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout ll_flight;
    private TextView tvTime;
    private static String now;
    private TextView tvFlightNo;
    private TextView tvFlight1;
    private TextView tvFlight2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.shuttle_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvFlightNo = (TextView) findViewById(R.id.tv_FlightNo);
        tvFlight1 = (TextView) findViewById(R.id.tvFlight1);
        tvFlight2 = (TextView) findViewById(R.id.tvFlight2);
        tvTime = (TextView) findViewById(R.id.tvTime);
        ll_flight = (LinearLayout) findViewById(R.id.ll_flight);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());
        tvTime.setText(now);

        ll_flight.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_flight:
                Intent intent = new Intent(ShuttleActivity.this, FlightSearchActivity.class);
                startActivityForResult(intent, 1);
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String FlightNo = data.getStringExtra("FlightNo");
                    String ArrScheduled = data.getStringExtra("ArrScheduled");
                    tvFlightNo.setText("航班号：" + FlightNo);
                    tvFlightNo.setVisibility(View.VISIBLE);
                    tvFlight1.setVisibility(View.GONE);
                    tvFlight2.setVisibility(View.GONE);
                    String[] strArr = ArrScheduled.split("T");
                    String strTime = strArr[0]+" "+strArr[1];
                    tvTime.setText(strTime);
                }
                break;
            default:
        }
    }
}
