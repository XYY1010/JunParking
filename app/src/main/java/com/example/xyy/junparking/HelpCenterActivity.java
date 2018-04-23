package com.example.xyy.junparking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by XYY on 2018/4/23.
 */

public class HelpCenterActivity extends AppCompatActivity {

    private Button btn_notify;
    private Button btn_withdrawmodify;
    private Button btn_feeinstruction;
    private Button btn_commonques;
    private Button btn_parkingguide;

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
        setContentView(R.layout.help_layout);

        btn_notify = (Button) findViewById(R.id.btn_notify);
        btn_withdrawmodify = (Button) findViewById(R.id.btn_withdrawmodify);
        btn_feeinstruction = (Button) findViewById(R.id.btn_feeinstruction);
        btn_commonques = (Button) findViewById(R.id.btn_commonques);
        btn_parkingguide = (Button) findViewById(R.id.btn_parkingguide);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btn_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpCenterActivity.this, BookNotes.class);
                startActivity(intent);
            }
        });

        btn_withdrawmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpCenterActivity.this, ReformPolicy.class);
                startActivity(intent);
            }
        });

        btn_feeinstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpCenterActivity.this, CostStatement.class);
                startActivity(intent);
            }
        });

        btn_commonques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpCenterActivity.this, CommonProblem.class);
                startActivity(intent);
            }
        });

        btn_parkingguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpCenterActivity.this, ParkingGuideActivity.class);
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
                finish();
                break;
            default:
        }
        return true;
    }
}
