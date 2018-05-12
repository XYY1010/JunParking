package com.example.xyy.dbhelper;

import org.litepal.crud.DataSupport;

/**
 * Created by XYY on 2018/5/11.
 */

public class CityName extends DataSupport {
    private String CityName;
    private String CityCode;

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

}
