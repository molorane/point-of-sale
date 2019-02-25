/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

import com.blessy.BLL.Functions;
import com.blessy.BLL.OrderBLL;
import com.blessy.BLL.OrderItemBLL;
import com.blessy.BLL.PrinterBLL;
import com.blessy.BLL.ProductBLL;
import com.blessy.DAL.Objects.Order;

/**
 *
 * @author Mothusi Molorane
 */
public class Test {
    
    public static void main(String[] args){
        Functions fn = new Functions();
        OrderItemBLL oi = new OrderItemBLL(ProductBLL.getProduct("10"), 3);
        OrderItemBLL oi1 = new OrderItemBLL(ProductBLL.getProduct("11"), 4);
        OrderItemBLL oi2 = new OrderItemBLL(ProductBLL.getProduct("1002"), 13);
        OrderItemBLL oi3 = new OrderItemBLL(ProductBLL.getProduct("1003"), 5);
        OrderBLL.addOrder(oi);
        OrderBLL.addOrder(oi1);
        OrderBLL.addOrder(oi2);
        OrderBLL.addOrder(oi3);        
    }
    
}
