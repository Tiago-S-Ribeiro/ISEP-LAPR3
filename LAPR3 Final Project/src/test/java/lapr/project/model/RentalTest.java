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
 * @author TiagoRibeiro
 */
public class RentalTest {
    
    private User user;
    private Vehicle vehicle;
    private Park parkFrom;
    private Rental instance;
    
    public RentalTest() {
        user = new User(1, "test@mail.pt", "test1", "12345678", 3 ,160 , 45, "F", "test1_1");
        vehicle = new Vehicle(1, 1, "vehicle1", 20, 0.3, 0.5);
        parkFrom = new Park(1, "park1", "Park 1", 1, 250, 16, new GeographicalLocation(41, -8, 0));
        instance = new Rental(user, vehicle, parkFrom);
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
     * Test of getID method, of class Rental.
     */
    @Test
    public void testGetIdRental() {
        
        int expected = 30;
        instance.setIdRental(30);
        int result = instance.getIdRental();
        assertEquals(expected, result);
    }

    /**
     * Test of getCost method, of class Rental.
     */
    @Test
    public void testGetCost() {
        
        double expected = 10.0;
        instance.setCost(10.0);
        double result = instance.getCost();
        assertEquals(expected, result);
    }

    /**
     * Test of getBeginDateHour method, of class Rental.
     */
    @Test
    public void testSetBeginDateHour() {
        instance.setBeginDateHour("01/07/2020 12:00:00");
        String expected = "01/07/2020 12:00:00";
        String result = instance.getBeginDateHour();
        assertEquals(expected, result);
    }

    /**
     * Test of getDuration method, of class Rental.
     */
    @Test
    public void testGetDuration() {
        instance.setDuration(20);
        int expected = 20;
        int result = instance.getDuration();
        assertEquals(expected, result);
    }

    /**
     * Test of setID method, of class Rental.
     */
    @Test
    public void testSetIdRental() {
       
        instance.setIdRental(1111);
        int expected = 1111;
        int result = instance.getIdRental();
        assertEquals(expected, result);
    }

    /**
     * Test of setCost method, of class Rental.
     */
    @Test
    public void testSetCost() {
        instance.setCost(11.11);
        double expected = 11.11;
        double result = instance.getCost();
        assertEquals(expected, result);
    }

    /**
     * Test of setBeginDateHour method, of class Rental.
     */
    @Test
    public void testSetDate() {
        instance.setBeginDateHour("20/11/2019 13:00:00");
        String expected = "20/11/2019 13:00:00";
        String result = instance.getBeginDateHour();
        assertEquals(expected, result);
    }

    /**
     * Test of setDuration method, of class Rental.
     */
    @Test
    public void testSetDuration() {
        instance.setDuration(55);
        int expected = 55;
        int result = instance.getDuration();
        assertEquals(expected, result);
    }

    /**
     * Test of getUser method, of class Rental.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        User expResult = new User(1, "test@mail.pt", "test1", "12345678", 3 ,160 , 45, "F", "test1_1");;
        User result = instance.getUser();
        assertEquals(expResult.getIdUser(), result.getIdUser());
        
    }

    /**
     * Test of getVehicle method, of class Rental.
     */
    @Test
    public void testGetVehicle() {
        System.out.println("getVehicle");
        Vehicle expResult = new Vehicle(1, 1, "vehicle1", 20, 0.3, 0.5);
        Vehicle result = instance.getVehicle();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getParkFrom method, of class Rental.
     */
    @Test
    public void testGetParkFrom() {
        System.out.println("getParkFrom");
        Park expResult = new Park(1, "park1", "Park 1", 1, 250, 16, new GeographicalLocation(41, -8, 0));
        Park result = instance.getParkFrom();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getParkTo method, of class Rental.
     */
    @Test
    public void testGetParkTo() {
        System.out.println("getParkTo");
        Park parkTo = new Park(1, "park1", "Park 1", 1, 250, 16, new GeographicalLocation(41, -8, 0));
        instance.setParkTo(parkTo);
        Park expResult = new Park(1, "park1", "Park 1", 1, 250, 16, new GeographicalLocation(41, -8, 0));
        Park result = instance.getParkTo();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getEndDateHour method, of class Rental.
     */
    @Test
    public void testGetEndDateHour() {
        System.out.println("getEndDateHour");
        instance.setEndDateHour("20/11/2019 14:00:00");
        String expResult = "20/11/2019 14:00:00";
        String result = instance.getEndDateHour();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setUser method, of class Rental.
     */
    @Test
    public void testSetUser() {
        System.out.println("setUser");
        User user = new User(1, "test@mail.pt", "test1", "12345678", 4 ,160 , 45, "F", "test1_1");
        User expResult = user;
        instance.setUser(user);
        assertEquals(expResult.getIdUser(), instance.getUser().getIdUser());
    }

    /**
     * Test of setVehicle method, of class Rental.
     */
    @Test
    public void testSetVehicle() {
        System.out.println("setVehicle");
        Vehicle vehicle = new Vehicle(1, 1, "vehicle1", 30, 0.3, 0.5);
        instance.setVehicle(vehicle);
        assertEquals(new Vehicle(1, 1, "vehicle1", 30, 0.3, 0.5), instance.getVehicle());
    }

    /**
     * Test of setParkFrom method, of class Rental.
     */
    @Test
    public void testSetParkFrom() {
        System.out.println("setParkFrom");
        Park parkFrom = new Park(1, "park1", "Park 1", 1, 255, 16, new GeographicalLocation(41, -8, 0));
        instance.setParkFrom(parkFrom);
        assertEquals(new Park(1, "park1", "Park 1", 1, 255, 16, new GeographicalLocation(41, -8, 0)), instance.getParkFrom());
    }

    /**
     * Test of setParkTo method, of class Rental.
     */
    @Test
    public void testSetParkTo() {
        System.out.println("setParkTo");
        Park parkTo = new Park(2, "park2", "Park 2", 1, 255, 16, new GeographicalLocation(41, -8, 0));
        instance.setParkTo(parkTo);
        assertEquals(new Park(2, "park2", "Park 2", 1, 255, 16, new GeographicalLocation(41, -8, 0)), instance.getParkTo());
    }

    /**
     * Test of setEndDateHour method, of class Rental.
     */
    @Test
    public void testSetEndDateHour() {
        System.out.println("setEndDateHour");
        String endDateHour = "20/11/2019 17:00:00";
        instance.setEndDateHour(endDateHour);
        assertEquals(endDateHour, "20/11/2019 17:00:00");
    }
    
    /**
     * Test of getEarnedPoints method, of class Rental.
     */
    @Test
    public void testGetEarnedPoints() {
        System.out.println("getEarnedPoints");
        instance.setEarnedPoints(5);
        int expResult = 5;
        int result = instance.getEarnedPoints();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setEarnedPoints method, of class Rental.
     */
    @Test
    public void testSetEarnedPoints() {
        System.out.println("setEarnedPoints");
        int earnedPoints = 10;
        instance.setEarnedPoints(earnedPoints);
        int expResult = 10;
        int result = instance.getEarnedPoints();
        assertEquals(expResult, result);
    }


}
