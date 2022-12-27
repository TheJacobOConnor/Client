/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Equipment.GrowEquipment;

import Backend.Equipment.Equipment;
import Backend.GeneralJacobData.MeasurementSelection;

/**
 *
 * @author jaket
 */
public class GrowContainer extends Equipment
{

    
    private final MeasurementSelection measurement;
    private double containerSize;
        
    private String growMedia;
    
    
    
 
    
    
    public GrowContainer(String itemName, int itemId, double containerSize, MeasurementSelection measurement,  String growMedia)
    {
        super(itemId, itemName);
        this.containerSize = containerSize;
        this.measurement = measurement;
        this.growMedia = growMedia;
      
        
        
    }
    
    public GrowContainer(String itemName, int itemId, double containerSize, MeasurementSelection measurement)
    {
        super(itemId, itemName);
        this.containerSize = containerSize;
        this.measurement = measurement;
    }

    public MeasurementSelection getMeasurement() {
        return measurement;
    }

    public void setContainerSize(double newSize){
        this.containerSize = newSize;
    }
    
    public double getContainerSize() {
        return containerSize;
    }

    public String getGrowMedia() {
        return growMedia;
    }

    public void setGrowMedia(String growMedia) {
        this.growMedia = growMedia;
    }

}