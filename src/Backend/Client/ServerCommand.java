/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Client;

import java.io.BufferedWriter;

/**
 *
 * @author jaket
 */
public class ServerCommand implements ServerCommandInterface
{
    private String commandName;
    
    public ServerCommand(String commandName)
    {
        this.commandName = commandName;
    }

    @Override
    public void runCommand(String[] args)
    {
       System.out.println("you accessed the parents run cmd");
    }
    
    
    
    
    @Override
    public void runCommand()
    {
      
    }

    @Override
    public String getCommandName() {
        return this.commandName;
    }

}
