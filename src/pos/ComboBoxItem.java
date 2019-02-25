/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

/**
 *
 * @author Mothusi Molorane
 */


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ComboBoxItem extends JFrame implements ActionListener
{
    public ComboBoxItem()
    {
        Vector model = new Vector();
        model.addElement( new Item(1, "car" ) );
        model.addElement( new Item(2, "plane" ) );
        model.addElement( new Item(3, "train" ) );
        model.addElement( new Item(49, "boat" ) );
        model.addElement( new Item(5, "boat aadf asfsdf a asd asd" ) );

        JComboBox comboBox;

        //  Easiest approach is to just override toString() method
        //  of the Item class

        comboBox = new JComboBox( model );
        comboBox.addActionListener( this );
        getContentPane().add(comboBox, BorderLayout.NORTH );

        //  Most flexible approach is to create a custom render
        //  to diplay the Item data
    }

    public void actionPerformed(ActionEvent e)
    {
        JComboBox comboBox = (JComboBox)e.getSource();
        Item item = (Item)comboBox.getSelectedItem();
        System.out.println( item.getId() + " : " + item.getDescription() );
    }

    class Item{
        private int id;
        private String description;

        public Item(int id, String description)
        {
            this.id = id;
            this.description = description;
        }

        public int getId()
        {
            return id;
        }

        public String getDescription()
        {
            return description;
        }

        public String toString()
        {
            return description;
        }
    }

    public static void main(String[] args){
        JFrame frame = new ComboBoxItem();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible( true );
    }
}

