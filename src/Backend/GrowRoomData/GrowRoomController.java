/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.GrowRoomData;

import Backend.Client.Client;
import Backend.Client.ClientHandler;
import Backend.PlantData.Plant;
import Backend.PlantHandler;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import javax.swing.JPanel;

public class GrowRoomController
{
    protected static JPanel growRoomPlantListPanel;
    protected static JPanel growRoomOverviewPanel;
    protected static JPanel growRoomToolPanel;
    protected static GrowRoom activeGrowRoom = new GrowRoom();
    protected static ArrayList<Plant> activePlantList = new ArrayList();
    
    protected static JPanel selectedGrowRoomPanel;
    protected static JPanel indoorModulePanel;

    

    public static void setGrowRoomPanels(ArrayList<JPanel> panels)
    {
        selectedGrowRoomPanel = panels.get(0);
        indoorModulePanel = panels.get(1);
        /*growRoomPlantListPanel = panels.get(0);
        growRoomOverviewPanel = panels.get(1);
        growRoomToolPanel = panels.get(2);*/
    }
    
    public static void setActiveGrowRoom(GrowRoom newRoom)
    {
        activeGrowRoom = newRoom;
        loadGrowRoom();
    }
    
    public static void requestGrowRoom(String roomId)
    {
        ClientHandler.sendMessage(roomId);
        
        //add loading screen here if needed.
         
    }
    
    public static void loadGrowRoom()
    {
        System.out.println("Simulating that I just loaded all of the details to the room display panels for room " + activeGrowRoom.roomId );
        enableActiveGrowRoom();
    }
    
    public static void enableActiveGrowRoom()
    {
        indoorModulePanel.setVisible(false);
        selectedGrowRoomPanel.setVisible(true);
    }
    
    
    public static ArrayList<Plant> getActivePlantList()
    {
        return activePlantList;
    }
    
    public static void setActivePlantList(ArrayList<Plant> plantList)
    {
        activePlantList = plantList;
    }
    
    
    
}