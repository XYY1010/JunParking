package com.example.xyy.junparking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by XYY on 2018/4/20.
 */

public class CheckActivity extends Activity {
    private ImageView imgtabhome;
    private ImageView imgtabcheck;
    private ImageView imgtabmsg;
    private ImageView imgtabme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_layout);

        imgtabhome = (ImageView) findViewById(R.id.imgtabhome2);
        imgtabcheck = (ImageView) findViewById(R.id.imgtabcheck2);
        imgtabmsg = (ImageView) findViewById(R.id.imgtabmsg2);
        imgtabme = (ImageView) findViewById(R.id.imgtabme2);

        imgtabhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckActivity.this, ParkActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        imgtabmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckActivity.this, MsgActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        imgtabme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckActivity.this, MeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
}
