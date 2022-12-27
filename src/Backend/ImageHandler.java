/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author jaket
 */
public class ImageHandler 
{
    String imagePath;
    
    
    public static Icon getImageThumbnail(String imageName, boolean inventory)
    {
        Icon icon = new ImageIcon();
        
        if(inventory)
        {
            String formatImageName = imageName.replaceAll("\\s+","");
            String iconPath = "src/Data/Images/Inventory/Thumbnails/InventorySlots/" + formatImageName + "Thumbnail.png";
            icon = new ImageIcon(iconPath);
        }
        
        else
        {
            String formatImageName = imageName.replaceAll("\\s+","");
            String iconPath = "src/Data/Images/Inventory/Thumbnails/" + formatImageName + "Thumbnail.png";
            icon = new ImageIcon(iconPath);
        }
        
        
        return icon;
    }
    
    public static String getImageName(String strain)
    {
        String returnStr = "";
        
        switch(strain)
        {
            case "Blue Dream":
                returnStr = "blueDream";
                break;
                
            case "Girl Scout Cookies":
                returnStr = "girlScoutCookies";
                break;
                
            case "Dale Diesel":
                returnStr = "daleDiesel";
                break;
        }
        
        return returnStr;
    }
}
