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

    public static void addNewModule(GrowRoom growRoom)
    {
        System.out.println(growRoom.getRoomName() + " is being created now ");
        IndoorGrowModule newModule = new IndoorGrowModule(growRoom);

       
        growModulePanel.add(newModule);

        moduleList.add(newModule);
  
        
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
    
    public static void changeState(boolean stateChange)
    {
        growModulePanel.setEnabled(stateChange);
        growModulePanel.setVisible(stateChange);
        
    }
    
    
    
}
