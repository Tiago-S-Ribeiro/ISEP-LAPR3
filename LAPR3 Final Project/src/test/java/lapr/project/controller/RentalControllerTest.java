/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import lapr.project.data.RentalDataHandler;
import lapr.project.model.GeographicalLocation;
import lapr.project.model.Park;
import lapr.project.model.PointInterest;
import lapr.project.model.Rental;
import lapr.project.model.Scooter;
import lapr.project.model.User;
import lapr.project.model.Vehicle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Beatriz Ribeiro
 */
public class RentalControllerTest {
    
    private static RentalController instance;
    
    public RentalControllerTest() {
    }

    @BeforeAll
   public static void setUpClass() throws SQLException, ClassNotFoundException {
        
        RentalDataHandler rentalDataHandlerMock = mock(RentalDataHandler.class);
        PointInterest poi1 = new PointInterest(1, new GeographicalLocation(41, -8, 0), "teste1");
        PointInterest poi2 = new PointInterest(2, new GeographicalLocation(41.3, -8, 0), "teste2");
        Park park1 = new Park(1, "park1", "teste1",1 , 220, 16, new GeographicalLocation(41, -8, 0));
        Park park2 = new Park(2, "park2", "teste2", 1, 220, 16, new GeographicalLocation(41.3, -8, 0));
        List<Pair<Double, List<PointInterest>>> list = null;
        User u = new User("mefiles@mail.pt", "Mefiles", "12345678", 20, 160, 50, "F", "ofTheHorde");
        Vehicle v = new Vehicle(46, 1, "Geralt of Rivia", 3.1, 1.0, 2.5);
        Rental rental = new Rental(u, v, park1);
        Vehicle vehicle = new Scooter(1, 100, 30,250, 1, "teste", 10, 0.8, 0.99);
        
        when(rentalDataHandlerMock.shortestRoute(poi1, poi2, 0)).thenReturn(list);
        when(rentalDataHandlerMock.mostEnergeticallyEfficientRoute(1,vehicle, poi1, poi2)).thenReturn(list);
        when(rentalDataHandlerMock.makeRental(1,1)).thenReturn(1);
        when(rentalDataHandlerMock.shortestRoutePassingThroughPoints(poi1, poi2, new ArrayList<>())).thenReturn(null);
        when(rentalDataHandlerMock.mostEfficientRoutePassingThroughPoints(1,vehicle,poi1, poi2, new ArrayList<>(), 1, true, "number_of_points")).thenReturn(null);
        when(rentalDataHandlerMock.getScootersWith10PercentageExtra(park1, park2, null)).thenReturn(new ArrayList());
        when(rentalDataHandlerMock.getScootersWithMoreEnergy(1)).thenReturn(new ArrayList());
        when(rentalDataHandlerMock.getUnfinishedTrips()).thenReturn(new ArrayList<>());
        when(rentalDataHandlerMock.getRentalById(1)).thenReturn(rental);
        instance = new RentalController(rentalDataHandlerMock);
    }

    /**
     * Test of shortestRoute method, of class RentalController.
     */
    
    @Test
    public void testShortestRoute() throws SQLException {
        System.out.println("shortestRoute");
        PointInterest point1 = new PointInterest(1, new GeographicalLocation(41, -8, 0), "teste1");
        PointInterest point2 = new PointInterest(2, new GeographicalLocation(41.3, -8, 0), "teste2");
        int numPois = 0;
        
        List<Pair<Double, List<PointInterest>>> expResult = null;
        List<Pair<Double, List<PointInterest>>> result = instance.shortestRoute(point1, point2, numPois);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of mostEnergeticallyEfficientRoute method, of class RentalController.
     */
    @Test
    public void testMostEnergeticallyEfficientRoute() throws SQLException {
        System.out.println("mostEnergeticallyEfficientRoute");
        int userId = 1;
        int vehicleId = 2;
        PointInterest point1 = new PointInterest(1, new GeographicalLocation(41, -8, 0), "teste1");
        PointInterest point2 = new PointInterest(2, new GeographicalLocation(41.3, -8, 0), "teste2");
        Vehicle vehicle = new Scooter(1, 100, 30,250, 1, "teste", 10, 0.8, 0.99);
        
        List<Pair<Double, List<PointInterest>>> expResult = null;
        List<Pair<Double, List<PointInterest>>> result = instance.mostEnergeticallyEfficientRoute(userId, vehicle, point1, point2);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of shortestRoutePassingThroughPoints method, of class RentalController.
     */
    @Test
    public void testShortestRoutePassingThroughPoints() throws SQLException {
        System.out.println("shortestRoutePassingThroughPoints");
        PointInterest point1 = new PointInterest(1, new GeographicalLocation(41, -8, 0), "teste1");
        PointInterest point2 = new PointInterest(2, new GeographicalLocation(41.3, -8, 0), "teste2");
        ArrayList<PointInterest> poisList = new ArrayList<>();
        List<Pair<Double, List<PointInterest>>> expResult = null;
        List<Pair<Double, List<PointInterest>>> result = instance.shortestRoutePassingThroughPoints(point1, point2, poisList);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of mostEfficientRoutePassingThroughPoints method, of class RentalController.
     */
    @Test
    public void testMostEfficientRoutePassingThroughPoints() throws SQLException {
        System.out.println("mostEfficientRoutePassingThroughPoints");
        int userId = 1;
        int vehicleId = 2;
        PointInterest point1 = new PointInterest(1, new GeographicalLocation(41, -8, 0), "teste1");
        PointInterest point2 = new PointInterest(2, new GeographicalLocation(41.3, -8, 0), "teste2");
        Vehicle vehicle = new Scooter(1, 100, 30,250, 1, "teste", 10, 0.8, 0.99);
        ArrayList<PointInterest> poisList = new ArrayList<>();
        int numRoutes = 1;
        boolean ascendingOrder = true;
        String sortingCriteria = "number_of_points";
        List<Pair<Double, List<PointInterest>>> expResult = null;
        List<Pair<Double, List<PointInterest>>> result = instance.mostEfficientRoutePassingThroughPoints(userId, vehicle, point1, point2, poisList, numRoutes, ascendingOrder, sortingCriteria);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of makeRental method, of class RentalController.
     */
    @Test
    public void testMakeRental() throws SQLException {
        System.out.println("makeRental");
        User user = new User(1, "test@mail.pt", "test1", "12345678", 3 ,160 , 45, "F", "test1_1");
        Vehicle vehicle = new Vehicle(1, 1, "vehicle1", 20, 0.3, 0.5);
        Park parkFrom = new Park(1, "park1", "Park 1", 1, 250, 16, new GeographicalLocation(41, -8, 0));
        int id = 1;
        Rental result = instance.makeRental(user, vehicle, parkFrom);
        assertEquals(id, result.getIdRental());
        
        
    }

    /**
     * Test of getScootersWith10PercentageExtra method, of class RentalController.
     */
    @Test
    public void testGetScootersWith10PercentageExtra() {
        System.out.println("getScootersWith10PercentageExtra");
        Park park1 = new Park(1, "park1", "teste1",1 , 220, 16, new GeographicalLocation(41, -8, 0));
        Park park2 = new Park(2, "park2", "teste2", 1, 220, 16, new GeographicalLocation(41.3, -8, 0));
        User user = null;
        List<Scooter> expResult = new ArrayList<>();
        List<Scooter> result = instance.getScootersWith10PercentageExtra(park1, park2, user);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getScootersWithMoreEnergy method, of class RentalController.
     */
    @Test
    public void testGetScootersWithMoreEnergy() {
        System.out.println("getScootersWithMoreEnergy");
        int idPark = 1;
        List<Scooter> expResult = new ArrayList<>();
        List<Scooter> result = instance.getScootersWithMoreEnergy(idPark);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getUnfinishedTrips method, of class RentalController.
     */
    @Test
    public void testGetUnfinishedTrips() {
        System.out.println("getUnfinishedTrips");
        List<Rental> expResult = new ArrayList<>();
        List<Rental> result = instance.getUnfinishedTrips();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getUnpaidRentals method, of class RentalController.
     */
    @Test
    public void testGetRentalById() {
        System.out.println("getRentalById");
        Park park1 = new Park(1, "park1", "teste1",1 , 220, 16, new GeographicalLocation(41, -8, 0));
        User u = new User("mefiles@mail.pt", "Mefiles", "12345678", 20, 160, 50, "F", "ofTheHorde");
        Vehicle v = new Vehicle(46, 1, "Geralt of Rivia", 3.1, 1.0, 2.5);
        Rental rental1 = new Rental(u, v, park1);
        Rental rental2 = instance.getRentalById(1);
        
        int expResult1 = rental1.getUser().getIdUser();
        int expResult2 = rental1.getVehicle().getId();
        int expResult3 = rental1.getParkFrom().getId();
        int result1 = rental2.getUser().getIdUser();
        int result2 = rental2.getVehicle().getId();
        int result3 = rental2.getParkFrom().getId();
        
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
    }
}
