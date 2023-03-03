/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.GrowRoomData;

import Backend.PlantData.Plant;
import java.util.ArrayList;

/**
 *
 * @author jaket
 */
public class GrowRoom 
{
    
    protected String roomName;
    protected String roomId;
    protected String roomTemp;
    protected String roomRH;
    protected String roomCo2;
    protected String lightSchedule;
    protected String roomStage;
    
    public GrowRoom()
    {
    
    }
    
    public void build(String args)
    {
        String[] list = args.split(",");
        for(String str : list)
        {
            System.out.println(str);
        }
        this.setRoomName(list[0]);
        this.setRoomId(list[1]);
        this.setRoomTemp(list[2]);
        this.setRoomRH(list[3]);
        this.setRoomCo2(list[4]);
        this.setLightSchedule(list[5]);
        this.setRoomStage(list[6]);
        
    }
  
    
    
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomTemp() {
        return roomTemp;
    }

    public void setRoomTemp(String roomTemp) {
        this.roomTemp = roomTemp;
    }

    public String getRoomRH() {
        return roomRH;
    }

    public void setRoomRH(String roomRH) {
        this.roomRH = roomRH;
    }

    public String getRoomCo2() {
        return roomCo2;
    }

    public void setRoomCo2(String roomCo2) {
        this.roomCo2 = roomCo2;
    }

    public String getLightSchedule() {
        return lightSchedule;
    }

    public void setLightSchedule(String lightSchedule) {
        this.lightSchedule = lightSchedule;
    }

    public String getRoomStage() {
        return roomStage;
    }

    public void setRoomStage(String roomStage) {
        this.roomStage = roomStage;
    }


    
}

  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*public GrowRoom(String roomId)
    {
        this.roomId = roomId;
        
        
    }
    
    public GrowRoomController getGrowRoomController()
    {
        return growRoomController;
    }
    
    
    
  /*  protected String roomName;
    GrowState roomGrowState;
    
    
    
    //Dimensions
    protected final int roomWidth;
    protected final int roomLength;
    protected final int roomHeight;
    //Dimensions
    
    
    public GrowRoom(int roomDimensions[])
    {
        this.roomLength = roomDimensions[0];
        this.roomWidth = roomDimensions[1];
        this.roomHeight = roomDimensions[2];
    }
    
    
    
    
    
    
    
    
    public static String getGrowRoom(GrowRoomLocation location)
    {
        
        String returnString = "Invalid Location";
        
        switch(location)
        {
            case VEGTENT4X8:
                returnString = "Veg Tent 4x8";
                
            case PROPOGATIONTENTA2X2:
                returnString = "Propogation Tent A 2x2";
                
            case PROPOGATIONTENTB2X2:
                returnString = "Propogation Tent B 2x2";
                
            case GROWTENT2X4:
                returnString = "Grow Tent 2x4";
                
            case FLOWERROOM:
                returnString = "Flower Room";
            
            case GROWRACK4X3:
                returnString = "Grow Rack 4x3";
                
            case UTBARN4X4:
                returnString = "Uncle Timmy's Barn 4x4";
            
            case UTBARN4X8:
                returnString = "Uncle Timmy's Barn 4x8";
                
            case SPIDERFARMER5X5:
                returnString = "Spider Farmer 5x5";
        }
        
        return returnString;
    }
    */

