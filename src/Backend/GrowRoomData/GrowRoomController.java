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

/**
 *
 * @author jaket
 */
public class GrowRoomController 
{
    protected JPanel plantDetailsPanel;

    public JPanel getRoomDetailsPanel() {
        return roomDetailsPanel;
    }

    public JPanel getPlantListPanel() {
        return plantListPanel;
    }
    protected JPanel roomDetailsPanel;
    protected JPanel calendarPanel;
    protected JPanel plantListPanel;
    protected ArrayList<Plant> plantList = new ArrayList<>();
    
    
    public GrowRoomController()
    {
        
    }
    
    public GrowRoomController(ArrayList<JPanel> panelList)
    {
        this.plantDetailsPanel = panelList.get(0);
        this.roomDetailsPanel = panelList.get(1);
        this.calendarPanel = panelList.get(2);
        this.plantListPanel = panelList.get(3);
        
        
    }

    public JPanel getPlantDetailsPanel() {
        return plantDetailsPanel;
    }
    
    
    
    
    
    
    
    
    
    
    public void setPanels(ArrayList<JPanel> panelList)
    {
        this.plantDetailsPanel = panelList.get(0);
        this.roomDetailsPanel = panelList.get(1);
        this.calendarPanel = panelList.get(2);
        this.plantListPanel = panelList.get(3);
    }
    
    
    
    public ArrayList<Plant> getPlantList()
    {
        return plantList;
    }

    
    
    
    public void setPlantList(ArrayList<Plant> plantList)
    {
        System.out.println("set plant list of a size of " + plantList.size());
        this.plantList = plantList;
    }
    
    
    
    
    public void updatePlantList() 
    {
        
    }
    

    
    
    
    
    
    public void updatePlantDetailsPanelByPlant(Plant plant)
    {
       
        
        Component[] compList = this.plantDetailsPanel.getComponents();
        JLabel plantLabel = (JLabel) compList[0];
        JLabel breederLabel = (JLabel) compList[1];
        JLabel plantIdLabel = (JLabel) compList[2];
        JLabel cultivarLabel = (JLabel) compList[3];
        
        plantLabel.setText(plant.getPlantLabel());
        breederLabel.setText("Solfire Gardens");
        plantIdLabel.setText(plant.getPlantId());
        cultivarLabel.setText(plant.getCultivar());
    }
    
    
    
    
    
    public void updatePlantDetailsPanelByIndex(int index)
    {
        Plant plant = plantList.get(index);
        
        Component[] compList = this.plantDetailsPanel.getComponents();
        JLabel plantLabel = (JLabel) compList[0];
        JLabel breederLabel = (JLabel) compList[1];
        JLabel plantIdLabel = (JLabel) compList[2];
        JLabel cultivarLabel = (JLabel) compList[3];
        
        plantLabel.setText(plant.getPlantLabel());
        breederLabel.setText("Solfire Gardens");
        plantIdLabel.setText(plant.getPlantId());
        cultivarLabel.setText(plant.getCultivar());
    }
    
    public Plant getPlantById(String id)
    {
        Plant plant = new Plant();
        
        for(Plant p : this.plantList)
        {
            if(p.getPlantId().equalsIgnoreCase(id))
            {
                plant = p;
                break;
            }
        }
        
        return plant;
    }
    
    
    
    
    
    
    
}
