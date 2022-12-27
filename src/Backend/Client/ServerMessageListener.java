/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Client;

import Backend.Client.Client;
import Backend.Client.ServerMessageHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaket
 */
public class ServerMessageListener extends Thread
{
    
    protected final Client client;
    protected final ServerMessageHandler serverMessageHandler;
    
    public ServerMessageListener(Client client, ServerMessageHandler serverMessageHandler)
    {
        this.client = client;
        this.serverMessageHandler = serverMessageHandler;
    }
    
    public void listen()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(client.getSocket().isConnected())
                {
                    String messageFromServer;
                    try 
                    {
                        messageFromServer = client.getBufferedReader().readLine();
                        if(messageFromServer != null)
                        {
                            
                            System.out.println("Going to process command " + messageFromServer);
                            String[] messageSplit = messageFromServer.split(":");
                            serverMessageHandler.processCommand(messageSplit);
                        }
                        System.out.println("Message from server: " + messageFromServer);
                    } 
                    catch (IOException ex) 
                    {
                        ex.printStackTrace();
                    }

                }
            }
        }).start();
    
    
    }
    
}
