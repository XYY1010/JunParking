package com.example.xyy.junparking;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xyy.binding.Bind;
import com.example.xyy.binding.ViewBinder;
import com.example.xyy.publicclass.AirportEntity;
import com.example.xyy.publicclass.LetterListView;
import com.example.xyy.utils.JsonReadUtil;
import com.example.xyy.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by XYY on 2018/4/26.
 */

public class NationalAirportActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    //文件名称
    private final static String CityFileName = "allcity.json";

    private String locationCity;
    private String curSelCity;
    private HashMap<String, Integer> alphaIndexer;  //存放存在的汉语拼音首字母和与之对应的列表位置
    private List<AirportEntity> curCityList = new ArrayList<>();
    private List<AirportEntity> hotCityList = new ArrayList<>();
    private List<AirportEntity> totalCityList = new ArrayList<>();
    private List<AirportEntity> searchCityList = new ArrayList<>();

    @Bind(R.id.total_city_lv)
    private ListView totalCityLv;
    @Bind(R.id.total_city_letters_lv)
    private LetterListView lettersLv;
    @Bind(R.id.search_locate_content_et)
    private EditText searchContentEt;
    @Bind(R.id.search_city_lv)
    private ListView searchCityLv;
    @Bind(R.id.no_search_result_tv)
    private TextView noSearchDataTv;

    private Handler handler;
    private TextView overlay;               //对话框首字母TextView
    private OverlayThread overlayThread;    //显示首字母对话框
    private boolean mReady = false;
    private boolean isScroll = false;
    protected AirportListAdapter airportListAdapter;
    protected SearchCityListAdapter searchCityListAdapter;
    protected HotCityListAdapter hotCityListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airport_layout);
        // 默认软键盘不弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
        initListener();
    }

    /**
     * 初始化视图
     */
    private void initViews(){
        ViewBinder.bind(this);
        handler = new Handler();
        overlayThread = new OverlayThread();
        searchCityListAdapter = new SearchCityListAdapter(this, searchCityList);
        searchCityLv.setAdapter(searchCityListAdapter);
        hotCityListAdapter = new HotCityListAdapter(this, hotCityList);
        locationCity = "杭州";
        curSelCity = locationCity;
    }

    /**
     * 初始化数据
     */
    private void initData(){
        initTotalCityList();
        totalCityLv = (ListView) findViewById(R.id.total_city_lv);
        lettersLv = (LetterListView) findViewById(R.id.total_city_letters_lv);
        airportListAdapter = new AirportListAdapter(NationalAirportActivity.this, totalCityList, hotCityList);
        totalCityLv.setAdapter(airportListAdapter);
        totalCityLv.setOnScrollListener(this);
        totalCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1){
                    AirportEntity airportEntity = totalCityList.get(position);
                    showSetCityDialog(airportEntity.getName(), airportEntity.getAirportCode());
                }
            }
        });
        lettersLv.setOnTouchingLetterChangedListener(new LetterListViewListener());
        initOverlay();
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

    private class AirportListAdapter extends BaseAdapter {
        private Context context;

        private List<AirportEntity> totalAirportList;
        private List<AirportEntity> hotAirportList;
        private LayoutInflater inflater;
        final int VIEW_TYPE = 3;

        AirportListAdapter(Context context, List<AirportEntity> totalAirportList, List<AirportEntity> hotAirportList){
            this.context = context;
            this.totalAirportList = totalAirportList;
            this.hotAirportList = hotAirportList;
            inflater = LayoutInflater.from(context);

            alphaIndexer = new HashMap<>();

            for (int i = 0; i < totalAirportList.size(); i++){
                //当前汉语拼音首字母
                String currentStr = totalAirportList.get(i).getKey();

                String previewStr = (i - 1) >=0 ? totalAirportList.get(i - 1).getKey() : " ";
                if (!previewStr.equals(currentStr)){
                    String name = getAlpha(currentStr);
                    alphaIndexer.put(name, i);
                }
            }
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE;
        }

        @Override
        public int getItemViewType(int position) {
            return position < 2 ? position : 2;
        }

        @Override
        public int getCount() {
            return totalAirportList == null ? 0 : totalAirportList.size();
        }

        @Override
        public Object getItem(int position) {
            return totalAirportList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView curCityNameTv;
            ViewHolder holder;
            int viewType = getItemViewType(position);
            if (viewType == 0){
                convertView = inflater.inflate(R.layout.select_airport_location_item, null);
                LinearLayout noLocationl = (LinearLayout) convertView.findViewById(R.id.cur_city_no_data_ll);
                TextView getLocationTv = (TextView) convertView.findViewById(R.id.cur_city_re_get_location_tv);
                curCityNameTv = (TextView) convertView.findViewById(R.id.cur_city_name_tv);

                if (TextUtils.isEmpty(locationCity)){
                    noLocationl.setVisibility(View.VISIBLE);
                    curCityNameTv.setVisibility(View.GONE);
                    getLocationTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //获取定位
                        }
                    });
                }else {
                    noLocationl.setVisibility(View.GONE);
                    curCityNameTv.setVisibility(View.VISIBLE);

                    curCityNameTv.setText(locationCity);
                    curCityNameTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!locationCity.equals(curSelCity)){
                                //设置城市代码
                                String cityCode = "";
                                for (AirportEntity airportEntity : curCityList){
                                    if (airportEntity.getName().equals(locationCity)){
                                        cityCode = airportEntity.getAirportCode();
                                        break;
                                    }
                                }
                                showSetCityDialog(locationCity, cityCode);
                            }else{
                                //ToastUtils.show("当前定位城市" + curCityNameTv.getText().toString());
                                Toast.makeText(NationalAirportActivity.this, "当前定位城市" + curCityNameTv.getText().toString().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }else if (viewType == 1){   //热门城市
                convertView = inflater.inflate(R.layout.recent_city_item, null);
                GridView hotCityGv = (GridView) convertView.findViewById(R.id.recent_city_gv);
                hotCityListAdapter = new HotCityListAdapter(NationalAirportActivity.this, hotCityList);
                hotCityGv.setAdapter(hotCityListAdapter);
                hotCityGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AirportEntity airportEntity = hotCityList.get(position);
                        showSetCityDialog(airportEntity.getName(), airportEntity.getAirportCode());
                    }
                });
            } else {
                if (convertView == null){
                    holder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.city_list_item_layout, null);
                    ViewBinder.bind(holder, convertView);
                    convertView.setTag(holder);
                }else {
                    holder = (ViewHolder) convertView.getTag();
                }

                AirportEntity airportEntity = totalAirportList.get(position);
                holder.cityKeyTv.setVisibility(View.VISIBLE);
                holder.cityKeyTv.setText(getAlpha(airportEntity.getKey()));
                holder.cityNameTv.setText(airportEntity.getName());

                if (position >= 1){
                    AirportEntity preCity = totalAirportList.get(position - 1);
                    if (preCity.getKey().equals(airportEntity.getKey())){
                        holder.cityKeyTv.setVisibility(View.GONE);
                    }else {
                        holder.cityKeyTv.setVisibility(View.VISIBLE);
                    }
                }
            }
            return convertView;
        }


        private class ViewHolder {
            @Bind(R.id.city_name_tv)
            TextView cityNameTv;
            @Bind(R.id.city_key_tv)
            TextView cityKeyTv;
        }
    }

    /**
     * 热门城市适配器
     */
    private class HotCityListAdapter extends BaseAdapter{
        private List<AirportEntity> airportEntities;
        private LayoutInflater inflater;

        public HotCityListAdapter(Context mcontext, List<AirportEntity> airportEntities){
            this.airportEntities = airportEntities;
            inflater = LayoutInflater.from(mcontext);
        }

        @Override
        public int getCount() {
            return airportEntities == null ? 0 : airportEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return airportEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.city_list_grid_item_layout, null);
                ViewBinder.bind(holder, convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            AirportEntity airportEntity = airportEntities.get(position);
            holder.cityNameTv.setText(airportEntity.getName());
            return convertView;
        }

        private class ViewHolder {
            @Bind(R.id.city_list_grid_item_name_tv)
            TextView cityNameTv;
        }
    }

    /**
     * 获得首字母
     * @param key
     * @return
     */
    private String getAlpha(String key){
        if (key.equals("0")){
            return "定位";
        }else if (key.equals("1")){
            return "热门";
        }else {
            return key;
        }
    }

    /**
     * 对话框显示
     * @param curCity
     * @param cityCode
     */
    private void showSetCityDialog(final String curCity, final String cityCode){
        if (curCity.equals(curSelCity)){
            ToastUtils.show("当前定位城市"+curCity);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(NationalAirportActivity.this);    //先得到构造器
        builder.setTitle("提示");
        builder.setMessage("是否设置" + curCity + "为您的当前城市？");  //设置内容

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //选中后做你的方法
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    /**
     * 初始化汉语拼音首字母弹出提示框
     */
    private void initOverlay(){
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private class LetterListViewListener implements LetterListView.OnTouchingLetterChangedListener{
        @Override
        public void onTouchingLetterChanged(final String s) {
            isScroll = true;
            if (alphaIndexer.get(s) != null){
                int postion = alphaIndexer.get(s);
                totalCityLv.setSelection(postion);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                //延迟让overlay为不可见
                handler.postDelayed(overlayThread, 700);
            }
        }
    }

    /**
     * 设置overlay不可见
     */
    private class OverlayThread implements Runnable{
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化全部城市列表
     */
    public void initTotalCityList(){
        hotCityList.clear();
        totalCityList.clear();
        curCityList.clear();

        String cityListJson = JsonReadUtil.getJsonStr(this, CityFileName);
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(cityListJson);
            JSONArray array = jsonObject.getJSONArray("City");
            for (int i = 0; i<array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                String key = object.getString("key");
                String pinyin = object.getString("full");
                String first = object.getString("first");
                String airportCode = object.getString("code");

                AirportEntity airportEntity = new AirportEntity();
                airportEntity.setName(name);
                airportEntity.setKey(key);
                airportEntity.setPinyin(pinyin);
                airportEntity.setFirst(first);
                airportEntity.setAirportCode(airportCode);

                if (key.equals("热门")){
                    hotCityList.add(airportEntity);
                }else {
                    if (!airportEntity.getKey().equals("0") && !airportEntity.getKey().equals("1")){
                        curCityList.add(airportEntity);
                    }
                    totalCityList.add(airportEntity);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL
                || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        } else {
            isScroll = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }

        if (mReady) {
            String key = getAlpha(totalCityList.get(firstVisibleItem).getKey());
            overlay.setText(key);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟让overlay为不可见
            handler.postDelayed(overlayThread, 700);
        }
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 搜索城市列表适配器
     */
    private class SearchCityListAdapter extends BaseAdapter {

        private List<AirportEntity> airportEntities;
        private LayoutInflater inflater;

        SearchCityListAdapter(Context mContext, List<AirportEntity> cityEntities) {
            this.airportEntities = cityEntities;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return airportEntities == null ? 0 : airportEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return airportEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.city_list_item_layout, null);
                ViewBinder.bind(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AirportEntity cityEntity = airportEntities.get(position);
            holder.cityKeyTv.setVisibility(View.GONE);
            holder.cityNameTv.setText(cityEntity.getName());

            return convertView;
        }


        private class ViewHolder {
            @Bind(R.id.city_name_tv)
            TextView cityNameTv;
            @Bind(R.id.city_key_tv)
            TextView cityKeyTv;
        }
    }

    private void initListener() {

        searchCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AirportEntity airportEntity = searchCityList.get(position);
                showSetCityDialog(airportEntity.getName(), airportEntity.getAirportCode());
            }
        });
        searchContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = searchContentEt.getText().toString().trim().toLowerCase();
                setSearchCityList(content);
            }
        });

        searchContentEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftInput(searchContentEt.getWindowToken());
                    String content = searchContentEt.getText().toString().trim().toLowerCase();
                    setSearchCityList(content);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 设置搜索数据展示
     */
    private void setSearchCityList(String content) {
        searchCityList.clear();
        if (TextUtils.isEmpty(content)) {
            totalCityLv.setVisibility(View.VISIBLE);
            lettersLv.setVisibility(View.VISIBLE);
            searchCityLv.setVisibility(View.GONE);
            noSearchDataTv.setVisibility(View.GONE);
        } else {
            totalCityLv.setVisibility(View.GONE);
            lettersLv.setVisibility(View.GONE);
            for (int i = 0; i < curCityList.size(); i++) {
                AirportEntity airportEntity = curCityList.get(i);
                if (airportEntity.getName().contains(content) || airportEntity.getPinyin().contains(content)
                        || airportEntity.getFirst().contains(content)) {
                    searchCityList.add(airportEntity);
                }
            }

            if (searchCityList.size() != 0) {
                noSearchDataTv.setVisibility(View.GONE);
                searchCityLv.setVisibility(View.VISIBLE);
            } else {
                noSearchDataTv.setVisibility(View.VISIBLE);
                searchCityLv.setVisibility(View.GONE);
            }

            searchCityListAdapter.notifyDataSetChanged();
        }
    }
}
