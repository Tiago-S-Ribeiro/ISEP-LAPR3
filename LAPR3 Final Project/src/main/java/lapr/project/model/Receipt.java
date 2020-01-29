/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

/**
 *
 * @author Ana Rita Veiga
 */
public class Receipt {

    private int idReceipt;
    private int idUser;
    private int idInvoice;
    private double totalCost;
    private String date;

    public Receipt(int idUser, int idInvoice, double totalCost, String date) {
        this.idReceipt = 0;
        this.idUser = idUser;
        this.idInvoice = idInvoice;
        this.totalCost = totalCost;
        this.date = date;
    }

    /**
     * @return the idReceipt
     */
    public int getIdReceipt() {
        return idReceipt;
    }

    /**
     * @param idReceipt the idReceipt to set
     */
    public void setIdReceipt(int idReceipt) {
        this.idReceipt = idReceipt;
    }

    /**
     * @return the idUser
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
     * @return the totalCost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * @param totalCost the totalCost to set
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

}
