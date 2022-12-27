/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Indoor;

import Backend.GrowRoomData.GrowRoom;
import Backend.PlantData.Plant;
import View.NewDashboard;
import View.customcomponents.customIndoor.IndoorPlantSelectionLabel;
import java.awt.Component;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jaket
 */
public class IndoorHandler 
{
    
    public static GrowRoom growRoom = new GrowRoom();
    public static JPanel labelPanel;
    
    public static void setGrowRoom(GrowRoom growRoom) {
        IndoorHandler.growRoom = growRoom;
    }

    public static void setLabelPanel(JPanel labelPanel) {
        IndoorHandler.labelPanel = labelPanel;
    }
    
    

    
    
 
    
    public void displaySelectedPlant(int index)
    {
        
        
        
        
        /*
        Plant newPlant = realPlantList.get(index);
        Component[] compList = plantDetailsPanel.getComponents();
        JLabel plantTag = (JLabel) compList[0];
        JLabel breeder = (JLabel) compList[1];
        JLabel plantId = (JLabel) compList[2];
        
        plantTag.setText(newPlant.getPlantLabel());
        breeder.setText("Solfire Gardens");
        plantId.setText(newPlant.getPlantId());*/
        
        
    }
    
    public static void loadGrowRoom(String plantList)
    {
        
       
        loadGrowRoomPlantCells(plantList);
        
    }
    
    public static ArrayList<JLabel> buildPlantLabels(int amount)
    {
        
        System.out.println("Building " + amount + " labels");
        ArrayList<JLabel> labelList = new ArrayList<>();
        
        for(int i = 0; i < amount; i++)
        {
            IndoorPlantSelectionLabel plantLabel = new IndoorPlantSelectionLabel("", i);
            labelList.add(plantLabel);
        }
        
        return labelList;
    }
    
    public static ArrayList<Plant> buildPlantList(String tempPlantString)
    {
        ArrayList<Plant> plantList = new ArrayList<>();
        System.out.println("Temp plant str: " + tempPlantString);
        
        
        
        
        
        String[] tempList = tempPlantString.split("-");
        System.out.println("temp list length " + tempList.length);
        for(String str : tempList)
        {
            System.out.println(str);
            String[] args = str.split(",");
            
            Plant newPlant = new Plant(args[0], args[1], args[2]);
            
            plantList.add(newPlant);
        }
        
        //growRoom.getGrowRoomController().setPlantList(plantList);
        return plantList;
    }
    
    public static ArrayList<Plant> buildPlantList(ArrayList<String> stringList)
    {
        ArrayList<Plant> plantList = new ArrayList<>();
        System.out.println("Building plant list");
        for(String str : stringList)
        {
            if(!str.equalsIgnoreCase("end"))
            {
                String[] newStr = str.split(",");
                System.out.println(newStr[0] + " " + newStr[1] + " " + newStr[2]);
                Plant newPlant = new Plant(newStr[0], newStr[1], newStr[2]);
                plantList.add(newPlant);
            }
            
            
        }
        System.out.println("returning plant list size " + plantList.size());
        return plantList;
    }
    
    
    public static void loadGrowRoomPlantCells(String plantList)
    {
        ArrayList<JLabel> plantCellList = buildPlantLabels(buildPlantList(plantList).size());
        //int amount = growRoom.getGrowRoomController().getPlantList().size();
        System.out.println("Loading plant cells");
        
        
        for(JLabel label : plantCellList)
        {
            label.setLocation(15, 70);
            //growRoom.getGrowRoomController().getPlantListPanel().add(label);
            label.setText("");
            label.setVisible(false);
        }
        
        int posCounter = 1;
        int xPos = 15;
        int yPos = 10;
        for(int i = 0; i < plantCellList.size(); i++)
        {
           
            switch(i % 6)
            {
                case 0:
                    System.out.println("building.");
                    xPos = 15;
                    yPos += 70;
                    break;
                    
                default:
                    System.out.println("building..");
                    xPos += 190;
                    break;
                
            }
            System.out.println("plant cell list: " + plantCellList.get(i));
            plantCellList.get(i).setLocation(xPos, yPos);
            plantCellList.get(i).setVisible(true);
        }
        
        /*if(growRoom.getGrowRoomController().getPlantList().size() <= plantCellList.size())
        {
            for(int i = 0; i < growRoom.getGrowRoomController().getPlantList().size(); i++)
            {
                plantCellList.get(i).setText(growRoom.getGrowRoomController().getPlantList().get(i).getPlantLabel());
                plantCellList.get(i).setVisible(true);
            }
        }
        
        else if(growRoom.getGrowRoomController().getPlantList().size() > plantCellList.size())
        {
            for(int i = 0; i < plantCellList.size(); i++)
            {
                plantCellList.get(i).setText(growRoom.getGrowRoomController().getPlantList().get(i).getPlantLabel());
                plantCellList.get(i).setVisible(true);
            }
        }*/
    }
    
    
    
}
