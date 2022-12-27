/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Client;

import Backend.Client.ServerCommands.IndoorCommands.LoadGrowRoom;
import Backend.Client.ServerCommands.InventoryCommands.LoadInventory;
import Backend.Client.ServerCommands.SeedBankCommands.LoadSeedPacks;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    
    public ServerMessageHandler()
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
    
    public ArrayList<ServerCommand> loadServerCommands() 
    {
        ArrayList<ServerCommand> commandList = new ArrayList<>();
        LoadGrowRoom loadGrowRoom = new LoadGrowRoom("LoadGrowRoom");
        LoadSeedPacks loadSeedPacks = new LoadSeedPacks("LoadSeedPacks");
        LoadInventory loadInventory = new LoadInventory("LoadInventory");
        commandList.add(loadGrowRoom);
        commandList.add(loadSeedPacks);
        commandList.add(loadInventory);
        
        
        /*Path filePath = Paths.get("src/Backend/Client/ServerCommands/serverCommandList.txt");
        File theFile = filePath.toFile();
        
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(theFile)))
        {
            String currentLine = bufferedReader.readLine();
            while(currentLine != null)
            {
                String commandName = currentLine;
                
                System.out.println(commandName + " loaded.");
                
                Object newCommand = Class.forName(commandName).newInstance();
                
                
                commandList.add((ServerCommand)newCommand);
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
