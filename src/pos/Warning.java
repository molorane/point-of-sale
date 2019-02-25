/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

import java.awt.Color;
import static java.lang.Thread.sleep;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mothusi Molorane
 */


class Warning extends java.lang.Thread{

    int millis;
    int loops;
    String message;
    int in;
    JLabel statusLabel;

    public Warning ( int millis, String message, int loops, JLabel statusLabel) {
        this.millis = millis;
        this.message = message;
        this.loops = loops;
        this.statusLabel = statusLabel;
    }

    public void run () {

        for ( int i = 1; i <= loops; ++i ) {

            SwingUtilities.invokeLater(
                new Runnable () {
                    public void run () {
                        statusLabel.setText(message);
                        statusLabel.setForeground(Color.RED);
                    }
                }
            );

            try {
                sleep( this.millis );
            } catch ( InterruptedException err ) {}

            SwingUtilities.invokeLater(
                new Runnable () {
                    public void run () {
                        statusLabel.setText(null);
                    }
                }
            );
            try {
                sleep( this.millis );
            } catch ( InterruptedException err ) {}

        }
    }
 }
