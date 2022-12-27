/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Client.ServerCommands.InventoryCommands;

import Backend.Client.ServerCommand;
import Backend.Inventory.InventoryController;
import Backend.Seed.SeedBankHandler;

/**
 *
 * @author jaket
 */
public class LoadInventory extends ServerCommand
{
    @Override
    public String getCommandName() {
        return super.getCommandName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void runCommand() {
        super.runCommand(); //To change body of generated methods, choose Tools | Templates.
    }

   
    public LoadInventory(String commandName)
    {
        super(commandName);
    }
    
    @Override
    public void runCommand(String[] args)
    {
        System.out.println("Processing LoadInventory command with " + args[1]);
        String inventoryList = args[1];
        
        InventoryController.setupInventory(inventoryList);
        
        
        
    }
}
