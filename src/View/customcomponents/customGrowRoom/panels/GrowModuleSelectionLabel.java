/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.customcomponents.customGrowRoom.panels;

import Backend.GrowRoomData.GrowRoomController;
import Backend.Indoor.IndoorPlantListHandler;
import View.customcomponents.customMisc.SelectionLabelMisc;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class GrowModuleSelectionLabel extends SelectionLabelMisc {

    protected String roomid;
    public GrowModuleSelectionLabel(String roomId, int width, int height) {
        super(width, height);
        this.roomid = roomId;
    
        addMouseListener(new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            GrowRoomController.requestGrowRoom("RequestData:GrowRoom:" + roomId);
            IndoorPlantListHandler.requestPlantList(roomid);
        }
        });
    }

    
    
}