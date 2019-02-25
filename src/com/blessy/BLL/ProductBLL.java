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
public class ProductBLL {
    
    private static ArrayList<Product> products = ProviderBase.Instance().getAllProducts();
    
    public static int AddProduct(String productBarcode, String productName,double productWholesalePrice,double productRetailPrice,
            int productCategoryID,double productWeight,int productWeightUnitID,String productDescription){
        return ProviderBase.Instance().AddProduct(productBarcode, productName, productWholesalePrice, productRetailPrice,
            productCategoryID, productWeight, productWeightUnitID,productDescription);
    }
    
    public static int EditProduct(String productBarcode, String productName,double productWholesalePrice,double productRetailPrice,
            int productCategoryID,double productWeight,int productWeightUnitID,String productDescription) {
        return ProviderBase.Instance().EditProduct(productBarcode, productName, productWholesalePrice, productRetailPrice,
            productCategoryID, productWeight, productWeightUnitID,productDescription);
    }
    
    public static int RemoveProduct(String productBarcode) {
        return ProviderBase.Instance().RemoveProduct(productBarcode);
    }
    
    public static Product getProduct(String productBarcode){
        for(Product pd: products){
            if(pd.getProductBarCode().equals(productBarcode)){
              return pd;
            }
        }
        return ProviderBase.Instance().getProduct(productBarcode);
    }
    
    public static void updateProductList(String itemCode){
        Product p = ProviderBase.Instance().getProduct(itemCode);
        products.stream().filter((pd) -> (pd.getProductBarCode().equals(itemCode))).forEachOrdered((pd) -> {
            pd.setProduct(p.getProductBarCode(),p.getProductName(),p.getProductWholesalePrice(),
                    p.getProductRetailPrice(),p.getProductCategoryID(),p.getProductWeight(),
                    p.getProductWeightUnitID(),p.getProductDescription(),p.getProductCategory(),
                    p.getProductWeightUnit(),p.getSiUnit());
        });
    }
    
    public static void AddNewProductToList(String itemCode, DefaultTableModel tblModelProducts){
        
        Product p = getProduct(itemCode);       
        products.add(p);
        Functions fn = new Functions(); 
        
        tblModelProducts.addRow(new Object[]{
            p.getProductBarCode(), 
            p.getProductName(),
            fn.getCurrencyZAR(p.getProductWholesalePrice()),
            fn.getCurrencyZAR(p.getProductRetailPrice()),
            p.getProductCategory(),
            p.getProductWeight(),
            p.getSiUnit(),
            p.getProductDescription()
         });
    }
    
    public static void refresh(){
        products = ProviderBase.Instance().getAllProducts();
    }
    
    public static ArrayList<Product> getAllProducts(){
        return products;
    }

    public static void fillTableProducts(DefaultTableModel tblModelProducts){
        
        Functions fn = new Functions();
        
        if(tblModelProducts.getColumnCount() > 0){
            tblModelProducts.setRowCount(0);
        }
        getAllProducts().forEach((bean) -> {
            tblModelProducts.addRow(new Object[]{
               bean.getProductBarCode(), 
               bean.getProductName(),
               fn.getCurrencyZAR(bean.getProductWholesalePrice()),
               fn.getCurrencyZAR(bean.getProductRetailPrice()),
               bean.getProductCategory(),
               bean.getProductWeight(),
               bean.getSiUnit(),
               bean.getProductDescription()
            });
        });
    }
}
