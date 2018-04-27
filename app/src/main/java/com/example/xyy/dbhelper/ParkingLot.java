package com.example.xyy.dbhelper;

import org.litepal.crud.DataSupport;

/**
 * Created by XYY on 2018/4/27.
 */

public class ParkingLot extends DataSupport {
    private int ParkingLotId;
    private String ParkingLotName;
    private String ImageSrc;
    private double RatingScore;
    private int Sales;
    private String IsInRoom;
    private String Distance;
    private String Remarks;
    private int AvgPrice;
    private String CityName;
    private boolean Service1;
    private boolean Service2;
    private boolean Service3;
    private boolean Service4;
    private String Discount;

    public int getAvgPrice() {
        return AvgPrice;
    }

    public void setAvgPrice(int avgPrice) {
        AvgPrice = avgPrice;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getImageSrc() {
        return ImageSrc;
    }

    public void setImageSrc(String imageSrc) {
        ImageSrc = imageSrc;
    }

    public String getIsInRoom() {
        return IsInRoom;
    }

    public void setIsInRoom(String isInRoom) {
        IsInRoom = isInRoom;
    }

    public int getParkingLotId() {
        return ParkingLotId;
    }

    public void setParkingLotId(int parkingLotId) {
        ParkingLotId = parkingLotId;
    }

    public String getParkingLotName() {
        return ParkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        ParkingLotName = parkingLotName;
    }

    public double getRatingScore() {
        return RatingScore;
    }

    public void setRatingScore(double ratingScore) {
        RatingScore = ratingScore;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public int getSales() {
        return Sales;
    }

    public void setSales(int sales) {
        Sales = sales;
    }

    public boolean isService1() {
        return Service1;
    }

    public void setService1(boolean service1) {
        Service1 = service1;
    }

    public boolean isService2() {
        return Service2;
    }

    public void setService2(boolean service2) {
        Service2 = service2;
    }

    public boolean isService3() {
        return Service3;
    }

    public void setService3(boolean service3) {
        Service3 = service3;
    }

    public boolean isService4() {
        return Service4;
    }

    public void setService4(boolean service4) {
        Service4 = service4;
    }
}
