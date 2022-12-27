/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.CloneSystem;

import Backend.JacobUtility;

/**
 *
 * @author jaket
 */
public class Cutting 
{
    private int motherId;
    private String cutDate;
    protected String label;
    
    
    public Cutting(int motherId)
    {
        this.motherId = motherId;
        this.cutDate = JacobUtility.getDate();
    }
    
    
    public Cutting(int motherId, String cutDate)
    {
        this.motherId = motherId;
        this.cutDate = cutDate;
    }
    
    public Cutting(int motherId, String cutDate, String label)
    {
        this.motherId = motherId;
        this.cutDate = cutDate;
        this.label = label;
    }
 
    
    
    
    
    
    
    
    
    
    

    public int getMotherId()
    {
        return motherId;
    }

    public void setMotherId(int motherId) 
    {
        this.motherId = motherId;
    }

    public String getCutDate() 
    {
        return cutDate;
    }

    public void setCutDate(String cutDate) 
    {
        this.cutDate = cutDate;
    }


    
}
