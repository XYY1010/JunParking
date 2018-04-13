package com.example.xyy.publicclass;

/**
 * Created by XYY on 2018/4/13.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
