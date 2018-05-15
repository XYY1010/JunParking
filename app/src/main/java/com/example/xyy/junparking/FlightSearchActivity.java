package com.example.xyy.junparking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.xyy.adapter.FlightAdapter;
import com.example.xyy.bean.Flight;
import com.example.xyy.dbhelper.CityName;
import com.example.xyy.publicclass.HttpCallbackListener;
import com.example.xyy.publicclass.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.R.attr.handle;
import static android.R.attr.key;
import static android.media.CamcorderProfile.get;
import static com.example.xyy.junparking.R.mipmap.flight;


/**
 * Created by XYY on 2018/5/10.
 */

public class FlightSearchActivity extends AppCompatActivity implements View.OnClickListener{

    //配置您申请的KEY
    public static final String APPKEY ="b9f48dcf8480338931744fe3e65ec2d0";
    private String startCity;
    private String lastCity;
    private String startCityCode;
    private String lastCityCode;
    private String address;

    private List<CityName> cityNames;
    private List<Flight> flights = new ArrayList<>();

    private Button btnSearch;
    private TextView textView;
    private EditText startCityEt;
    private EditText lastCityEt;
    private ListView listView;

    public static final int SHOW_RESPONSE = 0;
    public static final int ERROR_MESSAGE = 1;
    private boolean errorFlag = false;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    FlightAdapter flightAdapter = new FlightAdapter(FlightSearchActivity.this, R.layout.flight_info_item, flights);
                    listView.setAdapter(flightAdapter);
                    break;
                case ERROR_MESSAGE:
                    flightAdapter = new FlightAdapter(FlightSearchActivity.this, R.layout.flight_info_item, flights);
                    listView.setAdapter(flightAdapter);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("未查询到该线路航班，请重新确定航线！！！");
            }
        }

    };

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

        setContentView(R.layout.flight_search_layout);

        textView = (TextView) findViewById(R.id.tvRes);
        btnSearch = (Button) findViewById(R.id.id_btn_search);
        startCityEt = (EditText) findViewById(R.id.id_et_search_starting);
        lastCityEt = (EditText) findViewById(R.id.id_et_search_ending);
        listView = (ListView) findViewById(R.id.id_lv_found_flight);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btnSearch.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight flight = flights.get(position);
                Intent intent = new Intent();
                intent.putExtra("FlightNo", flight.getFlightNo());
                intent.putExtra("ArrScheduled", flight.getArrScheduled());
                setResult(RESULT_OK, intent);
                finish();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_search:
                textView.setVisibility(View.GONE);
                if (startCityEt.getText().length() != 0 && lastCityEt.getText().length() != 0){
                    startCity = startCityEt.getText().toString();
                    lastCity = lastCityEt.getText().toString();
                    cityNames = DataSupport.where("CityName = ?", startCity).find(CityName.class);
                    if (cityNames.size() == 0){
                        Toast.makeText(this, "无出发城市信息", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    startCityCode = cityNames.get(0).getCityCode();
                    cityNames.clear();
                    cityNames = DataSupport.where("CityName = ?", lastCity).find(CityName.class);
                    if (cityNames.size() == 0){
                        Toast.makeText(this, "无目的城市信息", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    lastCityCode = cityNames.get(0).getCityCode();
                    address = "http://op.juhe.cn/flight/df/fs?dtype=&orgCity="+startCityCode+"&dstCity="+lastCityCode+"&flightNo=&=&key="+APPKEY;
                    HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            parseFlightWithJSON(response);
                            Message message = new Message();
                            if (errorFlag == false) {
                                message.what = SHOW_RESPONSE;
                                message.obj = response.toString();
                                handler.sendMessage(message);
                            }else {
                                message.what = ERROR_MESSAGE;
                                handler.sendMessage(message);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    });
                }
                if (startCityEt.getText().length() == 0 && lastCityEt.getText().length() != 0){
                    Toast.makeText(this, "请输入出发城市", Toast.LENGTH_SHORT).show();
                }
                if (startCityEt.getText().length() != 0 && lastCityEt.getText().length() == 0){
                    Toast.makeText(this, "请输入抵达城市", Toast.LENGTH_SHORT).show();
                }
                if (startCityEt.getText().length() == 0 && lastCityEt.getText().length() == 0){
                    Toast.makeText(this, "请输入出发和抵达城市", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    protected void parseFlightWithJSON(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            String resultcode=jsonObject.getString("resultcode");
            if(resultcode.equals("200")){
                errorFlag = false;
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                flights.clear();
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject resultObject = jsonArray.getJSONObject(i);
                    String FlightNO = resultObject.getString("FlightNo");
                    String Rate = resultObject.getString("Rate");
                    String DepCity = resultObject.getString("DepCity");
                    String DepCode = resultObject.getString("DepCode");
                    String ArrCity = resultObject.getString("ArrCity");
                    String ArrCode = resultObject.getString("ArrCode");
                    String DepTerminal = resultObject.getString("DepTerminal");
                    String ArrTerminal = resultObject.getString("ArrTerminal");
                    String DepScheduled = resultObject.getString("DepScheduled");
                    String ArrScheduled = resultObject.getString("ArrScheduled");
                    String DepEstimated = resultObject.getString("DepEstimated");
                    String ArrEstimated = resultObject.getString("ArrEstimated");
                    String DepActual = resultObject.getString("DepActual");
                    String ArrActual = resultObject.getString("ArrActual");
                    String FlightState = resultObject.getString("FlightState");
                    Flight flight = new Flight();
                    flight.setFlightNo(FlightNO);
                    flight.setRate(Rate);
                    flight.setDepCity(DepCity);
                    flight.setDepCode(DepCode);
                    flight.setArrCity(ArrCity);
                    flight.setArrCode(ArrCode);
                    flight.setDepTerminal(DepTerminal);
                    flight.setArrTerminal(ArrTerminal);
                    flight.setDepScheduled(DepScheduled);
                    flight.setArrScheduled(ArrScheduled);
                    flight.setDepEstimated(DepEstimated);
                    flight.setArrEstimated(ArrEstimated);
                    flight.setDepActual(DepActual);
                    flight.setArrActual(ArrActual);
                    flight.setFlightState(FlightState);
                    flights.add(flight);
                }
            }else {
                errorFlag = true;
                flights.clear();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
