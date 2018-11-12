package com.example.user.mp_salesperson.bean;

/**
 * Created by user on 10/27/2017.
 */

public class DialItem {
    String Id;
    String CustomerId;
    String OrderId;
    String OrderAmount;
    String Point;
    String UsedUnused;
    String CreatedDate;
    public DialItem(String id, String customerId, String orderId, String orderAmount, String point, String usedUnused, String createdDate) {
        this.Id = id;
        this.CustomerId = customerId;
        this.OrderId = orderId;
        this.OrderAmount = orderAmount;
        this.CreatedDate = createdDate;
        this.Point = point;
        this.UsedUnused = usedUnused;




    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        OrderAmount = orderAmount;
    }

    public String getPoint() {
        return Point;
    }

    public void setPoint(String point) {
        Point = point;
    }

    public String getUsedUnused() {
        return UsedUnused;
    }

    public void setUsedUnused(String usedUnused) {
        UsedUnused = usedUnused;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }


}
