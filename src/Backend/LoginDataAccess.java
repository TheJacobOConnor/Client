/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class LoginDataAccess 
{
    public static Account login(String username, String password) throws FileNotFoundException
    {
        
        ArrayList<String> loginData = new ArrayList();
        Account acc = new Account();
        boolean fileExists = false;
        String tempUsername;
        String tempPassword;
        
        File tempFile = new File("src/Data/Accounts/" + username + "/loginData.txt");
        
        
        
        if(tempFile.exists() && !tempFile.isDirectory())
        {
            fileExists = true;
            System.out.println("True");
        }
        
        if(fileExists)
        {
            loginData = Account.getLoginData(username);
            
            tempUsername = loginData.get(0);
            tempPassword = loginData.get(1);
            
//            System.out.println("Testing " + tempUsername + " against " + username);
//            System.out.println("Testing " + tempPassword + " against " + password);
            if(tempUsername.equals(username) && tempPassword.equals(password))
            {
                System.out.println("You have successfully logged in, " + username);
                acc = AccountHandler.loadAccount(username);
                
                System.out.println("Greetings, " + acc.getName() + "!");
                
                System.out.println("You have an access level of " + acc.getAccess());
                
                
            }
            else
            {
                System.out.println("Failed to log in.");
            }
 
        }
        else
        {
            System.out.println(username + ".txt" + " | File does not exist");
        }
        
        return acc;
    }
}
