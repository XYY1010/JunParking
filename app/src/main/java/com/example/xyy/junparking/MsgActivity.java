package com.example.xyy.junparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MsgActivity extends AppCompatActivity {

    private ImageView imgtabhome;
    private ImageView imgtabcheck;
    private ImageView imgtabmsg;
    private ImageView imgtabme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_layout);

        imgtabhome = (ImageView) findViewById(R.id.imgtabhome3);
        imgtabcheck = (ImageView) findViewById(R.id.imgtabcheck3);
        imgtabmsg = (ImageView) findViewById(R.id.imgtabmsg3);
        imgtabme = (ImageView) findViewById(R.id.imgtabme3);

        imgtabhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MsgActivity.this, ParkActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        imgtabcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MsgActivity.this, CheckActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        imgtabme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MsgActivity.this, MeActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}
