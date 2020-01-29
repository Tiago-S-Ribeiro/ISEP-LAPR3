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
 * @author G25
 */
public class VehicleTest {
    
    private Vehicle v1;
    private Vehicle v2;
    
    public VehicleTest() {
        v1 = new Vehicle(46, 1, "coolBike", 3.1, 1.0, 2.5);
        v2 = new Vehicle(1, "coolBike", 3.1, 1.0, 2.5);
    }

    /**
     * Test of getId method, of class Vehicle.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        int expResult = 46;
        int result = v1.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Vehicle.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int expResult = 88;
        v2.setId(expResult);
        int result = v2.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of isState method, of class Vehicle.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        int expResult = 1;
        int result = instance.getState();
        assertEquals(expResult, result);
    }

    /**
     * Test of setState method, of class Vehicle.
     */
    @Test
    public void testSetState() {
        System.out.println("setState");
        int state = 0;
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        Vehicle expResult = new Vehicle(1, 0, "v", 12, 1.10,0.3);
        instance.setState(state);
        assertEquals(expResult, instance);
    }

    /**
     * Test of getDescription method, of class Vehicle.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        String expResult = "v";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDescription method, of class Vehicle.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = "v2";
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        Vehicle expResult = new Vehicle(1, 1, "v2", 12, 1.10,0.3);
        instance.setDescription(description);
        assertEquals(expResult, instance);
    }

    /**
     * Test of getWeight method, of class Vehicle.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        double expResult = 12;
        double result = instance.getWeight();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setWeight method, of class Vehicle.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        double weight = 15.9;
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        Vehicle expResult = new Vehicle(1, 1, "v", 15.9, 1.10,0.3);
        instance.setWeight(weight);
        assertEquals(expResult, instance);
    }

    /**
     * Test of getAerodynamicCoefficient method, of class Vehicle.
     */
    @Test
    public void testGetAerodynamicCoefficient() {
        System.out.println("getAerodynamicCoefficient");
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        double expResult = 1.10;
        double result = instance.getAerodynamicCoefficient();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setAerodynamicCoefficient method, of class Vehicle.
     */
    @Test
    public void testSetAerodynamicCoefficient() {
        System.out.println("setAerodynamicCoefficient");
        double aerodynamicCoefficient = 3.4;
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        Vehicle expResult = new Vehicle(1, 1, "v", 12, 3.4,0.3);
        instance.setAerodynamicCoefficient(aerodynamicCoefficient);
        assertEquals(expResult, instance);
    }

    /**
     * Test of getFrontalArea method, of class Vehicle.
     */
    @Test
    public void testGetFrontalArea() {
        System.out.println("getFrontalArea");
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        double expResult = 0.3;
        double result = instance.getFrontalArea();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setFrontalArea method, of class Vehicle.
     */
    @Test
    public void testSetFrontalArea() {
        System.out.println("setFrontalArea");
        double frontalArea = 2.2;
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        Vehicle expResult = new Vehicle(1, 1, "v", 12, 1.10,2.2);
        instance.setFrontalArea(frontalArea);
        assertEquals(expResult, instance);
    }

    /**
     * Test of hashCode method, of class Vehicle.
     */
    @Test
    public void testHashCode() {
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        int expected = 498;
        int obtained = instance.hashCode();
        assertEquals(expected, obtained);
    }
   
    /**
     * Test of hashCode method, of class Vehicle.
     */
    @Test
    public void testHashCode2() {
        Vehicle v1 = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        Vehicle v2 = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        Map<Vehicle, String> map = new HashMap<>();
        map.put(v1, "dummy");
        Assert.assertEquals("dummy", map.get(v2));
    }

    /**
     * Test of equals method, of class Vehicle.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Object();
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class Vehicle.
     */
    @Test
    public void test2Equals() {
        System.out.println("equals");
        Vehicle obj = null;
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class Vehicle.
     */
    @Test
    public void test3Equals() {
        System.out.println("equals");
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        boolean expResult = true;
        boolean result = instance.equals(instance);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class Vehicle.
     */
    @Test
    public void test4Equals() {
        System.out.println("equals");
        Vehicle obj = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class Vehicle.
     */
    @Test
    public void test5Equals() {
        System.out.println("equals");
        Vehicle obj = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        Vehicle instance = new Vehicle(2, 1, "v", 12, 1.10,0.3);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of toString method, of class Vehicle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Vehicle instance = new Vehicle(1, 1, "v", 12, 1.10,0.3);
        String expResult = "Vehicle{id=1, state=1, description=v, weight=12.0, aerodynamic_coefficient=1.1, frontal_area=0.3}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
