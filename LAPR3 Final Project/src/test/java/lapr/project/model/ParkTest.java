/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author G25
 */
public class ParkTest {

    private final static int BICYCLETYPEID = 1;
    private final static int SCOOTERTYPEID = 2;

    private Park park;
    private Park park2;

    public ParkTest() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        park = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        park2 = new Park(46, "parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetId() {
        int expected = 0;
        int obtained = park.getId();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getId method, of class Park.
     */
    @Test
    public void testGetId2() {
        park.setId(46);
        int expected = 46;
        int obtained = park.getId();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setId method, of class Park.
     */
    @Test
    public void testSetId() {
        int expected = 2;
        park2.setId(2);
        int obtained = park2.getId();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getDescription method, of class Park.
     */
    @Test
    public void testGetRefPark() {
        String expected = "parkReference";
        String obtained = park.getRefPark();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setDescription method, of class Park.
     */
    @Test
    public void testSetRefPark() {
        String expected = "VALENTINO_ROSSI_46";
        park.setRefPark("VALENTINO_ROSSI_46");
        String obtained = park.getRefPark();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getDescription method, of class Park.
     */
    @Test
    public void testGetDescription() {
        String expected = "Park Campus Sao Joao";
        String obtained = park.getDescription();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setDescription method, of class Park.
     */
    @Test
    public void testSetDescription() {
        String expected = "Park Senhora da Hora";
        park.setDescription("Park Senhora da Hora");
        String obtained = park.getDescription();
        assertEquals(expected, obtained);
    }

    /**
     * Test of isState method, of class Park.
     */
    @Test
    public void testIsState() {
        int expected = 1;
        int obtained = park.isState();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setState method, of class Park.
     */
    @Test
    public void testSetState() {
        int expected = 0;
        park.setState(0);
        int obtained = park.isState();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getPark_input_voltage method, of class Park.
     */
    @Test
    public void testGetPark_input_voltage() {
        int expected = 220;
        int obtained = park.getParkInputVoltage();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setPark_input_voltage method, of class Park.
     */
    @Test
    public void testSetPark_input_voltage() {
        int expected = 300;
        park.setParkInputVoltage(300);
        int obtained = park.getParkInputVoltage();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getPark_input_current method, of class Park.
     */
    @Test
    public void testGetPark_input_current() {
        int expected = 16;
        int obtained = park.getParkInputCurrent();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setPark_input_current method, of class Park.
     */
    @Test
    public void testSetPark_input_current() {
        int expected = 20;
        park.setParkInputCurrent(20);
        int obtained = park.getParkInputCurrent();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getGeoLocation method, of class Park.
     */
    @Test
    public void testGetGeoLocation() {
        GeographicalLocation expected = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        GeographicalLocation obtained = park.getGeoLocation();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setGeoLocation method, of class Park.
     */
    @Test
    public void testSetGeoLocation() {
        GeographicalLocation expected = new GeographicalLocation(-38.6131500, -41.3772300, 3.1);
        park.setGeoLocation(new GeographicalLocation(-38.6131500, -41.3772300, 3.1));
        GeographicalLocation obtained = park.getGeoLocation();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getCapacity method, of class Park.
     */
    @Test
    public void testGetCapacity() {
        List<Capacity> expected = new LinkedList<>();
        expected.add(new Capacity(BICYCLETYPEID, 30));
        expected.add(new Capacity(SCOOTERTYPEID, 30));

        List<Capacity> aux = new LinkedList<>();
        aux.add(new Capacity(BICYCLETYPEID, 30));
        aux.add(new Capacity(SCOOTERTYPEID, 30));
        park.setCapacity(aux);

        List<Capacity> obtained = park.getCapacity();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setCapacity method, of class Park.
     */
    @Test
    public void testSetCapacity() {
        List<Capacity> expected = new LinkedList<>();
        expected.add(new Capacity(BICYCLETYPEID, 40));
        expected.add(new Capacity(SCOOTERTYPEID, 70));

        LinkedList<Capacity> list = new LinkedList<>();
        list.add(new Capacity(BICYCLETYPEID, 40));
        list.add(new Capacity(SCOOTERTYPEID, 70));
        park.setCapacity(list);
        List<Capacity> obtained = park.getCapacity();
        assertEquals(expected, obtained);
    }

    /**
     * Test of hashCode method, of class GeographicalLocation.
     */
    @Test
    public void testHashCode() {
        int expected = 329;
        int obtained = park.hashCode();
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of hashCode method, of class Park.
     */
    @Test
    public void testHashCode2() {
        park2.setId(25);
        int count = 47*7 + 25;
        int expected = count;
        int obtained = park2.hashCode();
        
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park testHash = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        testHash.setId(55);
        int expected2 = 47*7 + 55;
        int obtained2 = testHash.hashCode();
        
        assertEquals(expected, obtained);
        assertEquals(expected2, obtained2);
    }
    
    /**
     * Test of hashCode method, of class Park.
     */
    @Test
    public void testEquals_Symmetric() {
        Park x = park;
        Park y = park2;
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }

    /**
     * Test of hashCode method, of class Park.
     */
    @Test
    public void testHashCode3() {
        GeographicalLocation gt = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park g = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, gt);
        Park g2 = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, gt);
        Map<Park, String> map = new HashMap<>();
        map.put(g, "dummy");
        Assert.assertEquals("dummy", map.get(g2));
    }

    /**
     * Test of equals method for equal objects, with the same reference, of class Park.
     */
    @Test
    public void testEquals() {
        boolean expected = true;
        boolean obtained = park.equals(park);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for null object, of class Park.
     */
    @Test
    public void testEquals2() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park object = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        Park otherObject = null;
        boolean expected = false;
        boolean obtained = object.equals(otherObject);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for object of differents classes, of class Park.
     */
    @Test
    public void testEquals3() {
        Scooter object = new Scooter(1, 1, 75, 2, 1, "PT050", 12, 1.10, 0.3);
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park otherObject = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        boolean expected = false;
        boolean obtained = object.equals(otherObject);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for different objects, of class Park.
     */
    @Test
    public void testEquals4() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park object = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        Park otherObject = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        otherObject.setId(46);
        boolean expected = false;
        boolean obtained = object.equals(otherObject);
        assertEquals(expected, obtained);
    }

    /**
     * Test of equals method for equal objects, of class Park.
     */
    @Test
    public void testEquals5() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park object = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        Park otherObject = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        boolean expected = true;
        boolean obtained = object.equals(otherObject);
        assertEquals(expected, obtained);
    }

    /**
     * Test of getParkInputVoltage method, of class Park.
     */
    @Test
    public void testGetParkInputVoltage() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park instance = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        int expResult = 220;
        int result = instance.getParkInputVoltage();
        assertEquals(expResult, result);
    }

    /**
     * Test of setParkInputVoltage method, of class Park.
     */
    @Test
    public void testSetParkInputVoltage() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park instance = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        instance.setParkInputVoltage(46);
        int expResult = 46;
        int result = instance.getParkInputVoltage();
        assertEquals(expResult, result);
    }

    /**
     * Test of getParkInputCurrent method, of class Park.
     */
    @Test
    public void testGetParkInputCurrent() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park instance = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        int expResult = 16;
        int result = instance.getParkInputCurrent();
        assertEquals(expResult, result);
    }

    /**
     * Test of setParkInputCurrent method, of class Park.
     */
    @Test
    public void testSetParkInputCurrent() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park instance = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        instance.setParkInputCurrent(78);
        int expResult = 78;
        int result = instance.getParkInputCurrent();
        assertEquals(expResult, result);
    }
}
