/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author jaket
 */
public class Product 
{
    String strain = "";
    String containerType = "";
    double supply = 0;
    String cureInfo = "";
    String description = "";
    String label = "";

    public Product()
    {
        
    }
    public Product(String strain, String containerType, double supply, String description, String label, String cureInfo) 
    {
        this.strain = strain;
        this.containerType = containerType;
        this.supply = supply;
        this.description = description;
        this.label = label;
        this.cureInfo = cureInfo;
        
    }

   

    public String getStrain() {
        return strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public double getSupply() {
        return supply;
    }

    public void setSupply(double supply) {
        this.supply = supply;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public void setCureInfo(String cureInfo)
    {
        this.cureInfo = cureInfo;
    }
    
    public String getCureInfo()
    {
        return this.cureInfo;
    }
    
    
}
