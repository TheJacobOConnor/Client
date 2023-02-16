/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Indoor;

import Backend.Client.ClientHandler;
import Backend.GrowRoomData.GrowRoomController;
import Backend.PlantData.Plant;
import java.util.ArrayList;
import javax.swing.JPanel;
import View.customcomponents.customIndoor.PlantListPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author jaket
 */
public class IndoorPlantListHandler 
{
    protected static JPanel listPanel;
    protected static ArrayList<Plant> plantList = new ArrayList<>();
    
    
    
    
    private static final int maxDisplayed = 15;
    protected static int pageNumbers = 0;
    protected static int currentPageNumber = -1;
    protected static JLabel pageLabel;
    
    
    public static void loadActivePlantList()
    {
        plantList = GrowRoomController.getActivePlantList();
    }
    
    public static void panelSetupTest(Plant plant)
    {
        /*PlantListPanel newplp = new PlantListPanel(plant);
        newplp.setBounds(11, 390, 349, 45);
        
        
        
        listPanel.add(newplp);
        listPanel.add(bgLabel);
        listPanel.repaint();*/
    }
    
    public static void requestPlantList(String roomId)
    {
        ClientHandler.sendMessage("RequestData:PlantList:29");
    }
    
    
    public static void updateListPanel()
    {
        
        
        
        loadActivePlantList();
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
        calcPageNumbers();
        buildPageSystem();
        listPanel.add(bgLabel);
        listPanel.repaint();
    }
    
    private static void buildPageSystem()
    {
        Font nameFont = new Font("bahnschrift", Font.PLAIN, 18);
        JLabel backArrowLabel;
        JLabel forwardArrowLabel;
        JLabel pageLabel;
        
        
        backArrowLabel = new JLabel();
        Icon backArrowIcon = new ImageIcon("src/Data/Images/Indoor/Panels/backArrow.png");
        backArrowLabel.setBounds(90, 850, 40, 30);
        backArrowLabel.setIcon(backArrowIcon);
        backArrowLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        listPanel.add(backArrowLabel);
        
        pageLabel = new JLabel();
        pageLabel.setText("Page " + currentPageNumber);
        pageLabel.setFont(nameFont);
        pageLabel.setForeground(Color.WHITE);
        pageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        pageLabel.setBounds(150, 850, 65, 30);
        listPanel.add(pageLabel);
        
        
        forwardArrowLabel = new JLabel();
        Icon forwardArrowIcon = new ImageIcon("src/Data/Images/Indoor/Panels/forwardArrow.png");
        forwardArrowLabel.setBounds(230, 850, 40, 30);
        forwardArrowLabel.setIcon(forwardArrowIcon);
        forwardArrowLabel.setHorizontalAlignment(SwingConstants.CENTER);
        listPanel.add(forwardArrowLabel);
        
        

    
   
    
    }
    
     public void updatePageNumbers(int num)
    {
        pageLabel.setText("Page " + num);
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
    
    private static void calcPageNumbers()
    {
        int plantTotal = 0;
        if(plantList.size() % maxDisplayed != 0)
        {
            plantTotal = Math.round(plantList.size() / maxDisplayed) + 1;
        }
        else
        {
            plantTotal = Math.round(plantList.size() / maxDisplayed);
        }
        

        pageNumbers = plantTotal;
    }

    public static int getPageNumbers()
    {
        return pageNumbers;
    }
    
    
    
    
    
    
}
