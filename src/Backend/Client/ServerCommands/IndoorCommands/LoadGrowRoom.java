/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Client.ServerCommands.IndoorCommands;

import Backend.Client.ServerCommand;
import Backend.GrowRoomData.GrowModuleHandler;
import Backend.GrowRoomData.GrowRoom;
import Backend.GrowRoomData.GrowRoomController;

/**
 *
 * @author jaket
 */
public class LoadGrowRoom extends ServerCommand
{
    
    public LoadGrowRoom(String commandName) 
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
        System.out.println("Processing LoadGrowRoom command. " + args[1]);
        //String plantList = args[1];
        //IndoorHandler.loadGrowRoom(plantList);
        GrowRoom newRoom = new GrowRoom();
        newRoom.build(args[1]);
        System.out.println(newRoom.getRoomName() + " was built.");
        GrowRoomController.setActiveGrowRoom(newRoom);
        
        
        
    }
    
}
