/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.MyTools;

import Backend.Client.Client;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaket
 */
public class ReadDataThread extends Thread
{
    Client client;
    
    public ReadDataThread(Client client)
    {
        this.client = client;
    }
    
    @Override
    public void run() 
    {
        while(true)
        {
            try 
            {
            System.out.println(client.getStreamList());
            System.out.println(client.getStreamList().size());
            
            Thread.sleep(6000);
            } 
            catch (InterruptedException ex)
            {
                Logger.getLogger(ReadDataThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
