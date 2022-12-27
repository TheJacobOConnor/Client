/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Seed;

import java.time.LocalDate;

/**
 *
 * @author jaket
 */
public class SeedCollection 
{
    private int stock;
    private int id;
    private String breeder;
    private String cultivar;
    private String lineage;
    private String release;
    private String seedType;
    private String description;
    private String floweringTime;
    private String terpeneProfile;
    private String expectedSize;
    private String expectedYield;
    private LocalDate acquiredDate;
    
    
    public SeedCollection()
    {
        
    }

    public String getLineage() {
        return lineage;
    }

    public void setLineage(String lineage) {
        this.lineage = lineage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFloweringTime() {
        return floweringTime;
    }

    public void setFloweringTime(String floweringTime) {
        this.floweringTime = floweringTime;
    }

    public String getTerpeneProfile() {
        return terpeneProfile;
    }

    public void setTerpeneProfile(String terpeneProfile) {
        this.terpeneProfile = terpeneProfile;
    }

    public String getExpectedSize() {
        return expectedSize;
    }

    public void setExpectedSize(String expectedSize) {
        this.expectedSize = expectedSize;
    }

    public String getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(String expectedYield) {
        this.expectedYield = expectedYield;
    }

    public LocalDate getAcquiredDate() {
        return acquiredDate;
    }

    public void setAcquiredDate(LocalDate acquiredDate) {
        this.acquiredDate = acquiredDate;
    }
    


    public String getCultivar() {
        return cultivar;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

   
    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeedType()
    {
        return seedType;
    }

    public void setSeedType(String seedType) 
    {
        this.seedType = seedType;
    }

    public String getBreeder() 
    {
        return breeder;
    }

    public void setBreeder(String breeder)
    {
        this.breeder = breeder;
    }


    public int getStock() 
    {
        return stock;
    }

    public void setStock(int stock) 
    {
        this.stock = stock;
    }
}

