/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.PlantData;

import javax.swing.JTextField;

/**
 *
 * @author jaket
 */
public class PlantCreationHandler
{
    protected static String cultivarName;
    protected static String plantNumber;
    protected static String growId;
    protected static String plantStage;
    
    protected static JTextField cultivarField;
    protected static JTextField plantNumberField;
    protected static JTextField growIdField;
    
    public static String createPlantString()
    {
        String returnStr = "";
        boolean single = true;
        cultivarName = cultivarField.getText();
        plantNumber = plantNumberField.getText();
        growId = growIdField.getText();
        
        
        if(plantNumber.contains(","))
            single = false;
        
        if(!single)
        {
            String[] args = plantNumber.split(",");
            int min = Integer.parseInt(args[0]);
            int max = Integer.parseInt(args[1]);
            
            for(int i = min; i <= max; i++)
            {
                returnStr += cultivarName + "," + i + "," + growId + "," + plantStage + "-";
            }
        }
        else
        {
            returnStr += cultivarName + "," + plantNumber + "," + growId + "," + plantStage + "-";
        }
        
        
        return returnStr;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static String getCultivarName() {
        return cultivarName;
    }

    public static void setCultivarName(String cultivarName) {
        PlantCreationHandler.cultivarName = cultivarName;
    }

    public static String getPlantNumber() {
        return plantNumber;
    }

    public static void setPlantNumber(String plantNumber) {
        PlantCreationHandler.plantNumber = plantNumber;
    }

    public static String getGrowId() {
        return growId;
    }

    public static void setGrowId(String growId) {
        PlantCreationHandler.growId = growId;
    }

    public static String getPlantStage() {
        return plantStage;
    }

    public static void setPlantStage(String plantStage) {
        PlantCreationHandler.plantStage = plantStage;
    }

    public static JTextField getCultivarField() {
        return cultivarField;
    }

    public static void setCultivarField(JTextField cultivarField) {
        PlantCreationHandler.cultivarField = cultivarField;
    }

    public static JTextField getPlantNumberField() {
        return plantNumberField;
    }

    public static void setPlantNumberField(JTextField plantNumberField) {
        PlantCreationHandler.plantNumberField = plantNumberField;
    }

    public static JTextField getGrowIdField() {
        return growIdField;
    }

    public static void setGrowIdField(JTextField growIdField) {
        PlantCreationHandler.growIdField = growIdField;
    }
    
    
    
    
    
}
