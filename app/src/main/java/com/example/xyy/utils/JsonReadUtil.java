package com.example.xyy.utils;

import android.content.Context;
import android.content.res.AssetManager;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by XYY on 2018/4/26.
 */

public class JsonReadUtil {

    public static String getJsonStr(Context context, String fileName){
        StringBuilder stringBuffer = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try{
            InputStream is = assetManager.open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String str = null;
            while ((str = br.readLine()) != null){
                stringBuffer.append(str);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
