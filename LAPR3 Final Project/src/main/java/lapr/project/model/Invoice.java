/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;



/**
 *
 * @author jujps00
 */
public class Invoice {

    private int idInvoice;

    private int idUser;

    private String issueDate;

    private double totalPrice;
    public Invoice(int idUser) {
        this.idInvoice = 0;
        this.idUser = idUser;
        this.issueDate = null;
        this.totalPrice = 0;
    }

    /**
     * @return the idInvoice
     */
    public int getIdInvoice() {
        return idInvoice;
    }

    /**
     * @param idInvoice the idInvoice to set
     */
    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    /**
     * @return the userName
     */
    public int getidUser() {
        return idUser;
    }

    /**
     * @param idUser the userName to set
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * @return the inssueDate
     */
    public String getIssueDate() {
        return issueDate;
    }

    /**
     * @param issueDate the issueDate to set
     */
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * @return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
