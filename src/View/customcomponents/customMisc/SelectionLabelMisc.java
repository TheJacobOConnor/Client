/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package View.customcomponents.customMisc;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class SelectionLabelMisc extends JLabel {
    private int width, height;

    public SelectionLabelMisc() 
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Label clicked!");
            }
        });
    }

    public SelectionLabelMisc(int width, int height) {
        this();
        setPreferredSize(new Dimension(width, height));
    }

    public void setLabelSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        
        
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}