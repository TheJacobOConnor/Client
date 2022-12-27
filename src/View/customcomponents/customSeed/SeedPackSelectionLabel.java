/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.customcomponents.customSeed;

import Backend.Indoor.IndoorHandler;
import Backend.Seed.SeedBankHandler;
import Backend.Seed.SeedCollection;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import javax.swing.JLabel;
import static javax.swing.SwingConstants.CENTER;

/**
 *
 * @author jaket
 */
public class SeedPackSelectionLabel extends JLabel
{
    transient private Color textColor;
    transient private Color backgroundColor;
    protected String labelText;
    protected Cursor cursor;
    protected int index;
    protected String packId;
    
    public SeedPackSelectionLabel(String labelText, int index, String packId)
    {
        this.index = index;
        this.packId = packId;
        
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
        setSize(225,50);
        Font font = new Font("Arial", Font.BOLD, 18);
        this.setFont(font);
        textColor = Color.black;
        backgroundColor = Color.white;
        this.setForeground(textColor);
        this.setBackground(backgroundColor);
        this.labelText = labelText;
        this.setHorizontalAlignment(CENTER);
        this.setOpaque(true);
        System.out.println("made it here");
        
        
    
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }
    
    public void entered()
    {
        this.setText("Pack ID: " + packId);
        cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        setCursor(cursor);
    }
    
    public void exited()
    {
        this.setText(labelText);
        cursor = Cursor.getDefaultCursor();
        setCursor(cursor);
    }
    
    public void pressed()
    {
        SeedBankHandler.displaySelectedPack(index);
        
    }
}
