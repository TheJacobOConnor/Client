/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Client;

import java.io.BufferedWriter;


public interface ServerCommandInterface 
{
    public void runCommand(String[] args);
    public void runCommand();
    public String getCommandName();
}
