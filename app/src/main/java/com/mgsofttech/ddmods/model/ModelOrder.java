package com.mgsofttech.ddmods.model;

import java.io.Serializable;
import java.util.Date;

public class ModelOrder implements Serializable {
    String orderDeviceId;
    String orderId;
    String orderInvoiceId;
    String orderPrice;
    String orderTransactionId;
    String orderUserId;
    Date timestamp;
    Date timestampUpdated;

    public ModelOrder() {
    }

    public ModelOrder(Date date, Date date2, String str, String str2, String str3, String str4, String str5, String str6) {
        this.timestamp = date;
        this.timestampUpdated = date2;
        this.orderId = str;
        this.orderDeviceId = str2;
        this.orderInvoiceId = str4;
        this.orderPrice = str3;
        this.orderTransactionId = str5;
        this.orderUserId = str6;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    public Date getTimestampUpdated() {
        return this.timestampUpdated;
    }

    public void setTimestampUpdated(Date date) {
        this.timestampUpdated = date;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String str) {
        this.orderId = str;
    }

    public String getOrderInvoiceId() {
        return this.orderInvoiceId;
    }

    public void setOrderInvoiceId(String str) {
        this.orderInvoiceId = str;
    }

    public String getOrderDeviceId() {
        return this.orderDeviceId;
    }

    public void setOrderDeviceId(String str) {
        this.orderDeviceId = str;
    }

    public String getOrderPrice() {
        return this.orderPrice;
    }

    public void setOrderPrice(String str) {
        this.orderPrice = str;
    }

    public String getOrderTransactionId() {
        return this.orderTransactionId;
    }

    public void setOrderTransactionId(String str) {
        this.orderTransactionId = str;
    }

    public String getOrderUserId() {
        return this.orderUserId;
    }

    public void setOrderUserId(String str) {
        this.orderUserId = str;
    }
}
