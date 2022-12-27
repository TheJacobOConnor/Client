/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Inventory;

import View.customcomponents.customInventory.panels.InventoryItemDisplayPanel;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jaket
 */
public class InventoryController 
{
    
    protected static ArrayList<InventoryItemDisplayPanel> displayPanelList = new ArrayList<>();
    private static ArrayList<JPanel> sidePanelList = new ArrayList<>();
    protected static ArrayList<Item> itemList = new ArrayList<>();
    
    protected static JPanel activePanel;
    protected static JPanel inventoryListPanel;
    protected static JPanel inventoryTopPanel;
    protected static JPanel inventorySelectionPanel;
    protected static JPanel inventoryBottomPanel;
    protected static JPanel inventoryListContainer;
    protected static JPanel inventoryHomePanel;
    protected static JPanel inventoryAddItemPanel;
    
    protected static JTextField itemNameField;
    protected static JTextField itemDateField;
    protected static JTextField itemBatchField;
    protected static JTextField itemWeightField;
    
    protected static int selectedItemStatus = 0;
    protected static int selectedItemType = 0;
    
    protected static Item selectedItem;

    
    
    
    
    public static void loadSelectionPanel()
    {
        activePanel.setVisible(false);
        inventorySelectionPanel.setVisible(true);
        activePanel = inventorySelectionPanel;
        System.out.println(selectedItem.getItemName());
        Component[] cList = inventorySelectionPanel.getComponents();
        
        ArrayList<JLabel> labelList = new ArrayList<>();
        
        for(Component c : cList)
        {
            labelList.add((JLabel) c);
        }
        
        JLabel itemNameLabel = labelList.get(0);
        JLabel itemIdLabel = labelList.get(1);
        JLabel itemBatchIdLabel = labelList.get(2);
        
        itemNameLabel.setText(selectedItem.getItemName());
        itemIdLabel.setText(selectedItem.getItemId());
        itemBatchIdLabel.setText(selectedItem.getBatchId());
        
        
    }
    
    public static void loadHomePanel()
    {
        if(activePanel != null)
            activePanel.setVisible(false);
        inventoryHomePanel.setVisible(true);
        activePanel = inventoryHomePanel;
    }
    
    public static void loadAddItemPanel()
    {
        activePanel.setVisible(false);
        inventoryAddItemPanel.setVisible(true);
        activePanel = inventoryAddItemPanel;
    }
    
    public static void setupInventory(String dataStr)
    {
        InventoryController.itemList = buildItemList(dataStr);
        sidePanelSetup();
        loadHomePanel();
        displayPanelList = new ArrayList<>();
        
        for(int i = 0; i < itemList.size(); i++)
        {
            InventoryItemDisplayPanel itemDisplayPanel = new InventoryItemDisplayPanel(itemList.get(i));
            itemDisplayPanel.setSize(745, 50);
            itemDisplayPanel.buildLabels();
            inventoryListContainer.add(itemDisplayPanel);
            displayPanelList.add(itemDisplayPanel);
            itemDisplayPanel.setVisible(true);
            
            System.out.println("Made panel");
            itemDisplayPanel.setLocation(20, i*50 + 105);
            
        }
            

        
        
    }
    
    private static void sidePanelSetup()
    {
        storePanelLists();
        for(JPanel panel : sidePanelList)
        {
            panel.setVisible(false);
        }
    }
    
    private static void storePanelLists()
    {
        sidePanelList.add(inventorySelectionPanel);
        sidePanelList.add(inventoryHomePanel);
        sidePanelList.add(inventoryAddItemPanel);
        
    }
    
    public static String buildNewItemString()
    {
        String newStr = "AddItem:";
        newStr += itemNameField.getText() + ",";
        newStr += itemDateField.getText() + ",";
        newStr += getSelectedItemStatus() + ",";
        newStr += getSelectedItemType() + ",";
        newStr += itemWeightField.getText() + ",";
        newStr += itemBatchField.getText();
        
        return newStr;
    }
    
    private static String getSelectedItemStatus()
    {
        switch(selectedItemStatus)
        {
            case 1:
                return "Indoor";
                
            case 2:
                return "Outdoor";
                
            default:
                return "N/A";
                
        }
    }
    
    private static String getSelectedItemType()
    {
        switch(selectedItemType)
        {
            case 1:
                return "Cured";
                
            case 2:
                return "Curing";
                
            case 3:
                return "Hanging";
                
            default:
                return "N/A";
                
        }
    }
    
    public void displaySelectedPanels(int start, int finish)
    {
        for(int i = 0; i < finish - start; i++)
        {
            displayPanelList.set(start, new InventoryItemDisplayPanel(itemList.get(start)));
            displayPanelList.get(start).setVisible(true);
            start++;
            
        }
    }
    
    private static ArrayList<Item> buildItemList(String dataStr)
    {
        ArrayList<Item> itemList = new ArrayList<>();
        
        String[] splitStr = dataStr.split("-");
        
        for(String str : splitStr)
        {
            Item newItem = new Item();
            String[] itemStr = str.split(",");
            newItem.setItemName(itemStr[0]);
            newItem.setItemAcquiredDate(itemStr[1]);
            newItem.setItemType(itemStr[2]);
            newItem.setItemStatus(itemStr[3]);
            newItem.setItemWeight(itemStr[4]);
            newItem.setBatchId(itemStr[5]);
            newItem.setItemId(itemStr[6]);

            itemList.add(newItem);
        }
        
        
        
        return itemList;
    }

    public static JPanel getInventoryListContainer() {
        return inventoryListContainer;
    }

    public static void setInventoryListContainer(JPanel inventoryListContainer) {
        InventoryController.inventoryListContainer = inventoryListContainer;
    }

    public static ArrayList<Item> getItemList() {
        return itemList;
    }

    public static void setItemList(ArrayList<Item> itemList) {
        InventoryController.itemList = itemList;
    }

    public static JTextField getItemNameField() {
        return itemNameField;
    }

    public static void setItemNameField(JTextField itemNameField) {
        InventoryController.itemNameField = itemNameField;
    }

    public static JTextField getItemDateField() {
        return itemDateField;
    }

    public static void setItemDateField(JTextField itemDateField) {
        InventoryController.itemDateField = itemDateField;
    }

    public static JTextField getItemBatchField() {
        return itemBatchField;
    }

    public static void setItemBatchField(JTextField itemBatchField) {
        InventoryController.itemBatchField = itemBatchField;
    }

    public static JTextField getItemWeightField() {
        return itemWeightField;
    }

    public static void setItemWeightField(JTextField itemWeightField) {
        InventoryController.itemWeightField = itemWeightField;
    }

   
    public static void setSelectedItemStatus(int selectedItemStatus) {
        InventoryController.selectedItemStatus = selectedItemStatus;
    }

   
    public static void setSelectedItemType(int selectedItemType) {
        InventoryController.selectedItemType = selectedItemType;
    }

    public static Item getSelectedItem() {
        return selectedItem;
    }

    public static void setSelectedItem(Item selectedItem) {
        InventoryController.selectedItem = selectedItem;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void setInventoryHomePanel(JPanel inventoryHomePanel) {
        InventoryController.inventoryHomePanel = inventoryHomePanel;
    }

    public static void setInventoryAddItemPanel(JPanel inventoryAddItemPanel) {
        InventoryController.inventoryAddItemPanel = inventoryAddItemPanel;
    }

    
    
    public static JPanel getInventoryListPanel()
    {
        return inventoryListPanel;
    }

    public static JPanel getInventoryTopPanel() 
    {
        return inventoryTopPanel;
    }

    public static JPanel getInventorySelectionPanel()
    {
        return inventorySelectionPanel;
    }

    public static JPanel getInventoryBottomPanel() 
    {
        return inventoryBottomPanel;
    }
   
    public static void setInventoryListPanel(JPanel inventoryListPanel)
    {
        InventoryController.inventoryListPanel = inventoryListPanel;
    }

    public static void setInventoryTopPanel(JPanel inventoryTopPanel)
    {
        InventoryController.inventoryTopPanel = inventoryTopPanel;
    }

    public static void setInventorySelectionPanel(JPanel inventorySelectionPanel)
    {
        InventoryController.inventorySelectionPanel = inventorySelectionPanel;
    }

    public static void setInventoryBottomPanel(JPanel inventoryBottomPanel) 
    {
        InventoryController.inventoryBottomPanel = inventoryBottomPanel;
    }
    
    
    
    
    
    
    
    
    
}
