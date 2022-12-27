/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Client;

import static Backend.Client.Client.streamList;
import Backend.GrowRoomData.GrowRoom;
import Backend.Indoor.IndoorHandler;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jaket
 */
public class ServerRequestHandler 
{
    
    protected Client client;
    
    
    public ServerRequestHandler(Client client)
    {
        this.client = client;
    }
    
    public void sendRequest(String messageRequest)
    {
        new Thread (new Runnable()
        {
            @Override
            public void run()
            {
                
                client.awaitMessageFromServer(messageRequest);
                System.out.println("Stream String: " + client.getStreamString());
                
                
            }
                
            
        }).start();
          
    }
 }


