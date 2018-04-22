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
import android.widget.Toast;

public class MeActivity extends AppCompatActivity {

    private ImageView imgtabhome;
    private ImageView imgtabcheck;
    private ImageView imgtabmsg;
    private ImageView imgtabme;
    private ImageView imgIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_layout);

        imgtabhome = (ImageView) findViewById(R.id.imgtabhome4);
        imgtabcheck = (ImageView) findViewById(R.id.imgtabcheck4);
        imgtabmsg = (ImageView) findViewById(R.id.imgtabmsg4);
        imgtabme = (ImageView) findViewById(R.id.imgtabme4);
        imgIcon = (ImageView) findViewById(R.id.icon_image);


        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgtabhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, ParkActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        imgtabcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, CheckActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        imgtabmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, MsgActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeActivity.this, MeInfoActivity.class);
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
