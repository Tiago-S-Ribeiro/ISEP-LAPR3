/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author G025
 */
public class ScooterTest {

    private Scooter scooter;

    public ScooterTest() {
        scooter = new Scooter(Scooter.SCOOTER_CITY_ID, 1, 75, 250 ,2, 1, "PT050", 12, 1.10, 0.3);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of getType method, of class Scooter.
     */
    @Test
    public void testGetType() {
        int obtido = scooter.getType();
        int esperado = Scooter.SCOOTER_CITY_ID;
        assertEquals(esperado, obtido);
    }

    /**
     * Test of setType method, of class Scooter.
     */
    @Test
    public void testSetType() {
        scooter.setType(Scooter.SCOOTER_OFF_ROAD_ID);
        int obtido = scooter.getType();
        int esperado = Scooter.SCOOTER_OFF_ROAD_ID;
        assertEquals(esperado, obtido);
    }

    /**
     * Test of getMax_batery_capacity method, of class Scooter.
     */
    @Test
    public void testGetMax_batery_capacity() {
        double obtido = scooter.getMaxBatteryCapacity();
        double esperado = 1;
        assertEquals(esperado, obtido);
    }

    /**
     * Test of setMax_batery_capacity method, of class Scooter.
     */
    @Test
    public void testSetMax_batery_capacity() {
        scooter.setMaxBatteryCapacity(0);
        double obtido = scooter.getMaxBatteryCapacity();
        double esperado = 0;
        assertEquals(esperado, obtido);
    }

    /**
     * Test of getActual_batery_capacity method, of class Scooter.
     */
    @Test
    public void testGetActual_batery_capacity() {
        double obtido = scooter.getActualBatteryCapacity();
        double esperado = 75;
        assertEquals(esperado, obtido);
    }

    /**
     * Test of setActual_batery_capacity method, of class Scooter.
     */
    @Test
    public void testSetActual_batery_capacity() {
        scooter.setActualBatteryCapacity(0);
        double obtido = scooter.getActualBatteryCapacity();
        double esperado = 0;
        assertEquals(esperado, obtido);
    }
    
    /**
     * Test of hashCode method, of class GeographicalLocation.
     */
    @Test
    public void testHashCode() {
    int expected = 2757009;
        int obtained = scooter.hashCode();
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of hashCode method, of class GeographicalLocation.
     */
    @Test
    public void testHashCode2() {
        Scooter scoot1 = new Scooter(1, 1, 75, 2, 1, "PT050", 12, 1.10, 0.3); 
        Scooter scoot2 = new Scooter(1, 1, 75, 2, 1, "PT050", 12, 1.10, 0.3);
        Map<Scooter, String> map = new HashMap<>();
        map.put(scoot1, "dummy");
        Assert.assertEquals("dummy", map.get(scoot2));
    }
    
    /**
     * Test of equals method for equal objects, with the same reference, of class Scooter.
     */
    @Test
    public void testEquals() {
        boolean expected = true;
        boolean obtained = scooter.equals(scooter);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for null objects, of class Scooter.
     */
    @Test
    public void testEquals2() {
        Scooter scoot = null;
        Scooter scoot2 = scooter = new Scooter(Scooter.SCOOTER_OFF_ROAD_ID, 1, 75, 2, 1, "PT050", 12, 1.10, 0.3);
        boolean expected = false;
        boolean obtained = scoot2.equals(scoot);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for objects of different classes, of class Scooter.
     */
    @Test
    public void testEquals3() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park park = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        Scooter scoot = new Scooter(Scooter.SCOOTER_OFF_ROAD_ID, 1, 75, 2, 1, "PT050", 12, 1.10, 0.3);
        boolean expected = false;
        boolean obtained = park.equals(scoot);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for different objects, of class Scooter.
     * DIFFERENT TYPE, MAX AND ACTUAL BATTERY
     */
    @Test
    public void testEquals4() {
        Scooter scoot1 = new Scooter(Scooter.SCOOTER_OFF_ROAD_ID, 10, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        Scooter scoot2 = new Scooter(Scooter.SCOOTER_CITY_ID, 9, 5, 2, 1, "PT040", 12, 1.10, 0.3);
        boolean expected = false;
        boolean obtained = scoot1.equals(scoot2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for different objects, of class Scooter.
     * DIFFERENT TYPE
     */
    @Test
    public void testEquals5() {
        Scooter scoot1 = new Scooter(Scooter.SCOOTER_CITY_ID, 10, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        Scooter scoot2 = new Scooter(Scooter.SCOOTER_OFF_ROAD_ID, 10, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        boolean expected = false;
        boolean obtained = scoot1.equals(scoot2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for different objects, of class Scooter.
     * DIFFERENT TYPE AND MAX BATTERY
     */
    @Test
    public void testEquals6() {
        Scooter scoot1 = new Scooter(Scooter.SCOOTER_CITY_ID, 10, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        Scooter scoot2 = new Scooter(Scooter.SCOOTER_OFF_ROAD_ID, 30, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        boolean expected = false;
        boolean obtained = scoot1.equals(scoot2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for different objects, of class Scooter.
     * DIFFERENT MAX BATTERY
     */
    @Test
    public void testEquals7() {
        Scooter scoot1 = new Scooter(Scooter.SCOOTER_CITY_ID, 10, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        Scooter scoot2 = new Scooter(Scooter.SCOOTER_CITY_ID, 11, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        boolean expected = false;
        boolean obtained = scoot1.equals(scoot2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for different objects, of class Scooter.
     * DIFFERENT ACTUAL BATTERY
     */
    @Test
    public void testEquals8() {
        Scooter scoot1 = new Scooter(Scooter.SCOOTER_CITY_ID, 10, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        Scooter scoot2 = new Scooter(Scooter.SCOOTER_OFF_ROAD_ID, 10, 2, 2, 1, "PT050", 12, 1.10, 0.3);
        boolean expected = false;
        boolean obtained = scoot1.equals(scoot2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for different objects, of class Scooter.
     * DIFFERENT MAX ACTUAL BATTERY
     */
    @Test
    public void testEquals9() {
        Scooter scoot1 = new Scooter(Scooter.SCOOTER_CITY_ID, 10, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        Scooter scoot2 = new Scooter(Scooter.SCOOTER_CITY_ID, 17, 2, 2, 1, "PT050", 12, 1.10, 0.3);
        boolean expected = false;
        boolean obtained = scoot1.equals(scoot2);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method for different objects, of class Scooter.
     * DIFFERENT TYPE AND ACTUAL BATTERY
     */
    @Test
    public void testEquals10() {
        Scooter scoot1 = new Scooter(Scooter.SCOOTER_CITY_ID, 10, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        Scooter scoot2 = new Scooter(Scooter.SCOOTER_CITY_ID, 10, 5, 2, 1, "PT050", 12, 1.10, 0.3);
        boolean expected = false;
        boolean obtained = scoot1.equals(scoot2);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for equal objects, of class Scooter.
     */
    @Test
    public void testEquals11() {
        Scooter scoot1 = new Scooter(Scooter.SCOOTER_CITY_ID, 10, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        Scooter scoot2 = new Scooter(Scooter.SCOOTER_CITY_ID, 10, 9, 2, 1, "PT050", 12, 1.10, 0.3);
        boolean expected = true;
        boolean obtained = scoot1.equals(scoot2);
        assertEquals(expected, obtained);
    }

    /**
     * Test of toString method, of class Scooter.
     */
    @Test
    public void testToString() {
        String obtido = scooter.toString();
        String esperado = "Scooter{" + "type=" + Scooter.SCOOTER_CITY_ID + ",max_batery_capacity=" + 1 + ", actual_batery_capacity=" + 75 + '}';

        System.out.println(obtido);
        assertEquals(esperado, obtido);
    }

    /**
     * Test of getMotor method, of class Scooter.
     */
    @Test
    public void testGetMotor() {
        System.out.println("getMotor");
        int expResult = 250;
        int result = scooter.getMotor();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setMotor method, of class Scooter.
     */
    @Test
    public void testSetMotor() {
        System.out.println("setMotor");
        int motor = 240;
        scooter.setMotor(motor);
        int result = scooter.getMotor();
        assertEquals(motor, result);
        
    }
}
