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
 * @author jujps00
 */
public class InvoiceTest {

    private Invoice invoice;

    public InvoiceTest() {
        
        invoice = new Invoice(2000);
    }

    /**
     * Test of getIdInvoice method, of class Invoice.
     */
    @Test
    public void testGetIdInvoice() {
        int expected = 0;
        int obtained = invoice.getIdInvoice();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setIdInvoice method, of class Invoice.
     */
    @Test
    public void testSetIdInvoice() {
        int expected = 1;
        invoice.setIdInvoice(1);
        int obtained = invoice.getIdInvoice();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getUserName method, of class Invoice.
     */
    @Test
    public void testGetIdUser() {
        int expected = 2000;
        int obtained = invoice.getidUser();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setUserName method, of class Invoice.
     */
    @Test
    public void testSetUserName() {
        int expected = 2001;
        invoice.setIdUser(2001);
        int obtained = invoice.getidUser();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getInssueDate method, of class Invoice.
     */
    @Test
    public void testGetIssueDate() {
        String expected = "30/12/2019";
        invoice.setIssueDate(expected);
        String obtained = invoice.getIssueDate();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setInssueDate method, of class Invoice.
     */
    @Test
    public void testSetIssueDate() {
        String expected = "31/12/2019";
        invoice.setIssueDate("31/12/2019");
        String obtained = invoice.getIssueDate();
        assertEquals(expected, obtained);
    }

    /**
     * Test of getTotalPrice method, of class Invoice.
     */
    @Test
    public void testGetTotalPrice() {
        double expected = 30;
        invoice.setTotalPrice(expected);
        double obtained = invoice.getTotalPrice();
        assertEquals(expected, obtained);
    }

    /**
     * Test of setTotalPrice method, of class Invoice.
     */
    @Test
    public void testSetTotalPrice() {
        double expected = 40;
        invoice.setTotalPrice(40);
        double obtained = invoice.getTotalPrice();
        assertEquals(expected, obtained);
    }


}
