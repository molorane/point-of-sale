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

public class Product extends POSBean{
    
    private String productBarCode;
    private String productName;
    private double productWholesalePrice;
    private double productRetailPrice;
    private int productCategoryID;
    private double productWeight;
    private int productWeightUnitID;
    private String productDescription;
    
    private String productCategory;
    private String productWeightUnit;
    private String siUnit;
    
    public Product(){}
    
    public void setProduct(String productBarCode,String productName,double productWholesalePrice,double productRetailPrice,
            int productCategoryID,double productWeight,int productWeightUnitID,String productDescription,String productCategory,
            String productWeightUnit,String siUnit){
        this.productBarCode = productBarCode;
        this.productName = productName;
        this.productWholesalePrice = productWholesalePrice;
        this.productRetailPrice = productRetailPrice;
        this.productCategoryID = productCategoryID;
        this.productWeight = productWeight;
        this.productWeightUnitID = productWeightUnitID;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productWeightUnit = productWeightUnit;
        this.siUnit = siUnit;
    }
    
    public String getProductBarCode() {
        return productBarCode;
    }
    public void setProductBarCode(String productBarCode) {
        this.productBarCode = productBarCode;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public double getProductWholesalePrice() {
        return productWholesalePrice;
    }
    public void setProductWholesalePrice(double productWholesalePrice) {
        this.productWholesalePrice = productWholesalePrice;
    }
    public double getProductRetailPrice() {
        return productRetailPrice;
    }
    public void setProductRetailPrice(double productRetailPrice) {
        this.productRetailPrice = productRetailPrice;
    }
    public int getProductCategoryID() {
            return productCategoryID;
    }
    public void setProductCategoryID(int productCategoryID) {
        this.productCategoryID = productCategoryID;
    }
    public double getProductWeight() {
        return productWeight;
    }
    public void setProductWeight(double productWeight) {
        this.productWeight = productWeight;
    }
    public int getProductWeightUnitID() {
        return productWeightUnitID;
    }
    public void setProductWeightUnitID(int productWeightUnitID) {
        this.productWeightUnitID = productWeightUnitID;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public String getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
    public String getProductWeightUnit() {
        return productWeightUnit;
    }
    public void setProductWeightUnit(String productWeightUnit) {
        this.productWeightUnit = productWeightUnit;
    }
    public String getSiUnit() {
        return siUnit;
    }
    public void setSiUnit(String siUnit) {
        this.siUnit = siUnit;
    }
    
    @Override
    public String toString(){
       return productName; 
    }
}
