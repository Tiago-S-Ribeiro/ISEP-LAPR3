/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;
import lapr.project.model.Invoice;
import lapr.project.model.InvoicePayment;
import lapr.project.model.InvoiceLine;
import lapr.project.model.Receipt;
import lapr.project.model.User;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Ana Rita Veiga
 */
public class InvoicePaymentDataHandler {

    private static final Logger WARNING_LOGGER_INVOICE = Logger.getLogger(InvoicePaymentDataHandler.class.getName());

    private final DataHandler dataHandler;

    public InvoicePaymentDataHandler() {
        dataHandler = DataHandler.getInstance();
    }

    public static void warningError() {
        WARNING_LOGGER_INVOICE.log(Level.WARNING, "Error on reading Database.");
    }

    /**
     * Method that gets all unpaid Invoices
     *
     * @param idUser id of the user
     * @return List with the invoices that user didn't pay
     */
    public List<Invoice> getAllUnpaidInvoice(int idUser) {
        CallableStatement callStmt = null;
        List<Invoice> allInvoicesUnpaid = new ArrayList<>();

        String query = "SELECT * FROM Invoice WHERE invoice_State=0 AND id_user=" + idUser;
        Statement stm = null;
        ResultSet rst = null;
        Invoice invoice = null;
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);
            while (rst.next()) {
                invoice = new Invoice(idUser);
                invoice.setIdInvoice(rst.getInt(1));
                invoice.setTotalPrice(rst.getDouble(3));
                invoice.setIssueDate(simpleDate.format(rst.getDate(4)));

                allInvoicesUnpaid.add(invoice);

            }
            return allInvoicesUnpaid;
        } catch (SQLException e) {
            Logger.getLogger(InvoicePaymentDataHandler.class.getName()).log(Level.WARNING, e.getMessage());
        }
        throw new IllegalArgumentException("No User Found");
    }

    /**
     * Method that generate Receipt by specific Invoice
     *
     * @param idInvioce id of the Invoice
     * @return Receipt of the invoice paid
     */
    public Receipt generateReceipt(int idInvioce) throws SQLException {
        InvoiceDataHandler invoiceDataHandler = new InvoiceDataHandler();
        Invoice invoice = invoiceDataHandler.getById(idInvioce);

        boolean payInvoice = InvoicePayment.payInvoice(invoice.getidUser(), invoice.getTotalPrice());

        if (payInvoice) {
            Calendar.getInstance();
            String date = Calendar.YEAR + "/" + Calendar.MONTH + "/" + Calendar.DATE;
            Receipt receipt = new Receipt(invoice.getidUser(), invoice.getIdInvoice(), invoice.getTotalPrice(), date);
            CallableStatement callStmt = null;

            callStmt = dataHandler.getConnection().prepareCall("{ ? = call funcPayInvoice(?) }");

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setInt(2, idInvioce);
            callStmt.execute();

            receipt.setIdReceipt(callStmt.getInt(1));

            try {
                if (callStmt != null) {
                    callStmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoicePaymentDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }

            return receipt;
        }
        return null;
    }

}
