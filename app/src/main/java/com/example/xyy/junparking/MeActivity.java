package com.example.xyy.junparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MeActivity extends AppCompatActivity {

    private ImageView imgtabhome;
    private ImageView imgtabcheck;
    private ImageView imgtabmsg;
    private ImageView imgtabme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_layout);

        imgtabhome = (ImageView) findViewById(R.id.imgtabhome4);
        imgtabcheck = (ImageView) findViewById(R.id.imgtabcheck4);
        imgtabmsg = (ImageView) findViewById(R.id.imgtabmsg4);
        imgtabme = (ImageView) findViewById(R.id.imgtabme4);

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
    }
}
