/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.customcomponents.customInventory.labels;

import Backend.Inventory.InventoryController;
import Backend.Inventory.Item;
import Backend.Seed.SeedBankHandler;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author jaket
 */
public class InventoryItemSelectionLabel extends JLabel
{
    
    protected Item item;
    protected Cursor cursor;
    
    public InventoryItemSelectionLabel(Item item)
    {
        System.out.println("Creating inventoryItemSelectionLabel");
        this.item = item;
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
        
        this.setVisible(true);
        
    
    }
    
    public void entered()
    {
        
        cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        setCursor(cursor);
    }
    
    public void exited()
    {
        
        cursor = Cursor.getDefaultCursor();
        setCursor(cursor);
    }
    
    public void pressed()
    {
        //System.out.println(item.getItemName());
        InventoryController.setSelectedItem(item);
        InventoryController.loadSelectionPanel();
        
    }
    
    
    
}
