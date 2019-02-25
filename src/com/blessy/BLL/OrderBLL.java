/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

import com.blessy.DAL.Objects.Product;
import com.blessy.DAL.Provider.ProviderBase;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mothusi Molorane
 */
public class OrderBLL {
    
    public static ArrayList<OrderItemBLL> order = new  ArrayList<OrderItemBLL>();
    
    private final Functions fn = new Functions();
    
    public static void addOrder(OrderItemBLL orderItem){
        order.add(orderItem);
    }
    
    public static void updateItemQuantity(Product product){
        order.stream().filter((orderItem) -> (orderItem.getProduct().getProductBarCode().equals(product.getProductBarCode()))).forEachOrdered((orderItem) -> {
            orderItem.incrementQuantity();
        });
    }
    
    public static void clearProducts(){
        order.clear();
    }
    
    public static void removeProduct(String productCode){
        order.remove(getOrderItem(productCode));
    }
    
    public static ArrayList<OrderItemBLL> getOrder(){
        return order;
    }
    
    public static double getOrderTotal(){
        double sum = 0;
        sum = order.stream().map((orderItem) -> orderItem.getSubTotal()).reduce(sum, (accumulator, _item) -> accumulator + _item);
        Functions fn = new Functions();
        return fn.toTwoDecimal(sum);
    }
    
    public static OrderItemBLL getOrderItem(String productCode){
       for(OrderItemBLL orderItem: order){
           if(orderItem.getProduct().getProductBarCode().equals(productCode)){
               return orderItem;
           }
        }
       return null;
    }
    
    public static void setItemQuantity(String productCode, int quantity){
        for(OrderItemBLL orderItem: order){
           if(orderItem.getProduct().getProductBarCode().equals(productCode)){
               orderItem.setQuantity(quantity);
               break;
           }
        }
    }
    
    public static boolean isOrderEmpty(){
        return order.isEmpty();
    }
    
    public static boolean itemExistInOrder(String productCode){
       return order.stream().anyMatch((orderItem) -> (orderItem.getProduct().getProductBarCode().equals(productCode)));
    }
    
    
    public static boolean processPayment(String cashier, double paymentAmount, int paymentMethodID){
        // add new order and get new orderID
        int orderID = ProviderBase.Instance().AddOrder(getOrderTotal(), paymentAmount, cashier, paymentMethodID);
        
        // Get how many items were inserted
        int rows = ProviderBase.Instance().AddOrderItems(orderID, order);
        
        // Check whether items inserted are same as number of available items
        // If the same, then order items were inserted successfully
        // Then empty the the order
        if(rows == order.size()){
            PrinterBLL.printOrder(order,orderID);
            order.clear();
            return true;
        }
        return false;
    }
    
    
    public static void fillTableOrderItems(DefaultTableModel tblOrder,Product pd,int quantity, double rowTotal){
        Functions fn = new Functions();
        tblOrder.addRow(new Object[]{
            pd.getProductBarCode(), 
            pd.getProductName(),
            fn.getCurrencyZAR(pd.getProductRetailPrice()),
            quantity,
            fn.getCurrencyZAR(rowTotal)
        });
    }
    
    public static void refreshTableOrderItems(DefaultTableModel tblOrder){
        Functions fn = new Functions();
        
        if(tblOrder.getColumnCount() > 0){
           tblOrder.setRowCount(0);
        }
        
        order.forEach((orderItem) -> {
            tblOrder.addRow(new Object[]{
                orderItem.getProduct().getProductBarCode(), 
                orderItem.getProduct().getProductName(),
                fn.getCurrencyZAR(orderItem.getProduct().getProductRetailPrice()),
                orderItem.getQuantity(),
                fn.getCurrencyZAR(orderItem.getSubTotal())
            });
        });
    }
}
