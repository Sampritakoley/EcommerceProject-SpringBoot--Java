package com.ecommerce.ecommerce.Dto;

import java.util.Date;

public class WishListModel {


    private int wishlist_id;

    private Date updated_time;

    private int item_id;

    public Date getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Date updated_time) {
        this.updated_time = updated_time;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(int wishlist_id) {
        this.wishlist_id = wishlist_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public WishListModel(int wishlist_id, String status,Date updated_time,int item_id,
                         boolean available, String description, String name,double offer, double price, int quantity,int products_id, String image,int brand_id) {
        this.wishlist_id = wishlist_id;
        this.status = status;
        this.updated_time=updated_time;
        this.item_id=item_id;
        this.available = available;
        this.description = description;
        this.name = name;
        this.offer = offer;
        this.price = price;
        this.quantity = quantity;
        this.products_id = products_id;
        this.image = image;
        this.brand_id=brand_id;
    }
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private double offer;

    private double price;

    private int quantity;

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private boolean available;

    private String description;

    private String name;

    public int getProducts_id() {
        return products_id;
    }

    public void setProducts_id(int products_id) {
        this.products_id = products_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private int products_id;

    private String image;

    private int brand_id;

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }
}
