/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.sql.SQLException;
import java.util.List;
import lapr.project.data.InvoiceDataHandler;
import lapr.project.model.Invoice;
import lapr.project.model.InvoiceLine;

/**
 *
 * @author jujps00
 */
public class InvoiceController {
    
    private final InvoiceDataHandler invoiceDataHandler;

    /**
     *
     * @param invoiceDataHandler
     */
    public InvoiceController(InvoiceDataHandler invoiceDataHandler) {
        this.invoiceDataHandler = invoiceDataHandler;
    }

    public Invoice addInvoice(int idUser, int month) throws SQLException {
        Invoice invoice = new Invoice(idUser);
        
        invoice.setIdInvoice(invoiceDataHandler.addInvoice(invoice, month));
        
        return invoice;
    }

    public Invoice getById(int id){
        Invoice invoice = null;
        invoice = invoiceDataHandler.getById(id);
        return invoice;
    }

    public List<InvoiceLine> getInvoiceLinesOfInvoice(int idInvoice){
        return invoiceDataHandler.getInvoiceLinesOfInvoice(idInvoice);
    }
}
