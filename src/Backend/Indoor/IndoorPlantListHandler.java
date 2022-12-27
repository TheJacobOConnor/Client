/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Indoor;

import Backend.PlantData.Plant;
import java.util.ArrayList;
import javax.swing.JPanel;
import View.customcomponents.customIndoor.PlantListPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author jaket
 */
public class IndoorPlantListHandler 
{
    protected static JPanel listPanel;
    protected static ArrayList<Plant> plantList = new ArrayList<>();
    
    
    public static void panelSetupTest(Plant plant)
    {
        /*PlantListPanel newplp = new PlantListPanel(plant);
        newplp.setBounds(11, 390, 349, 45);
        
        
        
        listPanel.add(newplp);
        listPanel.add(bgLabel);
        listPanel.repaint();*/
    }
    
    
    public static void updateListPanel()
    {
        JLabel bgLabel = new JLabel();
        listPanel.removeAll();
        listPanel.validate();
        int yMod = 50;
        
        for(Plant p : plantList)
        {
            if(plantList.indexOf(p) > 15)
            {
                break;
            }
            PlantListPanel newPanel = new PlantListPanel(p);
            newPanel.setBounds(11, 40 + yMod, 349, 45);
            yMod += 50;
            listPanel.add(newPanel);
        }
        
        bgLabel.setBounds(0, 0, 394, 894);
        Icon icon = new ImageIcon("src/Data/Images/Indoor/Panels/plantListGraphic.png");
        bgLabel.setIcon(icon);
        listPanel.add(bgLabel);
        listPanel.repaint();
    }
    
    
    

    public static JPanel getListPanel() {
        return listPanel;
    }

    public static void setListPanel(JPanel listPanel) {
        IndoorPlantListHandler.listPanel = listPanel;
    }

    public static ArrayList<Plant> getPlantList() {
        return plantList;
    }

    public static void setPlantList(ArrayList<Plant> plantList) {
        IndoorPlantListHandler.plantList = plantList;
    }
    
    public static void addPlantToList(Plant p)
    {
        plantList.add(p);
    }
    
    
    
    
    
    
}
