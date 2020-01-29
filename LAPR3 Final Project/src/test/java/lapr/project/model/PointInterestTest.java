/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Bernardo Carvalho
 */
public class PointInterestTest {
    
    public PointInterestTest() {
    }

    /**
     * Test of getDescription method, of class PointInterest.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        String expResult = "ponto1";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGeoLocation method, of class PointInterest.
     */
    @Test
    public void testGetGeoLocation() {
        System.out.println("getGeoLocation");
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        GeographicalLocation expResult = new GeographicalLocation(2, 2, 3);
        GeographicalLocation result = instance.getGeoLocation();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIdPoint method, of class PointInterest.
     */
    @Test
    public void testGetIdPoint() {
        System.out.println("getIdPoint");
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        int expResult = 1;
        int result = instance.getIdPoint();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDescription method, of class PointInterest.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = "ponto2";
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        instance.setDescription(description);
        assertEquals(description, instance.getDescription());
    }

    /**
     * Test of setGeoLocation method, of class PointInterest.
     */
    @Test
    public void testSetGeoLocation() {
        System.out.println("setGeoLocation");
        GeographicalLocation geoLocation = new GeographicalLocation(1, 3, 9);
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        instance.setGeoLocation(geoLocation);
        assertEquals(geoLocation, instance.getGeoLocation());
    }

    /**
     * Test of setIdPoint method, of class PointInterest.
     */
    @Test
    public void testSetIdPoint() {
        System.out.println("setIdPoint");
        int idPoint = 4;
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        instance.setIdPoint(idPoint);
        assertEquals(idPoint, instance.getIdPoint());
    }

    /**
     * Test of hashCode method, of class PointInterest.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        int expResult = 206;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of hashCode method, of class GeographicalLocation.
     */
    @Test
    public void testHashCode2() {
        GeographicalLocation gt = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        PointInterest g = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        PointInterest g2 = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        Map<PointInterest, String> map = new HashMap<>();
        map.put(g, "dummy");
        Assert.assertEquals("dummy", map.get(g2));
    }

    /**
     * Test of equals method for different classes, of class PointInterest.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Object();
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method for null object, of class PointInterest.
     */
    @Test
    public void test2Equals() {
        System.out.println("equals");
        Object obj = null;
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method for same instance, of class PointInterest.
     */
    @Test
    public void test3Equals() {
        System.out.println("equals");
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        boolean expResult = true;
        boolean result = instance.equals(instance);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method for different objects, of class PointInterest.
     */
    @Test
    public void test4Equals() {
        System.out.println("equals");
        PointInterest obj = new PointInterest(2, new GeographicalLocation(2, 3, 4), "ponto2");
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method for equal objects, of class PointInterest.
     */
    @Test
    public void test5Equals() {
        System.out.println("equals");
        PointInterest obj = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        PointInterest instance = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
}
