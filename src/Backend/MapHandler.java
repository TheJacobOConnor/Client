/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Backend.PlantData.Plant;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.tools.JavaFileManager.Location;

/**
 *
 * @author jaket
 */
public class MapHandler 
{
    public static void loadMapPinIcons(ArrayList<JLabel> list)
    {
        Icon icon = new ImageIcon("src/Data/Images/UI Icons/mapPin.png");
        
                    
        for(JLabel label : list)
        {
            label.setIcon(icon);
        }
    }
    
    public static void plotWindowPlantListHandler(ArrayList<JLabel> listA, ArrayList<JLabel> listB, String plotName, JLabel plotNameLabel)
    {
        plotNameLabel.setText(plotName);
        MapHandler.displayPlantList(listA, listB, plotName);
        for(int i = 0; i < listA.size(); i++)
        {
            
            if(i % 2 == 0)
            {
                
                listA.get(i).setBackground(new Color(153,153,153,50));
                /*Border border = listA.get(i).getBorder();
                Border margin = new EmptyBorder(10,10,10,10);
                listA.get(i).setBorder(new CompoundBorder(border, margin));*/
                listA.get(i).setFont(new Font("Verdana", Font.PLAIN, 18));
                listA.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            else 
            {
                listA.get(i).setBackground(new Color(204,204,204,50));
                /*Border border = listA.get(i).getBorder();
                Border margin = new EmptyBorder(10,10,10,10);
                listA.get(i).setBorder(new CompoundBorder(border, margin));*/
                listA.get(i).setFont(new Font("Verdana", Font.PLAIN, 18));
                listA.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }
        
        for(int i = 0; i < listB.size(); i++)
        {
            if(i % 2 == 0)
            {
                listB.get(i).setBackground(new Color(153,153,153,50));
                /*Border border = listB.get(i).getBorder();
                Border margin = new EmptyBorder(10,10,10,10);
                listB.get(i).setBorder(new CompoundBorder(border, margin));*/
                listB.get(i).setFont(new Font("Verdana", Font.PLAIN, 18));
                listB.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            else 
            {
                listB.get(i).setBackground(new Color(204,204,204,50));
                /*Border border = listB.get(i).getBorder();
                Border margin = new EmptyBorder(10,10,10,10);
                listB.get(i).setBorder(new CompoundBorder(border, margin));*/
                listB.get(i).setFont(new Font("Verdana", Font.PLAIN, 18));
                listB.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                
                
            }
        }
    }
    
    public static void plotWindowComponentsHandler(JPanel backgroundPanel, JPanel blurPanel, JLabel simpleIcon, JLabel plotNameLabel, JLabel exitIconLabel, boolean enable)
    {
        if (enable)
        {
            backgroundPanel.setVisible(true);
            simpleIcon.setVisible(true);
            plotNameLabel.setVisible(true);
            exitIconLabel.setVisible(true);
            blurPanel.setVisible(true);
        }
        
        else if(!enable)
        {
            backgroundPanel.setVisible(false);
            simpleIcon.setVisible(false);
            plotNameLabel.setVisible(false);
            exitIconLabel.setVisible(false);
            blurPanel.setVisible(false);
        }
       
        
    }
    
    public static ArrayList<Plant> loadPlotData(String plotName)
    {
        ArrayList<Plant> plantList = new ArrayList();
        ArrayList<Plant> plotList = new ArrayList();
        
        //plantList = PlantDataManager.loadPlantList();
        System.out.println(plantList.size());
        
        /*for(Plant plant : plantList)
        {
            if(plant.getLocation().equals(plotName))
            {
                plotList.add(plant);
            }
        }*/
        
        return plotList;    
    }
    
    public static void displayPlantList(ArrayList<JLabel> labelListA, ArrayList<JLabel> labelListB, String plotName)
    {
        ArrayList<Plant> plantList = new ArrayList();
        System.out.println("Made it here");
        plantList = loadPlotData(plotName);
        System.out.println(plantList.size());
        for(int i = 0; i < plantList.size(); i++)
        {
            labelListA.get(i).setText("");
            labelListA.get(i).setText(plantList.get(i).toString());
            
            labelListB.get(i).setText("");
            //labelListB.get(i).setText(plantList.get(i).getAge() + " Weeks");
        }
        
        
    }
    
    public static void setupMap(ArrayList<JLabel> list, JPanel outdoorPanel) throws NullPointerException
    {
        Component myComps[] = outdoorPanel.getComponents();
        setNames(list);
        
        
        for(Component c : myComps)
        {
            if(c.getName() == null)
            {
                c.setName("Unknown Component");
            }
            System.out.println(c.getName());
            if(c.getName().equals("mapPanel"))
            {
                c.setVisible(false);
                break;
            }
            
            else
            {
                
            }
        }
        
        MapHandler.setNames(list);

    }
    
    public static void setNames(ArrayList<JLabel> list)
    {
        System.out.println(list.size());
        for(int i = 1; i < list.size(); i++)
        {
            list.get(i).setName("SP" + (i + 1));
        }
    }
    
    public static Point adjustLocation(Point startPoint)
    {
 
        double newX = startPoint.getX();
        double newY = startPoint.getY();
        System.out.println(startPoint.getLocation());
        if(startPoint.getX() > 945)
            newX = 945;
        if(startPoint.getY() > 655)
            newY = 655;
        if(startPoint.getX() < 0)
            newX = 0;
        if(startPoint.getY() < 0)
            newY = 0;
        
        startPoint.setLocation(newX, newY);
        
        return startPoint;
    }
    
    public static void locationDisplay(ArrayList<JLabel> pinList)
    {
        for(JLabel pin : pinList)
        {
            pin.setFont(new Font("Serif", Font.PLAIN, 8));
            pin.setForeground(Color.WHITE);
            pin.setText(pin.getName());
            pin.setIcon(null);
        }
    }
    
    public static JLabel selectPin(MouseEvent evt, JLabel pin, JLabel lastPin, ArrayList<JPanel> panelList)
    {
        //ArrayList<Plant> plantList = PlantDataManager.loadPlantList();
        ArrayList<Plant> plantList = new ArrayList<>();
        String selectPanelName;
        Plant selectedPlant = null;
        JPanel emptySlotPanel = new JPanel();
        JPanel viewPlantPanel = new JPanel();
        JPanel selectionPanel = panelList.get(2);
        
        
        selectionPanel.setVisible(true);
        
       
        
        Point newPoint = new Point();
        newPoint.setLocation(pin.getX() - 107.5, pin.getY() - 125);
        
        selectionPanel.setLocation(MapHandler.adjustLocation(newPoint.getLocation()));
        
        

        
        for(Plant p : plantList)
        {
            /*if(p.getLocation().equals(pin.getName()))
            {
                System.out.println("Here");
                selectedPlant = p;
                break;
            }*/
            
        }
        
        if(selectedPlant == null)
        {
            viewPlantDisplay(null, panelList.get(2));
        }
        
        else
        {
      
            viewPlantDisplay(selectedPlant, panelList.get(1));

        }
        
        
       Icon icon2 = new ImageIcon("src/Data/Images/Map/mapPinSelected.png");
       pin.setIcon(icon2); 
        
       
       Icon icon = new ImageIcon("src/Data/Images/Map/mapPin.png");
       lastPin.setIcon(icon);
        
        return pin;
    }
    
    public static void nodeDisplay(ArrayList<JLabel> nodeList)
    {
        ArrayList<Point> pointList = new ArrayList();
        pointList = JacobCords.getZones();
        
        
        
        for(int i = 0; i < nodeList.size(); i++)
        {
            System.out.println("Setting node " + i + " to " + pointList.get(i).getLocation());
            nodeList.get(i).setLocation(pointList.get(i));
            nodeList.get(i).setText("<html>Node " + i + "<br/>" + nodeList.get(i).getX() + "," + nodeList.get(i).getY() + "</html>");
        }
        
    }
         
    public static Point calculateLocation(Point pin)
    {
        
        Point p = new Point();
        
        p.setLocation(pin.getX(), pin.getY());
        
        
        return p;

    }
    
    public static void viewEmptySelectionDisplay(String pinName, JPanel panel)
    {
        Component label = panel.getComponent(0);
        JLabel locationLabel = new JLabel();
        
        locationLabel = (JLabel) label;
        
        locationLabel.setText(pinName);
    }
    
    public static void viewPlantDisplay(Plant plant, JPanel panel)
    {
        
        panel.setVisible(true);
        
        Component[] cList = panel.getComponents();
       
        ArrayList<String> listNames = new ArrayList();
        
        JLabel idLabel = (JLabel) cList[0];
        
        
        JLabel strainLabel = (JLabel) cList[1];
        
        JLabel locationLabel = (JLabel) cList[2];
        
        idLabel.setText(String.valueOf(plant.getPlantId()));
        
        strainLabel.setText(plant.toString());
        
        locationLabel.setText("Outside");
        
        
    }
    
    public static void mouseEnteredPin(JLabel label)
    {
        
        Icon icon = new ImageIcon("src/Data/Images/Map/mapPinSelected.png");
        label.setIcon(icon);
        
    }
    
    public static void mouseExitedPin(JLabel label, JLabel currentLabel)
    {
        if(label == currentLabel)
        {
        
        }
        else
        {
            Icon icon = new ImageIcon("src/Data/Images/Map/mapPin.png");
            label.setIcon(icon);
        }
        
        
        
        
        
    }
    
    public static int calculateComponentCount(JPanel panel)
    {
        int numPins = 0;
        int componentListSize = panel.getComponentCount();
        
        
        Component[] compList = panel.getComponents();
        
        
        for(Component c : compList)
        {
            if(c.getName() == null)
            {
                c.setName("Component");
            }
            
            if(c.getName().substring(0,1).equals("S"))
            {
                numPins++;
                
               
            }
            
            
        }
        
        return componentListSize - numPins;
    }
    
    public static void setSpotNames(JPanel mapPanel)
    {
        Component[] compList = mapPanel.getComponents();
        ArrayList<JLabel> spotList = new ArrayList();
        
        for(Component c : compList)
        {
            if(c.getName() == null || c.getName().equals("") || c.getName().equals(" "))
            {
                c.setName("SSS");
            }
            System.out.println(c.getName());
            if(c.getName().substring(0, 2).equals("SP"))
            {
                spotList.add((JLabel) c);
            }
        }
        int counter = 1;
        String newName = "";
        for(int i = 0; i < spotList.size(); i++)
        {            
            newName = "SP" + counter;
            spotList.get(i).setName(newName);
            System.out.println(spotList.get(i).getName());
            counter += 1;
        }
    }
}
