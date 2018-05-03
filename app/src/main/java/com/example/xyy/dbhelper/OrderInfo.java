package com.example.xyy.dbhelper;

import org.litepal.crud.DataSupport;

/**
 * Created by XYY on 2018/5/3.
 */

public class OrderInfo extends DataSupport {
    private String StartTime;
    private String EndTime;
    private String PaymentTime;
    private String OrderNum;
    private String OrderState;
    private String ParkingLotName;
    private double Price;
    private String PhoneNum;
    private String isInRoom;

    public String getIsInRoom() {
        return isInRoom;
    }

    public void setIsInRoom(String isInRoom) {
        this.isInRoom = isInRoom;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(String orderNum) {
        OrderNum = orderNum;
    }

    public String getOrderState() {
        return OrderState;
    }

    public void setOrderState(String orderState) {
        OrderState = orderState;
    }

    public String getParkingLotName() {
        return ParkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        ParkingLotName = parkingLotName;
    }

    public String getPaymentTime() {
        return PaymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        PaymentTime = paymentTime;
    }
}
