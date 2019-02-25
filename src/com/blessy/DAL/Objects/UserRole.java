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
public class UserRole extends POSBean{
    
    private int roleID;
    private String roleName;
    
    public UserRole(){}
    
    public void setUserRole(int roleID,String roleName){
        this.roleID = roleID;
        this.roleName = roleName;
    }
    
    public int getRoleID() {
        return roleID;
    }
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    @Override
    public String toString(){
        return roleName;
    }
}
