package Backend.Client;


import Backend.MyTools.ReadDataThread;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jaket
 */
public class Client
{
    
    protected String username;
    protected Socket socket;

    public Socket getSocket() {
        return socket;
    }
    protected String password;
    protected int clientId;
    protected InputStreamReader inputStreamReader;
    protected OutputStreamWriter outputStreamWriter;
    protected BufferedReader bufferedReader;

   

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }
    protected BufferedWriter bufferedWriter;
    public static Client client = null;
    

  
    protected static ArrayList<String> streamList = new ArrayList<>();
    protected static String streamString;
    protected boolean streamActive;

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
    
    

    public Client(Socket socket, String username, String password) throws IOException
    {
        
        try
        {
            this.socket = socket;
            this.inputStreamReader = new InputStreamReader(this.socket.getInputStream());
            this.outputStreamWriter = new OutputStreamWriter(this.socket.getOutputStream());
            this.bufferedReader = new BufferedReader(inputStreamReader);
            this.bufferedWriter = new BufferedWriter(outputStreamWriter);
            ArrayList<String> authenticationList = authenticateWithServer(username, password);
            
            
            if(!authenticationList.get(0).equalsIgnoreCase("false"))
            {
               
                if(authenticationList.get(2).equalsIgnoreCase("true"))
                {
                    clientId = Integer.valueOf(authenticationList.get(3));
                }
                else
                {
                    System.out.println("false");
                }
                
            }
            
            client = this;
            
        }
        catch(IOException e)
        {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
        
    }

    
    public ArrayList<String> authenticateWithServer(String username, String password)
    {
        //System.out.println("Authenticating with server");
        ArrayList<String> infoList = new ArrayList<>();
        if(socket.isConnected())
        {
            try
            {
                bufferedWriter.write("AuthenticateClientLogin:" + username + ":" + password);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                String authentication = bufferedReader.readLine();
                String[] authArray = authentication.split(" ");
                
                for(String str : authArray)
                {
                    infoList.add(str);
                }
                if(authArray[2].equalsIgnoreCase("true"))
                {
                    //System.out.println("Successfully Autheticated");
                }
                else
                {
                    System.out.println("Not Authenticated: " + infoList.get(0));
                }
                
                
            } 
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            
        }
        
        return infoList;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void sendMessageToServer(String message)
    {
        try
        {
            if(socket.isConnected())
            {
                
                System.out.println("Sending " + message);
                String messageToSend = message;
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            
            else
                System.out.println("Socket not connected");
        }
        catch(IOException e)
        {
            closeEverything(socket, bufferedReader, bufferedWriter);
            e.printStackTrace();
        }
    }
    
    public void disconnectFromServer()
    {
        sendMessageToServer("bye");
        closeEverything(socket, bufferedReader, bufferedWriter);
    }
    
    public void recieveMessageFromServer()
    {
        
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String messageFromServer = "";
                while(socket.isConnected())
                {
                    streamActive = true;
                    try
                    {
                        
                        while(!messageFromServer.equalsIgnoreCase("end") || messageFromServer != null)
                        {
                            messageFromServer = bufferedReader.readLine();
                            if(messageFromServer == null)
                                break;
                            if(!messageFromServer.equalsIgnoreCase("end") || messageFromServer.equals(""))
                            {
                                streamList.add(messageFromServer);
                                //System.out.println("streamList size : " + streamList.size());
                            }
                        }
                        
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                          
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        streamActive = false;
                        break;
                    }
                }
                
                
            }
        }).start();
        
        
        
    }
    
    public ArrayList<String> awaitDataFromServer()
    {
        
        
        ArrayList<String> dataList = new ArrayList<>();
        String messageFromServer = "";
        
        
        if(socket.isConnected())
        {
            
            try {
                while (!messageFromServer.equalsIgnoreCase("end"))
                {
                    
                    try
                    {
                        
                    messageFromServer = bufferedReader.readLine();
                    if(!messageFromServer.equalsIgnoreCase("end"))
                    {
                        
                        dataList.add(messageFromServer);
                    }
                    
                    }
                    catch(NullPointerException e)
                    {
                        
                        System.out.println(e);
                    }
                    
                    
                   
                }
               

                

            } catch (IOException e)
            {
                e.printStackTrace();
                closeEverything(socket, bufferedReader, bufferedWriter);
                
            }

        }
        
        return dataList;
        
    }
    
    public String awaitMessageFromServer(String request)
    {
        String returnString = "";
        boolean finished = false;
        if(socket.isConnected())
        {
            try
            {
                this.sendMessageToServer(request);
                String messageFromServer = bufferedReader.readLine();
                
                
                streamString = messageFromServer;
                
                
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
        }
        
        return returnString;
    }
    
     public ArrayList<String> awaitDataFromServer(String message)
    {
        
        
        ArrayList<String> dataList = new ArrayList<>();
        String messageFromServer = "";
        boolean finished = false;
        
        if(socket.isConnected())
        {
            
            try {
                this.bufferedWriter.write(message);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
                while (!messageFromServer.equalsIgnoreCase("end"))
                {
                    
                    try
                    {
                        
                    messageFromServer = bufferedReader.readLine();
                   
                    if(!messageFromServer.equalsIgnoreCase("end"))
                    {
                        
                        dataList.add(messageFromServer);
                    }
                    else
                    {
                        
                        dataList.add("end");
                        messageFromServer = "end";
                    }
                    
                    }
                    catch(NullPointerException e)
                    {
                        System.out.println("ERROR: " + messageFromServer);
                        System.out.println("ERROR: " + e);
                    }
                    
                    
                   
                }

                

            } catch (IOException e)
            {
                e.printStackTrace();
                closeEverything(socket, bufferedReader, bufferedWriter);
             
                
            }

        }
        
      
       
        return dataList;
        
    }
    

   
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        
        try
        {
            if(bufferedReader != null)
            {
                bufferedReader.close();
            }
            
            if(bufferedWriter != null)
            {
                bufferedWriter.close();
            }
            
            if(socket != null)
            {
                socket.close();
            }
            
            System.exit(0);
        }
        
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(NullPointerException d)
        {
            d.printStackTrace();
        }
    }
    
    public ArrayList<String> getStreamList()
    {
        
        
        return streamList;
        
    }
    
    public void clearStreamList()
    {
        ArrayList<String> newList = new ArrayList<>();
        streamList = newList;
    }
    
    public boolean isStreamActive()
    {
        return streamActive;
    }
    
    public int getClientId()
    {
        return this.clientId;
    }
    
    public ArrayList<String> collectStreamList()
    {
        ArrayList<String> newStreamList = getStreamList();
        clearStreamList();
        return newStreamList;
    }
    
     public String getStreamString() {
        return streamString;
    }

    public void setStreamString(String streamString) {
        Client.streamString = streamString;
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    



























    
     
    /*
    @Override
    public void run()
    {
        startClient();
    }
    
    */

    
    /*public void sendMessageToConsole(String message) throws IOException
    {
        if(!message.equalsIgnoreCase(null))
        {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        
    }
    
    public void processServerMessage(ArrayList<String> stringList)
    {
        for(String str : stringList)
            System.out.println(str);
    }
    
    
    public void startClient()
    {
        try
        {

            
            
            while(true)
            {
                String msgToSend = scanner.nextLine();
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                try
                {
                    processServerMessage((ArrayList) this.objectInputReader.readObject());
                
                }
                catch(ClassN e)
                {
                    System.out.println(e);
                }
                 
                
                if(msgToSend.equalsIgnoreCase("BYE"))
                    break;
                
            }
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(socket != null)
                    socket.close();
                if(inputStreamReader != null)
                    inputStreamReader.close();
                if(outputStreamWriter != null)
                    outputStreamWriter.close();
                if(bufferedReader != null)
                    bufferedReader.close();
                if(bufferedWriter != null)
                    bufferedWriter.close();
   
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
        }
        
        
        
    }*/
