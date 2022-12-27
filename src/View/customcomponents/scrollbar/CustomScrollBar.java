/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.customcomponents.scrollbar;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

/**
 *
 * @author jaket
 */
public class CustomScrollBar extends JScrollBar
{
    
    public CustomScrollBar()
    {
        setUI(new ModernScrollbarUI());
        setPreferredSize(new Dimension(16,8));
        setForeground(new Color(48,144,216));
        setBackground(Color.WHITE);
    }
}
