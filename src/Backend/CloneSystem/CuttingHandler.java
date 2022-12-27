/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.CloneSystem;

import Backend.Client.Client;
import java.util.ArrayList;

/**
 *
 * @author jaket
 */
public class CuttingHandler 
{
    
    public static ArrayList<String> filterList(ArrayList<String> list)
    {
        System.out.println("here");
        ArrayList<String> newList = new ArrayList<>();
        for(String str : list)
        {
            if(!str.equalsIgnoreCase("end"))
            {
                newList.add(str);
            }
                
        }
    
        return newList;
    }
    
    public static ArrayList<Cutting>loadCuttingList(String machineName)
    {
        ArrayList<Cutting> cuttingList = new ArrayList<>();
        ArrayList<String> cuttingDataList = new ArrayList<>();
        String returnStr = "";
        returnStr = Client.client.awaitMessageFromServer("RequestData Cuttings " + machineName);
        cuttingList = buildCuttingList(returnStr);
      
        return cuttingList;
        
        
    }
    
    public static ArrayList<Cutting> buildCuttingList(String cuttingStr)
    {
        ArrayList<Cutting> cuttingList = new ArrayList<>();
        ArrayList<String> firstFix = new ArrayList<>();
        System.out.println(cuttingStr);
        String[] firstSplit = cuttingStr.split("|");
        for(String str : firstSplit)
        {
            String[] secondSplit = str.split(",");
            String motherId = secondSplit[0];
            String label = secondSplit[1];
            String cutDate = secondSplit[2];
            
            Cutting cutting = new Cutting(Integer.parseInt(motherId), label, cutDate);
            cuttingList.add(cutting);
        }
        
        return cuttingList;
    }
    
    public static ArrayList<Cutting>buildCuttingList(ArrayList<String> stringList)
    {
        ArrayList<Cutting> cuttingList = new ArrayList<>();
        
        
        for(String str : stringList)
        {
            if(!str.equalsIgnoreCase("end"))
            {
                String[] newStr = str.split(",");
                Cutting tempCutting = new Cutting(Integer.valueOf(newStr[0]), newStr[1], newStr[2]);
                cuttingList.add(tempCutting);  
            }
                
        }
        
        
        return cuttingList;
    }
    
    
      
    public static ArrayList<String> loadCuttingIdList(String machineName)
    {
        ArrayList<Cutting> cuttingList = new ArrayList<>();
        
        ArrayList<String> cuttingIdList = new ArrayList<>();
        
        for(Cutting cutting : cuttingList)
        {
            
            cuttingIdList.add(String.valueOf(cutting.getMotherId()));
        }
        
        return cuttingIdList;
    }
}
