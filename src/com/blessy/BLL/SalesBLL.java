/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

import com.blessy.DAL.Objects.Order;
import com.blessy.DAL.Objects.OrderItem;
import com.blessy.DAL.Provider.ProviderBase;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mothusi Molorane
 */
public class SalesBLL {
    
    public static ArrayList<Order> order = new  ArrayList<Order>();
        
    public static Order getOrder(int orderID){
        return ProviderBase.Instance().getOrder(orderID);
    }
    
    public static int AddOrder(double orderTotal, double paymentAmount, String cashier, int paymentMethodID){
        return ProviderBase.Instance().AddOrder(orderTotal, paymentAmount, cashier, paymentMethodID);
    }
    
    public static int EditOrder(int orderID,double orderTotal, double paymentAmount, String cashier, int paymentMethodID){
        return ProviderBase.Instance().EditOrder(orderID, orderTotal, paymentAmount, cashier, paymentMethodID);
    }
    
    public static int RemoveOrder(int orderID){
        return ProviderBase.Instance().RemoveOrder(orderID);
    }
    
    public static ArrayList<Order> getAllOrders(){
        return ProviderBase.Instance().getAllOrders();
    }
    
    
    public static void fillTableSales(DefaultTableModel tblModelSales){
        
        Functions fn = new Functions();
        
        if(tblModelSales.getColumnCount() > 0){
            tblModelSales.setRowCount(0);
        }
        getAllOrders().forEach((bean) -> {
            tblModelSales.addRow(new Object[]{
               bean.getOrderID(), 
               fn.getCurrencyZAR(bean.getOrderTotal()),
               fn.getCurrencyZAR(bean.getActualPrayment()),
               bean.getOrderDate(),
               UserBLL.getUser(bean.getCashier()),
               PaymentMethodBLL.getPaymentMethod(bean.getPaymentMethodID())
            });
        });
    }
    
    public static ArrayList<OrderItem> getOrderItems(int orderID){
        return ProviderBase.Instance().getOrderItems(orderID);
    }
    
    public static void fillTableOrderItems(DefaultTableModel tblModelOrderItems, int orderID){
       
       Functions fn = new Functions();
       
       if(tblModelOrderItems.getColumnCount() > 0){
            tblModelOrderItems.setRowCount(0);
       }
       getOrderItems(orderID).forEach((bean) -> {
            tblModelOrderItems.addRow(new Object[]{
               bean.getOrderItemID(), 
               bean.getProductID(),
               ProductBLL.getProduct(bean.getProductID()),
               fn.getCurrencyZAR(ProductBLL.getProduct(bean.getProductID()).getProductRetailPrice()),
               bean.getQuantity(),
               fn.getCurrencyZAR(ProductBLL.getProduct(bean.getProductID()).getProductRetailPrice() * bean.getQuantity())
            });
        });
    }
    
}
