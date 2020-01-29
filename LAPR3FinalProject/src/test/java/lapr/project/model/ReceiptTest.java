/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 *
 * @author Ana Rita Veiga
 */
public class ReceiptTest {

    public ReceiptTest() {
    }

    /**
     * Test of getIdReceipt method, of class Receipt.
     */
    @Test
    public void testGetIdReceipt() {
        System.out.println("getIdReceipt");
        Receipt instance = new Receipt(2, 3, 250, "2019-12-18");
        int expResult = 0;
        int result = instance.getIdReceipt();
        assertEquals(expResult, result);

    }

    /**
     * Test of setIdReceipt method, of class Receipt.
     */
    @Test
    public void testSetIdReceipt() {
        System.out.println("setIdReceipt");
        Receipt instance = new Receipt( 2, 3, 250, "2019-12-18");
        int expResult = 3;
        instance.setIdReceipt(expResult);
        int result = instance.getIdReceipt();
        assertEquals(expResult, result);

    }

    /**
     * Test of getIdUser method, of class Receipt.
     */
    @Test
    public void testGetIdUser() {
        System.out.println("getIdUser");
        Receipt instance = new Receipt( 2, 3, 250, "2019-12-18");
        int expResult = 2;
        int result = instance.getIdUser();
        assertEquals(expResult, result);

    }

    /**
     * Test of setIdUser method, of class Receipt.
     */
    @Test
    public void testSetIdUser() {
        System.out.println("setIdUser");
        Receipt instance = new Receipt(2, 3, 250, "2019-12-18");
        int expResult = 3;
        instance.setIdUser(expResult);
        int result = instance.getIdUser();
        assertEquals(expResult, result);

    }

    /**
     * Test of getIdInvoice method, of class Receipt.
     */
    @Test
    public void testGetIdInvoice() {
        System.out.println("getIdInvoice");
        Receipt instance = new Receipt( 2, 3, 250, "2019-12-18");
        int expResult = 3;
        int result = instance.getIdInvoice();
        assertEquals(expResult, result);

    }

    /**
     * Test of setIdInvoice method, of class Receipt.
     */
    @Test
    public void testSetIdInvoice() {
        System.out.println("setIdInvoice");
        Receipt instance = new Receipt( 2, 3, 250, "2019-12-18");
        int expResult = 5;
        instance.setIdInvoice(expResult);
        int result = instance.getIdInvoice();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTotalCost method, of class Receipt.
     */
    @Test
    public void testGetTotalCost() {
        System.out.println("getGetTotalCost");
        Receipt instance = new Receipt( 2, 3, 250.9, "2019-12-18");
        double expResult = 250.9;
        double result = instance.getTotalCost();
        assertEquals(expResult, result);

    }

    /**
     * Test of setTotalCost method, of class Receipt.
     */
    @Test
    public void testSetTotalCost() {
        System.out.println("setTotalCost");
        Receipt instance = new Receipt( 2, 3, 250, "2019-12-18");
        double expResult = 100;
        instance.setTotalCost(expResult);
        double result = instance.getTotalCost();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDate method, of class Receipt.
     */
    @Test
    public void testGetDate() {
        System.out.println("getGetDate");
        Receipt instance = new Receipt( 2, 3, 250.9, "2019-12-18");
        String expResult = "2019-12-18";
        String result = instance.getDate();
        assertEquals(expResult, result);

    }

    /**
     * Test of setDate method, of class Receipt.
     */
    @Test
    public void testSetDate() {
        System.out.println("setIdInvoice");
        Receipt instance = new Receipt( 2, 3, 250, "2019-12-18");
        String expResult = "2019-12-26";
        instance.setDate(expResult);
        String result = instance.getDate();
        assertEquals(expResult, result);
    }

  }
