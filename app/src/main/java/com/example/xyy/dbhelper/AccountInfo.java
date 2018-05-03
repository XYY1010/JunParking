package com.example.xyy.dbhelper;

import org.litepal.crud.DataSupport;

/**
 * Created by XYY on 2018/5/3.
 */

public class AccountInfo extends DataSupport {
    private String UserId;
    private String UserName;
    private double AccountBalance;
    private int CouponNum;
    private int HeadImgSrc;
    private String LoginPsw;
    private String PaymentPsw;

    public double getAccountBalance() {
        return AccountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        AccountBalance = accountBalance;
    }

    public int getCouponNum() {
        return CouponNum;
    }

    public void setCouponNum(int couponNum) {
        CouponNum = couponNum;
    }

    public int getHeadImgSrc() {
        return HeadImgSrc;
    }

    public void setHeadImgSrc(int headImgSrc) {
        HeadImgSrc = headImgSrc;
    }

    public String getLoginPsw() {
        return LoginPsw;
    }

    public void setLoginPsw(String loginPsw) {
        LoginPsw = loginPsw;
    }

    public String getPaymentPsw() {
        return PaymentPsw;
    }

    public void setPaymentPsw(String paymentPsw) {
        PaymentPsw = paymentPsw;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

}
