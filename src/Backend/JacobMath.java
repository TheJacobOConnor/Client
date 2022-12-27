/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.util.ArrayList;

/**
 *
 * @author jaket
 */
public class JacobMath 
{
    
    
    public static double convertWeight(double lb, double oz, double g)
    {
        double total = 0.0;
        if(lb >= 1.0)
        {
            total += lb * 454;
        }
        
        if (oz >= 1.0)
        {
            total += oz * 28;
        }
        
        if (g >= 0.0)
        {
            total += g;
        }
        
        
        
        return total;
    }
    
    public static ArrayList<Double> totalConversion(double weight)
    {
        
        ArrayList<Double> weightList = new ArrayList();
        double lbWeight = 454.0;
        double ozWeight = 28.0;
        double gWeight = 1.0;
        
        double lb = 0.0;
        double oz = 0.0;
        double g = 0.0;
        
        while(weight >= lbWeight)
        {
            weight = weight - lbWeight;
            lb += 1;
            
        }
        
        while(weight >= ozWeight)
        {
            weight = weight - ozWeight;
            oz += 1;
        }
        
        while(weight >= gWeight)
        {
            weight = weight - gWeight;
            g += 1;
        }
        
        
        weightList.add(g);
        weightList.add(oz);
        weightList.add(lb);
        
        return weightList;
        
    }
    
   
    public static String weightString(double gram, double oz, double lb)
    {
        ArrayList<Double> weightList = new ArrayList(); 
        
        double weightInG = convertWeight(gram, oz, lb);
        String returnString = "";
        
        weightList = totalConversion(weightInG);
        double g = weightList.get(0);
        double o = weightList.get(1);
        double p = weightList.get(2);
        
        
        if(p > 0)
        {
            returnString += Double.toString(p) + " lb ";
        }

        if(o > 0)
        {
            returnString += Double.toString(o) + " oz ";
        }
        
        if(g > 0)
        {
            returnString += Double.toString(g) + " g ";
        }
        
        
        System.out.println(returnString);
        return returnString;
    }
}
 