/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Client;

import Backend.Client.ServerCommands.IndoorCommands.LoadGrowRoom;
import Backend.Client.ServerCommands.IndoorCommands.LoadGrowRoomModule;
import Backend.Client.ServerCommands.IndoorCommands.UpdateActivePlantList;
import Backend.Client.ServerCommands.InventoryCommands.LoadInventory;
import Backend.Client.ServerCommands.SeedBankCommands.LoadSeedPacks;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author jaket
 */
public final class ServerMessageHandler 
{
    protected ArrayList<ServerCommand> serverCommandList = new ArrayList<>();
    
    public ServerMessageHandler() throws ClassNotFoundException, NoSuchMethodException
    {
        this.serverCommandList = loadServerCommands();
    }
    
  
    
    public void processCommand(String[] commandMessage)
    {
        
        
        System.out.println("command message: " + commandMessage);
        
        for(ServerCommand command : serverCommandList)
        {
            System.out.println("HEre at process command");
            if(command.getCommandName().equalsIgnoreCase(commandMessage[0]))
            {
                System.out.println("The command is: " + command.getCommandName());
                command.runCommand(commandMessage);
                break;
            }
        }
    }
    
    public ArrayList<ServerCommand> loadServerCommands() throws ClassNotFoundException, NoSuchMethodException
    {
        //ArrayList<ServerCommand> commandList = new ArrayList<>();
        
        
        ArrayList<ServerCommand> commandList = new ArrayList<>();
        
        LoadGrowRoomModule loadGrowRoomModule = new LoadGrowRoomModule("LoadGrowRoomModule");
        LoadSeedPacks loadSeedPacks = new LoadSeedPacks("LoadSeedPacks");
        LoadInventory loadInventory = new LoadInventory("LoadInventory");
        LoadGrowRoom loadGrowRoom = new LoadGrowRoom("LoadGrowRoom");
        UpdateActivePlantList uapl = new UpdateActivePlantList("UpdateActivePlantList");
        
        commandList.add(loadGrowRoomModule);
        commandList.add(loadSeedPacks);
        commandList.add(loadInventory);
        commandList.add(loadGrowRoom);
        commandList.add(uapl);
        
        
        /*Path filePath = Paths.get("src/Backend/Client/ServerCommands/serverCommandList.txt");
        File theFile = filePath.toFile();
        
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(theFile)))
        {
            String currentLine = bufferedReader.readLine();
            while(currentLine != null)
            {
                String[] commandSplit = currentLine.split(",");
                
                String commandName = commandSplit[0];
                String commandPath = commandSplit[1];
                
                ServerCommand newCommand = new ServerCommand(commandName);
                newCommand = newCommand.createCommand(commandName, commandPath);
                
                System.out.println(commandName + " loaded.");
                
                if(newCommand != null)
                {
                    commandList.add(newCommand);
                }
                
                //Object newCommand = Class.forName(commandName).newInstance();
                /*try
                {
                    Class<?> c = Class.forName(commandPath);
                    Constructor<?> cons = c.getConstructor(String.class);
                    Object newCommand = cons.newInstance(commandName);

                    //commandList.add((ServerCommand)newCommand);
                    commandList.add((ServerCommand)newCommand);
                }
                
                currentLine = bufferedReader.readLine();
            }
        }
        
        catch(IOException e)
        {
            e.printStackTrace();
        }*/
        
        return commandList;
    }
    
    
    
    
}
