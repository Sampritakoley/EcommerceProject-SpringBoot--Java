package com.ecommerce.ecommerce.Entities;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "address")
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String city;

    private String street;

    private String state;

    private String address_line;

    private long zip;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public long getZip() {
        return zip;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "billingAddress",orphanRemoval = true)
    private List<Order> order=new ArrayList<>();



    public void setZip(long zip) {
        this.zip = zip;
    }
    @ManyToMany(mappedBy = "addresses",fetch=FetchType.LAZY)
    private Set<User> users;
}
