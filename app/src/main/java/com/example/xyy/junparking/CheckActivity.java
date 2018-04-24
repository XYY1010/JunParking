package com.example.xyy.junparking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xyy.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.fragment;

/**
 * Created by XYY on 2018/4/20.
 */

public class CheckActivity extends AppCompatActivity {
    private ImageView imgtabhome;
    private ImageView imgtabcheck;
    private ImageView imgtabmsg;
    private ImageView imgtabme;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_layout);

        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.check_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.check_tablayout);
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

        Fragment fragment1 = new Fragment();
        Fragment fragment2 = new Fragment();
        Fragment fragment3 = new Fragment();
        Fragment fragment4 = new Fragment();
        Fragment fragment5 = new Fragment();

        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
        fragmentList.add(fragment5);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
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
