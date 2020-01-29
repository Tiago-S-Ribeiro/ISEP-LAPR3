/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lapr.project.data.InvoiceDataHandler;
import lapr.project.model.Invoice;
import lapr.project.model.InvoiceLine;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author G25
 */
public class InvoiceControllerTest {

    private static InvoiceController instance;

    public InvoiceControllerTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException, ClassNotFoundException {
        InvoiceDataHandler invoiceDataHandlerMock = mock(InvoiceDataHandler.class);

        List<InvoiceLine> listInvoiceLines = new LinkedList<>();
        listInvoiceLines.add(new InvoiceLine(123, 444, 30, 50, "30/12/2019",10));
        Invoice invoice = new Invoice(12345678);
        when(invoiceDataHandlerMock.addInvoice(invoice,1)).thenReturn(1);
        
        when(invoiceDataHandlerMock.getById(any(Integer.class))).thenReturn(invoice);
        when(invoiceDataHandlerMock.getInvoiceLinesOfInvoice(any(Integer.class))).thenReturn(new ArrayList<InvoiceLine>());
        instance = new InvoiceController(invoiceDataHandlerMock);
    }

    /**
     * Test of addInvoice methodA of class InvoiceController.
     */
    @Test
    public void testAddInvoice() throws SQLException {
        System.out.println("addInvoice");

        int idUser = 12345678;
        String issueDate = "";
        double totalPrice = 0.0;
        List<InvoiceLine> listInvoiceLines = null;
        
        Invoice result = instance.addInvoice(idUser,1);
        
        assertEquals(result.getIdInvoice(), 0);

        
    }

    /**
     * Test of getById method, of class InvoiceController.
     */
    @Test
    public void testGetById() throws SQLException {
        System.out.println("getById");
        int id = 1;

        List<InvoiceLine> listInvoiceLines = new LinkedList<>();
        listInvoiceLines.add(new InvoiceLine(123, 444, 30, 50, "30/12/2019",10));
        Invoice expResult = new Invoice(12345678);
        Invoice result = instance.getById(id);
        assertEquals(expResult.getIdInvoice(), result.getIdInvoice());

        
    }

    /**
     * Test of getInvoiceLinesOfInvoice method, of class InvoiceController.
     */
    @Test
    public void testGetInvoiceLinesOfInvoice() {
        System.out.println("getInvoiceLinesOfInvoice");
        int idInvoice = 1;
        List<InvoiceLine> expResult = new ArrayList<>();
        List<InvoiceLine> result = instance.getInvoiceLinesOfInvoice(idInvoice);
        assertEquals(expResult, result);
        
    }

}
