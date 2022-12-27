/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*package Backend.CloneSystem;

import Backend.PlantData.Plant;
import Backend.PlantData.PlantDataManager;
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
import java.util.Arrays;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jakeo
 *//*
public class CloneHandler 
{
    
    
   /* public static Clone loadCloneByMotherId(int motherId, int plantId)
    {
        Path filePath = Paths.get("src/Data/Core/Plants/MotherPlants/" + motherId + "/Clones/" + plantId + "/plantData.txt");
        
    }
    
    
    private Clone loadCloneByFile(Path filePath)
    {
        
    }
    
    public static void createClone(int motherId, int amount)
    {
        System.out.println();
    }
    
    
    
    
    
    */
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 /*   public static Clone loadClone(int plantId, int motherId)
    {
        Clone clone = new Clone();

        //public Clone(int plantId, String plantTag, int motherPlantId, String age, String location, String growContainer, String growthStage, int plantHeight)
        Path filePath = Paths.get("src/Data/Core/Plants/Phenotypes/" + motherId + "/Clones/" + plantId + "/data.txt");
        File theFile = filePath.toFile();
        
        try(BufferedReader in = new BufferedReader(new FileReader(theFile)))
        {
            String curF = in.readLine();
            
            while(curF != null)
            {
                String[] curLine = curF.split(",");
                
                String newPlantId = curLine[0];
                String newPlantTag = curLine[1];
                String newMotherId = curLine[2];
                String newAge = curLine[3];
                String newLocation = curLine[4];
                String newGrowContainer = curLine[5];
                String newGrowthStage = curLine[6];
                String height = curLine[7];
                
                
                Clone someClone = new Clone(Integer.parseInt(newPlantId), newPlantTag, Integer.parseInt(newMotherId), newAge, newLocation, newGrowContainer, newGrowthStage, Integer.parseInt(height));
                clone = someClone;
                curF = in.readLine();
                
            }
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }

        return clone;
    }

    
    
    
    
    
    
    
    
    */
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   /* public static void cloneMachineSetup(ArrayList<JLabel> slotList)
    { 
        ArrayList<String> nameList = getCloneSlots();
        
        for(int i = 0; i < slotList.size(); i++)
        {
            slotList.get(i).setName(nameList.get(i));
        }
            
        
        updateCloneMachineDisplay(slotList);
    }*/
    
   
    
    /*public static void updateCloneMachineDisplay(ArrayList<JLabel> slotList)
    {
        ArrayList<Clone> cloneList = loadMachineClones("CK1");
        
        
        ArrayList<Clone> activeList = new ArrayList();
        ArrayList<Clone> inactiveList = cloneList;
        
        for(Clone clone : cloneList)
        {
            if(clone != null)
            {
                activeList.add(clone);
                
            }
        }
        
        
        System.out.println("SlotList " + slotList.size());
        for(int i = 0; i < slotList.size(); i++)
        {
            
            useSlot(slotList.get(i), false);
        }
        
        
        for(int i = 0; i < activeList.size(); i++)
        {
            useSlot(slotList.get(i), true);
 

        }

        
        
        
        
    }
        
    public static void useSlot(JLabel label, boolean active)
    {
        if(active)
        {
            Icon icon = new ImageIcon("src/Data/Images/UI Icons/Cloning/cloneIcon.png");
            label.setIcon(icon);
            System.out.println("Adding Clone Icon");
        }
        
        else if(!active)
        {
            Icon icon = new ImageIcon("src/Data/Images/UI Icons/Cloning/emptyCloneIcon.png");
            label.setIcon(icon);
        }
        
    }
    
     public static int getMachineIndex(String spot)
    {
        int column = 1;
        String letters = "ABCDEF";
        int returnData;
        //"D6"
        
        for(int i = 0; i < 5; i++)
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
        
       
        //System.out.println(returnData);
        return returnData;
    }*/
    /*
    public static void addClone(Clone clone)
    {
        ArrayList<Clone> cloneList = loadCloneList();
        
        cloneList.add(clone);
        
        
        
        updateCloneList(cloneList, "CK1");
    }

    
    public static ArrayList<Clone> loadClones()
    {
        ArrayList<Clone> cloneList = new ArrayList();
        
        
        Path filePath = Paths.get("src/Data/Core/Plants/PlantData/CK1CloneList.txt");
        File theFile = filePath.toFile();
        
        try(BufferedReader in = new BufferedReader(new FileReader(theFile)))
        {
            String curF = in.readLine();
            
            while(curF != null)
            {
                String[] curLine = curF.split(",");
                
                String plantId = curLine[0];
                String age = curLine[1];
                String location = curLine[2];
                String strain = curLine[3];
                String origin = curLine[4];
                String floweringType = curLine[5];
                String growthStage = curLine[6];
                String motherPlantId = curLine[7];
                
                
                Clone newClone = new Clone(Integer.parseInt(plantId), age, location, strain, origin, floweringType, growthStage, Integer.parseInt(motherPlantId));
                cloneList.add(newClone);
                curF = in.readLine();
                
            }
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }

        return cloneList;
    }
    
    
    
    public static ArrayList<Clone> loadCloneList()
    {
        ArrayList<Clone> cloneList = new ArrayList();
        
        
        Path filePath = Paths.get("src/Data/Core/Plants/PlantData/CK1CloneList.txt");
        File theFile = filePath.toFile();
        
        try(BufferedReader in = new BufferedReader(new FileReader(theFile)))
        {
            String curF = in.readLine();
            
            while(curF != null)
            {
                String[] curLine = curF.split(",");
                
                String plantId = curLine[0];
                String age = curLine[1];
                String location = curLine[2];
                String strain = curLine[3];
                String origin = curLine[4];
                String floweringType = curLine[5];
                String growthStage = curLine[6];
                String motherPlantId = curLine[7];
                
                
                Clone newClone = new Clone(Integer.parseInt(plantId), age, location, strain, origin, floweringType, growthStage, Integer.parseInt(motherPlantId));
                cloneList.add(newClone);
                curF = in.readLine();
                
            }
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }

        return cloneList;
    }
    
   /* public static ArrayList<Clone> loadCloneList(String fileExtension)
    {
        ArrayList<Clone> cloneList = new ArrayList();
        
        
        Path filePath = Paths.get("src/Data/Core/Plants/PlantData/" + fileExtension + "CloneList.txt");
        File theFile = filePath.toFile();
        
        try(BufferedReader in = new BufferedReader(new FileReader(theFile)))
        {
            String curF = in.readLine();
            
            while(curF != null)
            {
                String[] curLine = curF.split(",");
                
                String cloneId = curLine[0];
                String plantTag = curLine[1];
                String location = curLine[1];
                String motherId = curLine[2];
                String age = curLine[3];
                
                
                Clone someClone = new Clone(Integer.parseInt(cloneId), plantTag, location,  age, potSize,Integer.parseInt(motherId), growthStage, plantHeight);
                cloneList.add(someClone);
                curF = in.readLine();
                
            }
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }

        return cloneList;
        
    }
    
    public static ArrayList<String> loadMachineClones(String machine)
    {
        ArrayList<String> cuttingIdList = new ArrayList();
        ArrayList<String> machineSlotList = new ArrayList();
        ArrayList<String> machineList = new ArrayList();
        for(int i = 0; i < 35; i++)
        {
            machineList.add(null);
        }
        
        
        
        Path filePath = Paths.get("src/Data/Core/Plants/PlantData/" + "CK1Clones.txt");
        File theFile = filePath.toFile();
        String data;
        
        try(BufferedReader in = new BufferedReader(new FileReader(theFile)))
        {
            String curF = in.readLine();
            
            
            while(curF != null)
            {
                String[] curLine = curF.split(",");
                
                String plantId = curLine[0];
                String machineSlot = curLine[1];
                
                
 
                cuttingIdList.add(plantId);
                machineSlotList.add(machineSlot);
                curF = in.readLine();
                
                
            }
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        for(int i = 0; i < cuttingIdList.size(); i++)
        {
            int newIndex = getMachineIndex(machineList.get(i));
            machineList.add(newIndex,cuttingIdList.get(i));
        }

        
        return machineList;
    }
   
    public static void cloneSelectionDisplay(String slotName, JPanel cloneSelectionPanel, Clone selectedClone)
    {
        
        Component[] compList = cloneSelectionPanel.getComponents();
        
        ArrayList<JLabel> labelList = new ArrayList();
        
        for(Component c : compList)
        {
            labelList.add((JLabel) c);
            
        }
        
        labelList.get(0).setText(slotName);
        labelList.get(1).setText(String.valueOf(selectedClone.getPlantId()));
        labelList.get(2).setText(String.valueOf(selectedClone.getMotherPlantId()));
        labelList.get(3).setText(selectedClone.getAge());    
        labelList.get(4).setText(selectedClone.getStrain());
        
        cloneSelectionPanel.setVisible(true);
        
        
        
    }
    
    public static void loadCloneEventPanel(String slotName, JPanel cloneEventPanel)
    {
        Component[] compList = cloneEventPanel.getComponents();
        Clone selectedClone = new Clone();
        selectedClone = CloneHandler.getCloneBySlotName(slotName);
        
        ArrayList<JLabel> labelList = new ArrayList();
        
        for(int i = 0; i < 1; i ++)
        {
            labelList.add((JLabel) compList[0]);
        }

        labelList.get(0).setText(String.valueOf(selectedClone.getPlantId()));
        
        System.out.println("Loading Event Panel!");
        cloneEventPanel.setVisible(true);

    }*/
    /*
    public static Clone getCloneBySlotName(String name)
    {
        ArrayList<Clone> cloneList = loadCloneList("CK1");
        Clone selectedClone = new Clone();
        

        for(Clone c : cloneList)
        {
            
            
            if (c.getLocation().equals(name))
            {
                selectedClone = c; 
                break;
            } 
        
        }
        
        return selectedClone;
    }
    
    public static String selectCloneSlot(String slotName, JPanel cloneSelectionPanel , JPanel clonePanel, JLabel blurBackground)
    {
        
        ArrayList<Clone> cloneList = loadCloneList("CK1");
        Clone selectedClone = new Clone();
        
        
        
        
        boolean slotFilled = false;
        
        for(Clone c : cloneList)
        {
            
            
            if (c.getLocation().equals(slotName))
            {
                selectedClone = c;
                slotFilled = true;
                break;
            } 
                
                
        }
        
        if(slotFilled)
        {
            cloneSelectionDisplay(slotName, cloneSelectionPanel, selectedClone);
        }
        
        else if(!slotFilled)
        {
            clonePanel.setVisible(true);
        }
        
        
        String selectedSlot = slotName;
        
        return selectedSlot;
    }
    */
    
    /*
    public static void updateCloneList(ArrayList<Clone> cloneList)
    {
        Path path = Paths.get("src/Data/Core/Plants/PlantData/cloneList.txt");
        File theFile = path.toFile();

        try
        {
            
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(theFile, false)));
            
            for(Clone c : cloneList)
            {
               
                writer.println(c.getPlantId() + "," + c.getAge() + "," + c.getLocation() + "," + c.getStrain() + "," + c.getOrigin() + "," + c.getFloweringType() + "," + c.getGrowthStage() + "," + c.getMotherPlantId());
                
            }
            
            writer.close();
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
        
    }
    
    public static void updateCloneList(ArrayList<Clone> cloneList, String machineName)
    {
        Path path = Paths.get("src/Data/Core/Plants/PlantData/" + machineName + "CloneList.txt");
        File theFile = path.toFile();
        String line = "";
     
        
        try
        {
            
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(theFile, false)));
            
            for(Clone c : cloneList)
            {
                writer.println(c.getPlantId() + "," + c.getLocation() + "," + c.getMotherPlantId() + "," + c.getAge());
            }
            
            writer.close();
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
      
    public static void logCloneEvent(String id, String event)
    {
        Path path = Paths.get("src/Data/Core/Plants/PlantData/CloneEvents.txt");
        File theFile = path.toFile();
        String line = "";
     
        
        try
        {
            
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(theFile, true)));
            
            
            writer.println(id + "," + event);
            writer.close();
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
      
      public static ArrayList<String> getCloneSlots()
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
      
      public static void addCloneToSlot(String slot, Clone clone)
      {
          ArrayList<Clone> cloneList = loadCloneList();
      }
    
}*/
