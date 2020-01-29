/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author G25
 */
public class PathTest {
    private Path path;
    private Path path2;
    
    public PathTest() {
        path = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"), 24.65, 36, 12.6f);
        path2 = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"));
    }
    
    /**
     * Test of getPointFrom method, of class Path.
     */
    @Test
    public void testGetPointFrom() {
        System.out.println("getPointFrom");
        Path instance = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"), 24.65, 36, 12.6f);
        PointInterest expResult = new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1");
        PointInterest result = instance.getPointFrom();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getPointTo method, of class Path.
     */
    @Test
    public void testGetPointTo() {
        System.out.println("getPointTo");
        Path instance = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"), 24.65, 36, 12.6f);
        PointInterest expResult = new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2");
        PointInterest result = instance.getPointTo();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getKineticCoefficient method, of class Path.
     */
    @Test
    public void testGetKineticCoefficient() {
        System.out.println("getKineticCoefficient");
        Path instance = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"), 24.65, 36, 12.6f);
        double expResult = 24.65;
        double result = instance.getKineticCoefficient();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getWindDirection method, of class Path.
     */
    @Test
    public void testGetWindDirection() {
        System.out.println("getWindDirection");
        Path instance = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"), 24.65, 36, 12.6f);
        float expResult = 36;
        float result = instance.getWindDirection();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWindSpeed method, of class Path.
     */
    @Test
    public void testGetWindSpeed() {
        System.out.println("getWindSpeed");
        Path instance = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"), 24.65, 36, 12.6f);
        float expResult = 12.6f;
        float result = instance.getWindSpeed();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of setPointFrom method, of class Path.
     */
    @Test
    public void testSetPointFrom() {
        System.out.println("setPointFrom");
        PointInterest pointFrom = new PointInterest(3, new GeographicalLocation(25, 238.4, 0.2), "poi3");
        Path instance = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"), 24.65, 36, 12.6f);
        instance.setPointFrom(pointFrom);
        PointInterest result = instance.getPointFrom();
        assertEquals(pointFrom, result);
    }

    /**
     * Test of setPointTo method, of class Path.
     */
    @Test
    public void testSetPointTo() {
        System.out.println("setPointTo");
        PointInterest pointTo = new PointInterest(2, new GeographicalLocation(25, 238.4, 0.2), "poi2");
        Path instance = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"), 24.65, 36, 12.6f);
        instance.setPointTo(pointTo);
        PointInterest result = instance.getPointTo();
        assertEquals(pointTo, result);
    }
    
    /**
     * Test of setKineticCoefficient method, of class Path.
     */
    @Test
    public void testSetKineticCoefficient() {
        System.out.println("setKineticCoefficient");
        double expResult = 48;
        Path instance = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"), 24.65, 36, 12.6f);
        instance.setKineticCoefficient(expResult);
        double result = instance.getKineticCoefficient();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of setWindDirection method, of class Path.
     */
    @Test
    public void testSetWindDirection() {
        System.out.println("setWindDirection");
        float windDirection = 48;
        Path instance = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"), 24.65, 36, 12.6f);
        instance.setWindDirection(windDirection);
        float expResult = instance.getWindDirection();
        assertEquals(expResult, windDirection);
    }

    /**
     * Test of setWindSpeed method, of class Path.
     */
    @Test
    public void testSetWindSpeed() {
        System.out.println("setWindSpeed");
        float expResult = 24.9f;
        Path instance = new Path(new PointInterest(1, new GeographicalLocation(25.36, 23.4, 45.2), "poi1"), new PointInterest(2, new GeographicalLocation(28.25, -12.97, 105.9), "poi2"), 24.65, 36, 12.6f);
        instance.setWindSpeed(expResult);
        float result = instance.getWindSpeed();
        assertEquals(expResult, result);
    }
}
