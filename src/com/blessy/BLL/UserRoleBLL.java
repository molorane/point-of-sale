/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

import com.blessy.DAL.Objects.UserRole;
import com.blessy.DAL.Provider.ProviderBase;
import java.util.ArrayList;

/**
 *
 * @author Mothusi Molorane
 */
public class UserRoleBLL {
    
    private static ArrayList<UserRole> userRoles = ProviderBase.Instance().getAllUserRoles();
    
    public int AddUserRole(String roleName) {
        return ProviderBase.Instance().AddUserRole(roleName);
    }
    
    public int EditUserRole(int roleID, String roleName) {
        return ProviderBase.Instance().EditUserRole(roleID,roleName);
    }
    
    public int RemoveUserRole(int roleID) {
        return ProviderBase.Instance().RemoveUserRole(roleID);
    }
    
    public UserRole getUserRole(int roleID){
        for(UserRole userRole: userRoles){
           if(userRole.getRoleID()== roleID){
               return userRole;
           }
        }
        return null;
    }
    
    public ArrayList<UserRole> getAllUserRoles(){
        return userRoles;
    }
    
}
