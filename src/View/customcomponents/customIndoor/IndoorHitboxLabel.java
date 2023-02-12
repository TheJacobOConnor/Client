/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.customcomponents.customIndoor;

import Backend.Inventory.InventoryController;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 *
 * @author jaket
 */
public class IndoorHitboxLabel extends JLabel
{
    protected JLabel hoverLabel;
    protected String plantId;
    
    public IndoorHitboxLabel(JLabel hoverLabel, String plantId)
    {
       this.hoverLabel = hoverLabel;
       this.plantId = plantId;
       
       addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent me)
            {
                released();
            }
            
            @Override
            public void mouseEntered(MouseEvent e)
            {
                entered();
            }
            
            public void mouseExited(MouseEvent e)
            {
                exited();
            }
        });
        
    }
    
    public void entered()
    {
        this.hoverLabel.setVisible(true);
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        this.setCursor(cursor);
        
    }
    
    public void exited()
    {
        
        this.hoverLabel.setVisible(false);
    }
    
    public void released()
    {
      System.out.println("Test loading plant " + plantId);
        
    }
    
    
    
}
