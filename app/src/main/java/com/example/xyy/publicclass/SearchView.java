package com.example.xyy.publicclass;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xyy.junparking.R;

/**
 * Created by XYY on 2018/4/24.
 */

public class SearchView extends LinearLayout implements View.OnClickListener {
    private EditText etInput;
    private ImageView imgCancel;
    private Context mContext;
    private ListView lvTips;    //弹出列表
    private ArrayAdapter<String> mHintAdapter;              //提示adapter(推荐adapter)
    private ArrayAdapter<String> mAutoCompleteAdapter;      //自动补全adapter 只显示名字
    private SearchViewListener mListener;

    /**
     * 设置搜索回调接口
     * @param listener 监听者
     */
    public void  setSearchViewListener(SearchViewListener listener){
        mListener = listener;
    }

    public SearchView(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);
        initViews();

    }

    private void initViews(){
        etInput = (EditText) findViewById(R.id.airport_et_search);
        imgCancel = (ImageView) findViewById(R.id.airport_cancel);
        lvTips = (ListView) findViewById(R.id.airport_lv_tips);

        lvTips.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //set edit text
                String text = lvTips.getAdapter().getItem(position).toString();
                etInput.setText(text);
                etInput.setSelection(text.length());
                //hint list view gone and result list view show
                lvTips.setVisibility(View.GONE);
                notifyStartSearching(text);
            }
        });

        imgCancel.setOnClickListener(this);

        etInput.addTextChangedListener(new EditChangedListener());
        etInput.setOnClickListener(this);
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    lvTips.setVisibility(GONE);
                    notifyStartSearching(etInput.getText().toString());
                }
                return true;
            }
        });
    }

    /**
     * 通知监听者 进行搜索操作
     * @param text
     */
    private void notifyStartSearching(String text){
        if (mListener != null){
            mListener.onSearch(etInput.getText().toString());
        }
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 设置热搜版提示 adapter
     */
    public void setTipsHintAdapter(ArrayAdapter<String> adapter){
        this.mHintAdapter = adapter;
        if (lvTips.getAdapter() == null){
            lvTips.setAdapter(mHintAdapter);
        }
    }

    /**
     * 设置自动补全adapter
     */
    public void setAutoCompleteAdapter(ArrayAdapter<String> adapter){
        this.mAutoCompleteAdapter = adapter;
    }

    private class EditChangedListener implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!"".equals(s.toString())){
                imgCancel.setVisibility(VISIBLE);
                lvTips.setVisibility(VISIBLE);
                if (mAutoCompleteAdapter != null && lvTips.getAdapter() != mAutoCompleteAdapter){
                    lvTips.setAdapter(mAutoCompleteAdapter);
                }
                //更新autoComplete数据
                if (mListener != null){
                    mListener.onRefreshAutoComplete(s+"");
                }
            }else {
                imgCancel.setVisibility(GONE);
                if (mHintAdapter != null){
                    lvTips.setAdapter(mHintAdapter);
                }
                lvTips.setVisibility(GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.airport_et_search:
                lvTips.setVisibility(VISIBLE);
                break;
            case R.id.airport_cancel:
                etInput.setText("");
                imgCancel.setVisibility(GONE);
                break;
            default:
                break;
        }
    }

    public interface SearchViewListener{
        //更新自动补全内容
        //@param text 传入补全后的文本
        void onRefreshAutoComplete(String text);

        //开始搜索
        //@param text 传入输入框的文本
        void onSearch(String text);
    }
}
