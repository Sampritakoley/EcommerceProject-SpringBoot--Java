package com.ecommerce.ecommerce.Entities;

import jakarta.persistence.*;

@Entity
@Table(name="order_item")
public class OrderItem{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public double getPriceToPay() {
        return priceToPay;
    }

    public void setPriceToPay(double priceToPay) {
        this.priceToPay = priceToPay;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    private int totalQty;

    private double priceToPay;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Items item;
}
