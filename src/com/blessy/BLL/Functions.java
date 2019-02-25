/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

import com.jtattoo.plaf.texture.TextureLookAndFeel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Mothusi Molorane
 */
public class Functions {
    public String getPassword(JPasswordField pass){
        String r = "";
        char[] password = pass.getPassword();
        
        for(int i=0;i<password.length;i++){
            r +=password[i];
        }
      return r;
    }
    
    public double toTwoDecimal(double money){
        DecimalFormat df = new DecimalFormat(".##");
        return Double.parseDouble(df.format(money));
    }
    
    public String getCurrency1(double amount){
        return String.format("%,.2f", amount);
    }
    
    public String getCurrencyZAR(double amount){
        Locale locale = new Locale("en", "ZA");      
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(amount);
    }
    
    public String getCurrency(double amount){
        Locale locale = new Locale("en", "ZA");      
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(amount).substring(2);
    }
    
    public String getDateToday(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = new Date();
        return formatter.format(dt);
    }
    
    public static String getDateTimeToday(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date dt = new Date();
        return formatter.format(dt); 
    }
    
    public static void setFormTheme(){
        try{
            Properties props = new Properties();
            props.put("logoString", "NanoWare");
            TextureLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void errorMessage(String msg){
        JOptionPane.showMessageDialog(null,msg,"Problem encountered!",JOptionPane.ERROR_MESSAGE);
    }
    
    public void warningMessage(String msg){
       JOptionPane.showMessageDialog(null,msg,"Warning!",JOptionPane.WARNING_MESSAGE); 
    }
    
    public void successMessage(String msg){
       JOptionPane.showMessageDialog(null,msg,"Action Successful!",JOptionPane.INFORMATION_MESSAGE); 
    }
}
