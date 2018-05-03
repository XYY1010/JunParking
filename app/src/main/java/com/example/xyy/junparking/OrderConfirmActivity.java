package com.example.xyy.junparking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xyy.dbhelper.AccountInfo;
import com.example.xyy.dbhelper.OrderInfo;
import com.example.xyy.dbhelper.ParkingLot;
import com.example.xyy.publicclass.CustomDatePicker;
import com.example.xyy.publicclass.PayPwdView;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.example.xyy.junparking.R.mipmap.set;


/**
 * Created by XYY on 2018/4/30.
 */

public class OrderConfirmActivity extends AppCompatActivity implements PayPwdView.InputCallBack, View.OnClickListener{

    private List<ParkingLot> parkingLots;
    private LinearLayout ll_choose1, ll_choose2;
    private ImageView imgChoose1, imgChoose2;
    private TextView tvParkingLotName, tvParkingMode;
    private TextView parkingDate, pickingDate;
    private TextView tvDanJia, tvZongJia, tvCal, tvDingJin, tvYingFuDingJin;
    private TextView Add, Sub, Num;
    private EditText etPlateNum;
    private CustomDatePicker customDatePicker1, customDatePicker2;
    private Button btnConfirmPayment;

    private String now;
    private String ParkingTime = "";
    private String PickingTime = "";
    private String ParkingLotName;
    private int num;
    private Boolean isChoose1 = true;

    private List<AccountInfo> accountInfoList;

    int[][] dayInMonthLeapYear = {{0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
            {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}};
    int[] perPrice = {0, 36, 72, 110, 143, 176, 209, 243};
    int totalPrice;
    int needDay;
    int totalNumCars = 1;
    int dingJin = 20;
    int totalDingJin;
    String[] parkingModeDes = {"室内", "室外"};

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

        setContentView(R.layout.order_confirm_layout);

        Intent intent = getIntent();
        ParkingLotName = intent.getStringExtra("ParkingLotName");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        accountInfoList = DataSupport.findAll(AccountInfo.class);

        ll_choose1 = (LinearLayout) findViewById(R.id.ll_choose1);
        ll_choose2 = (LinearLayout) findViewById(R.id.ll_choose2);
        imgChoose1 = (ImageView) findViewById(R.id.img_choose1);
        imgChoose2 = (ImageView) findViewById(R.id.img_choose2);
        parkingDate = (TextView) findViewById(R.id.parkingDate);
        pickingDate = (TextView) findViewById(R.id.pickingDate);
        tvParkingLotName = (TextView) findViewById(R.id.tv_parkinglot_name);
        tvParkingMode = (TextView) findViewById(R.id.tv_parking_mode);
        tvDanJia = (TextView) findViewById(R.id.tv_danjia);
        tvZongJia = (TextView) findViewById(R.id.tv_zongjia);
        tvCal = (TextView) findViewById(R.id.tv_cal);
        tvDingJin = (TextView) findViewById(R.id.tv_dingjin);
        tvYingFuDingJin = (TextView) findViewById(R.id.tv_yinfudingjin);
        etPlateNum = (EditText) findViewById(R.id.et_platenum);
        Add = (TextView) findViewById(R.id.tv_jia);
        Sub = (TextView) findViewById(R.id.tv_jian);
        Num = (TextView) findViewById(R.id.tv_num);
        btnConfirmPayment = (Button) findViewById(R.id.btn_confirm_payment);

        parkingDate.setOnClickListener(this);
        pickingDate.setOnClickListener(this);
        Add.setOnClickListener(this);
        Sub.setOnClickListener(this);
        ll_choose1.setOnClickListener(this);
        ll_choose2.setOnClickListener(this);
        etPlateNum.setOnClickListener(this);
        btnConfirmPayment.setOnClickListener(this);
        num = Integer.parseInt(Num.getText().toString().trim());

        initView();

        initDatePicker();

        parkingDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (pickingDate.getText() != ""){
                    btnConfirmPayment.setBackgroundColor(getColor(R.color.light_blue));
                    btnConfirmPayment.setEnabled(true);
                    needDay = calDay(parkingDate.getText().toString(),pickingDate.getText().toString());
                    totalNumCars = Integer.parseInt(Num.getText().toString().trim());
                    tvParkingMode.setText(parkingModeDes[isChoose1?0:1] + "停车" + String.valueOf(needDay) + "天");
                    if (needDay <= 7) {
                        tvDanJia.setText(String.valueOf(perPrice[needDay])+"元");
                        totalPrice =  perPrice[needDay] * totalNumCars;
                        tvCal.setText("(" + String.valueOf(perPrice[needDay]) + " * " + Num.getText().toString()+ ")");
                        tvZongJia.setText(String.valueOf(totalPrice) + "元");
                    }else{
                        tvDanJia.setText(String.valueOf(perPrice[6]+(needDay-7)*33)+"元");
                        totalPrice =  (perPrice[6]+(needDay-7)*33) * totalNumCars;
                        tvCal.setText("(" + String.valueOf(perPrice[6]+(needDay-7)*33) + " * " + Num.getText().toString()+ ")");
                        tvZongJia.setText(String.valueOf(totalPrice) + "元");
                    }
                    totalDingJin = totalNumCars * dingJin;
                    tvDingJin.setText(String.valueOf(dingJin)+"元 * " +Num.getText().toString());
                    tvYingFuDingJin.setText(String.valueOf(totalDingJin)+"元");
                }
            }
        });

        pickingDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (parkingDate.getText() != ""){
                    btnConfirmPayment.setBackgroundColor(getColor(R.color.light_blue));
                    btnConfirmPayment.setEnabled(true);
                    needDay = calDay(parkingDate.getText().toString(),pickingDate.getText().toString());
                    totalNumCars = Integer.parseInt(Num.getText().toString().trim());
                    tvParkingMode.setText(parkingModeDes[isChoose1?0:1] + "停车" + String.valueOf(needDay) + "天");
                    if (needDay <= 7) {
                        tvDanJia.setText(String.valueOf(perPrice[needDay])+"元");
                        totalPrice =  perPrice[needDay] * totalNumCars;
                        tvCal.setText("(" + String.valueOf(perPrice[needDay]) + " * " + Num.getText().toString()+ ")");
                        tvZongJia.setText(String.valueOf(totalPrice) + "元");
                    }else{
                        tvDanJia.setText(String.valueOf((perPrice[7]+(needDay-7)*33))+"元");
                        totalPrice =  (perPrice[7]+(needDay-7)*33) * totalNumCars;
                        tvCal.setText("(" + String.valueOf((perPrice[7]+(needDay-7)*33)) + " * " + Num.getText().toString()+ ")");
                        tvZongJia.setText(String.valueOf(totalPrice) + "元");
                    }
                    totalDingJin = totalNumCars * dingJin;
                    tvDingJin.setText(String.valueOf(dingJin)+"元 * " +Num.getText().toString());
                    tvYingFuDingJin.setText(String.valueOf(totalDingJin)+"元");
                }
            }
        });

        Num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                totalNumCars = Integer.parseInt(Num.getText().toString().trim());
                if (parkingDate.getText()!="" && pickingDate.getText()!=""){
                    if (needDay <= 7) {
                        totalPrice =  perPrice[needDay] * totalNumCars;
                        tvCal.setText("(" + String.valueOf(perPrice[needDay]) + " * " + Num.getText().toString()+ ")");
                        tvZongJia.setText(String.valueOf(totalPrice) + "元");
                    }else{
                        totalPrice =  (perPrice[7]+(needDay-7)*33) * totalNumCars;
                        tvCal.setText("(" + String.valueOf(perPrice[7]+(needDay-7)*33) + " * " + Num.getText().toString()+ ")");
                        tvZongJia.setText(String.valueOf(totalPrice) + "元");
                    }
                    totalDingJin = totalNumCars * dingJin;
                    tvDingJin.setText(String.valueOf(dingJin)+"元 * " +Num.getText().toString());
                    tvYingFuDingJin.setText(String.valueOf(totalDingJin)+"元");
                }
            }
        });
    }

    private void initView(){
        parkingLots = DataSupport.where("ParkingLotName = ?", ParkingLotName).find(ParkingLot.class);
        tvParkingLotName.setText(ParkingLotName);
        if (parkingLots.get(0).getIsInRoom().toString().equals("室内")){
            ll_choose2.setVisibility(View.GONE);
            isChoose1 = true;
            tvParkingMode.setText("室内停车");
        }else if (parkingLots.get(0).getIsInRoom().toString().equals("室外")){
            ll_choose1.setVisibility(View.GONE);
            isChoose1 = false;
            tvParkingMode.setText("室外停车");
        }else{
            imgChoose1.setImageResource(R.mipmap.choosed);
            imgChoose2.setImageResource(R.mipmap.unchoosed);
            isChoose1 = true;
            tvParkingMode.setText("室内停车");
        }
        tvDingJin.setText("0元");
        tvYingFuDingJin.setText("0元");

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.parkingDate:
                customDatePicker1.show(now);
                break;
            case R.id.pickingDate:
                customDatePicker2.show(now);
                break;
            case R.id.tv_jia:
                num = Integer.parseInt(Num.getText().toString().trim());
                if (num >= 5){
                    Add.setTextColor(0xFFBBBBBB);
                    Add.setEnabled(false);
                    Toast.makeText(OrderConfirmActivity.this, "一单最多预定5个车位", Toast.LENGTH_SHORT).show();
                }else {
                    Add.setTextColor(0xFF000000);
                    Sub.setTextColor(0xFF000000);
                    Add.setEnabled(true);
                    Sub.setEnabled(true);
                    num += 1;
                    Num.setText(String.valueOf(num));
                    if (num == 5)
                    {
                        Add.setTextColor(0xFFBBBBBB);
                        Add.setEnabled(false);
                        Toast.makeText(OrderConfirmActivity.this, "一单最多预定5个车位", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_jian:
                num = Integer.parseInt(Num.getText().toString().trim());
                if (num <= 1){
                    Sub.setTextColor(0xFFBBBBBB);
                    Sub.setEnabled(false);
                }else {
                    Sub.setTextColor(0xFF000000);
                    Add.setTextColor(0xFF000000);
                    Sub.setEnabled(true);
                    Add.setEnabled(true);
                    num -= 1;
                    Num.setText(String.valueOf(num));
                    if (num == 1)
                    {
                        Sub.setTextColor(0xFFBBBBBB);
                        Sub.setEnabled(false);
                    }
                }
                break;
            case R.id.ll_choose1:
                imgChoose1.setImageResource(R.mipmap.choosed);
                imgChoose2.setImageResource(R.mipmap.unchoosed);
                isChoose1 = true;
                tvParkingMode.setText("室内停车");
                if (parkingDate.getText()!=""&&pickingDate.getText()!=""){
                    needDay = calDay(parkingDate.getText().toString(),pickingDate.getText().toString());
                    tvParkingMode.setText(parkingModeDes[isChoose1?0:1] + "停车" + String.valueOf(needDay) + "天");
                    if (needDay <= 7) {
                        tvDanJia.setText(String.valueOf(perPrice[needDay])+"元");
                    }else{
                        tvDanJia.setText(String.valueOf(perPrice[7]+(needDay-7)*33)+"元");
                    }
                }
                break;
            case R.id.ll_choose2:
                imgChoose1.setImageResource(R.mipmap.unchoosed);
                imgChoose2.setImageResource(R.mipmap.choosed);
                isChoose1 = false;
                tvParkingMode.setText("室外停车");
                if (parkingDate.getText()!=""&&pickingDate.getText()!=""){
                needDay = calDay(parkingDate.getText().toString(),pickingDate.getText().toString());
                tvParkingMode.setText(parkingModeDes[isChoose1?0:1] + "停车" + String.valueOf(needDay) + "天");
                    if (needDay <= 7) {
                        tvDanJia.setText(String.valueOf(perPrice[needDay])+"元");
                    }else{
                        tvDanJia.setText(String.valueOf(perPrice[7]+(needDay-7)*33)+"元");
                    }
                }
                break;
            case R.id.et_platenum:
                Intent intent = new Intent(OrderConfirmActivity.this, LicenseActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_confirm_payment:
                Bundle bundle = new Bundle();
                bundle.putString(PayFragment.EXTRA_CONTENT, "支付：¥ " + (totalPrice+totalDingJin));

                PayFragment fragment = new PayFragment();
                fragment.setArguments(bundle);
                fragment.setPaySuccessCallBack(OrderConfirmActivity.this);
                fragment.show(getSupportFragmentManager(), "Pay");
                break;
        }

    }

    @Override
    public void onInputFinish(String result) {
        if (result.equals("123456")) {
            if (accountInfoList.size()!=0) {
                double accountBalance = accountInfoList.get(0).getAccountBalance();
                if (totalPrice+totalDingJin>accountBalance) {
                    Toast.makeText(this, "账户余额不足，请充值！！！", Toast.LENGTH_SHORT).show();
                }else {
                    String OrderNum = OrderNumGenerator();
                    String StartTime = parkingDate.getText().toString();
                    String EndTime = pickingDate.getText().toString();
                    String state = "已支付";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                    String paymentTime = sdf.format(new Date());
                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setParkingLotName(tvParkingLotName.getText().toString());
                    orderInfo.setStartTime(StartTime);
                    orderInfo.setEndTime(EndTime);
                    orderInfo.setOrderNum(OrderNum);
                    orderInfo.setOrderState(state);
                    orderInfo.setPaymentTime(paymentTime);
                    orderInfo.setPrice(totalPrice+totalDingJin);
                    orderInfo.setPhoneNum(accountInfoList.get(0).getUserId());
                    if (isChoose1) {
                        orderInfo.setIsInRoom("室内");
                 }else {
                        orderInfo.setIsInRoom("室外");
                    }
                    orderInfo.save();
                    Toast.makeText(this, "支付成功！！！", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(OrderConfirmActivity.this, OrderShowActivity.class);
                    intent.putExtra("OrderNum", OrderNum);
                    startActivity(intent);
                }
            }
        } else {
            Toast.makeText(this, "密码错误，支付失败！！！", Toast.LENGTH_SHORT).show();
        }
    }

    private void initDatePicker(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                ParkingTime = time;
                int res = time.compareTo(now);
                if (!PickingTime.equals("")) {
                    int res1 = time.compareTo(PickingTime);
                    if (res <= 0) {
                        Toast.makeText(OrderConfirmActivity.this, "开始时间不能等于当前时间", Toast.LENGTH_SHORT).show();
                    } else if (res1 >= 0){
                        Toast.makeText(OrderConfirmActivity.this, "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
                    }else {
                        parkingDate.setText(time);
                    }
                }else {
                    if (res <= 0) {
                    Toast.makeText(OrderConfirmActivity.this, "开始时间不能等于当前时间", Toast.LENGTH_SHORT).show();
                    }else {
                        parkingDate.setText(time);
                    }
                }
            }
        }, now, "2018-12-31 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(true); // 显示时和分
        customDatePicker1.setIsLoop(true); // 允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                PickingTime = time;
                int res = time.compareTo(now);
                if (!ParkingTime.equals("")) {
                    int res1 = time.compareTo(ParkingTime);
                    if (res == 0) {
                        Toast.makeText(OrderConfirmActivity.this, "结束时间不能等于当前时间", Toast.LENGTH_SHORT).show();
                    } else if (res1 <= 0) {
                        Toast.makeText(OrderConfirmActivity.this, "开始时间不能小于结束时间", Toast.LENGTH_SHORT).show();
                    } else {
                        pickingDate.setText(time);
                    }
                }else{
                    if (res == 0) {
                        Toast.makeText(OrderConfirmActivity.this, "结束时间不能等于当前时间", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        pickingDate.setText(time);
                    }
                }
            }
        }, now, "2018-12-31 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

    private int calDay(String time1, String time2){
        String[] strTime1 = time1.split("-");
        String[] strTime11 = strTime1[2].split(" ");
        String[] strTime12 = strTime11[1].split(":");
        String[] strTime2 = time2.split("-");
        String[] strTime21 = strTime2[2].split(" ");
        String[] strTime22 = strTime21[1].split(":");

        int year1 = Integer.parseInt(strTime1[0]);
        int year2 = Integer.parseInt(strTime2[0]);
        int month1 = Integer.parseInt(strTime1[1]);
        int month2 = Integer.parseInt(strTime2[1]);
        int day1 = Integer.parseInt(strTime11[0]);
        int day2 = Integer.parseInt(strTime21[0]);
        int hour1 = Integer.parseInt(strTime12[0]);
        int hour2 = Integer.parseInt(strTime22[0]);
        int minute1 = Integer.parseInt(strTime12[1]);
        int minute2 = Integer.parseInt(strTime22[1]);

        int hour = resHour(year1, month1, day1, hour1, minute1, year2, month2,day2, hour2, minute2);

        if (hour>0 && hour/24 == 0){
            hour = 24;
        }

        int day = hour / 24;

        return day;
    }

    private int resHour(int year1, int month1, int day1, int hour1, int minute1, int year2, int month2, int day2, int hour2, int minute2){
        int hour = 0;
        while (year1 < year2 || month1 < month2 || day1 < day2 || hour1 < hour2 || minute1 < minute2){
            minute1++;
            if (minute1>=60){
                minute1 = 0;
                hour1++;
                hour++;
                if (hour1>=24){
                    hour1 = 0;
                    day1++;
                    if (day1 > dayInMonthLeapYear[((year1%4==0&&year1%100!=0)||year1%400==0) ? 0 : 1][month1]){
                        day1 = 1;
                        month1++;
                        if (month1>12){
                            month1 = 1;
                            year1++;
                        }
                    }
                }
            }
        }
        return hour;
    }

    private String OrderNumGenerator(){
        Random random = new Random();
        int rand = random.nextInt(100);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);
        String time = sdf.format(new Date());
        String randomNum = time+"00"+String.valueOf(rand);
        return randomNum;
    }

}
