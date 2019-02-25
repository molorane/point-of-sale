/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

import com.blessy.DAL.Objects.PaymentMethod;
import com.blessy.DAL.Provider.ProviderBase;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author Mothusi Molorane
 */
public class PaymentMethodBLL {
    
    private static ArrayList<PaymentMethod> paymentMethods = ProviderBase.Instance().getAllPaymentMethods();
    
    public int AddPaymentMethod(String paymentMethod) {
        return ProviderBase.Instance().AddPaymentMethod(paymentMethod);
    }
    
    public int EditPaymentMethod(int paymentMethodID, String paymentMethod) {
        return ProviderBase.Instance().EditPaymentMethod(paymentMethodID,paymentMethod);
    }
    
    public int RemovePaymentMethod(int paymentMethodID) {
        return ProviderBase.Instance().RemovePaymentMethod(paymentMethodID);
    }
    
    public static PaymentMethod getPaymentMethod(int paymentMethodID){
        for(PaymentMethod paymentMethod: paymentMethods){
           if(paymentMethod.getPaymentMethodID()== paymentMethodID){
               return paymentMethod;
           }
        }
        return null;
    }
    
    public static PaymentMethod getFirstPaymentMethod(){
        return paymentMethods.get(0);
    }
    
    public static ArrayList<PaymentMethod> getAllPaymentMethods(){
        if(paymentMethods.size() < 0 )
            paymentMethods = ProviderBase.Instance().getAllPaymentMethods();
        return paymentMethods;
    }
    
    public static void fillComboBoxPaymentMethod(JComboBox con){
        if(con.getItemCount() > 0){
          con.removeAllItems();
        }
        getAllPaymentMethods().forEach((bean) -> {
            con.addItem(bean);
        });
    }
}
