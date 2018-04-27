package com.example.xyy.dbhelper;

import org.litepal.crud.DataSupport;

/**
 * Created by XYY on 2018/4/27.
 */

public class HighSpeedRialStation extends DataSupport {

    private int stationId;
    private String stationName;

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

}
