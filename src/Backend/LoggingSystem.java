/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author jaket
 */
public class LoggingSystem 
{
    String event = "";
    String logType = "";
    String filePath = "";
    String localTime = "";
    
    public LoggingSystem()
    {
        
    }
    
    public static void logEvent(String logType, String filePath, String event)
    {
        Path path = Paths.get(filePath);
        File theFile = path.toFile();
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter timeFormatted = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
        String timeLog = time.format(timeFormatted);
        try
        {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(theFile, true)));
            writer.println(logType + "|" + timeLog + "|" + event);
            writer.close();
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
        
    }
    
    public static void logTransaction(String logMessage, String endPath)
    {
        Path path = Paths.get("src/Data/Core/" + endPath + ".txt");
        File theFile = path.toFile();
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter timeFormatted = DateTimeFormatter.ofPattern("E MMM dd yyyy HH:mm:ss");
        String timeLog = time.format(timeFormatted);
        try
        {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(theFile, true)));
            writer.println(timeLog + " | " + logMessage);
            writer.close();
        }
        
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    
    
    
//    public static void multiLogEvent(String logType, String filePath, ArrayList<String> eventList)
//    {
//        Path path = Paths.get(filePath);
//        File theFile = path.toFile();
//        LocalTime time = LocalTime.now();
//        DateTimeFormatter timeFormatted = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//        String timeLog = time.format(timeFormatted);
//        try
//        {
//            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(theFile, false)));
//            writer.println(logType + "|" + timeLog + "|" + event + "\n");
//            writer.close();
//        }
//        
//        catch(IOException e)
//        {
//            System.out.println(e);
//        }
//        
//    }
}
