package com.example.xyy.publicclass;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by XYY on 2018/4/26.
 */

public class ScrollWithGridView extends GridView {
    public ScrollWithGridView(Context context){
        super(context);
    }

    public ScrollWithGridView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public ScrollWithGridView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
