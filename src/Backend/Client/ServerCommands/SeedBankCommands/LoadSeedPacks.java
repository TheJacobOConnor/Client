/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Client.ServerCommands.SeedBankCommands;

import Backend.Client.ServerCommand;
import Backend.Seed.SeedBankHandler;

/**
 *
 * @author jaket
 */
public class LoadSeedPacks extends ServerCommand
{
    @Override
    public String getCommandName() {
        return super.getCommandName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void runCommand() {
        super.runCommand(); //To change body of generated methods, choose Tools | Templates.
    }

   
    public LoadSeedPacks(String commandName)
    {
        super(commandName);
    }
    
    @Override
    public void runCommand(String[] args)
    {
        System.out.println("Processing LoadSeedpacks command with " + args[1]);
        String seedPackList = args[1];
        
        SeedBankHandler.loadSeedCollection(SeedBankHandler.getSeedDataList(seedPackList));
        
        
    }
            
}
