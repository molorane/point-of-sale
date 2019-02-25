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
public class OrderItem extends POSBean{
   
    private int orderItemID;
    private int orderID;
    private String productID;
    private int quantity;
    
    public OrderItem(){}
    
    public void setOrderItem(int orderItemID,int orderID,String productID,int quantity){
        this.orderItemID = orderItemID;
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
    }
    
    public int getOrderItemID() {
            return orderItemID;
    }
    public void setOrderItemID(int orderItemID) {
            this.orderItemID = orderItemID;
    }
    public int getOrderID() {
            return orderID;
    }
    public void setOrderID(int orderID) {
            this.orderID = orderID;
    }
    public String getProductID() {
            return productID;
    }
    public void setProductID(String productID) {
            this.productID = productID;
    }
    public int getQuantity() {
            return quantity;
    }
    public void setQuantity(int quantity) {
            this.quantity = quantity;
    }
}
