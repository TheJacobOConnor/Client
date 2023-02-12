/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Client;

import java.io.BufferedWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
    
    public ServerCommand createCommand(String commandName, String classPath)
    {
        try 
        {
            Class<?> commandClass = Class.forName(classPath);
            Constructor<?> cons = commandClass.getConstructor(ServerCommand.class);
            Object command = cons.newInstance(commandName);
            return (ServerCommand)command;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    
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
