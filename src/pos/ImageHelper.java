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


import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author moloranemothusimichael
 */
public class ImageHelper
{
  public static ImageIcon loadImage(String name)
  {
    ImageIcon image = null;
    try {
      URL url = ImageHelper.class.getResource(name);
      if (url != null) {
        Image img = Toolkit.getDefaultToolkit().createImage(url);
        if (img != null)
          image = new ImageIcon(img);
      }
    }
    catch (Throwable ex) {
      System.out.println("ERROR: loading image " + name + " failed. Exception: " + ex.getMessage());
    }
    return image;
  }
}