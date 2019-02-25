/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.factory;

import com.blessy.DAL.Objects.Order;
import com.blessy.DAL.Objects.OrderItem;
import com.blessy.DAL.Objects.POSBean;
import com.blessy.DAL.Objects.PaymentMethod;
import com.blessy.DAL.Objects.Product;
import com.blessy.DAL.Objects.ProductCategory;
import com.blessy.DAL.Objects.User;
import com.blessy.DAL.Objects.UserRole;
import com.blessy.DAL.Objects.WeightUnit;

/**
 *
 * @author Mothusi Molorane
 */
public class POSFactory {
    
    public POSBean createBean(String type){
        POSBean bean = null;
        
        switch (type) {
            case "Order":
                bean = new Order();
                break;
            case "Product":
                bean = new Product();
                break;
            case "ProductCategory":
                bean = new ProductCategory();
                break;
            case "WeightUnit":
                bean = new WeightUnit();
                break;
            case "OrderItem":
                bean = new OrderItem();
                break;
            case "User":
                bean = new User();
                break;
            case "UserRole":
                bean = new UserRole();
                break;
            case "PaymentMethod":
                bean = new PaymentMethod();
                break;
            default:
                break;
        }
        
        return bean;
    }
    
}
