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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.model.Invoice;
import lapr.project.model.InvoiceLine;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author jujps00
 */
public class InvoiceDataHandler {

    private static final Logger WARNING_LOGGER_INVOICE = Logger.getLogger(Invoice.class.getName());

    private final DataHandler dataHandler;

    public InvoiceDataHandler() {
        dataHandler = DataHandler.getInstance();
    }

    public static void warningError() {
        WARNING_LOGGER_INVOICE.log(Level.WARNING, "Error on reading Database.");
    }

    public int addInvoice(Invoice element, int month) throws SQLException {
        CallableStatement callStmt = null;
        int id = 0;

        callStmt = dataHandler.getConnection().prepareCall("{? = call funcAddInvoice(?,?) }");
        callStmt.registerOutParameter(1, OracleTypes.INTEGER);
        callStmt.setInt(2, element.getidUser());
        callStmt.setInt(3, month);
        callStmt.execute();

        id = callStmt.getInt(1);

        try {
            callStmt.close();

        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return id;
    }

    public Invoice getById(int id)  {
        String query = "SELECT * FROM invoice WHERE id_invoice= " + id;
        Statement stm = null;
        ResultSet rst = null;
        try {
            Connection con = DataHandler.getInstance().getConnection();
            stm = con.createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                int idUser = rst.getInt(2);
                String issueDate = String.valueOf(rst.getDate(4).getTime());
                double totalPrice = rst.getDouble(3);
                Invoice invoice = new Invoice(idUser);
                invoice.setIdInvoice(id);
                invoice.setIdUser(idUser);
                invoice.setIssueDate(issueDate);
                invoice.setTotalPrice(totalPrice);
                return invoice;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
            
        }
        return null;
    }
    

    

    public List<InvoiceLine> getInvoiceLinesOfInvoice(int id) {
        String query = "SELECT * FROM invoice_line WHERE id_invoice= " + id;
        Statement stm = null;
        ResultSet rst = null;
        List<InvoiceLine> invoiceLines = new ArrayList<>();
        try {
            Connection con = DataHandler.getInstance().getConnection();
            stm = con.createStatement();
            rst = stm.executeQuery(query);

            while (rst.next()) {
                int idRental = rst.getInt(1);
                int idVehicle = rst.getInt(3);
                double rentalDuration = rst.getInt(4);
                double rentalCost = rst.getDouble(5);
                String date = String.valueOf(rst.getDate(6).getTime());
                int earnedPoints = rst.getInt(7);
                InvoiceLine invoiceLine = new InvoiceLine(idRental, idVehicle, rentalDuration, rentalCost, date, earnedPoints);
                invoiceLines.add(invoiceLine);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return null;

    }

}
