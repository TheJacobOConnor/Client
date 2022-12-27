/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jaket
 */
public class AccountHandler 
{
    public static Account loadAccount(String file) throws FileNotFoundException
    {
        
        ArrayList<String> accList = new ArrayList();
        Account acc = new Account();
        Scanner scan = new Scanner(new File("src/Data/Accounts/" + file + "/accountData.txt"));
        
        while(scan.hasNext())
        {
            String curLine = scan.nextLine().toLowerCase().toString();
            if(curLine.contains("name:"))
            {
                accList.add(curLine.substring(6));
                //System.out.println(accList.get(0));
            }
            
            if(curLine.contains("access:"))
            {
                accList.add(curLine.substring(8));
            }
            
            if(curLine.contains("path:"))
            {
                accList.add(curLine.substring(6));
            }
        }
        
        acc.setName(accList.get(0));
        acc.setAccess(Integer.parseInt(accList.get(1)));
        acc.setAccountPath(accList.get(2));
        
        return acc;
    }
}
