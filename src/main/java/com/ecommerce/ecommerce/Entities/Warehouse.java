package com.ecommerce.ecommerce.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "warehouse")
public class Warehouse {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public List<Shippment> getShippments() {
        return shippments;
    }

    public void setShippments(List<Shippment> shippments) {
        this.shippments = shippments;
    }

    public long getZip() {
        return zip;
    }

    public void setZip(long zip) {
        this.zip = zip;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String type;

    private String city;

    private String street;

    private String state;

    private String address_line;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "warehouse",orphanRemoval = true)
    private List<Shippment> shippments=new ArrayList<>();

    private long zip;
}
