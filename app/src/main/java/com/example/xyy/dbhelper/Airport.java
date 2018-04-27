package com.example.xyy.dbhelper;

import org.litepal.crud.DataSupport;

/**
 * Created by XYY on 2018/4/27.
 */

public class Airport extends DataSupport{

    private int AirportId;
    private String AirportName;

    public int getAirportId() {
        return AirportId;
    }

    public void setAirportId(int airportId) {
        AirportId = airportId;
    }

    public String getAirportName() {
        return AirportName;
    }

    public void setAirportName(String airportName) {
        AirportName = airportName;
    }
}
