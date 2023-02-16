/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.PlantData;

import Backend.Equipment.GrowEquipment.GrowContainer;
import Backend.JacobUtility;

/**
 *
 * @author jaket
 */
public class Plant 
{
    
    
    protected String plantId;
  
    
    protected String plantLabel;
    
    protected String roomId;
    
    protected String cultivar;
    
    protected String plantHeight;
    

    protected String zone;
    
    public Plant()
    {
        
    }
    
    public Plant(String plantId, String plantLabel, String roomId)
    {
        this.plantId = plantId;
        this.plantLabel = plantLabel;
        this.roomId = roomId;
    }
    
    public Plant(String plantId, String plantLabel, String roomId, String zone)
    {
        this.plantId = plantId;
        this.plantLabel = plantLabel;
        this.roomId = roomId;
        this.zone = zone;
    }
    
    public Plant(String plantLabel, String plantId, String cultivar,String zone, String roomId)
    {
        this.plantId = plantId;
        this.plantLabel = plantLabel;
        this.cultivar = cultivar;
        this.roomId = roomId;
        this.zone = zone;
    }

    public String getCultivar() {
        return cultivar;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
  
    
    public String getPlantId() 
    {
        return this.plantId;
    }

    public void setPlantId(String plantId) 
    {
        this.plantId = plantId;
    }

    public String getPlantLabel() {
        return plantLabel;
    }

    public void setPlantLabel(String plantLabel) {
        this.plantLabel = plantLabel;
    }

    public String getPlantHeight() {
        return plantHeight;
    }

    public void setPlantHeight(String plantHeight) {
        this.plantHeight = plantHeight;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }



    
    


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
}
