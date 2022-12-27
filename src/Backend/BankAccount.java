/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author jaket
 */
public class BankAccount 
{

    double balance;
    int accountNumber;
    String accountName;
    
    public BankAccount()
    {
        
    }
    
    public static BankAccount loadAccount( String path) throws FileNotFoundException
    {
        BankAccount account = new BankAccount();
        String loadedBalance = "";
        String loadedAccountNumber = "";
        String loadedAccountName = "";
        
        Scanner scan = new Scanner(new File(path + "/bankAccount.txt"));
        
        while(scan.hasNext())
        {
            String curLine = scan.nextLine().toLowerCase().toString();
            if(curLine.contains("account number:"))
            {
                
                loadedAccountNumber = curLine.substring(16);
                System.out.println(loadedAccountNumber);
            }
            
            if(curLine.contains("account name:"))
            {
                loadedAccountName = curLine.substring(14);
            }
            
            if(curLine.contains("balance:"))
            {
                loadedBalance = curLine.substring(9);
            }
        }
        
        account.setBalance(Double.parseDouble(loadedBalance));
        account.setAccountNumber(Integer.parseInt(loadedAccountNumber));
        account.setAccountName(loadedAccountName);
        
        return account;
        
    }
    
    public void deposit(double amount, String path) throws FileNotFoundException
    {
        
        LoggingSystem.logEvent("Bank Deposit", path + "/bankLogs.txt", "$" + String.valueOf(amount) + " | new balance: $" + (balance + amount));
        this.balance += amount;
        this.update(path);
       
    }
    
    public void withdraw(double amount, String path)
    {
        System.out.println("Current balance: " + this.getBalance());
        if(this.getBalance() >= amount)
        {
            
            LoggingSystem.logEvent("Bank Withdrawal", path + "/bankLogs.txt", "$" + String.valueOf(amount) + " new balance: $"  + (balance - amount));
            this.balance -= amount;
            update(path);
        }
        
        else
        {
            JOptionPane.showMessageDialog(null, "You don't have enough to withdraw that amount. Check your balance!");
        }
    }
    
    public void update(String accountPath)
    {
        Path path = Paths.get(accountPath + "/bankAccount.txt");
        File theFile = path.toFile();
        try
        {
            
           PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(theFile, false)));
           writer.print("account number: " + this.accountNumber + "\n" +
                        "account name: " + this.accountName + "\n" +
                        "balance: " + this.balance);
            writer.close(); 
            
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
        
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public double getBalance() 
    {
        return balance;
    }

    public void setBalance(double balance) 
    {
        this.balance = balance;
    }

    public int getAccountNumber() 
    {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) 
    {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() 
    {
        return accountName;
    }

    public void setAccountName(String accountName) 
    {
        this.accountName = accountName;
    }
    
}
