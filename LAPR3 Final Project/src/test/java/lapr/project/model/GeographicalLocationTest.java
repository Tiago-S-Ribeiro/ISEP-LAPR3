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
 * @author G025
 */
public class GeographicalLocationTest {

    private GeographicalLocation geo;

    public GeographicalLocationTest() {
        geo = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
    }

    /**
     * Test of getLatitude method, of class GeographicalLocation.
     */
    @Test
    public void testGetLatitude() {
        double expected = -34.6131500;
        double obtained = geo.getLatitude();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setLatitude method, of class GeographicalLocation.
     */
    @Test
    public void testSetLatitude() {
        double expected = -40.6131500;
        geo.setLatitude(-40.6131500);
        double obtained = geo.getLatitude();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getLongitude method, of class GeographicalLocation.
     */
    @Test
    public void testGetLongitude() {
        double expected = -58.3772300;
        double obtained = geo.getLongitude();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setLongitude method, of class GeographicalLocation.
     */
    @Test
    public void testSetLongitude() {
        double expected = -60.4882300;
        geo.setLongitude(-60.4882300);
        double obtained = geo.getLongitude();
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of getElevation method, of class GeographicalLocation.
     */
    @Test
    public void testGetElevation() {
        double expected = 3.1;
        double obtained = geo.getElevation();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getElevation method, of class GeographicalLocation.
     */
    @Test
    public void testSetElevation() {
        double expected = 46.946;
        geo.setElevation(46.946);
        double obtained = geo.getElevation();
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of hashCode method, of class GeographicalLocation.
     */
    @Test
    public void testHashCode() {
    int expected = 389130125;
        int obtained = geo.hashCode();
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of hashCode method, of class GeographicalLocation.
     */
    @Test
    public void testHashCode2() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1); 
        GeographicalLocation g2 = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Map<GeographicalLocation, String> map = new HashMap<>();
        map.put(g, "dummy");
        Assert.assertEquals("dummy", map.get(g2));
    }
    
    /**
     * Test of equals method for equal objects, with the same reference, of class GeographicalLocation.
     */
    @Test
    public void testEquals() {
        boolean expected = true;
        boolean obtained = geo.equals(geo);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for null objects, of class GeographicalLocation.
     */
    @Test
    public void testEquals2() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        GeographicalLocation g2 = null;
        boolean expected = false;
        boolean obtained = g.equals(g2);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for objects of different classes, of class GeographicalLocation.
     */
    @Test
    public void testEquals3() {
        Scooter otherObject = new Scooter(1, 1, 75, 2, 1, "PT050", 12, 1.10, 0.3);
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park park = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        boolean expected = false;
        boolean obtained = otherObject.equals(g);
        boolean obtained2 = g.equals(park);
        assertEquals(expected, obtained);
        assertEquals(expected, obtained2);
    }

    /**
     * Test of equals method for different objects, of class GeographicalLocation.
     * Different LATITUDE
     */
    @Test
    public void testEquals4() {
        GeographicalLocation g1 = new GeographicalLocation(-37.6131500, -58.3772300, 3.1);
        GeographicalLocation g2 = new GeographicalLocation(-37.6112444, -58.3772300, 3.1);
        boolean expected = false;
        boolean obtained = g1.equals(g2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for different objects, of class GeographicalLocation.
     * Different LONGITUDE
     */
    @Test
    public void testEquals5() {
        GeographicalLocation g1 = new GeographicalLocation(-37.6131500, -58.3772300, 3.1);
        GeographicalLocation g2 = new GeographicalLocation(-37.6131500, -58.1111111, 3.1);
        boolean expected = false;
        boolean obtained = g1.equals(g2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for different objects, of class GeographicalLocation.
     * DIFFERENT LATITUDE AND LONGITUDE
     */
    @Test
    public void testEquals6() {
        GeographicalLocation g1 = new GeographicalLocation(-37.6131511, -58.3772300, 3.1);
        GeographicalLocation g2 = new GeographicalLocation(-34.6131500, -58.3772311, 3.1);
        boolean expected = false;
        boolean obtained = g1.equals(g2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for equal objects, of class GeographicalLocation.
     * DIFFERENT COORDINATES
     */
    @Test
    public void testEquals7() {
        GeographicalLocation g1 = new GeographicalLocation(-37.6131555, -58.3772300, 3.1);
        GeographicalLocation g2 = new GeographicalLocation(-34.6131500, -58.3772312, 3.7);
        boolean expected = false;
        boolean obtained = g1.equals(g2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for equal objects, of class GeographicalLocation.
     * DIFFERENT LATITUDE AND ELEVATION
     */
    @Test
    public void testEquals8() {
        GeographicalLocation g1 = new GeographicalLocation(-37.6131555, -58.3772300, 3.1);
        GeographicalLocation g2 = new GeographicalLocation(-34.6131500, -58.3772300, 3.7);
        boolean expected = false;
        boolean obtained = g1.equals(g2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for equal objects, of class GeographicalLocation.
     * DIFFERENT LONGITUDE AND ELEVATION
     */
    @Test
    public void testEquals9() {
        GeographicalLocation g1 = new GeographicalLocation(-37.6131555, -58.3772300, 3.1);
        GeographicalLocation g2 = new GeographicalLocation(-37.6131555, -58.3772311, 3.7);
        boolean expected = false;
        boolean obtained = g1.equals(g2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for equal objects, of class GeographicalLocation.
     * DIFFERENT ELEVATION
     */
    @Test
    public void testEquals10() {
        GeographicalLocation g1 = new GeographicalLocation(-37.6131555, -58.3772300, 3.1);
        GeographicalLocation g2 = new GeographicalLocation(-34.6131555, -58.3772300, 3.7);
        boolean expected = false;
        boolean obtained = g1.equals(g2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for equal objects, of class GeographicalLocation.
     */
    @Test
    public void testEquals11() {
        GeographicalLocation g1 = new GeographicalLocation(-37.6131500, -58.3772300, 5.5);
        GeographicalLocation g2 = new GeographicalLocation(-37.6131500, -58.3772300, 5.5);
        boolean expected = true;
        boolean obtained = g1.equals(g2);
        assertEquals(expected, obtained);
    }
}
