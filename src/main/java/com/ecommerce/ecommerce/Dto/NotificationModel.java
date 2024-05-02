package com.ecommerce.ecommerce.Dto;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notifications")
public class NotificationModel {
    private int id;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NotificationModel(int id, String type, String title, String message) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String title;

    private String message;
}
