package com.example.xyy.junparking;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xyy.adapter.SearchAdapter;
import com.example.xyy.bean.SearchBean;
import com.example.xyy.publicclass.SearchView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by XYY on 2018/4/24.
 */

public class NationalAirportActivity extends AppCompatActivity implements SearchView.SearchViewListener{

    /**
     * 搜索结果列表view
     */
    private ListView lvResult;
    /**
     * 搜索view
     */
    private  SearchView searchView;
    /**
     * 热搜框列表
     */
    private ArrayAdapter<String> hintAdapter;
    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;
    /**
     * 搜索结果列表adapter
     */
    private SearchAdapter resultAdapter;

    /**
     * 数据库数据，总数据
     */
    private List<SearchBean> dbData;

    /**
     * 热搜版数据
     */
    private List<String> hintData;
    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;
    /**
     * 搜索结果的数据
     */
    private List<SearchBean> resultData;
    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;
    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    /**
     * 设置提示框显示项的个数
     * @param hintSize
     */
    private static void setHintSize(int hintSize){
        NationalAirportActivity.hintSize = hintSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airport_layout);

        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initData();
        initViews();

    }

    /**
     * 初始化视图
     */
    private void initViews(){
        lvResult = (ListView) findViewById(R.id.airport_lv_search_result);
        searchView = (SearchView) findViewById(R.id.airport_search_view);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);

        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(NationalAirportActivity.this, position+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData(){
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 获得db数据
     */
    private void getDbData(){
        int size = 100;
        dbData = new ArrayList<>(size);
        for (int i=0; i<size; i++){
            dbData.add(new SearchBean(R.mipmap.ic_launcher, "萧山机场777停车场"+(i+1), "萧山机场", i * 20 + 2 + ""));
        }
    }

    /**
     * 获得热搜版data和adapter
     */
    private  void getHintData(){
        hintData = new ArrayList<>(hintSize);
        for (int i=1; i<=hintSize; i++){
            hintData.add("热搜版"+i+"：停车场");
        }
        hintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hintData);
    }

    /**
     * 自动补全data和adapter
     * @param text
     */
    private void getAutoCompleteData(String text){
        if (autoCompleteData == null){
            autoCompleteData = new ArrayList<>(hintSize);
        }else {
            //根据text获取auto data
            autoCompleteData.clear();
            for (int i=0, count = 0; i<dbData.size()&&count<hintSize; i++){
                if (dbData.get(i).getTitle().contains(text.trim())){
                    autoCompleteData.add(dbData.get(i).getTitle());
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null){
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
        }else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     * @param text
     */
    public void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    resultData.add(dbData.get(i));
                }
            }
        }
        if (resultAdapter == null) {
            resultAdapter = new SearchAdapter(this, resultData, R.layout.airport_item_list);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

    /**
     * 当搜索框文本改变时 出发的回调，更新自动补全数据
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edittext触发的回调
     * @param text
     */
    @Override
    public void onSearch(String text) {
        getResultData(text);
        lvResult.setVisibility(View.VISIBLE);
        if (lvResult.getAdapter() == null){
            lvResult.setAdapter(resultAdapter);
        }else {
            resultAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "完成搜索", Toast.LENGTH_LONG).show();
    }
}
