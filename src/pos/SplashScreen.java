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


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MouseInputAdapter;
 
public class SplashScreen extends JFrame implements Runnable{
    
    public SplashScreen(){
     setTitle("Starting Java Solitaire Project");
     setDefaultCloseOperation(2);
     
     setUndecorated(true);
     setFocusable(true);
     setLayout(null);
     setBackground(null);
     setSize(500,300);
     
     SplashPanel splashPanel = new SplashPanel();
     splashPanel.addMouseListener(new MouseInputAdapter()
     {
       public void mouseClicked(MouseEvent e)
       {
         SplashScreen.this.dispose();
       }
    });
     addFocusListener(new FocusListener()
     {
       public void focusLost(FocusEvent fe)
       {
         SplashScreen.this.requestFocus();
       }
       
       public void focusGained(FocusEvent fe) {}
     });
     add(splashPanel);
     setLocationRelativeTo(null);
    }
   
   public void run(){
     
       int i = 0;
     while (i == 0){
       i++;
       try{
         Thread.sleep(6000L);
         System.out.println(i);
       }catch (Exception e){
         e.printStackTrace();
       }
     }
     dispose();
     
    // Open login form
    new Login().setVisible(true);
   }
  
   private class SplashPanel extends JPanel{
     
        private BufferedImage splashImage = null;
     
        public SplashPanel(){

           setSize(500,300);
           try{
             this.splashImage = ImageIO.read(getClass().getResourceAsStream("../images/ico.png"));
           }catch (Exception e){
             System.err.println("Could not load splash image");
             e.printStackTrace();
           }
        }

        protected void paintComponent(Graphics graphics){
           Graphics2D g = (Graphics2D)graphics;
           if (this.splashImage != null) {
             g.drawImage(this.splashImage, null, 0, 0);
           }
           g.dispose();
        }
    }
  
    public static void main(String[] args){
        try{
            SplashScreen splash = new SplashScreen();
            Thread splashThread = new Thread(splash);
            splashThread.start();
            splash.setVisible(true);

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()){
                UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}

