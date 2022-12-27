/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.GrowRoomData;

import View.customcomponents.customGrowRoom.panels.IndoorGrowModule;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author jaket
 */
public class GrowModuleHandler
{
    protected static JPanel growModulePanel;
    protected static ArrayList<JPanel> moduleList = new ArrayList<>();

    public static void addNewModule(GrowRoom newRoom)
    {
        System.out.println(newRoom.getRoomName() + " is being created now ");
        IndoorGrowModule newModule = new IndoorGrowModule(newRoom);
        IndoorGrowModule newModule2 = new IndoorGrowModule(newRoom);
        IndoorGrowModule newModule3 = new IndoorGrowModule(newRoom);
        IndoorGrowModule newModule4 = new IndoorGrowModule(newRoom);
        IndoorGrowModule newModule5 = new IndoorGrowModule(newRoom);
        IndoorGrowModule newModule6 = new IndoorGrowModule(newRoom);
        IndoorGrowModule newModule7 = new IndoorGrowModule(newRoom);
        IndoorGrowModule newModule8 = new IndoorGrowModule(newRoom);
       
        growModulePanel.add(newModule);
        growModulePanel.add(newModule2);
        growModulePanel.add(newModule3);
        growModulePanel.add(newModule4);
        growModulePanel.add(newModule5);
        growModulePanel.add(newModule6);
        growModulePanel.add(newModule7);
        growModulePanel.add(newModule8);
        moduleList.add(newModule);
        moduleList.add(newModule2);
        moduleList.add(newModule3);
        moduleList.add(newModule4);
        moduleList.add(newModule5);
        moduleList.add(newModule6);
        moduleList.add(newModule7);
        moduleList.add(newModule8);
        
        updateModuleGraphics();
 
        
    }
    
    private static void updateModuleGraphics()
    {
        int x = 30;

        int width = 510;
        int height = 205;
        int xMod = 600;
        int yMod = 15;
        
        System.out.println("Module List size: " + moduleList.size());
        for (int i = 1; i <= moduleList.size(); i++)
        {
         
            if(i % 2 == 0)
            {  
                moduleList.get(i - 1).setBounds(xMod, yMod, width, height);
                yMod += 225;
            }
            else
            {
                moduleList.get(i - 1).setBounds(x, yMod, width, height);
                
            }
            
            System.out.println(moduleList.get(i - 1).getLocation());
        }
        
        growModulePanel.repaint();
    }
    
    
    public static JPanel getGrowModulePanel() 
    {
        return growModulePanel;
    }

    public static void setGrowModulePanel(JPanel growModulePanel)
    {
        GrowModuleHandler.growModulePanel = growModulePanel;
    }
    
    
    
    
    
}
