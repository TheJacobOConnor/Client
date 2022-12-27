/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author jaket
 */
public class OldInventoryDepricated 
{
    ArrayList<Product> inventory = new ArrayList();
    
    
        
//    String name = inventory.get(index).getName();
//        
//    Icon icon = inventory.get(index).getIconView();
//        
//    nameField.setText(name);
//    itemView.setIcon(icon);
    
    public static void inventoryController(int index, JTextField nameField, JLabel itemView, String keyword)
    {
        ArrayList<Product> inventory = new ArrayList();
        inventory = ProductHandler.loadProduct();
        inventory = sortInventory(inventory, keyword);
        
        String name = inventory.get(index).getLabel();
        
        String strainName = inventory.get(index).getStrain();
        
        Icon icon = new ImageIcon();
        icon = ImageHandler.getImageThumbnail("InventorySelectView/" + strainName, false);

        
        nameField.setText(name);
        itemView.setIcon(icon);
    }
    
    public static void displayInventory(ArrayList<JLabel> labels, int size, String keyword)
    {
        ArrayList<Product> inventory = new ArrayList();
        inventory = ProductHandler.loadProduct();
        
        if(!keyword.equals(null))
        {
            inventory = OldInventoryDepricated.sortInventory(inventory, keyword);
        }
        
        
        for(JLabel label : labels)
        {
            label.setIcon(null);
        }
        
        for(int i = 0; i < size; i++)
        {
           if(i < inventory.size())
           {
               Icon icon = new ImageIcon();
               icon = ImageHandler.getImageThumbnail(inventory.get(i).strain, true);
               labels.get(i).setIcon(icon);
               System.out.println(icon);
           }
           else if(i < size)
           {
               String curPath = "src/data/images/inventory/thumbnails/emptySlotInventoryIcon.png";
               Icon icon = new ImageIcon(curPath);
               labels.get(i).setIcon(icon);
           }
           
        }
    }
    
    public static ArrayList<Product> sortInventory(ArrayList<Product> inventory, String keyword)
    {
        ArrayList<Product> newInventory = new ArrayList();
        
        for(Product product : inventory)
        {
            if(product.getStrain().equals(keyword))
            {
                newInventory.add(product);
            }
        }
        
        return newInventory;
    }
    
    
    
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
    
    public static void loadStrainSelection(JComboBox strainBox) throws FileNotFoundException
    {
        ArrayList<String> strainList = new ArrayList();
        Scanner scan = new Scanner(new File("src/Data/Core/availableStrains.txt"));
        
        while(scan.hasNext())
        {
            String curLine = scan.nextLine().toString();
            strainList.add(curLine);
        }
        strainBox.removeAll();
        strainBox.addItem("Select a strain");
        for(String item : strainList)
        {
            strainBox.addItem(item);
        }
        
        
    }
    
    
    
    public static void removeProduct(ArrayList<Product> inventory, int selectedItemIndex, ArrayList<JLabel> labels, String keyword)
    {
        inventory = ProductHandler.loadProduct();
        inventory.remove(selectedItemIndex);
        ProductHandler.updateProductFile(inventory);
        OldInventoryDepricated.displayInventory(labels, 25, keyword);
    }
    
    public static void addNewProduct(JComboBox strainNameBox, JComboBox jarSizeBox, double supply, JTextField strainDescription, JTextField labelField, JTextField cureInfoField)
    {
        String strainName = "";
        String containerType = "";
        String description = "";
        String label = "";
        
        strainName = strainNameBox.getSelectedItem().toString();

        if(jarSizeBox.getSelectedIndex() == 1)
        {
            containerType = "Bucket";
        }
        
        else if(jarSizeBox.getSelectedIndex() == 2)
        {
            containerType = "Jar";
        }

        description = strainDescription.getText();
        label = labelField.getText();
        
        Product p = new Product();
        p.setStrain(strainName);
        p.setDescription(description);
        p.setLabel(label);
        p.setSupply(supply);
        p.setContainerType(containerType);
        p.setCureInfo(cureInfoField.getText());
        
        ProductHandler.createProduct(p);
        
    }
    
    
    
    
    
}
