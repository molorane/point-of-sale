/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

import com.blessy.DAL.Objects.ProductCategory;
import com.blessy.DAL.Provider.ProviderBase;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author Mothusi Molorane
 */
public class ProductCategoryBLL {
    
    private static ArrayList<ProductCategory> productCategories = ProviderBase.Instance().getAllProductCategories();
    
    public int AddProductCategory(String productCategory) {
        return ProviderBase.Instance().AddProductCategory(productCategory);
    }
    
    public int EditProductCategory(int productCategoryID, String productCategory) {
        return ProviderBase.Instance().EditProductCategory(productCategoryID,productCategory);
    }
    
    public int RemoveProductCategory(int productCategoryID) {
        return ProviderBase.Instance().RemoveProductCategory(productCategoryID);
    }
    
    public static ProductCategory getProductCategory(int productCategoryID){
        for(ProductCategory productCategory: productCategories){
           if(productCategory.getProductCategoryID() == productCategoryID){
               return productCategory;
           }
        }
        return null;
    }
    
    public static ArrayList<ProductCategory> getAllProductCategories(){
        return productCategories;
    }
    
    public static void fillComboBoxProductCategory(JComboBox con, ArrayList<ProductCategory> list){
        if(con.getItemCount() > 0){
          con.removeAllItems();
        }
        list.forEach((bean) -> {
            con.addItem(bean);
        });
    }
    
    public static void setSelectedProductCategory(JComboBox con,ProductCategory pc){
        con.setSelectedItem(pc);
    }
}
