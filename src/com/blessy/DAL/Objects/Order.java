/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.DAL.Objects;

/**
 *
 * @author Mothusi Molorane
 */
public class Order extends POSBean{
    
    private int orderID;
    private String orderDate;
    private double orderTotal;
    private double actualPrayment;
    private String cashier;
    private int paymentMethodID;
    
    private String paymentMethod;
    
    public Order(){}
    
    public void setOrder(int orderID, String orderDate,double orderTotal, double actualPrayment,String cashier,int paymentMethodID, String paymentMethod){
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.actualPrayment = actualPrayment;
        this.cashier = cashier;
        this.paymentMethodID = paymentMethodID;
        this.paymentMethod = paymentMethod;
    }

    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public double getOrderTotal() {
        return orderTotal;
    }
    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
    public double getActualPrayment() {
        return actualPrayment;
    }
    public void setActualPrayment(double actualPrayment) {
        this.actualPrayment = actualPrayment;
    }
    public String getCashier() {
        return cashier;
    }
    public void setCashier(String cashier) {
        this.cashier = cashier;
    }
    public int getPaymentMethodID() {
        return paymentMethodID;
    }
    public void setPaymentMethodID(int paymentMethodID) {
        this.paymentMethodID = paymentMethodID;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
