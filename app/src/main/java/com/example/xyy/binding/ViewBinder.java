package com.example.xyy.binding;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by XYY on 2018/4/26.
 * 通过注解绑定view
 */

public class ViewBinder {

    public static void bind(Activity activity){
        bind(activity,activity.getWindow().getDecorView());
    }

    public static void bind(Object target, View source) {
        Field[] fields = target.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(target) != null) {
                        continue;
                    }
                    Bind bind = field.getAnnotation(Bind.class);
                    if(bind != null){
                        int viewId = bind.value();
                        field.set(target,source.findViewById(viewId));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
