package ba.unsa.etf.rpr.model;

import ba.unsa.etf.rpr.enums.OrderStatus;

import java.time.LocalDateTime;

public class Package implements Comparable<Package> {

    private int id;
    private String description;
    private String address;
    private User receiver;
    private User sender;
    private Courier courier;
    private int weight;
    private int deliveryCost;
    private String city;
    private int zipCode;
    private LocalDateTime sendingTime;
    private LocalDateTime deliveryTime;
    private OrderStatus orderStatus;

    public Package() {
    }

    public Package(int id, String description, String address, User sender, User receiver, Courier courier, int weight, int deliveryCost, String city, int zipCode, LocalDateTime sendingTime, LocalDateTime deliveryTime, OrderStatus orderStatus) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.sender = sender;
        this.receiver = receiver;
        this.courier = courier;
        this.weight = weight;
        this.deliveryCost = deliveryCost;
        this.city = city;
        this.zipCode = zipCode;
        this.sendingTime = sendingTime;
        this.deliveryTime = deliveryTime;
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public LocalDateTime getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(LocalDateTime sendingTime) {
        this.sendingTime = sendingTime;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public int compareTo(Package o) {
        return Integer.compare(id,o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Package aPackage)) return false;
        return getId() == aPackage.getId();
    }
}