/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.customcomponents.customTests;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jaket
 */
public class JacobDropDownMenu extends JPanel
{
    protected JLabel targetLabel;
    ArrayList<JLabel> labelList;
    protected int itemCount;
    protected int maxItems;
    

    public JacobDropDownMenu(JLabel targetLabel)
    {
        this.targetLabel = targetLabel;
    }
    
    public void menuSetup(ArrayList<String> labelData)
    {
        int xStart = this.targetLabel.getLocation().x;
        int yStart = this.targetLabel.getLocation().y;
        
        int xSize = this.targetLabel.getWidth();
        int ySize = this.targetLabel.getHeight();
        
        int height = 0;
        int width = xSize;
        
        for(String str : labelData)
        {
            height += ySize;
        }
        
        this.setBounds(xStart, yStart, width, height);
        
        int yMod = 0;
        ArrayList<JLabel> labelList = buildLabels(labelData);
        
        for(JLabel label : labelList)
        {
            label.setLocation(0,yMod); 
            yMod += ySize;
            this.add(label);
        }
        
        
        
    }
    
    public ArrayList<JLabel> buildLabels(ArrayList<String> labelData)
    {
        ArrayList<JLabel> labelList = new ArrayList();
        
        return labelList;
    }
    
    
}
