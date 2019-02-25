/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blessy.BLL;

/**
 *
 * @author Mothusi Molorane
 */


import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mothusi Molorane
 */


public class ProcessBLL extends Thread{

    private final DefaultTableModel dtml;
    private final String table;

    public ProcessBLL (DefaultTableModel dtml,String table) {
        this.dtml = dtml;
        this.table = table;
    }

    @Override
    public void run () {
        if(this.table.equals("Sales")){
            SalesBLL.fillTableSales(dtml);
        }else if(this.table.equals("Products")){
            ProductBLL.fillTableProducts(dtml);
        }else if(this.table.equals("StaffInformation")){
            UserBLL.fillTableStaffInformation(dtml);
        }
    }
 }

