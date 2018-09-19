package com.example.user.mp_salesperson.bean;

/**
 * Created by User on 06-01-2018.
 */

public class ReportPojo {

    String orderId;
    String totalOrder;
    String totalAmount;


    public ReportPojo(String orderId, String totalOrder, String totalAmount) {

        this.orderId = orderId;
        this.totalOrder = totalOrder;
        this.totalAmount = totalAmount;

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
