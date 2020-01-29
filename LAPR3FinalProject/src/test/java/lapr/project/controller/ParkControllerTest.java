/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import lapr.project.data.ParkDataHandler;
import lapr.project.model.GeographicalLocation;
import lapr.project.model.Park;
import lapr.project.model.Path;
import lapr.project.model.PointInterest;
import lapr.project.model.Scooter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Tiago Ribeiro
 */
public class ParkControllerTest {

    private static ParkController instance;

    public ParkControllerTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException, ClassNotFoundException {
        ParkDataHandler parkDataHandlerMock = mock(ParkDataHandler.class);
        when(parkDataHandlerMock.addPark(any(Park.class))).thenReturn(1);

        Park park = new Park(1, "park1", "Tridade", 1, 12, 16, new GeographicalLocation(-41, 4, 0));
        when(parkDataHandlerMock.getById(any(Integer.class))).thenReturn(park);

        when(parkDataHandlerMock.removePark(any(Integer.class))).thenReturn(Boolean.TRUE);

        List<Pair<Double, Park>> nearbyParks = new ArrayList<>();
        when(parkDataHandlerMock.getNearestParks(-41, 5, 1000000)).thenReturn(nearbyParks);

        ArrayList<Park> parks = new ArrayList<>();
        parks.add(park);
        when(parkDataHandlerMock.getAllParks()).thenReturn(parks);

        when(parkDataHandlerMock.checkParkFreeScooterSpots(1)).thenReturn(46);

        when(parkDataHandlerMock.checkParkFreeBicycleSpots(1)).thenReturn(38);

        when(parkDataHandlerMock.addPOI(any(PointInterest.class))).thenReturn(1);

        when(parkDataHandlerMock.parkVehicle(1, 1)).thenReturn(Boolean.TRUE);

        when(parkDataHandlerMock.updatePark(any(Integer.class), any(String.class))).thenReturn(Boolean.TRUE);

        when(parkDataHandlerMock.getParkOfCoordinates(41.0, -8.9)).thenReturn(park);

        when(parkDataHandlerMock.getParkOfReference(any(String.class))).thenReturn(park);

        when(parkDataHandlerMock.checkParkFreeSpots(1, 2)).thenReturn(1);

        when(parkDataHandlerMock.addPath(any(Path.class))).thenReturn(Boolean.TRUE);

        when(parkDataHandlerMock.getPointByCoordinates(41, -8)).thenReturn(new PointInterest(1, new GeographicalLocation(41, -8, 0), "teste"));

        Park test = new Park(46, "parkReference", "Park Campus Sao Joao", 1, 220, 16, new GeographicalLocation(1, 2, 3));
        when(parkDataHandlerMock.getParkByVehicleID(any(Integer.class))).thenReturn(test);

        instance = new ParkController(parkDataHandlerMock);
    }

    /**
     * Test of addPark method, of class ParkController.
     */
    @Test
    public void testAddPark() throws SQLException {
        System.out.println("addPark");
        String parkRef = "";
        String descripton = "";
        int state = 0;
        int parkInputVoltage = 0;
        int parkInputCurrent = 0;
        double latitude = 0.0;
        double longitude = 0.0;
        double elevation = 0.0;
        int maxCapacityBike = 0;
        int maxCapacityScooter = 0;

        Park result = instance.addPark(parkRef, descripton, state, parkInputVoltage, parkInputCurrent, latitude, longitude, elevation, maxCapacityBike, maxCapacityScooter);
        assertEquals(result.getIdPoint(), 1);

    }

    /**
     * Test of getPark method, of class ParkController.
     */
    @Test
    public void testGetPark() throws SQLException {
        System.out.println("getPark");
        int id = 1;

        Park expResult = new Park(1, "park1", "Tridade", 1, 12, 16, new GeographicalLocation(-41, 4, 0));
        Park result = instance.getPark(id);
        assertEquals(expResult.getId(), result.getId());

    }

    /**
     * Test of removePark method, of class ParkController.
     */
    @Test
    public void testRemovePark() throws Exception {
        System.out.println("removePark");
        int idPark = 1;
        boolean expResult = true;
        boolean result = instance.removePark(idPark);
        assertEquals(expResult, result);

    }

    /**
     * Test of getNearestParks method, of class ParkController.
     */
    @Test
    public void testGetNearestParks() throws Exception {
        System.out.println("getNearestParks");
        double latitude = 0.0;
        double longitude = 0.0;
        double radius = 0.0;
        List<Pair<Double, Park>> expResult = new ArrayList<>();
        List<Pair<Double, Park>> result = instance.getNearestParks(latitude, longitude, radius);
        assertEquals(expResult, result);

    }

    /**
     * Test of getAllParks method, of class ParkController.
     */
    @Test
    public void testGetAllParks() throws Exception {
        System.out.println("getAllParks");
        Park park = new Park(1, "park1", "Tridade", 1, 12, 16, new GeographicalLocation(-41, 4, 0));
        ArrayList<Park> expResult = new ArrayList<>();
        expResult.add(park);
        List<Park> result = instance.getAllParks();
        assertEquals(expResult, result);

    }

    /**
     * Test of checkParkFreeSpots method, of class ParkController.
     */
    @Test
    public void testCheckParkFreeSpots() throws Exception {
        System.out.println("checkParkFreeSpots");
        int parkId = 1;
        int userId = 2;
        int expResult = 1;
        int result = instance.checkParkFreeSpots(parkId, userId);
        assertEquals(expResult, result);

    }

    @Test
    public void testAddPOI() throws Exception {
        System.out.println("addPOI");
        double latitude = 0.0;
        double longitude = 0.0;
        double elevation = 0.0;
        String description = "Laugh with many. Don't trust any.";

        PointInterest result = instance.addPOI(latitude, longitude, elevation, description);
        assertEquals(result.getIdPoint(), 1);

    }

    /**
     * Test of updatePark method, of class ParkController.
     */
    @Test
    public void testUpdatePark() throws Exception {
        System.out.println("updatePark");
        int idPark = 1;
        String description = "new_description";

        boolean expResult = true;
        boolean result = instance.updatePark(idPark, description);
        assertEquals(expResult, result);

    }

    /**
     * Test of getParkByCoordinates method, of class ParkController.
     */
    @Test
    public void testGetIdParkByCoordinates() throws SQLException {
        System.out.println("getIdParkByCoordinates");
        double latitude = 41.0;
        double longitude = -8.9;
        Park expResult = new Park(1, "park1", "Tridade", 1, 12, 16, new GeographicalLocation(-41, 4, 0));
        Park result = instance.getParkByCoordinates(latitude, longitude);
        assertEquals(expResult, result);

    }

    /**
     * Test of getParkByRefPark method, of class ParkController.
     */
    @Test
    public void testGetIdParkByRefPark() throws SQLException {
        System.out.println("getIdParkByRefPark");
        String refPark = "Park1";
        Park expResult = new Park(1, "park1", "Tridade", 1, 12, 16, new GeographicalLocation(-41, 4, 0));
        Park result = instance.getParkByRefPark(refPark);
        assertEquals(expResult, result);

    }

    /**
     * Test of checkParkFreeScooterSpots method, of class ParkController.
     */
    @Test
    public void testCheckParkFreeScooterSpots() throws Exception {
        System.out.println("checkParkFreeScooterSpots");

        int parkId = 1;
        int expResult = 46;
        int result = instance.checkParkFreeScooterSpots(parkId);
        assertEquals(expResult, result);

    }

    /**
     * Test of checkParkFreeBicycleSpots method, of class ParkController.
     */
    @Test
    public void testCheckParkFreeBicycleSpots() throws Exception {
        System.out.println("checkParkFreeBicycleSpots");

        int parkId = 1;
        int expResult = 38;
        int result = instance.checkParkFreeBicycleSpots(parkId);
        assertEquals(expResult, result);

    }

    /**
     * Test of addPath method, of class ParkController.
     *
     * @throws SQLException
     */
    @Test
    public void testAddPath() throws SQLException {
        System.out.println("addPath");
        double latitude = 0.0;
        double longitude = 0.0;
        double elevation = 0.0;
        String description = "Memento Mori";
        double latitude2 = 1.0;
        double longitude2 = 2.0;
        double elevation2 = 3.0;
        String description2 = "Mors Indecepta";
        double kinetic = 1.0;
        float windDirection = 90.0f;
        float windSpeed = 10.0f;

        GeographicalLocation geo = new GeographicalLocation(latitude, longitude, elevation);
        GeographicalLocation geo2 = new GeographicalLocation(latitude2, longitude2, elevation2);
        PointInterest poi1 = new PointInterest(geo, description);
        PointInterest poi2 = new PointInterest(geo2, description2);
        int idPoi1 = poi1.getIdPoint();
        int idPoi2 = poi2.getIdPoint();

        boolean expResult = true;
        boolean result = instance.addPath(idPoi1, idPoi2, kinetic, windDirection, windSpeed);
        assertEquals(expResult, result);

    }

    /**
     * Test of getPointByCoordinates method, of class ParkController.
     */
    @Test
    public void testGetPointByCoordinates() throws SQLException {
        System.out.println("getPointByCoordinates");
        double latitude = 41.0;
        double longitude = -8.0;
        PointInterest expResult = new PointInterest(1, new GeographicalLocation(latitude, longitude, 0), "teste");
        PointInterest result = instance.getPointByCoordinates(latitude, longitude);
        assertEquals(expResult, result);
    }

    /**
     * Test of getParkByVehicleID method, of class ParkController
     *
     * @throws SQLException
     */
    @Test
    public void testGetParkByVehicleID() throws SQLException {
        System.out.println("getParkByVehicleID");
        int id = 0;
        Park expResult = new Park(46, "parkReference", "Park Campus Sao Joao", 1, 220, 16, new GeographicalLocation(1, 2, 3));
        Park result = instance.getParkByVehicleID(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of parkVehicle method, of class ParkController.
     */
    @org.junit.Test
    public void testParkVehicle() throws Exception {
    }

    /**
     * Test of getParkByCoordinates method, of class ParkController.
     */
    @org.junit.Test
    public void testGetParkByCoordinates() {
    }

    /**
     * Test of calculateTimeNeededToChargeTotalyByPark method, of class
     * ParkController.
     */
    @Test
    public void testCalculateTimeNeededToChargeTotalyByPark() {
        System.out.println("CalculateTimeNeededToChargeTotalyByPark");
        GeographicalLocation geoLocation = new GeographicalLocation(0, 0, 0);
        int idPark1 = 0;
        Scooter scooter = new Scooter(0, 0, 0, 0, 0, "", 0, 0, 0);
        LinkedList<Pair<Scooter, Double>> expResult = new LinkedList<>();
        LinkedList<Pair<Scooter, Double>> result = instance.calculateTimeNeededToChargeTotalyByPark(idPark1);
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateTimeNeededToChargeTotalyByScooter method, of class
     * ParkController.
     */
    @Test
    public void testCalculateTimeNeededToChargeTotalyByScooter() {
        System.out.println("CalculateTimeNeededToChargeTotalyByScooter");
        GeographicalLocation geoLocation = new GeographicalLocation(0, 0, 0);
        int idPark1 = 0;
        Scooter scooter = new Scooter(0, 0, 0, 0, 0, "", 0, 0, 0);
        Pair<Scooter, Double> expResult =null;
        Pair<Scooter, Double> result = instance.calculateTimeNeededToChargeTotalyByScooter(idPark1, scooter.getDescription());
        assertEquals(expResult, result);
    }
}
