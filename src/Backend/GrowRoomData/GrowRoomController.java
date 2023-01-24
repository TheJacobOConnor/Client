/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.GrowRoomData;

import Backend.Client.Client;
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

    

    public static void setGrowRoomPanels(ArrayList<JPanel> panels)
    {
        growRoomPlantListPanel = panels.get(0);
        growRoomOverviewPanel = panels.get(1);
        growRoomToolPanel = panels.get(2);
    }
    
    public static void loadGrowRoom(String roomId)
    {
        
    }
}