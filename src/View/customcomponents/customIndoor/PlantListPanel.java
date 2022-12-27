/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.customcomponents.customIndoor;

import Backend.PlantData.Plant;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import View.customcomponents.customIndoor.IndoorHitboxLabel;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;

/**
 *
 * @author jaket
 */
public class PlantListPanel extends JPanel 
{
    protected Plant plant;
    private int xPos = 11;
    private int yPos = 390;
    private final int panelWidth = 349;
    private final int panelHeight = 45;
    
    private Font nameFont = new Font("bahnschrift", Font.PLAIN, 18);
    private Font idFont = new Font("bahnschrift", Font.PLAIN, 12);
    
    private JLabel hitboxLabel;
    private JLabel nameLabel;
    private JLabel idLabel;
    private JLabel plantIconLabel;
    private JLabel optionsLabel;
    private JLabel hoverLabel;
    
    
    
    
    public PlantListPanel(Plant plant)
    {
        System.out.println("here");
        this.plant = plant;
        this.setBounds(xPos, yPos, panelWidth, panelHeight);
        this.setLayout(null);
        this.setOpaque(false);
        this.buildLabels();
        this.setVisible(true);
        this.setToolTipText("Fuck");
        
        
        
    
        
    }
    
   
    
    
   
    
    public void buildLabels()
    {
        this.hoverLabel = new JLabel();
        this.hoverLabel.setBounds(0, 0, 350, 45);
        Icon hoverIcon = new ImageIcon("src/Data/Images/Indoor/Panels/hoverGraphic.png");
        this.hoverLabel.setIcon(hoverIcon);
        this.hoverLabel.setVisible(false);
        
        
        JLabel indoorHitboxLabel = new IndoorHitboxLabel(this.hoverLabel);
        indoorHitboxLabel.setBounds(0, 0, 350, 45);
        
        
        
        
        this.nameLabel = new JLabel();
        this.nameLabel.setFont(nameFont);
        this.nameLabel.setForeground(Color.WHITE);
        this.nameLabel.setBounds(50, 7, 190, 20);
        this.nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.nameLabel.setText(this.plant.getPlantLabel());
        this.add(nameLabel);
        
        this.idLabel = new JLabel();
        this.idLabel.setFont(idFont);
        this.idLabel.setForeground(Color.WHITE);
        this.idLabel.setBounds(50, 30, 70, 15);
        this.idLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.idLabel.setText("ID: " + this.plant.getPlantId());
        this.add(idLabel);

        this.plantIconLabel = new JLabel();
        Icon plantIcon = new ImageIcon("src/Data/Images/Indoor/Panels/greenPlantIcon.png");
        this.plantIconLabel.setBounds(5, 3, 40, 40);
        this.plantIconLabel.setIcon(plantIcon);
        this.add(plantIconLabel);
        
        this.optionsLabel = new JLabel();
        Icon optionsIcon = new ImageIcon("src/Data/Images/Indoor/Panels/optionsGraphic.png");
        this.optionsLabel.setBounds(320, 7, 15, 30);
        this.optionsLabel.setIcon(optionsIcon);
        this.optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.optionsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(optionsLabel);
        

        
        this.add(indoorHitboxLabel);
        this.add(hoverLabel);
        
        System.out.println(this.plant.getCultivar() + " done");
        
        
        
        
    }
    

}
