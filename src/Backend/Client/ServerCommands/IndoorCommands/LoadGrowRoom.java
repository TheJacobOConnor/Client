/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Client.ServerCommands.IndoorCommands;

import Backend.Client.ServerCommand;
import Backend.GrowRoomData.GrowModuleHandler;
import Backend.GrowRoomData.GrowRoom;
import Backend.Indoor.IndoorHandler;
import View.customcomponents.customGrowRoom.panels.IndoorGrowModule;

/**
 *
 * @author jaket
 */
public class LoadGrowRoom extends ServerCommand
{

    @Override
    public String getCommandName() {
        return super.getCommandName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void runCommand() {
        super.runCommand(); //To change body of generated methods, choose Tools | Templates.
    }

   
    public LoadGrowRoom(String commandName)
    {
        super(commandName);
    }
    
    @Override
    public void runCommand(String[] args)
    {
        System.out.println("Processing LoadGrowRoom command.");
        //String plantList = args[1];
        //IndoorHandler.loadGrowRoom(plantList);
        GrowRoom newRoom = new GrowRoom();
        newRoom.build(args[1]);
        System.out.println(newRoom.getRoomName() + " was built.");
        GrowModuleHandler.addNewModule(newRoom);
        
        
        
    }
}
