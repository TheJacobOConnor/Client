/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author jaket
 */
public class Contact 
{
    private String name;
    private String id;
    private String contactInfo;
    private String description; 
    private String phoneNumber;
    private String snapchat;
    private String venmo;
    private String cashapp;
    private String paypal;
    private String whatsapp;
    private double balance;
    
    public Contact(String name, double balance, String description, String phoneNumber, String snapchat, String venmo, String cashapp, String paypal, String whatsapp)
    {
        this.name = name;
        this.balance = balance;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.snapchat = snapchat;
        this.venmo = venmo;
        this.cashapp = cashapp;
        this.paypal = paypal;
        this.whatsapp = whatsapp;
        this.id = String.valueOf(generateId());
    }
    
    public Contact()
    {
        
    }
    
    public static int generateId()
    {
        int id = 0;
        int min = 100000;
        int max = 999999;
        boolean exists = true;
        boolean existCheck = false;
        
        while(exists)
        {
            id = (int)(Math.random() * (max - min + 1) + min);
            existCheck = checkIdExists(id);
            if(!existCheck)
            {
                exists = false;
            }
        }
        System.out.println("Your ID: " + id);
        return id;
    }
    
    private static boolean checkIdExists(int id)
    {
        boolean exists = false;
        
        Path path = Paths.get("src/Data/Core/idList.txt");
        File theFile = path.toFile();
        
        try(BufferedReader in = new BufferedReader(new FileReader(theFile)))
        {
            String curF = in.readLine();
            
            while(curF != null)
            {
                System.out.println("reading line");
                String[] curLine = curF.split(",");
                if(Integer.valueOf(curLine[0]) == id)
                {
                    System.out.println("id exists " + curLine[1]);
                    exists = true;
                }
                curF = in.readLine();
            }
            System.out.println("End of reading file");
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        
        System.out.println("returning exist " + exists);
        return exists;
    }
    
    public static void createContact(Contact contact) throws IOException
    {
        File file = new File("src/Data/Customers/" + "user " + contact.getId());
        file.mkdir();
        
        File detailsFile = new File("src/Data/Customers/" + "user " + contact.getId() + "/details.txt");
        if(detailsFile.createNewFile())
        {
            System.out.println("Log File Created");
        }
        else
        {
            System.out.println("File already Exists");
        }
        
        File purchaseLogFile = new File("src/Data/Customers/" + "user " + contact.getId() + "/transactionLog.txt");
        if(purchaseLogFile.createNewFile())
        {
            System.out.println("Purchase Log File Created");
        }
        else
        {
            System.out.println("File already Exists");
        }
        
        File idListFile = new File("src/Data/Core/idList.txt");
        
        try
        {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(detailsFile, false)));
            writer.println("Name: " + contact.getName());
            writer.println("Id: " + contact.getId());
            writer.println("Balance: " + contact.getBalance());
            writer.println("Description: " + contact.getDescription());
            writer.println("Phone Number: " + contact.getPhoneNumber());
            writer.println("Snapchat: " + contact.getSnapchat());
            writer.println("Venmo: " + contact.getVenmo());
            writer.println("Cashapp: " + contact.getCashapp());
            writer.println("Paypal: " + contact.getPaypal());
            writer.println("Whatsapp: " + contact.getWhatsapp());
            writer.close();
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        
        try
        {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(idListFile, true)));
            writer.println(contact.getId() + "," + contact.getName());
            writer.close();
            
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    
    public static void updateContact(Contact contact)
    {
        File detailsFile = new File("src/Data/Customers/" + "user " + contact.getId() + "/details.txt");
        try
        {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(detailsFile, false)));
            writer.println("Name: " + contact.getName());
            writer.println("Id: " + contact.getId());
            writer.println("Balance: " + contact.getBalance());
            writer.println("Descrption: " + contact.getDescription());
            writer.println("Phone Number: " + contact.getPhoneNumber());
            writer.println("Snapchat: " + contact.getSnapchat());
            writer.println("Venmo: " + contact.getVenmo());
            writer.println("Cashapp: " + contact.getCashapp());
            writer.println("Paypal: " + contact.getPaypal());
            writer.println("Whatsapp: " + contact.getWhatsapp());
            writer.close();
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    
    public static ArrayList<String> loadContactList()
    {
        ArrayList<String> contactList = new ArrayList();
        Path path = Paths.get("src/Data/Core/idList.txt");
        File theFile = path.toFile();
        
        try(BufferedReader in = new BufferedReader(new FileReader(theFile)))
        {
            String curF = in.readLine();
            
            while(curF != null)
            {
                String[] curLine = curF.split(",");
                
                String name = curLine[1];
                String id = curLine[0];     
                contactList.add(id);
                contactList.add(name);
                curF = in.readLine();
            }
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        return contactList;
    }
    
    public static Contact loadContact(String contactName) throws FileNotFoundException
    {
        System.out.println("Working on loading Contact for you");
        Contact contact = new Contact();
        ArrayList<String> contactList = new ArrayList();
        contactList = loadContactList();
        String contactId = "";
        
        for(int i = 1; i < contactList.size(); i+= 2)
        {
            if(contactName.equals(contactList.get(i)))
            {
                contactId = contactList.get(i - 1);
                break;
            }
        }
        Scanner scan = new Scanner(new File("src/Data/Customers/user " + contactId + "/details.txt"));
        while(scan.hasNext())
        {
            String curLine = scan.nextLine().toString();
            if(curLine.contains("Name:"))
            {
                contact.setName(curLine.substring(6));
            }
            
            if(curLine.contains("Id:"))
            {
                contact.setId(curLine.substring(4));
            }
            
            if(curLine.contains("Balance:"))
            {
                contact.setBalance(Double.valueOf(curLine.substring(9)));
            }
            
            if(curLine.contains("Description:"))
            {
                contact.setDescription(curLine.substring(13));
            }
            
            if(curLine.contains("Phone Number:"))
            {
                contact.setPhoneNumber(curLine.substring(14));
            }
            
            if(curLine.contains("Snapchat:"))
            {
                contact.setSnapchat(curLine.substring(10));
            }
            
            if(curLine.contains("Venmo:"))
            {
                contact.setVenmo(curLine.substring(7));
            }
            
            if(curLine.contains("Cashapp:"))
            {
                contact.setCashapp(curLine.substring(9));
            }
            
            if(curLine.contains("Paypal:"))
            {
                contact.setPaypal(curLine.substring(8));
            }
        }
        
        return contact;
    }
    
    
    public static void logTransaction(Contact contact, ArrayList<String> eventLog)
    {
        Path path = Paths.get("src/Data/Customers/user " + contact.getId() + "/transactionLog.txt");
        File theFile = path.toFile();
        
        try
        {
             PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(theFile, true)));
             for(int i = 0; i < eventLog.size(); i++)
             {
                 if(i < eventLog.size() - 1)
                 {
                    writer.print(eventLog.get(i) + ",");
                 }
                 else
                 {
                     writer.print(eventLog.get(i) + "\n");
                 }
                 
                 
             }
             writer.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    
    
    public void addBalance(double amount)
    {
        this.balance += amount;
    }
    
    public void removeBalance(double amount)
    {
        this.balance -= amount;
    }
    
    
    public String getName() {
        return name;
    }

    public void setBalance(double newBalance)
    {
        this.balance = newBalance;
    }
    
    public double getBalance()
    {
        return this.balance;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSnapchat() {
        return snapchat;
    }

    public void setSnapchat(String snapchat) {
        this.snapchat = snapchat;
    }

    public String getVenmo() {
        return venmo;
    }

    public void setVenmo(String venmo) {
        this.venmo = venmo;
    }

    public String getCashapp() {
        return cashapp;
    }

    public void setCashapp(String cashapp) {
        this.cashapp = cashapp;
    }

    public String getPaypal() {
        return paypal;
    }

    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
}
