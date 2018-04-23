package com.example.xyy.junparking;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.xyy.publicclass.FastBlur;

/**
 * Created by XYY on 2018/4/22.
 */

public class MeInfoActivity extends AppCompatActivity {

    private ImageView old_img, blur_img;
    private Bitmap mBitmap, overlay;
   private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Button btn_save;
    private LinearLayout ll_head;
    private LinearLayout ll_nickname;
    private LinearLayout ll_gender;
    private LinearLayout ll_birth;
    private LinearLayout ll_Email;
    private LinearLayout ll_platenum;

    public static final String USER_NAME = "YuJu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_info_layout);
        init();
        setView();
        setModel();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        btn_save = (Button) findViewById(R.id.btn_save);
        ll_head = (LinearLayout) findViewById(R.id.ll_head);
        ll_nickname = (LinearLayout) findViewById(R.id.ll_nickname);
        ll_gender = (LinearLayout) findViewById(R.id.ll_gender);
        ll_birth = (LinearLayout) findViewById(R.id.ll_birth);
        ll_Email = (LinearLayout) findViewById(R.id.ll_Email);
        ll_platenum = (LinearLayout) findViewById(R.id.ll_platenum);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(USER_NAME);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MeInfoActivity.this, "您点击了立即保存", Toast.LENGTH_SHORT).show();
            }
        });

        ll_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MeInfoActivity.this, "您点击了头像修改", Toast.LENGTH_SHORT).show();
            }
        });

        ll_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MeInfoActivity.this, "您点击了昵称修改", Toast.LENGTH_SHORT).show();
            }
        });

        ll_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MeInfoActivity.this, "您点击了生日修改", Toast.LENGTH_SHORT).show();
            }
        });

        ll_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MeInfoActivity.this, "您点击了邮箱修改", Toast.LENGTH_SHORT).show();
            }
        });

        ll_platenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MeInfoActivity.this, "您点击了车牌号修改", Toast.LENGTH_SHORT).show();
            }
        });
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

    /**
     * @return 返回值
     * @用途：初始化组件
     */
    private  void init(){
        old_img = (ImageView) findViewById(R.id.old_img);
        blur_img = (ImageView) findViewById(R.id.blur_img);
    }

    /**
     * @return 返回值
     * @用途： 设置模糊图片的长和宽
     * @param: 参数说明
     */
    private void setView(){
        ViewGroup.LayoutParams params = blur_img.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        blur_img.setLayoutParams(params);

    }

    /**
     * @return 返回值
     * @用途： 通过计算，然后模糊图片
     * @param: 参数说明
     */
    private void setModel(){
        if(overlay != null){
            blur_img.setImageBitmap(overlay);
        }
        //将old_img对象转化为bitmap对象
        old_img.buildDrawingCache();
        mBitmap = old_img.getDrawingCache();
        mBitmap = ((BitmapDrawable) old_img.getDrawable()).getBitmap();
        //下面这两个数字是控制模糊度的，可以自定义，数字1~15
        float scaleFactor = 3;
        float radius = 3;
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        overlay = Bitmap.createBitmap((int)(width/scaleFactor), (int)(height/scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        blur_img.setImageBitmap(overlay);
    }
}
