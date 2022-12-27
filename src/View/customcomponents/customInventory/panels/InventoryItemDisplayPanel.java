/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.customcomponents.customInventory.panels;

import Backend.Inventory.Item;
import View.customcomponents.customInventory.labels.InventoryItemSelectionLabel;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jaket
 */
public class InventoryItemDisplayPanel extends JPanel
{
    protected Item item;
    
    
    transient private Color textColor;
    transient private Color backgroundColor;
    /*protected JLabel selectionLabel = new JLabel();
    protected JLabel itemIdLabel = new JLabel();
    protected JLabel itemNameLabel = new JLabel();
    protected JLabel dateLabel = new JLabel();
    protected JLabel typeLabel = new JLabel();
    protected JLabel batchLabel = new JLabel();
    protected JLabel statusLabel = new JLabel();
    protected JLabel weightLabel = new JLabel();
    protected JLabel loadItemLabel = new JLabel();*/
    
    public InventoryItemDisplayPanel(Item item)
    {
        this.item = item;
        this.setVisible(true);
        this.setOpaque(false);
        this.setLayout(null);
        
    }
    
    
    public void buildLabels()
    {
        JLabel selectionLabel = new JLabel();
        JLabel itemIdLabel = new JLabel();
        JLabel itemNameLabel = new JLabel();
        JLabel dateLabel = new JLabel();
        JLabel typeLabel = new JLabel();
        JLabel batchLabel = new JLabel();
        JLabel statusLabel = new JLabel();
        JLabel weightLabel = new JLabel();
        JLabel loadItemLabel = new JLabel();
        JLabel itemSelectionLabel = new JLabel();
        
        Font font = new Font("Arial", Font.BOLD, 12);
        textColor = Color.black;
        backgroundColor = Color.LIGHT_GRAY;

        
        
        Icon selectionIcon = new ImageIcon("src/Data/Images/Inventory/Graphics/unselectedIcon.png");
        selectionLabel.setIcon(selectionIcon);
        selectionLabel.setBounds(5, 12, 18, 18);
        
        
        itemIdLabel.setFont(font);
        itemIdLabel.setText(this.item.getItemId());
        itemIdLabel.setForeground(textColor);
        itemIdLabel.setBackground(backgroundColor);
        itemIdLabel.setBounds(50, 2, 60, 40);
        
        itemNameLabel.setFont(font);
        itemNameLabel.setText(this.item.getItemName());
        itemNameLabel.setBackground(backgroundColor);
        itemNameLabel.setForeground(textColor);
        itemNameLabel.setBounds(150, 2, 160, 40);
        
        dateLabel.setFont(font);
        dateLabel.setText(this.item.getItemAcquiredDate());
        dateLabel.setBackground(backgroundColor);
        dateLabel.setForeground(textColor);
        dateLabel.setBounds(315, 2, 70, 40);
        
        typeLabel.setFont(font);
        typeLabel.setText(this.item.getItemType());
        typeLabel.setBackground(backgroundColor);
        typeLabel.setForeground(textColor);
        typeLabel.setBounds(405, 2, 60, 40);
        
        batchLabel.setFont(font);
        batchLabel.setText("#" + this.item.getBatchId());
        batchLabel.setBackground(backgroundColor);
        batchLabel.setForeground(textColor);
        batchLabel.setBounds(485, 2, 50, 40);
        
        
        Icon icon = new ImageIcon("src/Data/Images/Inventory/Graphics/" + item.getItemStatus() + "Icon.png");
        statusLabel.setFont(font);
        statusLabel.setIcon(icon);
        statusLabel.setBackground(backgroundColor);
        statusLabel.setForeground(textColor);
        statusLabel.setBounds(550, 2, 75, 40);
        
        weightLabel.setFont(font);
        weightLabel.setBackground(backgroundColor);
        weightLabel.setText(this.item.getItemWeight());
        weightLabel.setForeground(textColor);
        weightLabel.setBounds(645, 2, 40, 40);
        
        
        itemSelectionLabel = new InventoryItemSelectionLabel(item);
        Icon loadIcon = new ImageIcon("src/Data/Images/Inventory/Graphics/loadItemIcon2.png");
        itemSelectionLabel.setIcon(loadIcon);
        itemSelectionLabel.setBounds(705, 7, 30, 30);
        
        
        
        
        
        
      
        
        //itemIdLabel.setOpaque(true);
        
        //itemNameLabel.setOpaque(true);
        
        this.add(itemIdLabel);
        this.add(itemNameLabel);
        this.add(dateLabel);
        this.add(typeLabel);
        this.add(batchLabel);
        this.add(statusLabel);
        this.add(weightLabel);
        this.add(loadItemLabel);
        this.add(itemSelectionLabel);
        this.add(selectionLabel);
        
    }
    
    
  
    
}
