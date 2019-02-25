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
public class WeightUnit extends POSBean{
    
    private int weightUnitID;
    private String weightUnit;
    private String siUnit;
    
    public WeightUnit(){}
    
    public void setWeightUnit(int weightUnitID,String weightUnit,String siUnit){
        this.weightUnitID = weightUnitID;
        this.weightUnit = weightUnit;
        this.siUnit = siUnit;
    }
    
    public int getWeightUnitID() {
        return weightUnitID;
    }
    public void setWeightUnitID(int weightUnitID) {
        this.weightUnitID = weightUnitID;
    }
    public String getWeightUnit() {
        return weightUnit;
    }
    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }
    public String getSiUnit() {
        return siUnit;
    }
    public void setSiUnit(String siUnit) {
        this.siUnit = siUnit;
    }
    
    @Override
    public String toString(){
        return weightUnit +" ("+ siUnit+")";
    }
}
