package com.ecommerce.ecommerce.Enums;

public enum NotificationTypes {
    New_Order("New_Order"),
    Delivery_Date("Delivery_Date"),
    confirm("confirm"),
    out_for_delivery("out_for_delivery"),
    shipped("shipped"),
    delivered("delivered");



    private String value;

    public String getValue() {
        return value;
    }

    private NotificationTypes(String value) {
        this.value = value;
    }
}
