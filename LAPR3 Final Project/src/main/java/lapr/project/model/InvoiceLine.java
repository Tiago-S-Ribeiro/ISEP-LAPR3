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
public class InvoiceLine {
    
    /**
     * id of the rental the invoice represents
     */
    private int idRental;
    /**
     * ID of the vehicle used
     */
    private int idVehicle;
    /**
     * duration of the rental
     */
    private double rentalDuration;
    /**
     * cost of the invoice line
     */
    private double rentalCost;
    /**
     * date of the invoice
     */
    private String date;
    
    /**
     * earned points in that rental 
     */
    private int earnedPoints;
    
    public InvoiceLine(int idRental, int idVehicle, double rentalDuration, double rentalCost, String date, int earnedPoints) {
        this.idRental = idRental;
        this.idVehicle = idVehicle;
        this.rentalDuration = rentalDuration;
        this.rentalCost = rentalCost;
        this.date = date;
        this.earnedPoints = earnedPoints;
    }

    /**
     * @return the idRental
     */
    public int getIdRental() {
        return idRental;
    }

    /**
     * @param idRental the idRental to set
     */
    public void setIdRental(int idRental) {
        this.idRental = idRental;
    }

    /**
     * @return the idVehicle
     */
    public int getIdVehicle() {
        return idVehicle;
    }

    /**
     * @param idVehicle the idVehicle to set
     */
    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }

    /**
     * @return the rental_duration
     */
    public double getRentalDuration() {
        return rentalDuration;
    }

    /**
     * 
     * @return earned points
     */
    public int getEarnedPoints() {
        return earnedPoints;
    }

    /**
     * @param rentalDuration the rental_duration to set
     */
    public void setRentalDuration(double rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    /**
     * @return the rental_cost
     */
    public double getRentalCost() {
        return rentalCost;
    }

    /**
     * @param rentalCost the rental_cost to set
     */
    public void setRentalCost(double rentalCost) {
        this.rentalCost = rentalCost;
    }
    
    /**
     * @return the date of the invoice
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date to be set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @param earnedPoints new earned points
     */
    public void setEarnedPoints(int earnedPoints) {
        this.earnedPoints = earnedPoints;
    }
    
    
}
