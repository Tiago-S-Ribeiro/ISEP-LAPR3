
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.sql.SQLException;
import java.util.List;
import lapr.project.data.InvoiceDataHandler;
import lapr.project.data.InvoicePaymentDataHandler;
import lapr.project.model.Invoice;
import lapr.project.model.InvoiceLine;
import lapr.project.model.Receipt;
import lapr.project.model.User;

/**
 *
 * @author G25
 */
public class InvoicePaymentController {

    private final InvoicePaymentDataHandler invoicePaymentDataHandler;

    public InvoicePaymentController(InvoicePaymentDataHandler invoicePaymentDataHandler) {
        this.invoicePaymentDataHandler = invoicePaymentDataHandler;
    }

    public List<Invoice> getAllUnpaidInvoices(int idUser) {
        List<Invoice> unpaidInvoiceTotal = null;
        unpaidInvoiceTotal = invoicePaymentDataHandler.getAllUnpaidInvoice(idUser);
        return unpaidInvoiceTotal;
    }

    public Receipt generateReceipt(int idInvoice) throws SQLException {
        Receipt receipt = null;
        receipt = invoicePaymentDataHandler.generateReceipt(idInvoice);
        return receipt;

    }
}
