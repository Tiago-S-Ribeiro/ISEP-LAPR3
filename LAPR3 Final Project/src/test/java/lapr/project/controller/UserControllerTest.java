/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import lapr.project.data.UserDataHandler;
import lapr.project.model.GeographicalLocation;
import lapr.project.model.InvoiceLine;
import lapr.project.model.PointInterest;
import lapr.project.model.Rental;
import lapr.project.model.User;
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
public class UserControllerTest {

    private static UserController instance;

    public UserControllerTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException {
        UserDataHandler userDataHandlerMock = mock(UserDataHandler.class);
        when(userDataHandlerMock.add(any(User.class))).thenReturn(1);

        User user = new User("user@mail.pt", "user_1", "12345678", 50, 160, 50, "F", "lolipop");
        when(userDataHandlerMock.getById(any(Integer.class))).thenReturn(user);

        when(userDataHandlerMock.validateLogin("user@mail.pt", "lolipop")).thenReturn(0);

        PointInterest poi1 = new PointInterest(1, new GeographicalLocation(2, 3, 4), "ponto1");
        PointInterest poi2 = new PointInterest(2, new GeographicalLocation(20, 34, 6), "ponto2");
        ArrayList<PointInterest> path = new ArrayList<>();
        path.add(poi1);
        path.add(poi2);
        when(userDataHandlerMock.calculateAmountCalories(path, 1, 1)).thenReturn(2.0);

        when(userDataHandlerMock.getUserHistorical(any(Integer.class))).thenReturn(new ArrayList<>());

        when(userDataHandlerMock.getUserByUsername(any(String.class))).thenReturn(user);
        
        when(userDataHandlerMock.getUnpaidRentals(1)).thenReturn(new TreeMap<>());
        instance = new UserController(userDataHandlerMock);
    }

    /**
     * Test of addUser method, of class UserController.
     */
    @Test
    public void testAddUser() throws SQLException {
        System.out.println("addUser");
        String email = "";
        String username = "";
        String creditCard = "";
        float cyclingAverage = 0.0F;
        int height = 0;
        float weight = 0.0F;
        String gender = "";
        String pwd = "";

        User user = instance.addUser(email, username, creditCard, cyclingAverage, height, weight, gender, pwd);
        assertEquals(user.getIdUser(), 1);

    }

    /**
     * Test of getById method, of class UserController.
     */
    @Test
    public void testGetById() throws SQLException {
        System.out.println("getById");
        int id = 1;

        User expResult = new User("bea@mail.pt", "bea_1", "12345678", 50, 160, 50, "F", "lolipop");
        User result = instance.getById(id);
        assertEquals(expResult.getIdUser(), result.getIdUser());

    }

    /**
     * Test of login method, of class UserController.
     */
    @Test
    public void testLogin() throws SQLException {
        System.out.println("validateLogin");
        String email = "user@mail.pt";
        String password = "lolipop";
        User result = instance.login(email, password);
        assertEquals(0, result.getIdUser());

    }

    /**
     * Test of calculateAmountCalories method, of class UserController.
     */
    @Test
    public void testCalculateAmountCalories() throws SQLException {
        System.out.println("calculateAmountCalories");
        PointInterest poi1 = new PointInterest(1, new GeographicalLocation(2, 3, 4), "ponto1");
        PointInterest poi2 = new PointInterest(2, new GeographicalLocation(20, 34, 6), "ponto2");
        ArrayList<PointInterest> path = new ArrayList<>();
        path.add(poi1);
        path.add(poi2);
        int userId = 1;
        int vehicleId = 1;
        double expResult = 2.0;
        double result = instance.calculateAmountCalories(path, userId, vehicleId);
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of getUserHistorical method, of class UserController.
     */
    @Test
    public void testGetUserHistorical() throws SQLException {
        System.out.println("getUserHistorical");
        int id = 0;
        List<Rental> expResult = new ArrayList<>();
        List<Rental> result = instance.getUserHistorical(id);
        assertEquals(expResult, result);

    }

    @Test
    public void testGetUserByUsername() throws SQLException {
        System.out.println("getUserByUsername");
        String username = "tiago_rib";

        User expResult = new User("1181444@isep.ipp.pt", "tiago_rib", "87654321", 50, 158, 50, "M", "123");
        User result = instance.getUserByUsername(username);
        assertEquals(expResult.getIdUser(), result.getIdUser());
    }
    
    /**
     * Test of getUnpaidRentals method, of class UserController.
     */
    @Test
    public void testGetUnpaidRentals() throws SQLException {
        System.out.println("getUnpaidRentals");
        TreeMap<Long,InvoiceLine> expResult = new TreeMap<>();
        TreeMap<Long,InvoiceLine> result = instance.getUnpaidRentals(1);
        assertEquals(expResult,result);
    }
}
