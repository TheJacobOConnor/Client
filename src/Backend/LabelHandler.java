/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Jacob
 */
public class LabelHandler
{
    
    public static void panelHighlight(ArrayList<JPanel> panels, int index)
    {
        
        for(JPanel panel : panels)
        {
            
            if(panels.indexOf(panel) == index)
            {
                panel.setBackground(new Color(150,150,150));
            }
            
            else
            {
                panel.setBackground(new Color(210,210,210));
            }
        }
        //panels.get(index).setBackground(Color.red);
        
    }
    

    public static void resetLabels(ArrayList<JLabel> labels)
    {
        String imgPath = "src/data/images/emptySlotCraftingIcon.png";
        Icon icon = new ImageIcon(imgPath);

        
        for(JLabel label : labels)
        {
            label.setIcon(icon);
        }
    }
    
    
    
    public static void resetViewFields(JTextField nameField, JTextArea descriptionField, JLabel viewLabel)
    {
        nameField.setText("");
        descriptionField.setText("");
        String curPath = "src/data/images/emptySlotSelectIcon.png";
        Icon icon = new ImageIcon(curPath);
        viewLabel.setIcon(icon);
    }
}
