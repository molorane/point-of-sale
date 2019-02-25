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
public class PaymentMethod extends POSBean{
    
    private int paymentMethodID;
    private String paymentMethod;
    
    public PaymentMethod(){}
    
    public void setPaymentMethod(int paymentMethodID,String paymentMethod){
        this.paymentMethodID = paymentMethodID;
        this.paymentMethod = paymentMethod;
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
    
    @Override
    public String toString(){
        return paymentMethod;
    }
}
