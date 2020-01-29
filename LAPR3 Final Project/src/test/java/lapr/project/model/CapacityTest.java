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
 * @author jujps00
 */
public class CapacityTest {

    private final static int BICYCLETYPEID=1; 
    private final static int SCOOTERTYPEID = 2;

    private Capacity capacity;
    private Capacity capacity2;

    public CapacityTest() {
        capacity = new Capacity(BICYCLETYPEID, 30);
        capacity2 = new Capacity(SCOOTERTYPEID, 50);
        
    }

    /**
     * Test of getType method, of class Capacity.
     */
    @Test
    public void testGetType() {
        int expected = 1;
        int obtained = capacity.getType();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setType method, of class Capacity.
     */
    @Test
    public void testSetType() {
        int expected = 2;
        capacity.setType(SCOOTERTYPEID);
        int obtained = capacity.getType();
        assertEquals(expected, obtained);
    }

    /**
     * Test of hashCode method, of class Capacity.
     */
    @Test
    public void testHashCode() {
        int expected = 2944;
        int obtained = capacity.hashCode();
        assertEquals(expected, obtained);
    }

    /**
     * Test of hashCode method, of class GeographicalLocation.
     */
    @Test
    public void testHashCode2() {
        Capacity c1 = new Capacity(SCOOTERTYPEID, 30);
        Capacity c2 = new Capacity(SCOOTERTYPEID, 30);
        Map<Capacity, String> map = new HashMap<>();
        map.put(c1, "dummy");
        Assert.assertEquals("dummy", map.get(c2));
    }

    /**
     * Test of equals method for equal objects, with the same reference, of
     * class Capacity.
     */
    @Test
    public void testEquals1() {
        boolean expected = true;
        boolean obtained = capacity.equals(capacity);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for null object, of class Capacity.
     */
    @Test
    public void testEquals2() {
        Capacity c = new Capacity(SCOOTERTYPEID, 30);
        Park otherObject = null;
        boolean expected = false;
        boolean obtained = c.equals(otherObject);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for object of differents classes, of class
     * Capacity.
     */
    @Test
    public void testEquals3() {
        Capacity object = new Capacity(SCOOTERTYPEID, 20);
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park otherObject = new Park("parkReference", "Park Senhora da Hora", 1, 220, 16, g);
        boolean expected = false;
        boolean obtained = object.equals(otherObject);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for different objects, of class Capacity. DIFFERENT
     * TYPE
     */
    @Test
    public void testEquals4() {
        Capacity object = new Capacity(SCOOTERTYPEID, 20);
        Capacity otherObject = new Capacity(BICYCLETYPEID, 20);
        boolean expected = false;
        boolean obtained = object.equals(otherObject);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for different objects, of class Capacity. DIFFERENT
     * MAX CAPACITY
     */
    @Test
    public void testEquals5() {
        Capacity object = new Capacity(SCOOTERTYPEID, 20);
        Capacity otherObject = new Capacity(SCOOTERTYPEID, 30);
        boolean expected = false;
        boolean obtained = object.equals(otherObject);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for different objects, of class Capacity. DIFFERENT
     * TYPE AND MAX CAPACITY
     */
    @Test
    public void testEquals6() {
        Capacity object = new Capacity(SCOOTERTYPEID, 20);
        Capacity otherObject = new Capacity(BICYCLETYPEID, 55);
        boolean expected = false;
        boolean obtained = object.equals(otherObject);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for equal objects, of class Capacity.
     */
    @Test
    public void testEquals7() {
        Capacity object = new Capacity(SCOOTERTYPEID, 20);
        Capacity otherObject = new Capacity(SCOOTERTYPEID, 20);
        boolean expected = true;
        boolean obtained = object.equals(otherObject);
        assertEquals(expected, obtained);
    }

    /**
     * Test of getCapacityMax method, of class Capacity.
     */
    @Test
    public void testGetCapacityMax() {
        System.out.println("getCapacityMax");
        int expResult = 30;
        int result = capacity.getCapacityMax();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCapacityMax method, of class Capacity.
     */
    @Test
    public void testSetCapacityMax() {
        System.out.println("setCapacityMax");
        int expResult = 46;
        capacity.setCapacityMax(expResult);
        int result = capacity.getCapacityMax();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAvailability method, of class Capacity.
     */
    @Test
    public void testGetAvailability() {
        System.out.println("getAvailability");
        int expResult = 50;
        int result = capacity2.getAvailability();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAvailability method, of class Capacity.
     */
    @Test
    public void testSetAvailability() {
        System.out.println("setAvailability");
        int availability = 33;
        capacity2.setAvailability(availability);
        int result = capacity2.getAvailability();
        assertEquals(availability, result);
    }
}
