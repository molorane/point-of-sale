/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.DAL.Provider;

import com.blessy.BLL.OrderItemBLL;
import com.blessy.DAL.Objects.Order;
import com.blessy.DAL.Objects.OrderItem;
import com.blessy.DAL.Objects.PaymentMethod;
import com.blessy.DAL.Objects.Product;
import com.blessy.DAL.Objects.ProductCategory;
import com.blessy.DAL.Objects.User;
import com.blessy.DAL.Objects.UserRole;
import com.blessy.DAL.Objects.WeightUnit;
import com.blessy.factory.POSFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Mothusi Molorane
 */
public abstract class ProviderBase {
    
    private static ProviderBase tpbInstance = null;

    public static ProviderBase Instance(){
        if (tpbInstance == null){
            tpbInstance = new MySQLProvider();
        }
        return tpbInstance;
    }
    
    // ORDER DECLARATIONS
    public abstract int AddOrder(double orderTotal, double actualPayment,String cashier,int paymentMethodID);
    public abstract int EditOrder(int orderID,double orderTotal, double actualPayment,String cashier,int paymentMethodID);
    public abstract int RemoveOrder(int orderID);    
    public abstract Order getOrder(int orderID);    
    public abstract ArrayList<Order> getAllOrders();
    
    private final POSFactory bean = new POSFactory();
    
    // THIS SECTION IS FOR CONVERSION METHODS
    
    // ORDER CONVERSION METHODS
    
    protected Order GetOrderDetailsFromResultSet(ResultSet rs) throws SQLException{
        Order order = (Order) bean.createBean("Order");
        order.setOrder(Integer.parseInt(rs.getString("orderID")), 
                        rs.getString("orderDate"), 
                        Double.parseDouble(rs.getString("orderTotal")), 
                        Double.parseDouble(rs.getString("actualPayment")), 
                        rs.getString("cashier"), 
                        Integer.parseInt(rs.getString("paymentMethodID")),
                        rs.getString("paymentMethod"));
        return order;
    }
    
    protected ArrayList<Order> GetOrderDetailsCollectionFromResultSet(ResultSet rs) throws SQLException{
       ArrayList<Order> orders = new ArrayList<>();
        while (rs.next())
            orders.add(GetOrderDetailsFromResultSet(rs));
        return orders;
    }
    
    // ORDER ITEM DECLARATIONS
    public abstract int AddOrderItem(int orderID,int productID,int quantity);
    public abstract int EditOrderItem(int orderItemID, int orderID,String productID,int quantity);
    public abstract int RemoveOrderItem(int orderItemID);
    public abstract int AddOrderItems(int orderID, ArrayList<OrderItemBLL> order);    
    public abstract OrderItem getOrderItem(int orderItemID);    
    public abstract ArrayList<OrderItem> getOrderItems(int orderID);
    
    // THIS SECTION IS FOR CONVERSION METHODS
    
    // ORDER ITEM CONVERSION METHODS
    
    protected OrderItem GetOrderItemDetailsFromResultSet(ResultSet rs) throws SQLException{
        OrderItem orderitem = (OrderItem) bean.createBean("OrderItem");
        orderitem.setOrderItem(
                Integer.parseInt(rs.getString("orderItemID")),
                Integer.parseInt(rs.getString("orderID")),
                rs.getString("productID"),
                Integer.parseInt(rs.getString("quantity")));
        return orderitem;
    }
    
    protected ArrayList<OrderItem> GetOrderItemDetailsCollectionFromResultSet(ResultSet rs) throws SQLException{
       ArrayList<OrderItem> orderItems = new ArrayList<>();
        while (rs.next())
            orderItems.add(GetOrderItemDetailsFromResultSet(rs));
        return orderItems;
    }
    
    
     // PAYMENT METHOD DECLARATIONS
    
    public abstract int AddPaymentMethod(String paymentMethod);
    public abstract int EditPaymentMethod(int paymentMethodID,String paymentMethod);
    public abstract int RemovePaymentMethod(int paymentMethodID);    
    public abstract PaymentMethod getPaymentMethod(int paymentMethodID);    
    public abstract ArrayList<PaymentMethod> getAllPaymentMethods();
    
    // THIS SECTION IS FOR CONVERSION METHODS
    
    // PAYMENT METHOD CONVERSION METHODS
    
    protected PaymentMethod GetPaymentMethodDetailsFromResultSet(ResultSet rs) throws SQLException{
        PaymentMethod paymentMethod = (PaymentMethod) bean.createBean("PaymentMethod");
        paymentMethod.setPaymentMethod(
                Integer.parseInt(rs.getString("paymentMethodID")),
                rs.getString("paymentMethod"));
        return paymentMethod;
    }
    
    protected ArrayList<PaymentMethod> GetPaymentMethodDetailsCollectionFromResultSet(ResultSet rs) throws SQLException{
       ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
        while (rs.next())
            paymentMethods.add(GetPaymentMethodDetailsFromResultSet(rs));
        return paymentMethods;
    }
    
    
    // PRODUCT DECLARATIONS
    
    public abstract int AddProduct(String productBarcode, String productName,double productWholesalePrice,double productRetailPrice,
            int productCategoryID,double productWeight,int productWeightUnitID,String productDescription);
    public abstract int EditProduct(String productBarcode, String productName,double productWholesalePrice,double productRetailPrice,
            int productCategoryID,double productWeight,int productWeightUnitID,String productDescription);
    public abstract int RemoveProduct(String productBarcode);     
    public abstract Product getProduct(String productBarcode);    
    public abstract ArrayList<Product> getAllProducts();
    
    // THIS SECTION IS FOR CONVERSION METHODS
    
    // PRODUCT CONVERSION METHODS
    
    protected Product GetProductDetailsFromResultSet(ResultSet rs) throws SQLException{
        Product product = (Product) bean.createBean("Product");
        product.setProduct(rs.getString("productBarCode"),
                rs.getString("productName"),
                Double.parseDouble(rs.getString("productWholesalePrice")),
                Double.parseDouble(rs.getString("productRetailPrice")),
                Integer.parseInt(rs.getString("productCategoryID")),
                Double.parseDouble(rs.getString("productWeight")),
                Integer.parseInt(rs.getString("productWeightUnitID")),
                rs.getString("productDescription"),
                rs.getString("productCategory"),
                rs.getString("WeightUnit"),
                rs.getString("siUnit"));
        return product;
    }
    
    protected ArrayList<Product> GetProductDetailsCollectionFromResultSet(ResultSet rs) throws SQLException{
       ArrayList<Product> products = new ArrayList<>();
        while (rs.next())
            products.add(GetProductDetailsFromResultSet(rs));
        return products;
    }
    
    // PRODUCT CATEGORY DECLARATIONS
    
    public abstract int AddProductCategory(String productCategory);
    public abstract int EditProductCategory(int productCategoryID,String productCategory);
    public abstract int RemoveProductCategory(int productCategoryID);    
    public abstract ProductCategory getProductCategory(int productCategoryID);    
    public abstract ArrayList<ProductCategory> getAllProductCategories();
    
    // THIS SECTION IS FOR CONVERSION METHODS
    
    // PRODUCT CATEGORY CONVERSION METHODS
    
    protected ProductCategory GetProductCategoryDetailsFromResultSet(ResultSet rs) throws SQLException{
        ProductCategory productCategory = (ProductCategory) bean.createBean("ProductCategory");
        productCategory.setProductCategory(
                Integer.parseInt(rs.getString("productCategoryID")),
                rs.getString("productCategory"));
        return productCategory;
    }
    
    protected ArrayList<ProductCategory> GetProductCategoryDetailsCollectionFromResultSet(ResultSet rs) throws SQLException{
       ArrayList<ProductCategory> productCategories = new ArrayList<>();
        while (rs.next())
            productCategories.add(GetProductCategoryDetailsFromResultSet(rs));
        return productCategories;
    }
    
     // WEIGHT UNIT DECLARATIONS
    
    public abstract int AddWeightUnit(String weightUnit,String siUnit);
    public abstract int EditWeightUnit(int weightUnitID,String weightUnit,String siUnit);
    public abstract int RemoveWeightUnit(int weightUnitID);    
    public abstract WeightUnit getWeightUnit(int weightUnitID);    
    public abstract ArrayList<WeightUnit> getAllWeightUnits();
    
    // THIS SECTION IS FOR CONVERSION METHODS
    
    // WEIGHT UNIT CONVERSION METHODS
    
    protected WeightUnit GetWeightUnitDetailsFromResultSet(ResultSet rs) throws SQLException{
        WeightUnit weightUnit = (WeightUnit) bean.createBean("WeightUnit");
        weightUnit.setWeightUnit(
                Integer.parseInt(rs.getString("weightUnitID")),
                rs.getString("weightUnit"),
                rs.getString("siUnit"));
        return weightUnit;
    }
    
    protected ArrayList<WeightUnit> GetWeightUnitDetailsCollectionFromResultSet(ResultSet rs) throws SQLException{
       ArrayList<WeightUnit> weightUnits = new ArrayList<>();
        while (rs.next())
            weightUnits.add(GetWeightUnitDetailsFromResultSet(rs));
        return weightUnits;
    }
    
    
    // USER DECLARATIONS
    
    public abstract int AddUserAccount(String userName, String email, int isLocked,String createBy,int roleID);
    public abstract int EditUserAccount(String userName, String email,int isLocked,int roleID);
    public abstract int RemoveUserAccount(String userName);    
    public abstract User getUser(String userName);    
    public abstract ArrayList<User> getAllUsers();
    public abstract boolean Login(String userName, String password);
    public abstract int changePassword(String userName, String password); 
    
    public abstract int AddUserInformation(String userName,String firstName,String lastName,String telephone);
    public abstract int EditUserInformation(String userName,String firstName,String lastName,String telephone);
    public abstract int RemoveUserInformation(String userName);
    
    // THIS SECTION IS FOR CONVERSION METHODS
    
    // USER CONVERSION METHODS
    
    protected User GetUserDetailsFromResultSet(ResultSet rs) throws SQLException{
        User user = (User) bean.createBean("User");
        user.setUser(rs.getString("userName"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("isLocked") == null? -1: Integer.parseInt(rs.getString("isLocked")),
                rs.getString("createDate"),
                rs.getString("createBy"),
                rs.getString("roleID") == null? -1 : Integer.parseInt(rs.getString("roleID")),
                rs.getString("roleName"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("telephone"),
                rs.getString("profile"));
        return user;
    }
    
    protected ArrayList<User> GetUserDetailsCollectionFromResultSet(ResultSet rs) throws SQLException{
       ArrayList<User> users = new ArrayList<>();
        while (rs.next())
            users.add(GetUserDetailsFromResultSet(rs));
        return users;
    }
    
    // USER ROLE DECLARATIONS
    
    public abstract int AddUserRole(String roleName);
    public abstract int EditUserRole(int roleID,String roleName);
    public abstract int RemoveUserRole(int roleID);    
    public abstract UserRole getUserRole(int roleID);    
    public abstract ArrayList<UserRole> getAllUserRoles();
    
    // THIS SECTION IS FOR CONVERSION METHODS
    
    // USER ROLE CONVERSION METHODS
    
    protected UserRole GetUserRoleDetailsFromResultSet(ResultSet rs) throws SQLException{
        UserRole userRole = (UserRole) bean.createBean("UserRole");
        userRole.setUserRole(
                Integer.parseInt(rs.getString("roleID")),
                rs.getString("roleName"));
        return userRole;
    }
    
    protected ArrayList<UserRole> GetUserRoleDetailsCollectionFromResultSet(ResultSet rs) throws SQLException{
       ArrayList<UserRole> userRoles = new ArrayList<>();
        while (rs.next())
            userRoles.add(GetUserRoleDetailsFromResultSet(rs));
        return userRoles;
    }
}
