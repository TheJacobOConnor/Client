/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Indoor.Map;

import Backend.Indoor.IndoorHandler;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import static javax.swing.SwingConstants.CENTER;

/**
 *
 * @author jaket
 */
public class IndoorMapSlot extends JLabel
{
    transient private Color textColor;
    transient private Color backgroundColor;
    protected Cursor cursor;
    
    
    protected String plantId = "";
    protected int bayIndex;
    protected int mapIndex = -1;
    
    
    public IndoorMapSlot(int bayIndex, String plantId, int index)
    {
        this.bayIndex = bayIndex;
        this.mapIndex = index;
        this.plantId = plantId;
        Font font = new Font("Verdana", Font.BOLD, 24);
        
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent me)
            {
                pressed();
            }
            
            @Override
            public void mouseEntered(MouseEvent e)
            {
                entered();
            }
            
            public void mouseExited(MouseEvent e)
            {
                exited();
            }
        });
        setSize(100,100);
        textColor = Color.black;
        backgroundColor = Color.white;
        this.setForeground(textColor);
        this.setBackground(backgroundColor);
        this.setHorizontalAlignment(CENTER);
        this.setOpaque(true);
        this.setFont(font);
        
    }

    public String getPlantId() {
        return plantId;
    }

    public int getBayIndex() {
        return bayIndex;
    }

    public void setBayIndex(int bayIndex) {
        this.bayIndex = bayIndex;
    }

    public int getMapIndex() {
        return mapIndex;
    }

    public void setMapIndex(int mapIndex) {
        this.mapIndex = mapIndex;
    }
    
      public void entered()
    {
        cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        this.setText(plantId);
        setCursor(cursor);
    }
    
    public void exited()
    {
        cursor = Cursor.getDefaultCursor();
        setCursor(cursor);
        this.setText(String.valueOf(mapIndex));
    }
    
    public void pressed()
    {
        
        //IndoorHandler.growRoom.getGrowRoomController().updatePlantDetailsPanelByPlant(IndoorHandler.growRoom.getGrowRoomController().getPlantById(this.plantId));
        
        
    }
    
    public String getToString()
    {
        return String.valueOf(this.bayIndex) + "," + this.plantId + "," + String.valueOf(this.mapIndex);
    }
}
