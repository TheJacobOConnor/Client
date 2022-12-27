/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
package Backend.CloneSystem;

import Backend.Client.Client;
import Backend.Phenotype;
import Backend.PlantData.PlantDataManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class CuttingController 
{
    
    
    
    
  
    
    public static void takeCutting(int amount, int motherId, String machine, String spot)
    {
        ArrayList<Cutting> cuttingList = CuttingHandler.loadCuttingList(machine);
        ArrayList<Integer> availableIndexList = CloningMachineHandler.getAvaialableMachineSlots(cuttingList, machine);
        ArrayList<Integer> newIndexList = new ArrayList();
        
        boolean readyToCut = false;
        boolean cutsWereMade = false;
        boolean enoughSlots = false;
        int startIndex = 0;
        int startSlot = 0;
        int index = CloningMachineHandler.getMachineIndex(spot);
        
        System.out.println("The index is: " + startIndex);
        for(Integer i : availableIndexList)
        {
            if(i == index)
            {
                startIndex = availableIndexList.indexOf(i);
                startSlot = i;
                break;
            }
            
        }
        
        System.out.println("The index is: " + startIndex);
            
        if(startIndex + amount <= availableIndexList.size())
        {
            for(int i = startIndex; i < availableIndexList.size(); i++)
                newIndexList.add(i);
        }
                
        
        System.out.println("Here " + availableIndexList.size());
        if(amount <= availableIndexList.size())
            readyToCut = true;
        
        
        
      
        if(readyToCut)
        {
            
        System.out.println("Ready to cut");
            for(int i = 0; i < amount; i++)
            {
                
                Cutting cutting = getCuttingByMotherId(String.valueOf(motherId));
                System.out.println(newIndexList.get(i));
                cuttingList.add(startSlot + i, cutting);
                cutsWereMade = true;
            }
        }
        
        if(cutsWereMade)
        {
            System.out.println("Cuts were made");
            updateCuttingList(cuttingList, machine);      
        }
            
            
    }
    
    public static void updateCuttingList(ArrayList<Cutting> cuttingList, String machine)
    {
        Path path = Paths.get("src/Data/Core/Plants/PlantData/CloningMachines/" + machine + ".txt");
        File theFile = path.toFile();
        String line = "";
     
        
        try
        {
            
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(theFile, false)));
            
            for(Cutting cutting : cuttingList)
            {
                writer.println(cutting.getMotherId() + "," + cutting.getCutDate());
            }
            
            writer.close();
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    
    
   
    
    
    public static Cutting getCuttingByMotherId(String motherId)
    {
        Phenotype pheno = PlantDataManager.getPheno(Integer.parseInt(motherId));
        
        Cutting cutting = new Cutting(pheno.getPlantId(), pheno.getPlantTag());
        
        return cutting;
        
        
    }
    
    public static Cutting getCuttingByIndex(int index, String machineName)
    {
        ArrayList<Cutting> cuttingList = CuttingHandler.loadCuttingList(machineName);

        Cutting cutting = new Cutting(cuttingList.get(index).getMotherId(), cuttingList.get(index).getCutDate());
        
        return cutting;
        
    }
    
    
  
  
    
    
}*/
