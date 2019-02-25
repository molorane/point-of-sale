/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

import com.blessy.DAL.Objects.WeightUnit;
import com.blessy.DAL.Provider.ProviderBase;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author Mothusi Molorane
 */
public class WeightUnitBLL {
    
    private static ArrayList<WeightUnit> weightUnits = ProviderBase.Instance().getAllWeightUnits();
    
    public int AddWeightUnit(String weightUnit,String siUnit)  {
        return ProviderBase.Instance().AddWeightUnit(weightUnit,siUnit);
    }
    
    public int EditWeightUnit(int weightUnitID,String weightUnit,String siUnit){
        return ProviderBase.Instance().EditWeightUnit(weightUnitID,weightUnit,siUnit);
    }
    
    public int RemoveWeightUnit(int weightUnitID) {
        return ProviderBase.Instance().RemoveWeightUnit(weightUnitID);
    }
    
    public static WeightUnit getWeightUnit(int weightUnitID) {
        for(WeightUnit weightUnit: weightUnits){
           if(weightUnit.getWeightUnitID()== weightUnitID){
               return weightUnit;
           }
        }
        return null;
    }
    
    public static ArrayList<WeightUnit> getAllWeightUnits(){
        return weightUnits;
    }
    
    public static void fillComboBoxWeightUnit(JComboBox con, ArrayList<WeightUnit> list){
        if(con.getItemCount() > 0){
          con.removeAllItems();
        }
        list.forEach((bean) -> {
            con.addItem(bean);
        });
    }
    
    public static void setSelectedWeightUnit(JComboBox con,WeightUnit wu){
        con.setSelectedItem(wu);
    }
}
