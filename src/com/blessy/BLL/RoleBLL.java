/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

import com.blessy.DAL.Objects.UserRole;
import com.blessy.DAL.Provider.ProviderBase;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author Mothusi Molorane
 */
public class RoleBLL {
    
    private static ArrayList<UserRole> roleNames = ProviderBase.Instance().getAllUserRoles();
    
    public int AddUserRole(String roleName) {
        return ProviderBase.Instance().AddUserRole(roleName);
    }
    
    public int EditUserRole(int roleID, String roleName) {
        return ProviderBase.Instance().EditUserRole(roleID,roleName);
    }
    
    public int RemoveUserRole(int roleID) {
        return ProviderBase.Instance().RemoveUserRole(roleID);
    }
    
    public static UserRole getUserRole(int roleID){
        for(UserRole roleName: roleNames){
           if(roleName.getRoleID() == roleID){
               return roleName;
           }
        }
        return null;
    }
    
    public static UserRole getFirstUserRole(){
        return roleNames.get(0);
    }
    
    public static ArrayList<UserRole> getAllUserRoles(){
        if(roleNames.size() < 0 )
            roleNames = ProviderBase.Instance().getAllUserRoles();
        return roleNames;
    }
    
    public static void fillComboBoxUserRole(JComboBox con){
        if(con.getItemCount() > 0){
          con.removeAllItems();
        }
        getAllUserRoles().forEach((bean) -> {
            con.addItem(bean);
        });
    }
    
    public static void setSelectedUserRole(JComboBox con,UserRole ur){
        con.setSelectedItem(ur);
    }
}
