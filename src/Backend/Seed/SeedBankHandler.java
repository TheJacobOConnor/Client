/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Seed;

import View.customcomponents.customSeed.SeedPackSelectionLabel;
import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author jaket
 */
public class SeedBankHandler 
{
    
    protected static JPanel slotPanel = null;
    protected static JPanel displayPanel = null;
    protected static JPanel filterPanel = null;
    protected static JTextArea descriptionArea;
    
    
    protected static ArrayList<SeedCollection> activeSeedList = new ArrayList<>();
    protected static ArrayList<JTextField> addPackTextFields = new ArrayList<>();
    
    public static void setAddPackTextFields(ArrayList<JTextField> textFields)
    {
        for(JTextField field : textFields)
        {
            addPackTextFields.add(field);
        }
    }
    
    public static void clearAddPackTextFields()
    {
        
        for(JTextField field : addPackTextFields)
        {
            
            field.setText("");
        }
        addPackTextFields.get(0).requestFocus();
        descriptionArea.setText("");
    }
    
    public static void setDescriptionArea(JTextArea desc)
    {
        SeedBankHandler.descriptionArea = desc;
    }
    
    
    
    public static SeedCollection getSeedPackByIndex(int index)
    {
        return SeedBankHandler.activeSeedList.get(index);
    }
   
    
    public static void loadSeedCollection(ArrayList<String> seedList)
    {
        
        ArrayList<SeedCollection> seedCollectionList = new ArrayList<>();
        
        for(String str : seedList)
        {
            if(str.equalsIgnoreCase("end"))
                break;
            
            String[] splitString = str.split(",");
            SeedCollection seedC = new SeedCollection();
            seedC.setId(Integer.parseInt(splitString[0]));
            seedC.setCultivar(splitString[1]);
            seedC.setBreeder(splitString[2]);
            seedC.setStock(Integer.parseInt(splitString[3]));
            seedC.setRelease(splitString[4]);
            seedC.setLineage(splitString[5]);
            seedC.setSeedType(splitString[6]); 
            seedC.setFloweringTime(splitString[7]);
            seedC.setTerpeneProfile(splitString[8]);
            seedC.setExpectedSize(splitString[9]);
            seedC.setExpectedYield(splitString[10]);
            seedC.setDescription(splitString[11]);
            
            
            
            
            
            seedCollectionList.add(seedC);
            
            
        }
        
        SeedBankHandler.activeSeedList = seedCollectionList;
        setup();
        
    }
    
    public static ArrayList<String> getSeedDataList(String fullSeedString)
    {
        ArrayList<String> seedList = new ArrayList<>();
        String[] splitString = fullSeedString.split("-");
        for(String str : splitString)
        {
            seedList.add(str);
        }
        
        return seedList;
        
    }
    
    public static void assignPanels(JPanel seedPanel, JPanel displayPanel, JPanel filterPanel)
    {
        SeedBankHandler.displayPanel = displayPanel;
        SeedBankHandler.slotPanel = seedPanel;
        SeedBankHandler.filterPanel = filterPanel;
    }
    
    public static void setup()
    {

        loadFilterDisplay();
        ArrayList<SeedPackSelectionLabel> selectionLabels = buildSeedPackCells(SeedBankHandler.activeSeedList);
        displayMapCells(selectionLabels);
    }


    public static ArrayList<SeedPackSelectionLabel> buildSeedPackCells(ArrayList<SeedCollection> seedCollection)
    {
        ArrayList<SeedPackSelectionLabel> cellList = new ArrayList<>();
        
        for(int i = 0; i < seedCollection.size(); i++)
        {
            SeedPackSelectionLabel label = new SeedPackSelectionLabel(seedCollection.get(i).getCultivar(), i, String.valueOf(seedCollection.get(i).getId()));
            SeedBankHandler.slotPanel.add(label);
            cellList.add(label);
        }
        
        return cellList;
    }
    
    public static void displayMapCells(ArrayList<SeedPackSelectionLabel> cellList)
    {
        
        int xPos = 10;
        int yPos = -60;
        
        for(SeedPackSelectionLabel cell : cellList)
        {
            cell.setLocation(xPos, yPos);
            cell.setVisible(true);
            cell.setText(cell.getLabelText());
            
        }
        
        for(int i = 0; i < cellList.size(); i++)
        {
            switch(cellList.get(i).getIndex() % 3)
            {
                case 0:
                    yPos += 70;
                    xPos = 10;
                    break;
                    
                default:
                    xPos += 240;
                    break;
            }
            cellList.get(i).setLocation(xPos, yPos);
        }
    }
    
    public static void loadSeedPackCells(ArrayList<SeedCollection> seedCollection, JPanel slotPanel)
    {
        
        ArrayList<SeedPackSelectionLabel> cellList = new ArrayList<>();
        cellList = buildSeedPackCells(seedCollection);
        for(SeedPackSelectionLabel label : cellList)
        {
            slotPanel.add(label);
        }
        
        SeedBankHandler.slotPanel = slotPanel;

    }
    
    public static void displaySelectedPack(int index)
    {
        SeedCollection selectedPack = new SeedCollection();
        loadSelectionDisplay();
        if(activeSeedList.get(index) != null)
        {
            selectedPack = activeSeedList.get(index);
            Component[] components = displayPanel.getComponents();
            displayBuilder(components, selectedPack);
        }
        
        
        
    }
    
    private static void displayBuilder(Component[] compList, SeedCollection selectedPack)
    {
        JLabel packCultivarLabel = (JLabel) compList[0];
        JLabel packImageLabel = (JLabel) compList[1];
        JLabel packYieldLabel = (JLabel) compList[2];
        JLabel packSizeLabel = (JLabel) compList[3];
        JLabel packTerpeneLabel = (JLabel) compList[4];
        JLabel packFloweringTimeLabel = (JLabel) compList[5];
        JLabel packSeedTypeLabel = (JLabel) compList[6];
        JLabel packLineageLabel = (JLabel) compList[7];
        JLabel packReleaseLabel = (JLabel) compList[8];
        JLabel packBreederLabel = (JLabel) compList[9];
        JLabel packStockLabel = (JLabel) compList[10];
        JLabel packIdLabel = (JLabel) compList[11];
        JTextArea description = (JTextArea) compList[12];
        
        packCultivarLabel.setText(selectedPack.getCultivar());
        packYieldLabel.setText(selectedPack.getExpectedYield());
        packSizeLabel.setText(selectedPack.getExpectedSize());
        packTerpeneLabel.setText(selectedPack.getTerpeneProfile());
        packFloweringTimeLabel.setText(selectedPack.getFloweringTime());
        packSeedTypeLabel.setText(selectedPack.getSeedType());
        packLineageLabel.setText(selectedPack.getLineage());
        packReleaseLabel.setText(selectedPack.getRelease());
        packBreederLabel.setText(selectedPack.getBreeder());
        packStockLabel.setText(String.valueOf(selectedPack.getStock()));
        packIdLabel.setText(String.valueOf(selectedPack.getId()));
        description.setText(selectedPack.getDescription());
        
        
        //ImageIcon check if exists, if not set to default image. If it does set image to selectedPack.getCultivar()
        String imagePath = "src/Data/Images/seedbank/cultivars/" + selectedPack.getCultivar() + ".png";
        String defaultImagePath = "src/Data/Images/seedbank/cultivars/default.png";
        
        File checkFile = new File(imagePath);
        if(checkFile.exists())
        {
            Icon imageIcon = new ImageIcon(imagePath);
            packImageLabel.setIcon(imageIcon);
        }
        else
        {
            Icon imageIcon = new ImageIcon(defaultImagePath);
            packImageLabel.setIcon(imageIcon);    
  
        }
        
       
        
        
        
    }
    
    
   
    
    public static void loadFilterDisplay()
    {
        SeedBankHandler.displayPanel.setVisible(false);
        SeedBankHandler.filterPanel.setVisible(true);
    }
    
    
    public static void loadSelectionDisplay()
    {
        SeedBankHandler.displayPanel.setVisible(true);
        SeedBankHandler.filterPanel.setVisible(false);
    }
}

