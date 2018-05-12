package com.example.xyy.utils;

/**
 * Created by XYY on 2018/5/11.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
