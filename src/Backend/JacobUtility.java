/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jakeo
 */
public class JacobUtility 
{
    public static String getDateAndTime()
    {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm:ss");
        
        String formattedDate = date.format(format);
        
        return formattedDate;
    }
    
    public static String getDate()
    {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/mm/yyyy");
        
        String formattedDate = date.format(format);
        
        return formattedDate;
    }
    
    public static String getTime()
    {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        String formattedDate = date.format(format);
        
        return formattedDate;
    }
    
    
    
    
    
    
}
