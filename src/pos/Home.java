/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

import com.blessy.BLL.Functions;
import com.blessy.BLL.OrderBLL;
import com.blessy.BLL.OrderItemBLL;
import com.blessy.BLL.PaymentMethodBLL;
import com.blessy.BLL.ProcessBLL;
import com.blessy.BLL.ProductBLL;
import com.blessy.BLL.ProductCategoryBLL;
import com.blessy.BLL.SalesBLL;
import com.blessy.BLL.UserBLL;
import com.blessy.BLL.WeightUnitBLL;
import com.blessy.DAL.Objects.Order;
import com.blessy.DAL.Objects.PaymentMethod;
import com.blessy.DAL.Objects.Product;
import com.blessy.DAL.Objects.ProductCategory;
import com.blessy.DAL.Objects.User;
import com.blessy.DAL.Objects.WeightUnit;
import com.blessy.barcode.BarcodeAwareAWTEventListener;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 * @author Mothusi Molorane
 */

public class Home extends javax.swing.JFrame {
    
    private User loggedInUser;
    private Product currentProduct;
    private int rowSelected = -1;
    private String itemCodeSelected = "";
    private PaymentMethod paymentMethod = null;
    
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension appSize = new Dimension(1116,750);
    private static final int appPosX = screenSize.width/2- appSize.width/2;
    private static final int appPosY = screenSize.width/2- appSize.width/2;
    private static final Rectangle appBounds = new Rectangle(appPosX,appPosY,appSize.width,appSize.height);
    
    private final DefaultTableModel tblModel;
    
    
    // Panel Products Declarations
    private final DefaultTableModel tblModelProducts;
    private Product pnlProduct;
    private WeightUnit pnlWeightUnit;
    private ProductCategory pnlProductCategory;
    private int pnlProductTaleRow = -1;
    
    // Panel Sales Declarations
    private final DefaultTableModel tblModelSales;
    private Order pnlOrder;
    private int pnlSaleTaleRow = -1;
    
    // Panel Sales Declarations
    private final DefaultTableModel tblModelStaffInformation;
    private User pnlStaffInformation;
    private int pnlStaffInformationTaleRow = -1;
            
    private final Functions fn = new Functions();
    
    /**
     * Creates new form Home
     * @param loggedInUser
     */
    public Home(User loggedInUser) {
        super("LOGGED IN USER:   "+loggedInUser.getRoleName().toUpperCase()+"  , "+loggedInUser.getLastName()+" "+loggedInUser.getFirstName());
        initComponents();
        this.loggedInUser = loggedInUser;
        setLabel(lblDateToday,fn.getDateToday());
        setLabel(lblCashier, loggedInUser.getLastName()+" "+loggedInUser.getFirstName());
        setIconImage(ImageHelper.loadImage("../images/img.png").getImage());
        setBounds(appBounds);
        setLocationRelativeTo(null);
        tblModel = (DefaultTableModel) tblOrderItems.getModel();
        tblModelProducts = (DefaultTableModel) tblProducts.getModel();
        tblModelSales = (DefaultTableModel) tblSales.getModel();
        tblModelStaffInformation = (DefaultTableModel) tblStaffInformation.getModel();
        Listenerscanner();
        fillPaymentMethods();
    }
    
    public final void fillPaymentMethods(){
        PaymentMethodBLL.fillComboBoxPaymentMethod(jcbPaymentMethod);
        paymentMethod = PaymentMethodBLL.getFirstPaymentMethod();
    }
    
    public final void Listenerscanner() {
        Toolkit.getDefaultToolkit().addAWTEventListener(new BarcodeAwareAWTEventListener((String barcodeString) -> {
            //stid.setText(barcodeString);
        }), AWTEvent.KEY_EVENT_MASK);
    }
     
    
    public void setLabel(JLabel lblDate, String msg){
        lblDate.setText(msg);
    }
    
    public void setSalesJTable(){
        tblSales.getColumnModel().getColumn(0).setPreferredWidth(40);
        tblSales.getColumnModel().getColumn(1).setPreferredWidth(10);
        tblSales.getColumnModel().getColumn(0).setPreferredWidth(10);
    }
     
    public void setOrderItem(){
        lblItemCode.setText(currentProduct.getProductBarCode());
        lblItemName.setText(currentProduct.getProductName());
        lblDescription.setText(currentProduct.getProductDescription());
        lblCategory.setText(currentProduct.getProductCategory());
        lblItemPrice.setText("R"+currentProduct.getProductRetailPrice());
        lblItemWeight.setText(currentProduct.getProductWeight()+""+currentProduct.getSiUnit());
        txtItemQuantity.setText("1");
        lblNotification.setText("");
    }
    
    public void setOrderItemWithQuantity(String quantity){
        txtItemCode.setText(currentProduct.getProductBarCode());
        lblItemCode.setText(currentProduct.getProductBarCode());
        lblItemName.setText(currentProduct.getProductName());
        lblDescription.setText(currentProduct.getProductDescription());
        lblCategory.setText(currentProduct.getProductCategory());
        lblItemPrice.setText("R"+currentProduct.getProductRetailPrice());
        lblItemWeight.setText(currentProduct.getProductWeight()+""+currentProduct.getSiUnit());
        txtItemQuantity.setText(quantity); 
    }
    
    public void clearOrderItem(){
        lblItemCode.setText("");
        lblItemName.setText("");
        lblDescription.setText("");
        lblCategory.setText("");
        lblItemPrice.setText("");
        lblItemWeight.setText("");
        txtItemQuantity.setText("");
        lblNotification.setText("");
    }
    
    public void clearOrderItemAfterAdd(){
        txtItemCode.setText("");
        lblItemCode.setText("");
        lblItemName.setText("");
        lblDescription.setText("");
        lblCategory.setText("");
        lblItemPrice.setText("");
        lblItemWeight.setText("");
        txtItemQuantity.setText("");
        lblNotification.setText("");
    }
    
    public void posDefaults(){
        rowSelected = -1;
        itemCodeSelected = "";
        currentProduct = null;
        clearOrderItemAfterAdd();
    }
    
    public void showPOS(){
        panelPOS.setVisible(true);
        panelExpenditures.setVisible(false);
        panelOrders.setVisible(false);
        panelPurchases.setVisible(false);
        panelStaff.setVisible(false);
        panelProducts.setVisible(false);
        panelStock.setVisible(false);
    }
    
    public void showExpenditures(){
        panelPOS.setVisible(false);
        panelExpenditures.setVisible(true);
        panelOrders.setVisible(false);
        panelPurchases.setVisible(false);
        panelStaff.setVisible(false);
        panelProducts.setVisible(false);
        panelStock.setVisible(false);
    }
    
    public void showSales(){
        panelPOS.setVisible(false);
        panelExpenditures.setVisible(false);
        panelOrders.setVisible(true);
        panelPurchases.setVisible(false);
        panelStaff.setVisible(false);
        panelProducts.setVisible(false);
        panelStock.setVisible(false);
    }
    
    public void showPurchases(){
        panelPOS.setVisible(false);
        panelExpenditures.setVisible(false);
        panelOrders.setVisible(false);
        panelPurchases.setVisible(true);
        panelStaff.setVisible(false);
        panelProducts.setVisible(false);
        panelStock.setVisible(false);
    }
    
    public void showStaff(){
        panelPOS.setVisible(false);
        panelExpenditures.setVisible(false);
        panelOrders.setVisible(false);
        panelPurchases.setVisible(false);
        panelStaff.setVisible(true);
        panelProducts.setVisible(false);
        panelStock.setVisible(false);
    }
    
    public void showProducts(){
        panelPOS.setVisible(false);
        panelExpenditures.setVisible(false);
        panelOrders.setVisible(false);
        panelPurchases.setVisible(false);
        panelStaff.setVisible(false);
        panelProducts.setVisible(true);
        panelStock.setVisible(false);
    }
    
    public void showStock(){
        panelPOS.setVisible(false);
        panelExpenditures.setVisible(false);
        panelOrders.setVisible(false);
        panelPurchases.setVisible(false);
        panelStaff.setVisible(false);
        panelProducts.setVisible(false);
        panelStock.setVisible(true);
    }
    
    public void setProductDetails(){
        jtfItemCode.setText(pnlProduct.getProductBarCode());
        jtfItemName.setText(pnlProduct.getProductName());
        jtfWholeSalePrice.setText(pnlProduct.getProductWholesalePrice()+"");
        jtfRetailPrice.setText(pnlProduct.getProductRetailPrice()+"");
        jtfItemWeight.setText(pnlProduct.getProductWeight()+"");
        jtfItemDescription.setText(pnlProduct.getProductDescription());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpanelHome = new javax.swing.JPanel();
        panelPOS = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblDateToday = new javax.swing.JLabel();
        lblNotification = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblCashier = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblOrderTotal = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblChange = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrderItems = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnRemoveOrderItem = new javax.swing.JButton();
        btnAddOrderItem = new javax.swing.JButton();
        btnEditOrderItem = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblItemCode = new javax.swing.JLabel();
        lblItemName = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        lblItemPrice = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lblItemWeight = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtItemCode = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtItemQuantity = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtActualAmount = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jcbPaymentMethod = new javax.swing.JComboBox<>();
        tbnProcessOrder = new javax.swing.JToggleButton();
        panelProducts = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jtfSearchProduct = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jtfItemCode = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jtfItemName = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jcbItemCategory = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jtfWholeSalePrice = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jtfRetailPrice = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jtfItemWeight = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jcbWeightUnit = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jtfItemDescription = new javax.swing.JTextField();
        btnSaveProduct = new javax.swing.JButton();
        btnEditProduct = new javax.swing.JButton();
        btnDeleteProduct = new javax.swing.JButton();
        panelOrders = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSales = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        jtfSearchSales = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        pnlSalesJTFOrderID = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        pnlSalesJTFOrderTotal = new javax.swing.JTextField();
        pnlSalesJTFActualPayment = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        panelPurchases = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        panelExpenditures = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        panelStaff = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jtfSearchProduct1 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblStaffInformation = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        txtTelephone = new javax.swing.JTextField();
        btnSaveUserInfo = new javax.swing.JButton();
        btnEditUserInfo = new javax.swing.JButton();
        btnDeleteUserInfo = new javax.swing.JButton();
        btnAccount = new javax.swing.JButton();
        panelStock = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmiPOS = new javax.swing.JMenuItem();
        jmiProducts = new javax.swing.JMenuItem();
        jmiOrders = new javax.swing.JMenuItem();
        jmiStock = new javax.swing.JMenuItem();
        jmiPurchases = new javax.swing.JMenuItem();
        jmiExpenditures = new javax.swing.JMenuItem();
        jmiStaff = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        jpanelHome.setLayout(new java.awt.CardLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("POINT OF SALE:");

        lblDateToday.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        lblNotification.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lblNotification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDateToday, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNotification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDateToday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("CASHIER:");

        lblCashier.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("ORDER TOTAL:");

        lblOrderTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setText("CHANGE:");

        lblChange.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCashier, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblOrderTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblChange, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblOrderTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblChange, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCashier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel3))))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Order Items"));

        tblOrderItems.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tblOrderItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITEM CODE", "ITEM NAME", "PRICE", "Quantity", "SUB TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrderItems.setRowHeight(40);
        tblOrderItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrderItemsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOrderItems);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnRemoveOrderItem.setBackground(new java.awt.Color(0, 51, 51));
        btnRemoveOrderItem.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnRemoveOrderItem.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoveOrderItem.setText("REMOVE");
        btnRemoveOrderItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveOrderItemActionPerformed(evt);
            }
        });

        btnAddOrderItem.setBackground(new java.awt.Color(0, 51, 51));
        btnAddOrderItem.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnAddOrderItem.setForeground(new java.awt.Color(255, 255, 255));
        btnAddOrderItem.setText("ADD");
        btnAddOrderItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOrderItemActionPerformed(evt);
            }
        });

        btnEditOrderItem.setBackground(new java.awt.Color(0, 51, 51));
        btnEditOrderItem.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnEditOrderItem.setForeground(new java.awt.Color(255, 255, 255));
        btnEditOrderItem.setText("EDIT");
        btnEditOrderItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditOrderItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddOrderItem, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnEditOrderItem, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRemoveOrderItem, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddOrderItem, btnEditOrderItem, btnRemoveOrderItem});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddOrderItem, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditOrderItem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveOrderItem, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(169, Short.MAX_VALUE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddOrderItem, btnEditOrderItem, btnRemoveOrderItem});

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("Item"));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Item Code:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("Item Name:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("Descripton:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("Category:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Price:");

        lblItemCode.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        lblItemName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        lblDescription.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        lblCategory.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        lblItemPrice.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel29.setText("Weigth:");

        lblItemWeight.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel29)
                    .addComponent(jLabel5))
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCategory, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                    .addComponent(lblItemPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblItemName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblItemCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblItemWeight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblItemCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(lblItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblItemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lblItemWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(lblDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setText("Item:");

        txtItemCode.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtItemCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtItemCodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtItemCodeKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setText("Quantity:");

        txtItemQuantity.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setText("Payment:");

        txtActualAmount.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtActualAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtActualAmountKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setText("Method:");

        jcbPaymentMethod.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jcbPaymentMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbPaymentMethodActionPerformed(evt);
            }
        });

        tbnProcessOrder.setBackground(new java.awt.Color(0, 51, 51));
        tbnProcessOrder.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        tbnProcessOrder.setForeground(new java.awt.Color(204, 204, 204));
        tbnProcessOrder.setText("PROCESS ORDER");
        tbnProcessOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnProcessOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbnProcessOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtActualAmount)
                            .addComponent(jcbPaymentMethod, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtItemCode)
                            .addComponent(txtItemQuantity))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtItemQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtActualAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jcbPaymentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tbnProcessOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelPOSLayout = new javax.swing.GroupLayout(panelPOS);
        panelPOS.setLayout(panelPOSLayout);
        panelPOSLayout.setHorizontalGroup(
            panelPOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelPOSLayout.setVerticalGroup(
            panelPOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPOSLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jpanelHome.add(panelPOS, "card2");

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("PRODUCTS");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel28.setText("Search:");

        jtfSearchProduct.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jButton6.setBackground(new java.awt.Color(0, 51, 51));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Search");

        tblProducts.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITEM CODE", "ITEM NAME", "WHOLESALE PRICE", "RETAIL PRICE", "ITEM CATEGORY", "ITEM WEIGHT", "WEIGHT UNIT", "DESCRIPTION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProducts.setRowHeight(35);
        tblProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProducts);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)
                        .addGap(0, 163, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jtfSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("ITEM CODE");

        jtfItemCode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setText("ITEM NAME");

        jtfItemName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel22.setText("ITEM CATEGORY");

        jcbItemCategory.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jcbItemCategory.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbItemCategoryItemStateChanged(evt);
            }
        });
        jcbItemCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbItemCategoryActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setText("WHOLESALE PRICE");

        jtfWholeSalePrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setText("RETAIL PRICE");

        jtfRetailPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel25.setText("WEIGHT");

        jtfItemWeight.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel26.setText("WEIGHT UNIT");

        jcbWeightUnit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jcbWeightUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbWeightUnitActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setText("DESCRIPTION");

        jtfItemDescription.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnSaveProduct.setBackground(new java.awt.Color(0, 51, 51));
        btnSaveProduct.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSaveProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveProduct.setText("Save");
        btnSaveProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveProductActionPerformed(evt);
            }
        });

        btnEditProduct.setBackground(new java.awt.Color(0, 51, 51));
        btnEditProduct.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnEditProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnEditProduct.setText("Edit");
        btnEditProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProductActionPerformed(evt);
            }
        });

        btnDeleteProduct.setBackground(new java.awt.Color(0, 51, 51));
        btnDeleteProduct.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnDeleteProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteProduct.setText("Delete");
        btnDeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfItemCode)
                    .addComponent(jtfItemName)
                    .addComponent(jcbItemCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfWholeSalePrice)
                    .addComponent(jtfRetailPrice)
                    .addComponent(jtfItemWeight)
                    .addComponent(jcbWeightUnit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfItemDescription)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(btnSaveProduct)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEditProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteProduct)))
                .addContainerGap())
        );

        jPanel17Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDeleteProduct, btnEditProduct, btnSaveProduct});

        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfItemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(jcbItemCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfWholeSalePrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfRetailPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfItemWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbWeightUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfItemDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveProduct)
                    .addComponent(btnEditProduct)
                    .addComponent(btnDeleteProduct))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelProductsLayout = new javax.swing.GroupLayout(panelProducts);
        panelProducts.setLayout(panelProductsLayout);
        panelProductsLayout.setHorizontalGroup(
            panelProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelProductsLayout.setVerticalGroup(
            panelProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductsLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpanelHome.add(panelProducts, "card3");

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setText("ORDERS");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblSales.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tblSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ORDER ID", "ORDER TOTAL", "ACTUAL PAYMENT", "ORDER DATE", "CASHIER", "PAYMENT METHOD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSales.setRowHeight(35);
        tblSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSalesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSales);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setText("Search:");

        jtfSearchSales.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("Search");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfSearchSales, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(0, 185, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jtfSearchSales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel30.setText("ORDER ID:");

        pnlSalesJTFOrderID.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel31.setText("ORDER TOTAL:");

        pnlSalesJTFOrderTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        pnlSalesJTFActualPayment.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel32.setText("ACTUAL PAYMENT:");

        jButton2.setBackground(new java.awt.Color(0, 51, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("VIEW ORDER DETAILS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSalesJTFOrderID)
                    .addComponent(pnlSalesJTFOrderTotal, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlSalesJTFActualPayment, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSalesJTFOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSalesJTFOrderTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSalesJTFActualPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(45, 45, 45))
        );

        jTabbedPane1.addTab("Daily Orders", jPanel14);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 757, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Monthly Orders", jPanel21);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 757, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Annual Orders", jPanel23);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1090, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 757, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Summary of Orders", jPanel24);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelOrdersLayout = new javax.swing.GroupLayout(panelOrders);
        panelOrders.setLayout(panelOrdersLayout);
        panelOrdersLayout.setHorizontalGroup(
            panelOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelOrdersLayout.setVerticalGroup(
            panelOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrdersLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpanelHome.add(panelOrders, "card8");

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setText("PURCHASES");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(1001, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelPurchasesLayout = new javax.swing.GroupLayout(panelPurchases);
        panelPurchases.setLayout(panelPurchasesLayout);
        panelPurchasesLayout.setHorizontalGroup(
            panelPurchasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelPurchasesLayout.setVerticalGroup(
            panelPurchasesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPurchasesLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 836, Short.MAX_VALUE))
        );

        jpanelHome.add(panelPurchases, "card3");

        panelExpenditures.setPreferredSize(new java.awt.Dimension(1077, 815));

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("EXPENDITURES");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addContainerGap(916, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelExpendituresLayout = new javax.swing.GroupLayout(panelExpenditures);
        panelExpenditures.setLayout(panelExpendituresLayout);
        panelExpendituresLayout.setHorizontalGroup(
            panelExpendituresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelExpendituresLayout.setVerticalGroup(
            panelExpendituresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExpendituresLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 836, Short.MAX_VALUE))
        );

        jpanelHome.add(panelExpenditures, "card3");

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText("STAFF");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addContainerGap())
        );

        jPanel25.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel26.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel34.setText("Search:");

        jtfSearchProduct1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jButton7.setBackground(new java.awt.Color(0, 51, 51));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Search");

        tblStaffInformation.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tblStaffInformation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "USERNAME", "FIRST NAME", "LAST NAME", "TELEPHONE", "USER ROLE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStaffInformation.setRowHeight(35);
        tblStaffInformation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStaffInformationMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblStaffInformation);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfSearchProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7)
                        .addGap(0, 152, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jtfSearchProduct1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4)
                .addContainerGap())
        );

        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel35.setText("USERNAME");

        txtUserName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel36.setText("FIRSTNAME");

        txtFirstName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel37.setText("LASTNAME");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel38.setText("TELEPHONE");

        txtLastName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        txtTelephone.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        btnSaveUserInfo.setBackground(new java.awt.Color(0, 51, 51));
        btnSaveUserInfo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSaveUserInfo.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveUserInfo.setText("SAVE");
        btnSaveUserInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveUserInfoActionPerformed(evt);
            }
        });

        btnEditUserInfo.setBackground(new java.awt.Color(0, 51, 51));
        btnEditUserInfo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnEditUserInfo.setForeground(new java.awt.Color(255, 255, 255));
        btnEditUserInfo.setText("EDIT");
        btnEditUserInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUserInfoActionPerformed(evt);
            }
        });

        btnDeleteUserInfo.setBackground(new java.awt.Color(0, 51, 51));
        btnDeleteUserInfo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnDeleteUserInfo.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteUserInfo.setText("DELETE");
        btnDeleteUserInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserInfoActionPerformed(evt);
            }
        });

        btnAccount.setBackground(new java.awt.Color(0, 51, 51));
        btnAccount.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAccount.setForeground(new java.awt.Color(255, 255, 255));
        btnAccount.setText("ACCOUNT");
        btnAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccountActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUserName)
                    .addComponent(txtFirstName)
                    .addComponent(txtLastName)
                    .addComponent(txtTelephone)
                    .addComponent(btnSaveUserInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditUserInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36)
                            .addComponent(jLabel37)
                            .addComponent(jLabel38))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnDeleteUserInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAccount, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnSaveUserInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditUserInfo)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteUserInfo)
                .addGap(18, 18, 18)
                .addComponent(btnAccount)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jPanel28Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAccount, btnDeleteUserInfo, btnEditUserInfo, btnSaveUserInfo});

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Staff Information", jPanel26);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelStaffLayout = new javax.swing.GroupLayout(panelStaff);
        panelStaff.setLayout(panelStaffLayout);
        panelStaffLayout.setHorizontalGroup(
            panelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelStaffLayout.setVerticalGroup(
            panelStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStaffLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpanelHome.add(panelStaff, "card3");

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("STOCK");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1125, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 823, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelStockLayout = new javax.swing.GroupLayout(panelStock);
        panelStock.setLayout(panelStockLayout);
        panelStockLayout.setHorizontalGroup(
            panelStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelStockLayout.setVerticalGroup(
            panelStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStockLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpanelHome.add(panelStock, "card3");

        getContentPane().add(jpanelHome, "card2");

        jMenu1.setText("POS");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menuicons/cancel.png"))); // NOI18N
        jMenuItem1.setText("Quit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menuicons/lock_delete.png"))); // NOI18N
        jMenuItem2.setText("Logout");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Dashboard");

        jmiPOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menuicons/pos.png"))); // NOI18N
        jmiPOS.setText("POS");
        jmiPOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiPOSActionPerformed(evt);
            }
        });
        jMenu2.add(jmiPOS);

        jmiProducts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menuicons/product.png"))); // NOI18N
        jmiProducts.setText("Products");
        jmiProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProductsActionPerformed(evt);
            }
        });
        jMenu2.add(jmiProducts);

        jmiOrders.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menuicons/sale.png"))); // NOI18N
        jmiOrders.setText("Orders");
        jmiOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiOrdersActionPerformed(evt);
            }
        });
        jMenu2.add(jmiOrders);

        jmiStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menuicons/stock.png"))); // NOI18N
        jmiStock.setText("Stock");
        jmiStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiStockActionPerformed(evt);
            }
        });
        jMenu2.add(jmiStock);

        jmiPurchases.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menuicons/purchase.png"))); // NOI18N
        jmiPurchases.setText("Purchases");
        jmiPurchases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiPurchasesActionPerformed(evt);
            }
        });
        jMenu2.add(jmiPurchases);

        jmiExpenditures.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menuicons/expen.png"))); // NOI18N
        jmiExpenditures.setText("Expenditures");
        jmiExpenditures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiExpendituresActionPerformed(evt);
            }
        });
        jMenu2.add(jmiExpenditures);

        jmiStaff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menuicons/stDependent.png"))); // NOI18N
        jmiStaff.setText("Staff");
        jmiStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiStaffActionPerformed(evt);
            }
        });
        jMenu2.add(jmiStaff);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menuicons/help.png"))); // NOI18N
        jMenuItem7.setText("Help");
        jMenu3.add(jMenuItem7);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menuicons/earth_night.png"))); // NOI18N
        jMenuItem8.setText("About");
        jMenu3.add(jMenuItem8);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiOrdersActionPerformed
        // TODO add your handling code here:
        showSales();
        new ProcessBLL(tblModelSales,"Sales").start();
        setSalesJTable();
    }//GEN-LAST:event_jmiOrdersActionPerformed

    private void btnAddOrderItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOrderItemActionPerformed
        // TODO add your handling code here:
       lblChange.setText("");
       lblOrderTotal.setText("");
       if(currentProduct != null){
           if(txtItemQuantity.getText().length() > 0){
               
           }else{
              fn.errorMessage("Specify quantity before adding an item!"); 
           }
           
           try{
                if(!OrderBLL.itemExistInOrder(currentProduct.getProductBarCode())){
                    int quantity = Integer.parseInt(txtItemQuantity.getText());
                    
                    if(quantity <= 0){
                        quantity = 1;
                    } 
                    OrderItemBLL orderItem = new OrderItemBLL(currentProduct, quantity);
                    OrderBLL.addOrder(orderItem);
                    double rowTotal = currentProduct.getProductRetailPrice() * quantity;
                    OrderBLL.fillTableOrderItems(tblModel,currentProduct,quantity,rowTotal);
                    lblOrderTotal.setText(fn.getCurrencyZAR(OrderBLL.getOrderTotal()));
                    posDefaults();
                }else{
                    OrderBLL.updateItemQuantity(currentProduct);
                    OrderBLL.refreshTableOrderItems(tblModel);
                    lblOrderTotal.setText(fn.getCurrencyZAR(OrderBLL.getOrderTotal()));
                    posDefaults();
                }
           }catch(NumberFormatException ex){
                fn.errorMessage(ex.getLocalizedMessage());
           }
        }else{
            fn.errorMessage("No product to add. Search for product to add first!");
        }
    }//GEN-LAST:event_btnAddOrderItemActionPerformed

    private void txtItemCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemCodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemCodeKeyPressed

    private void txtItemCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemCodeKeyReleased
        // TODO add your handling code here:
        String itemCode = txtItemCode.getText();
        
        ProductBLL pbll = new ProductBLL();
        Product p = pbll.getProduct(itemCode);
        
        if(p != null){
            currentProduct = p;
            setOrderItem();
        }else{
            currentProduct = null;
            clearOrderItem();
        }
    }//GEN-LAST:event_txtItemCodeKeyReleased

    private void tblOrderItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderItemsMouseClicked
        // TODO add your handling code here:
        int row = tblOrderItems.getSelectedRow();
        String itemCode = (tblOrderItems.getModel().getValueAt(row, 0).toString());
        rowSelected = row;
        itemCodeSelected = itemCode;
        OrderItemBLL orderItem = OrderBLL.getOrderItem(itemCode);
        currentProduct = orderItem.getProduct();
        setOrderItemWithQuantity(orderItem.getQuantity()+"");
    }//GEN-LAST:event_tblOrderItemsMouseClicked

    private void btnEditOrderItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditOrderItemActionPerformed
        // TODO add your handling code here:
        if(rowSelected != -1){
            try{
                int quantity = Integer.parseInt(txtItemQuantity.getText());
                OrderBLL.setItemQuantity(itemCodeSelected, quantity);
                double itemTotal = currentProduct.getProductRetailPrice() * quantity;
                tblOrderItems.getModel().setValueAt(quantity, rowSelected, 3);
                tblOrderItems.getModel().setValueAt(fn.getCurrencyZAR(itemTotal), rowSelected, 4);
                lblOrderTotal.setText(fn.getCurrencyZAR(OrderBLL.getOrderTotal()));
                posDefaults();
            }catch(NumberFormatException ex){
                fn.errorMessage(ex.getLocalizedMessage());
            }
        }else{
            fn.errorMessage("No order item selected. Please select an order item before editing!");
        }
    }//GEN-LAST:event_btnEditOrderItemActionPerformed

    private void btnRemoveOrderItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveOrderItemActionPerformed
        // TODO add your handling code here:        
        if(rowSelected != -1){
            OrderBLL.removeProduct(currentProduct.getProductBarCode());
            tblModel.removeRow(rowSelected);
            lblOrderTotal.setText(fn.getCurrencyZAR(OrderBLL.getOrderTotal()));
            posDefaults();
        }else{
            fn.errorMessage("No order item selected. Please select an order item before editing!");
        }
    }//GEN-LAST:event_btnRemoveOrderItemActionPerformed

    private void tbnProcessOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnProcessOrderActionPerformed
        // TODO add your handling code here:
        String paymentAmount = txtActualAmount.getText();
        String paymentMethod = jcbPaymentMethod.getSelectedItem().toString();
        int paymentMethodID = this.paymentMethod.getPaymentMethodID();
        if(paymentAmount.length() > 0){
            if(!OrderBLL.isOrderEmpty()){
                try{
                    double actualPaymentAmount = Double.parseDouble(paymentAmount);
                    if(actualPaymentAmount >= OrderBLL.getOrderTotal()){
                        boolean success = OrderBLL.processPayment(loggedInUser.getUserName(), actualPaymentAmount,paymentMethodID);
                        if(success){
                            tblModel.setRowCount(0); // Empty the table
                            txtActualAmount.setText("");
                        }else{
                            fn.errorMessage("Error! Order could not be successfully processed!"); 
                        }
                    }else{
                       fn.errorMessage("Order total of "+
                                fn.getCurrencyZAR(OrderBLL.getOrderTotal())+
                               " must be greater than actual amount of "+
                               fn.getCurrencyZAR(actualPaymentAmount)); 
                    }
                }catch(NumberFormatException ex){
                    fn.errorMessage(ex.getLocalizedMessage());
                }
            }else{
                fn.errorMessage("Order is empty, please add items before processing!");
            }
        }else{
            fn.errorMessage("Pefore processing the order, you have to specify the actual payment of the customer "
                    + " on payment field!");
        }
        
    }//GEN-LAST:event_tbnProcessOrderActionPerformed

    private void txtActualAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtActualAmountKeyReleased
        // TODO add your handling code here:
        String strActualAmount = txtActualAmount.getText();
        if(strActualAmount.length() > 0){
            try{
                double actualAmount = Double.parseDouble(strActualAmount);
                double change = fn.toTwoDecimal((actualAmount - OrderBLL.getOrderTotal()));
                if(change >= 0){
                    lblChange.setText(fn.getCurrencyZAR(change));
                    lblNotification.setText("");
                    lblNotification.setForeground(Color.BLACK);
                }else{
                    lblNotification.setText("Order total of "+
                                    fn.getCurrencyZAR(OrderBLL.getOrderTotal())+
                                   " must be greater than actual amount of "+
                                   fn.getCurrencyZAR(actualAmount));
                    lblNotification.setForeground(Color.red);
                }
            }catch(NumberFormatException ex){
                fn.errorMessage(ex.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_txtActualAmountKeyReleased

    private void jcbPaymentMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbPaymentMethodActionPerformed
        // TODO add your handling code here:
        paymentMethod = (PaymentMethod) jcbPaymentMethod.getSelectedItem();
        
        if(paymentMethod != null){
            String payMethod = paymentMethod.getPaymentMethod();
            if(!payMethod.equals("Cash")){
                txtActualAmount.setText(OrderBLL.getOrderTotal()+"");
            }else{
                txtActualAmount.setText("");
            }
        }
    }//GEN-LAST:event_jcbPaymentMethodActionPerformed

    private void jmiPOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiPOSActionPerformed
        // TODO add your handling code here:
        showPOS();
    }//GEN-LAST:event_jmiPOSActionPerformed

    private void jmiProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiProductsActionPerformed
        // TODO add your handling code here:
        showProducts();
        ProductCategoryBLL.fillComboBoxProductCategory(jcbItemCategory,ProductCategoryBLL.getAllProductCategories());
        WeightUnitBLL.fillComboBoxWeightUnit(jcbWeightUnit,WeightUnitBLL.getAllWeightUnits());
        new ProcessBLL(tblModelProducts,"Products").start();
    }//GEN-LAST:event_jmiProductsActionPerformed

    private void jmiStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiStockActionPerformed
        // TODO add your handling code here:
        showStock();
    }//GEN-LAST:event_jmiStockActionPerformed

    private void jmiPurchasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiPurchasesActionPerformed
        // TODO add your handling code here:
        showPurchases();
    }//GEN-LAST:event_jmiPurchasesActionPerformed

    private void jmiExpendituresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiExpendituresActionPerformed
        // TODO add your handling code here:
        showExpenditures();
    }//GEN-LAST:event_jmiExpendituresActionPerformed

    private void jmiStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiStaffActionPerformed
        // TODO add your handling code here:
        showStaff();
        new ProcessBLL(tblModelStaffInformation,"StaffInformation").start();
    }//GEN-LAST:event_jmiStaffActionPerformed

    private void jcbItemCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbItemCategoryActionPerformed
        // TODO add your handling code here:
        pnlProductCategory = (ProductCategory) jcbItemCategory.getSelectedItem();
    }//GEN-LAST:event_jcbItemCategoryActionPerformed

    private void jcbItemCategoryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbItemCategoryItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbItemCategoryItemStateChanged

    private void tblProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductsMouseClicked
        // TODO add your handling code here:
        pnlProductTaleRow = tblProducts.getSelectedRow();
        String itemCode = (tblProducts.getModel().getValueAt(pnlProductTaleRow, 0).toString());
        pnlProduct = ProductBLL.getProduct(itemCode);
        pnlProductCategory = ProductCategoryBLL.getProductCategory(pnlProduct.getProductCategoryID());
        pnlWeightUnit = WeightUnitBLL.getWeightUnit(pnlProduct.getProductWeightUnitID());
        ProductCategoryBLL.setSelectedProductCategory(jcbItemCategory,pnlProductCategory);
        WeightUnitBLL.setSelectedWeightUnit(jcbWeightUnit, pnlWeightUnit);
        setProductDetails();
    }//GEN-LAST:event_tblProductsMouseClicked

    private void btnSaveProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveProductActionPerformed
        // TODO add your handling code here:
        String ItemCode = jtfItemCode.getText();
        String ItemName = jtfItemName.getText();
        int ItemCategory = pnlProductCategory.getProductCategoryID();
        double ItemWholeSalePrice = Double.parseDouble(jtfWholeSalePrice.getText());
        double ItemRetailPrice = Double.parseDouble(jtfRetailPrice.getText());
        double ItemWeightUnit = Double.parseDouble(jtfItemWeight.getText());
        int ItemWeghtUnitID = pnlWeightUnit.getWeightUnitID();
        String ItemDescription = jtfItemDescription.getText();
        
        int productAdded = ProductBLL.AddProduct(ItemCode, ItemName, ItemWholeSalePrice, ItemRetailPrice, ItemCategory, ItemWeightUnit, ItemWeghtUnitID, ItemDescription);
        
        if(productAdded > 0){
            ProductBLL.AddNewProductToList(ItemCode, tblModelProducts);
            fn.successMessage("Success: Product added!");
        }else{
            fn.errorMessage("Error: Product not added!");
        }
    }//GEN-LAST:event_btnSaveProductActionPerformed

    private void jcbWeightUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbWeightUnitActionPerformed
        // TODO add your handling code here:
        pnlWeightUnit = (WeightUnit) jcbWeightUnit.getSelectedItem();
    }//GEN-LAST:event_jcbWeightUnitActionPerformed

    private void btnEditProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProductActionPerformed
        // TODO add your handling code here:
        
        String ItemCode = jtfItemCode.getText();
        String ItemName = jtfItemName.getText();
        int ItemCategory = pnlProductCategory.getProductCategoryID();
        String productCategory = pnlProductCategory.getProductCategory();
        double ItemWholeSalePrice = Double.parseDouble(jtfWholeSalePrice.getText());
        double ItemRetailPrice = Double.parseDouble(jtfRetailPrice.getText());
        double ItemWeightUnit = Double.parseDouble(jtfItemWeight.getText());
        int ItemWeghtUnitID = pnlWeightUnit.getWeightUnitID();
        String siUnit = pnlWeightUnit.getSiUnit();
        String ItemDescription = jtfItemDescription.getText();
        
        int productEdited = ProductBLL.EditProduct(ItemCode, ItemName, ItemWholeSalePrice, ItemRetailPrice, ItemCategory, ItemWeightUnit, ItemWeghtUnitID, ItemDescription);
        
        if(productEdited > 0){
            tblProducts.getModel().setValueAt(ItemName, pnlProductTaleRow, 1);
            tblProducts.getModel().setValueAt(fn.getCurrencyZAR(ItemWholeSalePrice), pnlProductTaleRow, 2);
            tblProducts.getModel().setValueAt(fn.getCurrencyZAR(ItemRetailPrice), pnlProductTaleRow, 3);
            tblProducts.getModel().setValueAt(productCategory, pnlProductTaleRow, 4);
            tblProducts.getModel().setValueAt(ItemWeightUnit, pnlProductTaleRow, 5);
            tblProducts.getModel().setValueAt(siUnit, pnlProductTaleRow, 6);
            tblProducts.getModel().setValueAt(ItemDescription, pnlProductTaleRow, 7);
            ProductBLL.updateProductList(ItemCode);
            fn.successMessage("Success: Product edited!");
        }else{
            fn.errorMessage("Error: Product not edited!");
        } 
    }//GEN-LAST:event_btnEditProductActionPerformed

    private void btnDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProductActionPerformed
        // TODO add your handling code here:
        String ItemCode = jtfItemCode.getText();
        int productDeleted = ProductBLL.RemoveProduct(ItemCode);
        
        if(productDeleted > 0){
            tblModelProducts.removeRow(pnlProductTaleRow);
            fn.successMessage("Success: Product deleted!");
        }else{
            fn.errorMessage("Error: Product not deleted!");
        } 
    }//GEN-LAST:event_btnDeleteProductActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void tblSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSalesMouseClicked
        // TODO add your handling code here:
        pnlSaleTaleRow = tblSales.getSelectedRow();
        int orderID = Integer.parseInt((tblSales.getModel().getValueAt(pnlSaleTaleRow, 0).toString()));
        pnlOrder = SalesBLL.getOrder(orderID);
        
        pnlSalesJTFOrderID.setText(pnlOrder.getOrderID()+"");
        pnlSalesJTFOrderTotal.setText(pnlOrder.getOrderTotal()+"");
        pnlSalesJTFActualPayment.setText(pnlOrder.getActualPrayment()+"");
    }//GEN-LAST:event_tblSalesMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(pnlOrder != null){
            new OrderItems(loggedInUser, pnlOrder.getOrderID()).setVisible(true);
            pnlSaleTaleRow = -1;
        }else{
            fn.warningMessage("Before viewing the order details, select the order first!");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblStaffInformationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStaffInformationMouseClicked
        // TODO add your handling code here:
        pnlStaffInformationTaleRow = tblStaffInformation.getSelectedRow();
        String userName = tblStaffInformation.getModel().getValueAt(pnlStaffInformationTaleRow, 0).toString();
        pnlStaffInformation = UserBLL.getUser(userName);
        
        txtUserName.setText(pnlStaffInformation.getUserName());
        txtFirstName.setText(pnlStaffInformation.getFirstName());
        txtLastName.setText(pnlStaffInformation.getLastName());
        txtTelephone.setText(pnlStaffInformation.getTelephone());
        
    }//GEN-LAST:event_tblStaffInformationMouseClicked

    private void btnSaveUserInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveUserInfoActionPerformed
        // TODO add your handling code here:
        
        String userName = txtUserName.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String telephone = txtTelephone.getText();
        
        if(userName.length() > 0 && firstName.length() > 0 && lastName.length() > 0 && telephone.length() > 0){
           
            int userAdded = UserBLL.AddUserInformation(userName, firstName, lastName, telephone);
            if(userAdded > 0){
                UserBLL.refreshUsers();
                UserBLL.fillTableStaffInformation(tblModelStaffInformation);
                clearStaffInformation();
                fn.successMessage("User added!");
            }else{
                fn.errorMessage("User not added");
            }            
        }else{
            fn.warningMessage("Fill all the fields before adding a new user!");
        }
    }//GEN-LAST:event_btnSaveUserInfoActionPerformed

    private void btnEditUserInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUserInfoActionPerformed
        // TODO add your handling code here:
        String userName = txtUserName.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String telephone = txtTelephone.getText();
        
        if(userName.length() > 0 && firstName.length() > 0 && lastName.length() > 0 && telephone.length() > 0){
           
            int userEdited = UserBLL.EditUserInformation(userName, firstName, lastName, telephone);
            if(userEdited > 0){
                UserBLL.refreshUsers();
                UserBLL.fillTableStaffInformation(tblModelStaffInformation);
                clearStaffInformation();
                fn.successMessage("User edited!");
            }else{
                fn.errorMessage("User not edited");
            }            
        }else{
            fn.warningMessage("Fill all the fields before editing a user!");
        }
        
    }//GEN-LAST:event_btnEditUserInfoActionPerformed

    private void btnDeleteUserInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserInfoActionPerformed
        // TODO add your handling code here:
        
        String userName = txtUserName.getText();
        
        if(userName.length() > 0){
           
            int userRemoved = UserBLL.RemoveUserInforamtion(userName);
            if(userRemoved > 0){
                UserBLL.refreshUsers();
                UserBLL.fillTableStaffInformation(tblModelStaffInformation);
                clearStaffInformation();
                fn.successMessage("User removed!");
            }else{
                fn.errorMessage("User not removed");
            }            
        }else{
            fn.warningMessage("Select the user you want to delete!");
        }
    }//GEN-LAST:event_btnDeleteUserInfoActionPerformed

    private void btnAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccountActionPerformed
        // TODO add your handling code here:
        if(pnlStaffInformation.getUserName().length() > 0){
            new UserAccount(pnlStaffInformation,loggedInUser).setVisible(true);
        }else{
            fn.warningMessage("Select the user first!");
        }
    }//GEN-LAST:event_btnAccountActionPerformed

    public void clearStaffInformation(){
        txtUserName.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtTelephone.setText("");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        Functions.setFormTheme();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home(UserBLL.getUser("blessy")).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccount;
    private javax.swing.JButton btnAddOrderItem;
    private javax.swing.JButton btnDeleteProduct;
    private javax.swing.JButton btnDeleteUserInfo;
    private javax.swing.JButton btnEditOrderItem;
    private javax.swing.JButton btnEditProduct;
    private javax.swing.JButton btnEditUserInfo;
    private javax.swing.JButton btnRemoveOrderItem;
    private javax.swing.JButton btnSaveProduct;
    private javax.swing.JButton btnSaveUserInfo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JComboBox<String> jcbItemCategory;
    private javax.swing.JComboBox<String> jcbPaymentMethod;
    private javax.swing.JComboBox<String> jcbWeightUnit;
    private javax.swing.JMenuItem jmiExpenditures;
    private javax.swing.JMenuItem jmiOrders;
    private javax.swing.JMenuItem jmiPOS;
    private javax.swing.JMenuItem jmiProducts;
    private javax.swing.JMenuItem jmiPurchases;
    private javax.swing.JMenuItem jmiStaff;
    private javax.swing.JMenuItem jmiStock;
    private javax.swing.JPanel jpanelHome;
    private javax.swing.JTextField jtfItemCode;
    private javax.swing.JTextField jtfItemDescription;
    private javax.swing.JTextField jtfItemName;
    private javax.swing.JTextField jtfItemWeight;
    private javax.swing.JTextField jtfRetailPrice;
    private javax.swing.JTextField jtfSearchProduct;
    private javax.swing.JTextField jtfSearchProduct1;
    private javax.swing.JTextField jtfSearchSales;
    private javax.swing.JTextField jtfWholeSalePrice;
    private javax.swing.JLabel lblCashier;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblChange;
    private javax.swing.JLabel lblDateToday;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblItemCode;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblItemPrice;
    private javax.swing.JLabel lblItemWeight;
    private javax.swing.JLabel lblNotification;
    private javax.swing.JLabel lblOrderTotal;
    private javax.swing.JPanel panelExpenditures;
    private javax.swing.JPanel panelOrders;
    private javax.swing.JPanel panelPOS;
    private javax.swing.JPanel panelProducts;
    private javax.swing.JPanel panelPurchases;
    private javax.swing.JPanel panelStaff;
    private javax.swing.JPanel panelStock;
    private javax.swing.JTextField pnlSalesJTFActualPayment;
    private javax.swing.JTextField pnlSalesJTFOrderID;
    private javax.swing.JTextField pnlSalesJTFOrderTotal;
    private javax.swing.JTable tblOrderItems;
    private javax.swing.JTable tblProducts;
    private javax.swing.JTable tblSales;
    private javax.swing.JTable tblStaffInformation;
    private javax.swing.JToggleButton tbnProcessOrder;
    private javax.swing.JTextField txtActualAmount;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtItemCode;
    private javax.swing.JTextField txtItemQuantity;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtTelephone;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
