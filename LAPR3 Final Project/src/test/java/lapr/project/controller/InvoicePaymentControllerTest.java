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
import lapr.project.data.InvoiceDataHandler;
import lapr.project.data.InvoicePaymentDataHandler;
import lapr.project.data.UserDataHandler;
import lapr.project.model.Invoice;
import lapr.project.model.InvoiceLine;
import lapr.project.model.Receipt;
import lapr.project.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Ana Rita Veiga
 */
public class InvoicePaymentControllerTest {

    private static InvoicePaymentController instance;

    public InvoicePaymentControllerTest() {
    }

    @BeforeAll
    public static void setUpClass() throws SQLException, ClassNotFoundException {
        InvoicePaymentDataHandler invoicePaymentDataHandlerMock = mock(InvoicePaymentDataHandler.class);
        int idUser = 1;
        int idInvoice = 1;
        Invoice invoice = new Invoice(idUser);
        List<Invoice> lstInvoice = new ArrayList<Invoice>();
        lstInvoice.add(invoice);
        Receipt receipt = new Receipt(idUser, idInvoice, 120, "2019/10/12");
        when(invoicePaymentDataHandlerMock.generateReceipt(any(Integer.class))).thenReturn(receipt);
        when(invoicePaymentDataHandlerMock.getAllUnpaidInvoice(any(Integer.class))).thenReturn(lstInvoice);
        instance = new InvoicePaymentController(invoicePaymentDataHandlerMock);

    }

    /**
     * Test of getAllUnpaidInvoices method, of class InvoicePaymentController.
     */
    @Test
    public void testgetAllUnpaidInvoices() {
        System.out.println("returngetAllUnpaidInvoices");
        int idUser = 1;
        Invoice invoice = new Invoice(idUser);
        List<Invoice> expResult = new ArrayList<>();
        expResult.add(invoice);
        List<Invoice> result = instance.getAllUnpaidInvoices(idUser);
        assertEquals(expResult.size(), result.size());

    }

    /**
     * Test of generateReceipt method, of class InvoicePaymentController.
     */
    @Test
    public void testGenerateReceipt() throws SQLException {
        int idUser = 1;
        int idInvoice = 1;
        Receipt expResult = new Receipt(idUser, idInvoice, 120, "2019/10/12");
        Receipt result = instance.generateReceipt(idUser);
        assertEquals(expResult.getDate(), result.getDate());

    }

}
