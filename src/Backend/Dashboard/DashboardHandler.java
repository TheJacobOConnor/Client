/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Dashboard;

import Backend.GrowRoomData.GrowRoom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jakeo
 */
public class DashboardHandler 
{
    
    
    static String numRegex   = ".*[0-9].*";
    static String alphaRegex = ".*[A-Z].*";
    static String multiRegex = ".*([a-zA-Z].*[0-9]|[0-9].*[a-zA-Z]).*";
    
    
    
    public static void dashboardStartup(ArrayList<JPanel> panelList, ArrayList<JLabel> iconList, ArrayList<JLabel> sideLabelList)
    {
        selectPanel(panelList, panelList.get(0));
        
        for(JLabel label : sideLabelList)
        {
            label.setVisible(false);
        }
        
        updateNavBarSelection(iconList, sideLabelList, panelList, 0);
    }
    
    public static void navBarEnter(JLabel iconLabel)
    {
        iconLabel.setBackground(new Color(74,82,95));
        iconLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        
        
        
        
    }
    
    public static void navBarExit(JLabel iconLabel)
    {
        
        
        if(iconLabel.getName() == null)
        {
            iconLabel.setBackground(new Color(55,62,72));
        }
        
        else
        {
            iconLabel.setBackground(new Color(33,38,44));
        }
        
        
        
    }
    
    public static void updateNavBarSelection(ArrayList<JLabel> labelList, ArrayList<JLabel> sideLabelList, ArrayList<JPanel> panelList, int index)
    {
        ArrayList<String> labelNameList = new ArrayList();
        labelNameList = getIconList();
        
        for(int i = 0; i < labelList.size(); i++)
        {
            labelList.get(i).setBackground(new Color(55,62,72));
            //labelList.get(i).setOpaque(false);
            labelList.get(i).setIcon(DashboardHandler.getNavIcon(labelNameList.get(i), false));
            
            sideLabelList.get(i).setVisible(false);
        }
        
        
        
        
        labelList.get(index).setBackground(new Color(74,82,95));
        labelList.get(index).setIcon(DashboardHandler.getNavIcon(labelNameList.get(index), true));
        sideLabelList.get(index).setVisible(true);
        
        selectPanel(panelList, panelList.get(index));
    }
    
    public static void selectPanel(ArrayList<JPanel> panelList, JPanel panel)
    {
        for(JPanel p : panelList)
        {
            p.setVisible(false);
        }
        
        panel.setVisible(true);
        
        
    }
    
    
    public static Icon getNavIcon(String iconName, boolean extension)
    {
        String selected = "";
        if(extension)
        {
            selected = "Selected";
        }
        
        String iconPath = "src/Data/Images/UI Icons/Navigation/" + iconName + "Icon" + selected + ".png";
        Icon icon = new ImageIcon(iconPath);
        return icon;
    }
    
    public static ArrayList<String> getIconList()
    {
        ArrayList<String> iconList = new ArrayList();
        
        iconList.add("home");
        iconList.add("bank");
        iconList.add("inventory");
        iconList.add("indoor");
        iconList.add("outdoor");
        iconList.add("seedbank");
        iconList.add("phenotypes");
        iconList.add("clones");
        
        return iconList;
    }
    
    public static void getPanelComponents(JPanel panel)
    {
        Component[] components;
        String componentName;
        
        components = panel.getComponents();
        
        for(Component c : components)
        {
            componentName = c.getClass().getName();
            System.out.println(c.getName());
        }
        
    }
    
   /* public static ArrayList<Phenotype> phenoSearch(String search)
    {
        ArrayList<Phenotype> phenoReturnList = new ArrayList();
        ArrayList<Phenotype> phenoList = new ArrayList<>();
        
        String searchFormat = search.replaceAll(" ", "");
        
        System.out.println("im at the pheno serach");
        
        System.out.println(search.matches("[a-zA-Z]+"));
        try
        {
            if(searchFormat.matches(numRegex) && !searchFormat.matches(alphaRegex) && searchFormat.length() <= 4 && searchFormat.length() >= 3)
            {
                
                for(Phenotype pheno : phenoList)
                {
                    
                    if( String.valueOf(pheno.getPlantId()).equals(search))
                    {
                        
                        phenoReturnList.add(pheno);
                        break;
                    }
                }
            }

            else if(searchFormat.matches(multiRegex) && searchFormat.length() < 6)
            {
                for(Phenotype pheno : phenoList)
                {
                    /*if(pheno.getLocation().equals(search))
                    {
                        phenoReturnList.add(pheno);
                    }
                }
            }

            else if(searchFormat.matches(multiRegex) || searchFormat.matches(alphaRegex))
            {

                for(Phenotype pheno : phenoList)
                {
                    if(pheno.getSeed().getCultivar().equals(search))
                    {
                        phenoReturnList.add(pheno);
                    }
                }

            }

            else
            {
                phenoReturnList.add(null);

                System.out.println("Incorrect");

            }
        }
        
        catch(NullPointerException e)
        {
            System.out.println(e);
        }
        
       
        return phenoReturnList;
    }
    
 
    public static void resetPhenoSearchFields(ArrayList<ArrayList<JLabel>> labelLists)
    {
        
        ArrayList<JLabel> idList = labelLists.get(0);
        ArrayList<JLabel> strainList = labelLists.get(1);
        ArrayList<JLabel> locationList = labelLists.get(2);
        
        for(int i = 0; i < 10; i++)
        {
            idList.get(i).setText("");
            strainList.get(i).setText("");
            locationList.get(i).setText("");
        }
    }
    
    public static void searchDisplay(ArrayList<Phenotype> phenoList, ArrayList<ArrayList<JLabel>> labelLists)
    {
        ArrayList<JLabel> idList = labelLists.get(0);
        ArrayList<JLabel> strainList = labelLists.get(1);
        ArrayList<JLabel> locationList = labelLists.get(2);
        
        for(int i = 0; i < phenoList.size() && i < 12; i++)
        {
            idList.get(i).setText(String.valueOf(phenoList.get(i).getPlantId()));
            strainList.get(i).setText(phenoList.get(i).getSeed().getCultivar());
            //locationList.get(i).setText(GrowRoom.getGrowRoom(phenoList.get(i).getLocation()));
        }
    
    }
    
    public static void searchDisplay(Phenotype pheno, ArrayList<ArrayList<JLabel>> labelLists)
    {
        ArrayList<JLabel> idList = labelLists.get(0);
        ArrayList<JLabel> strainList = labelLists.get(1);
        ArrayList<JLabel> locationList = labelLists.get(2);
        
        
        idList.get(0).setText(String.valueOf(pheno.getPlantId()));
        strainList.get(0).setText(pheno.getSeed().getCultivar());
        //locationList.get(0).setText(GrowRoom.getGrowRoom(pheno.getLocation()));
        
    
    }
    
    
    */
    
    
    
    
    
    
    
    
}
