package com.example.user.mp_salesperson.bean;

import java.util.ArrayList;

/**
 * Created by Krishna on 19-01-2017.
 */

public class CartItemBean {
    ArrayList<CartItemInfo> mCartItemInfos;
    double totalPrice;
    double totalQuantity;
    double deliveryCharges = 0;

    double totalDpPoints;


    String wareHouseId;
    String companyId;
    double savingAmount = 0;
    double totalItemPrice = 0;

    public String getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(String wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public double getTotalDpPoints() {
        return totalDpPoints;
    }

    public void setTotalDpPoints(double totalDpPoints) {
        this.totalDpPoints = totalDpPoints;
    }


    public CartItemBean(ArrayList<CartItemInfo> mCartItemInfos, double totalPrice, double totalQuantity, double deliveryCharges, double totalDpPoints,double totalItemPrice,double savingAmount, String wareHouseId, String companyId) {
        this.mCartItemInfos = mCartItemInfos;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.deliveryCharges = deliveryCharges;
        this.totalDpPoints = totalDpPoints;
        this.totalItemPrice = totalItemPrice;
        this.savingAmount = savingAmount;
        this.wareHouseId = wareHouseId;
        this.companyId = companyId;

    }

    public double getSavingAmount() {
        return savingAmount;
    }

    public void setSavingAmount(double savingAmount) {
        this.savingAmount = savingAmount;
    }

    public double getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public ArrayList<CartItemInfo> getmCartItemInfos() {
        return mCartItemInfos;
    }

    public void setmCartItemInfos(ArrayList<CartItemInfo> mCartItemInfos) {
        this.mCartItemInfos = mCartItemInfos;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }




}
