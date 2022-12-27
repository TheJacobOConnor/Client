/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.CloneSystem;


import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jaket
 */
public class CloningMachineHandler 
{
    
    
    public static void cloneMachineSetup(ArrayList<Cutting> cuttingList, ArrayList<JLabel> slotList)
    { 
        ArrayList<String> nameList = getMachineSlots();
        
        for(int i = 0; i < slotList.size(); i++)
        {
            slotList.get(i).setName(nameList.get(i));
        }
            
        
        updateMachineDisplay(cuttingList, slotList);
    }
    
    
    public static void updateMachineDisplay(ArrayList<Cutting> cuttingList, ArrayList<JLabel> slotList)
    {
        
        
        
        ArrayList<Integer> activeList = new ArrayList();
        ArrayList<String> cuttingIdList = new ArrayList();
        
        for(Cutting c : cuttingList)
            cuttingIdList.add(String.valueOf(c.getMotherId()));
        
        for(int i = 0; i < cuttingList.size(); i++)
        {
            if(cuttingList.get(i).getMotherId() != 0)
            {
                activeList.add(i);
                
                
            }
        }
        
        
        
        for(int i = 0; i < slotList.size(); i++)
        {
            
            useMachineSlot(slotList.get(i), false);
        }
        
        
        for(Integer i : activeList)
        {
            useMachineSlot(slotList.get(i), true);
 

        }

        
        
        
        
    }
    
    
    public static void addCutting(Cutting newCutting, String machine, String location)
    {
        ArrayList<Cutting> cuttingList = CuttingHandler.loadCuttingList(machine);
        ArrayList<String> machineCuttingList = new ArrayList<>();
        
        int cuttingIndex = getMachineIndex(location);
        
        machineCuttingList.add(cuttingIndex, String.valueOf(newCutting.getMotherId()));
        
    }
    
    

     
    
     public static int getMachineIndex(String spot)
    {
        
        
        int column = 1;
        String letters = "ABCDEF";
        int returnData;
        //"D6"
        
        for(int i = 0; i < 6; i++)
        {
            
            if(spot.substring(0,1).equals(letters.substring(i,i + 1)))
            {
                
                
                column = i;
                break;
            }
            
        }
        
        
        int numIndex = Integer.parseInt(spot.substring(1));
        
        
        
        //D4 column 3 index 
        //1
        //3
        
        returnData = numIndex - 1 + (column * 6);
        
       
       
        return returnData;
    }
     
      public static String getMachineIndex(int index)
    {
        String letters = "ABCDEF";
        String currentSlot = "";
       
        int indexSlot = 0;
        
        for(int i = 0; i < letters.length(); i++)
        {
            for(int j = 0; j < 6; j++)
            {
                currentSlot = letters.substring(i) + j + 1;
                if(indexSlot == index)
                {
                    break;
                }
                indexSlot++;
            }
        }
       
        
        return currentSlot;
    }
     
    
      
      
      
    public static void useMachineSlot(JLabel label, boolean active)
    {
        
        
        if(active)
        {
            Icon icon = new ImageIcon("src/Data/Images/UI Icons/Cloning/cloneIcon.png");
            label.setIcon(icon);
            
        }
        
        else if(!active)
        {
            Icon icon = new ImageIcon("src/Data/Images/UI Icons/Cloning/emptyCloneIcon.png");
            label.setIcon(icon);
        }
        
    }
      
      
      
      
    public static ArrayList<String> getMachineSlots()
      {
          ArrayList<String> slotList = new ArrayList();
          
          ArrayList<String> indexList = new ArrayList();
          
          int counter = 1;
          int indexCounter = 0;
          indexList.add("A");
          indexList.add("B");
          indexList.add("C");
          indexList.add("D");
          indexList.add("E");
          indexList.add("F");
          
          for(int i = 0; i < 36; i++)
          {
              slotList.add(indexList.get(indexCounter) + counter);
              
              if(counter == 6)
              {
                  indexCounter += 1;
                  counter = 0;
              }
              counter ++;
              
          }
       
          
          return slotList;
      }
      
         
      
    public static String selectCloneSlot(String slotName, ArrayList<Cutting> cuttingList, JPanel cloneSelectionPanel, JPanel clonePanel, JPanel phenoFinderPanel, JLabel blurBackground)
    {
        
       
        
        ArrayList<String> cuttingIdList = new ArrayList<>();
        for(Cutting c : cuttingList)
            cuttingIdList.add(String.valueOf(c.getMotherId()));
        
        
        boolean slotFilled = false;
        
        
        int machineIndex = getMachineIndex(slotName);
        
        System.out.println("Yo " + cuttingIdList.get(machineIndex));
        if(cuttingList.get(machineIndex).getMotherId() != 0)
            slotFilled = true;

        if(slotFilled) 
            cuttingSelectionDisplay(slotName, cloneSelectionPanel, phenoFinderPanel, cuttingIdList.get(machineIndex));
         
        if(!slotFilled)
        {
            clonePanel.setVisible(true);
            System.out.println("slot not filled");
        }
        
        
        String selectedSlot = slotName;
        
        return selectedSlot;
    }
    
    
    public static void cuttingSelectionDisplay(String slotName, JPanel cloneSelectionPanel, JPanel phenoFinderPanel, String selectedCutting)
    {
        
        Component[] compList = cloneSelectionPanel.getComponents();
        
        ArrayList<JLabel> labelList = new ArrayList();
        
        
        for(Component c : compList)
        {
            labelList.add((JLabel) c);
            
        }
        
        if(!selectedCutting.equals("EMPTY"))
        {
            
            
            labelList.get(0).setText("test");
            labelList.get(1).setText(selectedCutting);
            labelList.get(2).setText("test");
            cloneSelectionPanel.setVisible(true);
            
        }
        
        else
        {
            phenoFinderPanel.setVisible(true);
        }
        
        
        
        
        
        
        
        
        
    }
    
    
     public static ArrayList<Integer> getAvaialableMachineSlots(ArrayList<Cutting> cuttingList, String machine)
    {
        
        ArrayList<Integer> availableIndexList = new ArrayList();
        
        for(int i = 0; i < cuttingList.size(); i++)
        {
            if(cuttingList.get(i).getCutDate().equals("empty"))
                availableIndexList.add(i);
        }
        
        return availableIndexList;
    }
    
    
    
    
    public static void cloneEventDisplay(String selectedSlot, JPanel eventPanel)
    {
        Component[] compList = eventPanel.getComponents();
        ArrayList<JLabel> labelList = new ArrayList();
        
        //Cutting selectedCutting = CuttingController.getCuttingByIndex(getMachineIndex(selectedSlot), "machine1");
        Cutting selectedCutting = new Cutting(0, "");
        
        //Phenotype pheno = PlantDataManager.getPheno(selectedCutting.getMotherId());
        
        for(Component c : compList)
        {
            if(c instanceof JLabel)
                labelList.add((JLabel) c);
        }
        
        //labelList.get(0).setText(pheno.getPlantTag());
        labelList.get(1).setText(String.valueOf(selectedCutting.getMotherId()));
        
        
        eventPanel.setVisible(true);
    }
    
    
}
