/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Seed;


public class Seed 
{
    private String cultivar;
    private String breeder;
    private String floweringType;

    
    
    
    public Seed(String cultivar, String breeder, String floweringType)
    {
        this.cultivar = cultivar;
        this.breeder = breeder;
        this.floweringType = floweringType;
    
    }
    
    
    
    public String getLabel()
    {
        return this.breeder + " - " + cultivar + " | " + floweringType;
        
    }
    
    
    //getters and setters

    public String getCultivar() 
    {
        return cultivar;
    }

    public void setCultivar(String cultivar)
    {
        this.cultivar = cultivar;
    }

    public String getBreeder() 
    {
        return breeder;
    }

    public void setBreeder(String breeder) 
    {
        this.breeder = breeder;
    }

    public String getFloweringType()
    {
        return floweringType;
    }

    public void setFloweringType(String floweringType)
    {
        this.floweringType = floweringType;
    }


    
}
