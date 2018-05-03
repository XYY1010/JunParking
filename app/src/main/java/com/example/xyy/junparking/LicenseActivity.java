package com.example.xyy.junparking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.xyy.utils.CarKeyboardUtil;
import com.example.xyy.utils.LicenseKeyboardUtil;


/**
 * Created by XYY on 2018/4/30.
 */

public class LicenseActivity extends AppCompatActivity implements View.OnTouchListener{

    EditText etCarBrand;
    private CarKeyboardUtil keyboardUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.license_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        etCarBrand = (EditText) findViewById(R.id.etcarnum);
        keyboardUtil = new CarKeyboardUtil(this, etCarBrand);
        etCarBrand.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.etcarnum:
                keyboardUtil.hideSystemKeyBroad();
                keyboardUtil.hideSoftInputMethod();
                if (!keyboardUtil.isShow())
                    keyboardUtil.showKeyboard();
                break;
            default:
                if (keyboardUtil.isShow())
                    keyboardUtil.hideKeyboard();
                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (keyboardUtil.isShow()) {
            keyboardUtil.hideKeyboard();
        }
        return super.onTouchEvent(event);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyboardUtil.isShow()) {
                keyboardUtil.hideKeyboard();
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
