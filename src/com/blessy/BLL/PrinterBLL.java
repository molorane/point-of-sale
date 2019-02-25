/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

import com.blessy.DAL.Objects.Order;
import com.blessy.printerservice.PrinterService;
import java.util.ArrayList;

/**
 *
 * @author Mothusi Molorane
 */
public class PrinterBLL {
    
    public static void printOrder(ArrayList<OrderItemBLL> orderItems, int orderID) {
        
        Functions fn = new Functions();
        
        Order order = SalesBLL.getOrder(orderID);
        
        PrinterService printerService = new PrinterService();
        String printOrder = 
                  "               POS Supply shop\n"
                 +"               Sales Receipt\n"
                 +"Date: "+Functions.getDateTimeToday()+"\n"
                + "--------------------------------------------\n"
                + "ITEM                 PRICE     Qty  Total   \n"
                + "--------------------------------------------\n";
        
            String itemName;
            String itemPrice;
            String itemQuantity;
                
            for(OrderItemBLL orderItem: orderItems){
                
                itemName = orderItem.getProduct().getProductName();
                itemPrice = fn.getCurrency(orderItem.getProduct().getProductRetailPrice());
                itemQuantity = orderItem.getQuantity()+"";
                
                printOrder = printOrder + addSpacesAfter(itemName, 22 - itemName.length()) 
                         +addSpacesAfter(itemPrice, 11 - itemPrice.length())
                         +addSpacesAfter(itemQuantity, 6 - itemQuantity.length())
                         +fn.getCurrency(orderItem.getSubTotal())+"\n";
            }
            printOrder += "--------------------------------------------\n";
            printOrder += "Total   : "+fn.getCurrency(order.getOrderTotal())+"\n";
            printOrder += "Payment : "+fn.getCurrency(order.getActualPrayment())+"\n";
            printOrder += "Change  : "+fn.getCurrency(order.getActualPrayment() - order.getOrderTotal())+"\n";
            System.out.println(printOrder);
        
        /*
        //System.out.println(printerService.getPrinters());

        //print some stuff
        printerService.printString("EPSON-TM-T20II", printOrder);

        // cut that paper!
        byte[] cutP = new byte[] { 0x1d, 'V', 1 };
        printerService.printBytes("EPSON-TM-T20II", cutP);
        */
    }
    
    public static String addSpacesAfter(String result, int padNum){
        StringBuilder sb = new StringBuilder(result);
        for(int i=1; i< padNum; i++){
            sb.append(" ");
        }
        return sb.toString();
    }
}
