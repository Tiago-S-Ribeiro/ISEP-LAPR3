/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import lapr.project.data.VehicleDataHandler;
import lapr.project.model.Bicycle;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;

/**
 *
 * @author Tiago Ribeiro
 */
public class VehicleControllerTest {

    private static VehicleController instance;

    public VehicleControllerTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {
        VehicleDataHandler vehicleDataHandlerMock = mock(VehicleDataHandler.class);
        Bicycle bicycle = new Bicycle(10, 1, "teste", 20, 0.5, 0.9);
        Vehicle v = new Vehicle(46, 1, "Rossi", 3.1, 1.0, 2.5);
        when(vehicleDataHandlerMock.addBicycle(bicycle, 1)).thenReturn(1);

        Scooter scooter = new Scooter(1, 100, 30,250, 1, "teste", 10, 0.8, 0.99);
        when(vehicleDataHandlerMock.addScooter(scooter, 1)).thenReturn(2);

        when(vehicleDataHandlerMock.removeVehicle(1)).thenReturn(Boolean.TRUE);

        when(vehicleDataHandlerMock.getUnlockedVehicles()).thenReturn(new HashMap<>());
        
        when(vehicleDataHandlerMock.getAllBicycles()).thenReturn(new ArrayList<>());

        when(vehicleDataHandlerMock.getBicyclesOfPark(any(Integer.class))).thenReturn(new ArrayList<>());
        
        when(vehicleDataHandlerMock.getBicycleByDescription(any(String.class))).thenReturn(bicycle);
        
        Scooter yamahaM1 = new Scooter(Scooter.SCOOTER_CITY_ID, 1, 75, 250 ,2, 1, "VALENTINO_ROSSI", 12, 1.10, 0.3);
        when(vehicleDataHandlerMock.getScooterByDescription(any(String.class))).thenReturn(yamahaM1);
        
        when(vehicleDataHandlerMock.getById(1)).thenReturn(v);
        instance = new VehicleController(vehicleDataHandlerMock);

    }

    /**
     * Test of addScooter method, of class VehicleController.
     *
     * @throws java.sql.SQLException
     */
    @Test
    public void testAddScooter() throws SQLException {
        System.out.println("addScooter");
        int idPark = 1;
        int type = 1;
        int maxBatteryCapacity = 100;
        int actualBattteryCapacity = 30;
        int state = 1;
        String description = "teste";
        double weight = 10;
        double aerodynamicCoefficient = 0.8;
        double frontalArea = 0.99;
        int motor = 250;
        Vehicle expResult = new Scooter(type, maxBatteryCapacity, actualBattteryCapacity, 2, state, description, weight, aerodynamicCoefficient, frontalArea);
        Vehicle result = instance.addScooter(idPark, type, maxBatteryCapacity, actualBattteryCapacity, motor,state, description, weight, aerodynamicCoefficient, frontalArea);
        assertEquals(expResult, result);


    }

    /**
     * Test of addBicycle method, of class VehicleController.
     *
     * @throws java.sql.SQLException
     */
    @Test
    public void testAddBicycle() throws SQLException {
        System.out.println("addBicycle");
        int idPark = 1;
        double wheelSize = 10;
        int state = 1;
        String description = "teste";
        double weight = 20;
        double aerodynamicCoefficient = 0.5;
        double frontalArea = 0.9;
        Vehicle expResult = new Bicycle(1, 10, 1, "teste", 20, 0.5, 0.9);
        Vehicle result = instance.addBicycle(idPark, wheelSize, state, description, weight, aerodynamicCoefficient, frontalArea);
        assertEquals(expResult, result);


    }

    /**
     * Test of removeVehicle method, of class VehicleController.
     *
     * @throws java.sql.SQLException
     */
    @Test
    public void testRemoveVehicle() throws SQLException {
        System.out.println("removeVehicle");
        int idVehicle = 1;
        boolean expResult = true;
        boolean result = instance.removeVehicle(idVehicle);
        assertEquals(expResult, result);

       

    }

    /**
     * Test of getUnlockedVehicles method, of class VehicleController.
     */
    @Test
    public void testGetUnlockedVehicles() throws SQLException {
        System.out.println("getUnlockedVehicles");
        Map<Pair<Integer, String>, Pair<Integer, String>> expResult = new HashMap<>();
        Map<Pair<Integer, String>, Pair<Integer, String>> result = instance.getUnlockedVehicles();
        assertEquals(expResult, result);

        
    }

    /**
     * Test of getAllBicycles method, of class VehicleController.
     */
    @Test
    public void testGetAllBicycles() throws SQLException {
        System.out.println("getAllBicycles");
       
        List<Bicycle> expResult = new ArrayList<>();
        List<Bicycle> result = instance.getAllBicycles();
        assertEquals(expResult, result);
        
        
    }

    
    /**
     * Test of getAllBicyclesOfPark method, of class VehicleController.
     */
    @Test
    public void testGetAllBicyclesOfPark() throws SQLException {
        System.out.println("getAllBicyclesOfPark");
        int idPark = 0;

        List<Bicycle> expResult = new ArrayList<>();
        List<Bicycle> result = instance.getAllBicyclesOfPark(idPark);
        assertEquals(expResult, result);
        
        
    }

     /**
     * Test of getAllScootersOfPark method, of class VehicleController.
     */
    @Test
    public void testGetAllScootersOfPark() throws SQLException {
        System.out.println("getAllScootersOfPark");
        int idPark = 0;

        List<Scooter> expResult = new ArrayList<>();
        List<Scooter> result = instance.getAllScootersOfPark(idPark);
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of getScootersDontHavecapacityToPerformEstimatedTrip method, of class VehicleController.
     */
    @Test
    public void testGetScootersDontHavecapacityToPerformEstimatedTrip() throws SQLException {
     System.out.println("getScootersDontHavecapacityToPerformEstimatedTrip");
        int idPark = 0;
        int distance =0;
        double userCyclingAverageSpeed=0;
        List<Scooter> expResult = new ArrayList<>();
        List<Scooter> result = instance.getScootersDontHavecapacityToPerformEstimatedTrip(distance, idPark, userCyclingAverageSpeed);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBicycleByDescription() throws SQLException {
        System.out.println("getBicycleByDescription");
        Bicycle expResult = new Bicycle(10, 1, "teste", 20, 0.5, 0.9);
        String testString = "teste";
        Bicycle result = instance.getBicycleByDescription(testString);
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetScooterByDescription() throws SQLException {
        System.out.println("getScooterByDescription");
        Scooter expResult = new Scooter(Scooter.SCOOTER_CITY_ID, 1, 75, 250 ,2, 1, "VALENTINO_ROSSI", 12, 1.10, 0.3);
        String testString = "VALENTINO_ROSSI";
        Scooter result = instance.getScooterByDescription(testString);
        
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetById(){
        System.out.println("getById");
        Vehicle vr46 = new Vehicle(46, 1, "Rossi", 3.1, 1.0, 2.5);
        Vehicle nh69 = instance.getById(1);
        
        String expResult1 = vr46.getDescription();
        boolean expResult2 = true;
        String result1 = nh69.getDescription();
        boolean result2 = vr46.equals(nh69);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }
}
    
   
