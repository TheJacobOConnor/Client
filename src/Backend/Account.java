/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jaket
 */
public class Account 
{

    private String name;
    
    private int access;
    
    private String accountPath;
    
//    public Account(String username, String password, String name, int access) 
//    {
//        this.username = username;
//        this.password = password;
//        this.name = name;
//        this.access = access;
//    }
    
    
    public void setAccountPath(String newPath)
    {
        this.accountPath = newPath;
    }
    
    public String getAccountPath()
    {
        return this.accountPath;
    }
   
    public void setAccess(int newAccess)
    {
        this.access = newAccess;
    }
    
    public void setName(String newName)
    {
        this.name = newName;
    }
    
    //Getters
    public int getAccess() 
    {
        return access;
    }

    public String getName() 
    {
        return name;
    }
   

    //Setters
    
    
    
    public static ArrayList<String> getLoginData(String fileName) throws FileNotFoundException
    {
        
        String sendUsername = null;
        String sendPassword = null;
        ArrayList<String> loginData = new ArrayList<>();
        Scanner scan = new Scanner(new File("src/Data/Accounts/" + fileName + "/loginData.txt"));
        
        while(scan.hasNext())
        {
            String curLine = scan.nextLine().toLowerCase().toString();
            if(curLine.contains("username:"))
            {
                sendUsername = curLine.substring(10);
            }
            
            if(curLine.contains("password:"))
            {
                sendPassword = curLine.substring(10);
            }
        }
        
        loginData.add(sendUsername);
        loginData.add(sendPassword);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        try(BufferedReader reader = new BufferedReader(new FileReader(theFile)))
//        {
//            String currentFile = reader.readLine();
//            
//            while(currentFile != null)
//            {
//                if(currentFile.contains("username:"))
//                {
//                    String[] usernameArr = currentFile.split(":", 0);
//                    for(String str : usernameArr)
//                    {
//                        sendUsername = str;
//                        loginData.add(sendUsername);
//                    }
//                }
//                if(currentFile.contains("lol"))
//                {
//                    String[] passwordArr = currentFile.split(":",0);
//                    for(String str : passwordArr)
//                    {
//                        sendPassword = str;
//                        loginData.add(sendPassword);
//                    }
//                }
//                
//                
////                System.out.println(currentLine[0]);
////                String name = currentLine[0];
////                String description = currentLine[1];
////                String zone = currentLine[2];
////                String location = currentLine[3];
////                String inUseString = currentLine[4];
////                boolean inUse = Boolean.parseBoolean(inUseString);
//                
////              Item newItem = new Item(name, description, zone, location, inUse);
////                inventory.add(newItem);
//                currentFile = reader.readLine();
//            } 
//            
//        }
//        
//        catch(IOException e)
//        {
//            
//            System.out.println(e);
//            
//        }
//        for(String str : loginData)
//        {
//            System.out.println(str);
//        }
//
        return loginData;
    }
    
    

}
