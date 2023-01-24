/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Client;

/**
 *
 * @author jaket
 */
public class ClientHandler 
{
    private static Client client;
    
    
    public static void sendMessage(String message)
    {
        client.sendMessageToServer(message);
    }
    

    public static void setClient(Client client)
    {
        client = client;
    }
    
    
    
    
}
