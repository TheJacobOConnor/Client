/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.awt.Point;
import static java.lang.Math.abs;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author jakeo
 */
public class JacobCords 
{
    
    public static Point getNode(JLabel pin)
    {
        ArrayList<Point> zoneList = getZones();
        int x1, x2, y1, y2;
        ArrayList<Double> distanceList = new ArrayList();
        Point shortestPoint = new Point();
        int shortestIndex = 0;
        double newDist;
        
        for(Point p : zoneList)
        {
            System.out.println("I am looking at point " + p);
            System.out.println(abs(pin.getY() - p.getY()));
            if(calcDistance(pin.getX(), p.getX(), pin.getY(), p.getY()) > 6 && abs(pin.getY() - p.getY()) > 5 && abs(pin.getX() - p.getX()) > 6)
            {
                //System.out.println("Pin X: " + pin.getX() + " p X: " + p.getX());
                distanceList.add(calcDistance(pin.getX(), p.getX(), pin.getY(), p.getY()));
                
                //System.out.println("Distance between " + pin.getLocation() + " and " + p.getLocation() + " is " + calcDistance(pin.getX(), p.getX(), pin.getY(), p.getY()));
                
            }
            else
            {
                System.out.println("Cant go to point " + p);
            }
            
            
            
        }
        double shortest = distanceList.get(0);
        
        
        for(int i = 0; i < distanceList.size(); i++)
        {
            if(distanceList.get(i) <= shortest)
            {
                shortest = distanceList.get(i);
                
                shortestPoint = zoneList.get(i);
            }
        }
        
        
        
        System.out.println("Pin Loc: " + pin.getLocation());
        System.out.println("The shortest distance away is " + shortest);
        System.out.println("Send it to Zone Point " + shortestPoint);
        return shortestPoint;
    }
    
    public static double calcDistance(double x1, double x2, double y1, double y2)
    {
        double ac = Math.abs(y2 - y1);
        double cb = Math.abs(x2- x1);
        
        
        
        return Math.hypot(ac, cb);
    }
    
    public static ArrayList<Point> getZones()
    {
        ArrayList<Point> zoneList = new ArrayList();
        zoneList.add(new Point(261, 651));
        zoneList.add(new Point(389, 468));
        zoneList.add(new Point(520, 181));
        zoneList.add(new Point(828, 613));
        zoneList.add(new Point(882, 642));
        zoneList.add(new Point(823, 476));
        zoneList.add(new Point(920, 95));
        zoneList.add(new Point(615, 501));
        zoneList.add(new Point(283, 342));
        
        
        
        
       
        return zoneList;
        
    }
    
    
}
