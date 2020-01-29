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
 * @author G25
 */
public class InvoiceLineTest {

    private InvoiceLine invoiceLines;

    public InvoiceLineTest() {
        invoiceLines = new InvoiceLine(123, 999, 20, 30, "30/12/2019",10);
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
     * Test of getIdRental method, of class InvoiceLine.
     */
    @Test
    public void testGetIdRental() {
        int expected = 123;
        int obtained = invoiceLines.getIdRental();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setIdRental method, of class InvoiceLine.
     */
    @Test
    public void testSetIdRental() {
        int expected = 234;
        invoiceLines.setIdRental(234);
        int obtained = invoiceLines.getIdRental();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getIdVehicle method, of class InvoiceLine.
     */
    @Test
    public void testGetIdVehicle() {
        int expected = 999;
        int obtained = invoiceLines.getIdVehicle();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setIdVehicle method, of class InvoiceLine.
     */
    @Test
    public void testSetIdVehicle() {
        int expected = 888;
        invoiceLines.setIdVehicle(888);
        int obtained = invoiceLines.getIdVehicle();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getRental_duration method, of class InvoiceLine.
     */
    @Test
    public void testGetRentalDuration() {
        double expected = 20;
        double obtained = invoiceLines.getRentalDuration();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setRental_duration method, of class InvoiceLine.
     */
    @Test
    public void testSetRentalDuration() {
        double expected = 30;
        invoiceLines.setRentalDuration(30);
        double obtained = invoiceLines.getRentalDuration();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getRental_cost method, of class InvoiceLine.
     */
    @Test
    public void testGetRentalCost() {
        double expected = 30;
        double obtained = invoiceLines.getRentalCost();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setRentalCost method, of class InvoiceLine.
     */
    @Test
    public void testSetRentalCost() {
        double expected = 20;
        invoiceLines.setRentalCost(20);
        double obtained = invoiceLines.getRentalCost();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getDate method, of class InvoiceLine.
     */
    @Test
    public void testGetDate() {
        System.out.println("getDate");
        String expResult = "30/12/2019";
        String result = invoiceLines.getDate();

        assertEquals(expResult, result);
    }

    /**
     * Test of setDate method, of class InvoiceLine.
     */
    @Test
    public void testSetDate() {
        System.out.println("setDate");
        String expResult = "NEW_DATE_STRING_TEST";

        invoiceLines.setDate(expResult);
        String result = invoiceLines.getDate();

        assertEquals(expResult, result);
    }

    /**
     * Test of getEarnedPoints method, of class InvoiceLine.
     */
    @Test
    public void testGetEarnedPoints() {
        System.out.println("getEarnedPoints");
        int expResult = 10;
        int result = invoiceLines.getEarnedPoints();
        assertEquals(expResult, result);

    }

    /**
     * Test of setEarnedPoints method, of class InvoiceLine.
     */
    @Test
    public void testSetEarnedPoints() {
        System.out.println("setEarnedPoints");
        int earnedPoints = 9;
        invoiceLines.setEarnedPoints(earnedPoints);
        assertEquals(9, invoiceLines.getEarnedPoints());
    }

}
