/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.customcomponents.customGrowRoom.panels;

import Backend.GrowRoomData.GrowRoom;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author jaket
 */
public class IndoorGrowModule extends JPanel
{
    
    protected GrowRoom growRoom;
    private final int panelWidth = 510;
    private final int panelHeight = 205;
    transient private Color textColor;
    
    public IndoorGrowModule(GrowRoom growRoom)
    {
        this.growRoom = growRoom;
        System.out.println("Begun building indoorgrowmodule object");
        
        this.setBounds(10, 10, 510, 205);
        this.setLayout(null); 
        this.setOpaque(false);
        this.setupLabels();
        this.setVisible(true);
        
        
       
    }
    
    public void setupLabels()
    {
        System.out.println("Setting up labels " + this.growRoom.getRoomName());   
        Font font = new Font("Banschrift", Font.BOLD, 12);
        Font nameLabelFont = new Font("Banschrift", Font.BOLD, 13);
        Font biggerFont = new Font("Banschrift", Font.BOLD, 17);
        Font largeFont = new Font("Banschrift", Font.PLAIN, 24);
        textColor = Color.white;
        
        GrowModuleSelectionLabel selectLabel = new GrowModuleSelectionLabel(this.growRoom.getRoomId(), 300, 145);
        selectLabel.setBounds(380, 130, 120, 60);     
        this.add(selectLabel);
        
        JLabel weekLabel = new JLabel();
        weekLabel.setFont(font);
        weekLabel.setText("4");
        weekLabel.setForeground(textColor);
        weekLabel.setOpaque(false);
        weekLabel.setBounds(246, 60, 40, 15);
        this.add(weekLabel);
        
        JLabel dayLabel = new JLabel();
        dayLabel.setBounds(237, 74, 40, 15);
        dayLabel.setFont(font);
        dayLabel.setText("25");
        dayLabel.setForeground(textColor);
        dayLabel.setOpaque(false);
        this.add(dayLabel);
        
        
        JLabel roomNameLabel = new JLabel();
        roomNameLabel.setFont(nameLabelFont);
        roomNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roomNameLabel.setText(this.growRoom.getRoomName());
        roomNameLabel.setForeground(textColor);
        roomNameLabel.setOpaque(false);
        roomNameLabel.setBounds(388, 20, 105, 30);
        this.add(roomNameLabel);
        
        JLabel roomIdLabel = new JLabel();
        roomIdLabel.setFont(nameLabelFont);
        roomIdLabel.setText(this.growRoom.getRoomId());
        roomIdLabel.setForeground(textColor);
        roomIdLabel.setHorizontalAlignment(SwingConstants.LEFT);
        roomIdLabel.setOpaque(false);
        roomIdLabel.setBounds(455, 65, 40, 20);
        this.add(roomIdLabel);
        
        JLabel roomRHLabel = new JLabel();
        roomRHLabel.setFont(largeFont);
        roomRHLabel.setText(this.growRoom.getRoomRH() + "%");
        roomRHLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roomRHLabel.setForeground(textColor);
        roomRHLabel.setOpaque(false);
        roomRHLabel.setBounds(195, 135, 80, 40);
        this.add(roomRHLabel);
        
        JLabel roomTempLabel = new JLabel();
        roomTempLabel.setFont(largeFont);
        //roomTempLabel.setText(this.growRoom.getRoomTemp());
        roomTempLabel.setText("25" + "°F");
        roomTempLabel.setForeground(textColor);
        roomTempLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roomTempLabel.setOpaque(false);
        roomTempLabel.setBounds(300, 145, 60, 20);
        this.add(roomTempLabel);
    
        
        JLabel plantCountLabel = new JLabel();
        plantCountLabel.setFont(biggerFont);
        plantCountLabel.setText(this.growRoom.getRoomPlantCount());
        plantCountLabel.setForeground(textColor);
        plantCountLabel.setHorizontalAlignment(SwingConstants.LEFT);
        plantCountLabel.setBounds(320, 75, 40, 20);
        this.add(plantCountLabel);
       
        
          
        JLabel backgroundLabel = new JLabel();
        Icon backgroundIcon = new ImageIcon("src/Data/Images/Indoor/GrowModules/DemoModule.png");
        backgroundLabel.setBounds(0, 0, panelWidth, panelHeight);
        backgroundLabel.setIcon(backgroundIcon);
        backgroundLabel.setOpaque(false);
        this.add(backgroundLabel);
        
        
    }
    
}
