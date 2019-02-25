/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

import com.blessy.DAL.Objects.User;
import com.blessy.DAL.Provider.ProviderBase;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mothusi Molorane
 */
public class UserBLL {
    
    private static ArrayList<User> users = ProviderBase.Instance().getAllUsers();
    
    
    public static int AddUserInformation(String userName, String firstName, String lastName,String telephone){
        return ProviderBase.Instance().AddUserInformation(userName, firstName, lastName, telephone);
    }
    
    public static int EditUserInformation(String userName,String firstName,String lastName,String telephone){
        return ProviderBase.Instance().EditUserInformation(userName, firstName, lastName, telephone);
    }
    
    public static int RemoveUserInforamtion(String userName){
        return ProviderBase.Instance().RemoveUserInformation(userName);
    }
    
    public static int AddUserAccount(String userName, String email, int isLocked,String createBy,int roleID){
        return ProviderBase.Instance().AddUserAccount(userName, email, isLocked, createBy, roleID);
    }
    
    public static int EditUserAccount(String userName, String email,int isLocked,int roleID){
        return ProviderBase.Instance().EditUserAccount(userName, email, isLocked, roleID);
    }
    
    public static int ChangePassword(String userName, String password){
       return ProviderBase.Instance().changePassword(userName, password);
    }
    
    public static User getUser(String userName){
        for(User user: users){
            if(user.getUserName().equals(userName)){
              return user;
            }
        }
        return ProviderBase.Instance().getUser(userName);
    }
    
    public static boolean Login(String userName, String password){
        return ProviderBase.Instance().Login(userName, password);
    }
    
    public static void refreshUsers(){
        users = ProviderBase.Instance().getAllUsers();
    }
    
    public static ArrayList<User> getAllUsers(){
        return users;
    }

    public static void fillTableStaffInformation(DefaultTableModel tblModelStaffInformation){
        
        if(tblModelStaffInformation.getColumnCount() > 0){
            tblModelStaffInformation.setRowCount(0);
        }
        getAllUsers().forEach((bean) -> {
            tblModelStaffInformation.addRow(new Object[]{
               bean.getUserName(), 
               bean.getFirstName(),
               bean.getLastName(),
               bean.getTelephone(),
               bean.getRoleName()
            });
        });
    }
}
