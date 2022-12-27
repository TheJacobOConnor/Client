/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Client;

import java.util.ArrayList;

/**
 *
 * @author jaket
 */
public class ServerRequest 
{
    
    public void ServerRequest()
    {
        
    }
    public static ArrayList<String> stringServerRequest(Client client, String message)
    {
        ArrayList<String> list = new ArrayList<>();
        list = client.awaitDataFromServer(message);
        
        return list;
    }
    
    public static void requestSeeds(String message)
    {
        
    }
    
}
