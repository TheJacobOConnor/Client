/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Indoor.Map;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jaket
 */
public class IndoorMap 
{
    
    protected String growRoomId = "";
    protected ArrayList<IndoorMapSlot> mapLabelList = new ArrayList<>();
    protected int plantCount = -1;
    ArrayList<JPanel> panelList = new ArrayList<>();
    
    public IndoorMap(String growRoomId, ArrayList<JPanel> panelList)
    {
        System.out.println("Building indoor map");
        this.growRoomId = growRoomId;
        mapLabelList = IndoorMapHandler.loadMapPlantCells(growRoomId);
        IndoorMapHandler.displayMapCells(panelList, mapLabelList);
        
    }
    
   

    public ArrayList<JPanel> getPanelList() {
        return panelList;
    }

    public void setPanelList(ArrayList<JPanel> panelList) {
        this.panelList = panelList;
    }
    
}
