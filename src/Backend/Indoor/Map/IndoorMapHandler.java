/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Indoor.Map;

import Backend.Client.Client;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jaket
 */
public class IndoorMapHandler 
{
    public static ArrayList<IndoorMapSlot> loadMapPlantCells(String growRoom)
    {
        
        System.out.println("loading map cells");
        ArrayList<IndoorMapSlot> labelList = new ArrayList<>();
        Client.client.sendMessageToServer("RequestData IndoorMapCells " + growRoom);
        //Client.client.awaitMessageFromServer();
        ArrayList<String> mapCellList = Client.client.getStreamList();
        Client.client.clearStreamList();
        
        for(String s : mapCellList)
        {
            if(s.equalsIgnoreCase("end"))
            {
                break;
            }
            String[] split = s.split(",");
            IndoorMapSlot slot = new IndoorMapSlot(Integer.parseInt(split[0]), split[1], Integer.parseInt(split[2]));
            labelList.add(slot);
            System.out.println("added new slot");
            
        }
        
        
        return labelList;
    }
    
    public static void displayMapCells(ArrayList<JPanel> panelList, ArrayList<IndoorMapSlot> labelList)
    {
        System.out.println("displaying map cells " + labelList.size());
        int xPos = 40;
        int yPos = 30;
        for(IndoorMapSlot slot : labelList)
        {
            slot.setLocation(xPos,yPos);
            panelList.get(slot.getBayIndex()).add(slot);
        }
        
        
        for(int j = 0; j < panelList.size(); j++)
        {
            xPos = 70;
            yPos = -120;
            
            for(int i = 0; i < labelList.size(); i++)
            {
                
                if(labelList.get(i).getBayIndex() == j)
                {
                    System.out.println(labelList.get(i).bayIndex + "," + labelList.get(i).plantId + "," + labelList.get(i).mapIndex);
                    switch(labelList.get(i).getMapIndex() % 4)
                    {
                        
                        case 0:
                            xPos += 160;
                            yPos = 10;
                            
                            break;
                        default:
                            yPos += 130;
                            
                            break;
                        
                    }
                    
                    labelList.get(i).setLocation(xPos, yPos);
                     System.out.println(labelList.get(i).getLocation());
                    labelList.get(i).setVisible(true);
                    labelList.get(i).setText(String.valueOf(labelList.get(i).mapIndex));
                }
            }
        }
        
    }
    
    
    public static void writeMapSlot(String toString)
    {
        Client.client.sendMessageToServer(toString);
        
    }
    
}
