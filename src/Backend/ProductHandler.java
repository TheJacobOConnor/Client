/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

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

/**
 *
 * @author jaket
 */
public class ProductHandler 
{
    public ProductHandler()
    {
    
    }
    
    public static ArrayList<Product> loadProductByStrain(String strain)
    {
        ArrayList<Product> wholeProductList = new ArrayList();
        ArrayList<Product> specialList = new ArrayList();
        wholeProductList = loadProduct();
        
        for(Product p : wholeProductList)
        {
            if(p.getStrain().equals(strain))
            {
                specialList.add(p);
            }
        }
        
        return specialList;
    }
    
        
    public static ArrayList<Product> loadProduct()
    {
        ArrayList<Product> productList = new ArrayList();
        
        Path path = Paths.get("src/Data/Core/productList.txt");
        File theFile = path.toFile();
        
        try(BufferedReader in = new BufferedReader(new FileReader(theFile)))
        {
            String curF = in.readLine();
            
            while(curF != null)
            {
                String[] curLine = curF.split(",");
                
                String strain = curLine[0];
                String containerType = curLine[1];
                String supply = curLine[2];
                String description = curLine[3];
                String label = curLine[4];
                String cureInfo = curLine[5];
                
                double newSupply = Double.valueOf(supply);
                Product newProduct = new Product(strain, containerType, newSupply, description, label, cureInfo);
                productList.add(newProduct);
                curF = in.readLine();
            }
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        
        
        return productList;
    }
    
    public static void createProduct(Product newProduct)
    {
        ArrayList<Product> productList = new ArrayList();
        productList = ProductHandler.loadProduct();
        productList.add(newProduct);
        updateProductFile(productList);
    }
    
    public static void updateProductFile(ArrayList<Product> productList)
    {
        
        Path path = Paths.get("src/Data/Core/productList.txt");
        File theFile = path.toFile();
 
        try
        {
             PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(theFile, false)));
             for(Product product : productList)
             {
                 //System.out.println(product.getStrain() + "," + product.getContainerType() + "," + product.getSupply() + "," + product.getDescription() + "," + product.getLabel() + "," + product.getCureInfo());
                 writer.println(product.getStrain() + "," + product.getContainerType() + "," + product.getSupply() + "," + product.getDescription() + "," + product.getLabel() + "," + product.getCureInfo());
                 
             }
             writer.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    
    public static void updateExistingProduct(Product newProduct)
    {
        ArrayList<Product> productList = new ArrayList();
        
        productList = ProductHandler.loadProduct();
        
        for(int i = 0; i < productList.size(); i++)
        {
            System.out.println(productList.get(i).getLabel() + " against " + newProduct.getLabel());
            if(productList.get(i).getLabel().equals(newProduct.getLabel()))
            {
                
                productList.set(i, newProduct);
                System.out.println(productList.get(i).getSupply());
                break;
            }
        }
        ProductHandler.updateProductFile(productList);
    }
    
    public static void removeProduct(Product product)
    {
        ArrayList<Product> productList = new ArrayList();
        productList = ProductHandler.loadProduct();
        
        for(Product p : productList)
        {
            if(p.getLabel().equals(product.getLabel()) && p.getDescription().equals(product.getDescription()))
            {
                productList.remove(p);
                break;
            }
        }
        
        ProductHandler.updateProductFile(productList);
        
    }
    
    public static void addProduct(Product product, double weight)
    {
       double newWeight = product.supply + weight;
        product.setSupply(newWeight);
        updateExistingProduct(product);
    }
    
    public static void subtractProduct(Product product, double weight)
    {
        
       
        double newWeight = product.getSupply() - weight;
       
        if(newWeight < 0.0)
        {
            newWeight = 0.0;
        }
        
       
        product.setSupply(newWeight);
        updateExistingProduct(product);
        
    }
    
   
}
