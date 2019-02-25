/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.DAL.Objects;

import com.blessy.DAL.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Mothusi Molorane
 */
public class User extends POSBean{
    
    private String userName;
    private String email;
    private String password;
    private int isLocked;
    private String createDate;
    private String createBy;
    private int roleID;
    private String roleName;
    
    private String firstName;
    private String lastName;
    private String telephone;
    private String profile;
    
    public User(){}
    
    public void setUser(String userName,String email,String password,int isLocked,String createDate,String createBy,int roleID,
            String roleName,String firstName,String lastName,String telephone,String profile){
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isLocked = isLocked;
        this.createDate = createDate;
        this.createBy = createBy;
        this.roleID = roleID;
        this.roleName = roleName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.profile = profile;
    }
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getIsLocked() {
        return isLocked;
    }
    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCreateBy() {
        return createBy;
    }
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }
    
    @Override
    public String toString(){
        return lastName + " " + firstName;
    }
}
