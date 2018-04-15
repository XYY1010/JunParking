package com.example.xyy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyy.bean.News;
import com.example.xyy.junparking.R;

import java.util.ArrayList;

import static android.view.View.inflate;

/**
 * Created by XYY on 2018/4/14.
 */

public class MyPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<News> newsList;

    public MyPagerAdapter(Context context, ArrayList<News> newsList){
        this.context = context;
        this.newsList = newsList;
    }

    //获取轮播图的展示的view个数
    @Override
    public int getCount(){
        //return newsList.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //getView
        View view = View.inflate(context, R.layout.viewpager_item, null);
        ImageView img = (ImageView) view.findViewById(R.id.imgView);
        TextView tv = (TextView) view.findViewById(R.id.tv_title);

        //0 1 2 3 4 5 6 7
        //0 1 2 3 0 1 2 3
        img.setImageResource(newsList.get(position%newsList.size()).getResId());
        tv.setText(newsList.get(position%newsList.size()).getTitle());

        //添加到容器中
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
