package com.ecommerce.ecommerce.Dto;

import com.ecommerce.ecommerce.Entities.Brands;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import org.xmlunit.util.DocumentBuilderFactoryConfigurer;

@Getter
public class CartItemModel {

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public double getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(double cartPrice) {
        this.cartPrice = cartPrice;
    }

    public String getName() {
        return name;
    }

    private int cartQuantity;

    private double cartPrice;

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    private int item_id;

    private String name;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public CartItemModel() {
    }

    public CartItemModel(int item_id,boolean available,String description, String name,double offer, double price, int quantity,String image,int cid,int cartQuantity,double cartPrice) {
        this.name = name;
        this.quantity = quantity;
        this.image = image;
        this.available = available;
        this.price = price;
        this.offer = offer;
        this.description = description;
        this.cid = cid;
        this.cartQuantity=cartQuantity;
        this.cartPrice=cartPrice;
        this.item_id=item_id;
    }

    private int quantity;

    private String image;

    private boolean available;

    private double price;

    private double offer;

    private String description;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
    private int cid;
}
