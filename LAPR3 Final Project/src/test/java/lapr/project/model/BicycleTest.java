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
 * @author Ana Rita Veiga
 */
public class BicycleTest {

    private Bicycle bike;

    public BicycleTest() {
        bike = new Bicycle(1, 19, 1, "PT050", 12, 1.10,0.3);
    }

    /**
     * Test of getWheel_size method, of class Bicycle.
     */
    @Test
    public void testGetWheelSize() {
        double obtido = bike.getWheelSize();
        double esperado = 19;
        assertEquals(esperado, obtido);
    }

    /**
     * Test of setWheel_size method, of class Bicycle.
     */
    @Test
    public void testSetWheelSize() {
        bike.setWheelSize(55);
        double obtido = bike.getWheelSize();
        double esperado = 55;
        assertEquals(esperado, obtido);
    }

    /**
     * Test of equals method, of class Bicycle.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Object();
        Bicycle instance = new Bicycle(2, 2, 1, "bicicleta1", 2.3, 4.77, 3.1);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class Bicycle.
     */
    @Test
    public void test2Equals() {
        System.out.println("equals");
        Bicycle obj = null;
        Bicycle instance = new Bicycle(2, 2, 1, "bicicleta1", 2.3, 4.77, 3.1);
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class Bicycle.
     */
    @Test
    public void test3Equals() {
        System.out.println("equals");
        Bicycle instance = new Bicycle(2, 2, 1, "bicicleta1", 2.3, 4.77, 3.1);
        boolean expResult = true;
        boolean result = instance.equals(instance);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class Bicycle.
     */
    @Test
    public void test4Equals() {
        System.out.println("equals");
        Bicycle b = new Bicycle(2, 5, 0, "bicicleta2", 14.3, 5, 6.1);
        Bicycle instance = new Bicycle(2, 2, 1, "bicicleta1", 2.3, 4.77, 3.1);
        boolean expResult = true;
        boolean result = instance.equals(b);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class Bicycle.
     */
    @Test
    public void test5Equals() {
        System.out.println("equals");
        Bicycle b = new Bicycle(1, 5, 0, "bicicleta2", 14.3, 5, 6.1);
        Bicycle instance = new Bicycle(2, 2, 1, "bicicleta1", 2.3, 4.77, 3.1);
        boolean expResult = false;
        boolean result = instance.equals(b);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Bicycle.
     */
    @Test
    public void testToString() {
        String obtido = bike.toString();
        String esperado = "Bycicle{" + "wheelSize=" + 19.0 + '}';
        assertEquals(esperado,obtido);
    }

    /**
     * Test of hashCode method, of class Bicycle.
     */
    @Test
    public void testHashCode() {
        int expected = 1072693871;
        int obtained = bike.hashCode();
        assertEquals(expected, obtained);
    }
   
    /**
     * Test of hashCode method, of class Bicycle.
     */
    @Test
    public void testHashCode2() {
        Bicycle b1 = new Bicycle(2, 2, 1, "bicicleta1", 2.3, 4.77, 3.1);
        Bicycle b2 = new Bicycle(2, 2, 1, "bicicleta1", 2.3, 4.77, 3.1);
        Map<Bicycle, String> map = new HashMap<>();
        map.put(b1, "dummy");
        Assert.assertEquals("dummy", map.get(b2));
    }

}
