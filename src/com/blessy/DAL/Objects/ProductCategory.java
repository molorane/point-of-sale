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
public class ProductCategory extends POSBean{
    
    private int productCategoryID;
    private String productCategory;
    
    public ProductCategory(){}
    
    public void setProductCategory(int productCategoryID,String productCategory){
        this.productCategoryID = productCategoryID;
        this.productCategory = productCategory;
    }
    
    public int getProductCategoryID() {
        return productCategoryID;
    }
    public void setProductCategoryID(int productCategoryID) {
        this.productCategoryID = productCategoryID;
    }
    public String getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
    
    @Override
    public String toString(){
        return productCategory;
    }
}
