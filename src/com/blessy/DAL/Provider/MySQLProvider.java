/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.DAL.Provider;

import com.blessy.BLL.OrderItemBLL;
import com.blessy.DAL.DBConnection;
import com.blessy.DAL.Objects.Order;
import com.blessy.DAL.Objects.OrderItem;
import com.blessy.DAL.Objects.PaymentMethod;
import com.blessy.DAL.Objects.Product;
import com.blessy.DAL.Objects.ProductCategory;
import com.blessy.DAL.Objects.User;
import com.blessy.DAL.Objects.UserRole;
import com.blessy.DAL.Objects.WeightUnit;
import com.mysql.jdbc.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Mothusi Molorane
 */
public class MySQLProvider extends ProviderBase{
    
    /*
    * THIS SECTION IS FOR ORDERS
    */
    @Override
    public int AddOrder(double orderTotal, double actualPayment, String cashier, int paymentMethodID) {
       try {
            Connection conn = DBConnection.getConnection();
            CallableStatement cs = null;
            String sql = "CALL addOrder(?,?,?,?,?)";
            cs = conn.prepareCall(sql);
            cs.setDouble(1, orderTotal);
            cs.setDouble(2, actualPayment);
            cs.setString(3, cashier);
            cs.setInt(4, paymentMethodID);
            cs.registerOutParameter(5, java.sql.Types.INTEGER);
            cs.executeUpdate();
            System.out.println("OrderID: "+cs.getInt(5));
            return cs.getInt(5);
        } catch (SQLException e) {
            System.out.println("addOrder error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int EditOrder(int orderID, double orderTotal, double actualPayment, String cashier, int paymentMethodID) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE tbl_order SET orderTotal=?, actualPayment=?, cashier=?, paymentMethodID=?"+
                            "WHERE orderID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setDouble(1, orderTotal);
            pst.setDouble(2, actualPayment);
            pst.setString(3, cashier);
            pst.setInt(4, paymentMethodID);
            pst.setInt(5, orderID);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("addOrder error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int RemoveOrder(int orderID) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM tbl_order WHERE orderID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, orderID);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("addOrder error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public Order getOrder(int orderID) {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT o.orderID,o.orderDate,o.orderTotal,o.actualPayment,o.cashier,o.paymentMethodID,pm.paymentMethod " +
                            "FROM tbl_order o " +
                            "JOIN tbl_payment_method pm " +
                            "ON o.paymentMethodID = pm.paymentMethodID "+
                            "WHERE o.orderID=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderID);
            rs = pst.executeQuery();
            if (rs.next()) {
                return GetOrderDetailsFromResultSet(rs);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT o.orderID,o.orderDate,o.orderTotal,o.actualPayment,o.cashier,o.paymentMethodID,pm.paymentMethod " +
                            "FROM tbl_order o " +
                            "JOIN tbl_payment_method pm " +
                            "ON o.paymentMethodID = pm.paymentMethodID ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            return GetOrderDetailsCollectionFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("get all orders error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public int AddOrderItems(int orderID, ArrayList<OrderItemBLL> order) {
        Connection conn = DBConnection.getConnection();
        PreparedStatement pst = null;
        String sql = "INSERT INTO tbl_order_items(orderID,productID,quantity) VALUES(?,?,?)";
         try{
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(sql);
            for(OrderItemBLL orderItem: order){
                pst.setInt(1, orderID);
                pst.setString(2, orderItem.getProduct().getProductBarCode());
                pst.setInt(3, orderItem.getQuantity());
                pst.addBatch();
            }
            int[] rows;
            rows = pst.executeBatch();
            conn.commit();
            return rows.length;
        }catch(SQLException e){
            System.out.println("addOrderItems error: "+ e.getLocalizedMessage());
        }
        return 0;
    }
    
     @Override
    public int AddOrderItem(int orderID, int productID, int quantity) {
         try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "INSERT INTO tbl_order_items(orderID,productID,quantity) VALUES(?,?,?)";
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, orderID);
            pst.setInt(2, productID);
            pst.setInt(3, quantity);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("addOrderItem error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int EditOrderItem(int orderItemID, int orderID, String productID, int quantity) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE tbl_order_items SET orderID=?, productID=?, quantity=?"+
                            "WHERE orderItemID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, orderID);
            pst.setString(2, productID);
            pst.setInt(3, quantity);
            pst.setInt(4, orderItemID);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("edtOrderItem error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int RemoveOrderItem(int orderItemID) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM tbl_order_items WHERE orderItemID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, orderItemID);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("remove OrderItem error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public OrderItem getOrderItem(int orderItemID) {
       try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT oi.orderItemID,oi.orderID,oi.productID,oi.quantity,p.productName " +
                        "FROM tbl_order_items oi " +
                        "JOIN tbl_product p " +
                        "ON oi.productID = p.productBarcode "+
                        "WHERE oi.orderItemID=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderItemID);
            rs = pst.executeQuery();
            if (rs.next()) {
                return GetOrderItemDetailsFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("get OrderItem error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public ArrayList<OrderItem> getOrderItems(int orderID) {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT oi.orderItemID,oi.orderID,oi.productID,oi.quantity,p.productName " +
                        "FROM tbl_order_items oi " +
                        "JOIN tbl_product p " +
                        "ON oi.productID = p.productBarcode "+
                        "WHERE oi.orderID=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderID);
            rs = pst.executeQuery();
            return GetOrderItemDetailsCollectionFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("get all users error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    
    /*
    * THIS SECTION IS FOR PRODUCTS
    */
    @Override
    public int AddProduct(String productBarcode, String productName, double productWholesalePrice, double productRetailPrice, int productCategoryID, double productWeight, int productWeightUnitID, String productDescription) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = null;
           // the mysql insert statement
            String query = " INSERT INTO tbl_product (productBarcode, productName, productWholesalePrice, productRetailPrice, "+
                             "productCategoryID,productWeight,productWeightUnitID,productDescription)"+
                             " values (?, ?, ?, ?, ?, ?, ?, ?)";
            // create the mysql insert preparedstatement
            pst = conn.prepareStatement(query);
            pst.setString(1, productBarcode);
            pst.setString(2, productName);
            pst.setDouble(3, productWholesalePrice);
            pst.setDouble(4, productRetailPrice);
            pst.setInt(5, productCategoryID);
            pst.setDouble(6, productWeight);
            pst.setInt(7, productWeightUnitID);
            pst.setString(8, productDescription);
            // execute the preparedstatement
           int count = pst.executeUpdate();
           conn.close();
           return count;
        } catch (SQLException e) {
            System.out.println("add product error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int EditProduct(String productBarcode, String productName, double productWholesalePrice, double productRetailPrice, 
            int productCategoryID, double productWeight, int productWeightUnitID, String productDescription) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE tbl_product SET productName=?, productWholesalePrice=?, productRetailPrice=?, productCategoryID=?,"
                            +"productWeight=?, productWeightUnitID=?, productDescription=? "
                            +"WHERE productBarcode=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, productName);
            pst.setDouble(2, productWholesalePrice);
            pst.setDouble(3, productRetailPrice);
            pst.setInt(4, productCategoryID);
            pst.setDouble(5, productWeight);
            pst.setInt(6, productWeightUnitID);
            pst.setString(7, productDescription);
            pst.setString(8, productBarcode);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("Edit product error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int RemoveProduct(String productBarcode) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM tbl_product WHERE productBarcode=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, productBarcode);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("removeProduct error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public Product getProduct(String productBarcode) {
       try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT p.productBarcode, p.productName, p.productWholesalePrice, p.productRetailPrice, p.productCategoryID, "+
                        "p.productWeight, p.productWeightUnitID, p.productDescription, pc.productCategory, wu.weightUnit,wu.siUnit "+
                        "FROM tbl_product p "+
                        "JOIN tbl_product_categories pc "+
                        "ON p.productCategoryID = pc.productCategoryID "+
                        "JOIN tbl_weight_units wu "+
                        "ON p.productWeightUnitID = wu.weightUnitID "+
                        "WHERE p.productBarcode=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, productBarcode);
            rs = pst.executeQuery();
            if (rs.next()) {
                return GetProductDetailsFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("get product error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT p.productBarcode, p.productName, p.productWholesalePrice, p.productRetailPrice, p.productCategoryID, "+
                        "p.productWeight, p.productWeightUnitID, p.productDescription, pc.productCategory, wu.weightUnit,wu.siUnit "+
                        "FROM tbl_product p "+
                        "JOIN tbl_product_categories pc "+
                        "ON p.productCategoryID = pc.productCategoryID "+
                        "JOIN tbl_weight_units wu "+
                        "ON p.productWeightUnitID = wu.weightUnitID";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            return GetProductDetailsCollectionFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("get all orders error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public int AddUserAccount(String userName, String email, int isLocked,String createBy,int roleID) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = null;
           // the mysql insert statement
            String query = "INSERT INTO tbl_user (userName, email, password, isLocked, createBy, roleID)"+
                            " values (?, ?, SHA1(?), ?, ?, ?)";
            // create the mysql insert preparedstatement
            pst = conn.prepareStatement(query);
            pst.setString(1, userName);
            pst.setString(2, email);
            pst.setString(3, userName);
            pst.setInt(4, isLocked);
            pst.setString(5, createBy);
            pst.setInt(6, roleID);
            // execute the preparedstatement
           int count = pst.executeUpdate();
           conn.close();
           return count;
        } catch (SQLException e) {
            System.out.println("add user account error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int EditUserAccount(String userName, String email,int isLocked,int roleID) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE tbl_user SET email=?,isLocked=?,roleID=? "+
                            "WHERE userName=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, email);
            pst.setInt(2, isLocked);
            pst.setInt(3, roleID);
            pst.setString(4, userName);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("Edit user account error: "+ e.getLocalizedMessage());
        }
        return 0;
    }
    
    @Override
    public int changePassword(String userName, String password){
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE tbl_user SET password=SHA1(?) "+
                            "WHERE userName=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, password);
            pst.setString(2, userName);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("change account password error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int RemoveUserAccount(String userName) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM tbl_user WHERE userName=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, userName);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("remove user account error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public User getUser(String userName) {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT ui.userName, ui.firstName,ui.lastName,ui.telephone,ui.profile, "+
                        "u.email,u.password,u.isLocked,u.createDate,u.createBy,u.roleID,r.roleName " +
                        "FROM tbl_user_info ui " +
                        "LEFT JOIN tbl_user u " +
                        "ON ui.userName = u.userName " +
                        "LEFT JOIN tbl_role r " +
                        "ON u.roleID = r.roleID "+
                        "WHERE ui.userName=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            rs = pst.executeQuery();
            if (rs.next()) {
                return GetUserDetailsFromResultSet(rs);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers() {
       try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT ui.userName, ui.firstName,ui.lastName,ui.telephone,ui.profile, "+
                        "u.email,u.password,u.isLocked,u.createDate,u.createBy,u.roleID,r.roleName " +
                        "FROM tbl_user_info ui " +
                        "LEFT JOIN tbl_user u " +
                        "ON ui.userName = u.userName " +
                        "LEFT JOIN tbl_role r " +
                        "ON u.roleID = r.roleID";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            return GetUserDetailsCollectionFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("get all users error: "+ e.getLocalizedMessage());
        }
        return null;
    }
    
    @Override
    public boolean Login(String userName, String password){
        try{
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql = "SELECT * "
                            + "FROM tbl_user "
                            + "WHERE userName=? AND password=SHA1(?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, password);
            rs = pst.executeQuery();
            if(rs.next()){
               rs.close();
               pst.close();
               return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println("login error: "+ e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public int AddPaymentMethod(String paymentMethod) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = null;
           // the mysql insert statement
            String query = "INSERT INTO tbl_payment_method (paymentMethod)"+
                            " values (?)";
            // create the mysql insert preparedstatement
            pst = conn.prepareStatement(query);
            pst.setString(1, paymentMethod);
            // execute the preparedstatement
           int count = pst.executeUpdate();
           conn.close();
           return count;
        } catch (SQLException e) {
            System.out.println("add payment method error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int EditPaymentMethod(int paymentMethodID, String paymentMethod) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE tbl_payment_method SET paymentMethod=?"+
                            "WHERE paymentMethodID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, paymentMethodID);
            pst.setString(2, paymentMethod);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("Edit paymentMethod error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int RemovePaymentMethod(int paymentMethodID) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM tbl_payment_method WHERE paymentMethodID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, paymentMethodID);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("remove paymentMethod error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public PaymentMethod getPaymentMethod(int paymentMethodID) {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT paymentMethodID,paymentMethod "
                + "FROM tbl_payment_method "
                + "WHERE paymentMethodID=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, paymentMethodID);
            rs = pst.executeQuery();
            if (rs.next()) {
                return GetPaymentMethodDetailsFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println("get payment method methods error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public ArrayList<PaymentMethod> getAllPaymentMethods() {
       try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT paymentMethodID,paymentMethod "
                + "FROM tbl_payment_method ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            return GetPaymentMethodDetailsCollectionFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("get all payment methods error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public int AddProductCategory(String productCategory) {
       try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = null;
           // the mysql insert statement
            String query = "INSERT INTO tbl_product_categories (productCategory)"+
                            " values (?)";
            // create the mysql insert preparedstatement
            pst = conn.prepareStatement(query);
            pst.setString(1, productCategory);
            // execute the preparedstatement
           int count = pst.executeUpdate();
           conn.close();
           return count;
        } catch (SQLException e) {
            System.out.println("add productCategory error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int EditProductCategory(int productCategoryID, String productCategory) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE tbl_product_categories SET productCategory=?"+
                            "WHERE productCategoryID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, productCategoryID);
            pst.setString(2, productCategory);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("Edit productCategory error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int RemoveProductCategory(int productCategoryID) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM tbl_product_categories WHERE productCategoryID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, productCategoryID);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("remove productCategory error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public ProductCategory getProductCategory(int productCategoryID) {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT productCategoryID,productCategory "
                + "FROM tbl_product_categories "
                + "WHERE productCategoryID=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, productCategoryID);
            rs = pst.executeQuery();
            if (rs.next()) {
                return GetProductCategoryDetailsFromResultSet(rs);
            }
        } catch (SQLException e) {
           System.out.println("get product category methods error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public ArrayList<ProductCategory> getAllProductCategories() {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
           String sql="SELECT productCategoryID,productCategory "
                + "FROM tbl_product_categories ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            return GetProductCategoryDetailsCollectionFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("get all ProductCategories error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public int AddWeightUnit(String weightUnit,String siUnit) {
       try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = null;
           // the mysql insert statement
            String query = "INSERT INTO tbl_weight_units (weightUnit,siUnit)"+
                            " values (?,?)";
            // create the mysql insert preparedstatement
            pst = conn.prepareStatement(query);
            pst.setString(1, weightUnit);
            pst.setString(2, siUnit);
            // execute the preparedstatement
           int count = pst.executeUpdate();
           conn.close();
           return count;
        } catch (SQLException e) {
            System.out.println("add weightUnit error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int EditWeightUnit(int weightUnitID,String weightUnit,String siUnit) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE tbl_weight_units SET weightUnit=?,siUnit=? "+
                            "WHERE weightUnitID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, weightUnit);
            pst.setString(2, siUnit);
            pst.setInt(3, weightUnitID);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("Edit weight unit error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int RemoveWeightUnit(int weightUnitID) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM tbl_weight_units WHERE weightUnitID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, weightUnitID);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("remove weightUnit error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public WeightUnit getWeightUnit(int weightUnitID) {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT weightUnitID,weightUnit,siUnit "
                + "FROM tbl_weight_units "
                + "WHERE weightUnitID=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, weightUnitID);
            rs = pst.executeQuery();
            if (rs.next()) {
                return GetWeightUnitDetailsFromResultSet(rs);
            }
        } catch (SQLException e) {
           System.out.println("get weightUnit methods error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public ArrayList<WeightUnit> getAllWeightUnits() {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
           String sql="SELECT weightUnitID,weightUnit,siUnit "
                + "FROM tbl_weight_units ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            return GetWeightUnitDetailsCollectionFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("get all weightUnits error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public int AddUserRole(String roleName) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = null;
           // the mysql insert statement
            String query = "INSERT INTO tbl_role (roleName)"+
                            " values (?)";
            // create the mysql insert preparedstatement
            pst = conn.prepareStatement(query);
            pst.setString(1, roleName);
            // execute the preparedstatement
           int count = pst.executeUpdate();
           conn.close();
           return count;
        } catch (SQLException e) {
            System.out.println("add user role error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int EditUserRole(int roleID, String roleName) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE tbl_role SET roleName=? "+
                            "WHERE roleID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, roleName);
            pst.setInt(2, roleID);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("Edit role error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int RemoveUserRole(int roleID) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM tbl_role WHERE roleID=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, roleID);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("remove role error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public UserRole getUserRole(int roleID) {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT roleID,roleName "
                + "FROM tbl_role "
                + "WHERE roleID=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, roleID);
            rs = pst.executeQuery();
            if (rs.next()) {
                return GetUserRoleDetailsFromResultSet(rs);
            }
        } catch (SQLException e) {
           System.out.println("get roles error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public ArrayList<UserRole> getAllUserRoles() {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = null;
            PreparedStatement pst = null;
            String sql="SELECT roleID,roleName "
                + "FROM tbl_role ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            return GetUserRoleDetailsCollectionFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("get all roles error: "+ e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public int AddUserInformation(String userName, String firstName, String lastName, String telephone) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = null;
           // the mysql insert statement
            String query = "INSERT INTO tbl_user_info (userName, firstName, lastName, telephone)"+
                            " values (?, ?, ?, ?)";
            // create the mysql insert preparedstatement
            pst = conn.prepareStatement(query);
            pst.setString(1, userName);
            pst.setString(2, firstName);
            pst.setString(3, lastName);
            pst.setString(4, telephone);
            // execute the preparedstatement
           int count = pst.executeUpdate();
           conn.close();
           return count;
        } catch (SQLException e) {
            System.out.println("add user information error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int EditUserInformation(String userName, String firstName, String lastName, String telephone) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "UPDATE tbl_user_info SET firstname=?,lastName=?, telephone=?"+
                            "WHERE userName=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setString(3, telephone);
            pst.setString(4, userName);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("Edit user information error: "+ e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public int RemoveUserInformation(String userName) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "DELETE FROM tbl_user_info WHERE userName=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, userName);
            // execute the java preparedstatement
            int count = pst.executeUpdate();
            conn.close();
            return count;
        } catch (SQLException e) {
            System.out.println("remove user information error: "+ e.getLocalizedMessage());
        }
        return 0;
    }
}
