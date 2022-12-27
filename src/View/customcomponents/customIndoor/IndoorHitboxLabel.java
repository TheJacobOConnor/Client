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
    
    public IndoorHitboxLabel(JLabel hoverLabel)
    {
       this.hoverLabel = hoverLabel;
       
       addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent me)
            {
                pressed();
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
    }
    
    public void exited()
    {
        
        this.hoverLabel.setVisible(false);
    }
    
    public void pressed()
    {
        
        
    }
    
    
    
}
