/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Client.ServerCommands.IndoorCommands;

import Backend.Client.ServerCommand;
import Backend.GrowRoomData.GrowRoom;
import Backend.GrowRoomData.GrowRoomController;
import Backend.Indoor.IndoorPlantListHandler;
import Backend.PlantData.Plant;
import java.util.ArrayList;

/**
 *
 * @author jaket
 */
public class UpdateActivePlantList extends ServerCommand
{
    
    public UpdateActivePlantList(String commandName) 
    {
        super(commandName);
    }
    
    @Override
    public String getCommandName()
    {
        return super.getCommandName();
    }

    @Override
    public void runCommand()
    {
        super.runCommand();
    }
    
    @Override
    public void runCommand(String[] args)
    {
        for(String str : args)
        {
            System.out.println(str);
        }
        System.out.println("Processing LoadActivePlantList command." + args[1]);
        ArrayList<Plant> plantList = buildPlantList(args[1]);
        GrowRoomController.setActivePlantList(plantList);
        IndoorPlantListHandler.updateListPanel();

        
        
    }
    
    private ArrayList<Plant> buildPlantList(String args)
    {
        ArrayList<Plant> plantList = new ArrayList();
        System.out.println("Args list: " + args);
        String argList[] = args.split("-");
        for(String plant : argList)
        {
            System.out.println("Args list: " + plant);
            String[] plantStr = plant.split(",");
            String plantId = plantStr[0];
            String plantLabel = plantStr[1];
            String growRoomId = plantStr[2];
            String zone = plantStr[3];
            
            plantList.add(new Plant(plantId, plantLabel, growRoomId, zone));
        }
        
        return plantList;
    }
    
}