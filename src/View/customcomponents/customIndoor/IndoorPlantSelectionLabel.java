/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.customcomponents.customIndoor;

import Backend.Indoor.IndoorHandler;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.JLabel;

/**
 *
 * @author jaket
 */
public class IndoorPlantSelectionLabel extends JLabel implements Serializable
{
    transient private Color textColor;
    transient private Color backgroundColor;
    protected String labelText;
    protected Cursor cursor;
    protected int index;
    
    public IndoorPlantSelectionLabel(String labelText, int index)
    {
        this.index = index;
        
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
        setSize(160,50);
        textColor = Color.black;
        backgroundColor = Color.white;
        this.setForeground(textColor);
        this.setBackground(backgroundColor);
        this.labelText = labelText;
        this.setHorizontalAlignment(CENTER);
        this.setOpaque(true);
        
        
    
    }
    
    public void entered()
    {
        cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        setCursor(cursor);
    }
    
    public void exited()
    {
        cursor = Cursor.getDefaultCursor();
        setCursor(cursor);
    }
    
    public void pressed()
    {
        //IndoorHandler.growRoom.getGrowRoomController().updatePlantDetailsPanelByIndex(this.index);
        
        
    }
}
