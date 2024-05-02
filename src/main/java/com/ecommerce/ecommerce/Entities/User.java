package com.ecommerce.ecommerce.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String first_name;

    public String getFirst_name() {
        return first_name;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user",orphanRemoval = true)
    private List<Cart> carts=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    private String last_name;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




    private String password;


    private String role;



    @Column(unique = true)
    private String email;

    private boolean active;

    private String image;

    public List<WishList> getWishLists() {
        return wishLists;
    }

    public void setWishLists(List<WishList> wishLists) {
        this.wishLists = wishLists;
    }

    @ManyToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name="user_address",
    joinColumns = {
           @JoinColumn(name="user_id",referencedColumnName = "id")
    },inverseJoinColumns = {
            @JoinColumn(name = "address_id",referencedColumnName = "id")
    })
    private Set<Address> addresses;

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    @ManyToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name="user_notification",
            joinColumns = {
                    @JoinColumn(name="user_id",referencedColumnName = "id")
            },inverseJoinColumns = {
            @JoinColumn(name = "notification_id",referencedColumnName = "id")
    })
    private Set<Notification> notifications;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user",orphanRemoval = true)
    private List<WishList> wishLists=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user",orphanRemoval = true)
    private List<Order> orders=new ArrayList<>();


}
