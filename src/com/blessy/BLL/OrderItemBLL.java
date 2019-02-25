/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

import com.blessy.DAL.Objects.OrderItem;
import com.blessy.DAL.Objects.Product;
import com.blessy.DAL.Provider.ProviderBase;

/**
 *
 * @author Mothusi Molorane
 */
public class OrderItemBLL {
    
    private final Product product;
    private int quantity;
    private double subTotal;
    
    public OrderItemBLL(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        this.subTotal = this.product.getProductRetailPrice() * this.quantity;
    }
    
    public Product getProduct(){
        return this.product;
    }
    
    public int getQuantity(){
        return this.quantity;
    }
    
    public void setQuantity(int quantity){
        this.quantity = quantity;
        this.subTotal = quantity * product.getProductRetailPrice();
    }
    
    public double getSubTotal(){
        return subTotal;
    }
    
    public void setSubTotal(int quantity){
        this.subTotal = quantity * product.getProductRetailPrice();
    }
    
    public void incrementQuantity(){
        this.quantity++;
        this.subTotal = quantity * product.getProductRetailPrice();
    }
    
    public static OrderItem getOrderItem(int product){
        return ProviderBase.Instance().getOrderItem(product);
    }
    
}
