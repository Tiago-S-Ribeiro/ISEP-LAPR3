/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

/**
 *
 * @author Tiago Ribeiro
 */
public class Rental implements Comparable<Rental>{

    /**
     * id of rental
     */
    private int idRental;
    /**
     * total cost of rental
     */
    private double cost;
    /**
     * beginDateHour of rental
     */
    private String beginDateHour;
    
    /**
     * end date hour of rental
     */
    private String endDateHour;
    
    /**
     * duration of rental
     */
    private int duration;

    /**
     * user that made the rental
     */
    private User user;
    
    /**
     * rented vehicle  
     */
    private Vehicle vehicle;
    
    /**
     * park where vehicle was unlocked
     */
    private Park parkFrom;
    
    /**
     * park where vehicle was parked
     */
    private Park parkTo;
    
    /**
     * earned points on trip 
     */
    
    private int earnedPoints;
    /**
     * full constructor
     *
     * @param user user that made the rental
     * @param vehicle rented vehicle
     * @param parkFrom park where vehicle was picked
     */
    public Rental(User user, Vehicle vehicle, Park parkFrom) {
        this.idRental = 0;
        this.user = user;
        this.vehicle = vehicle;
        this.parkFrom = parkFrom;
        this.parkTo = null;
        this.cost = 0;
        this.beginDateHour = String.valueOf(System.currentTimeMillis());
        this.endDateHour = null;
        this.duration = 0;
        this.earnedPoints = 0;
    }

    /**
     *
     * @return rental id
     */
    public int getIdRental() {
        return idRental;
    }
    
    /**
     * 
     * @return user
     */
    public User getUser() {
        return user;
    }
    
    /**
     * 
     * @return vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    /**
     * 
     * @return park origin
     */
    public Park getParkFrom() {
        return parkFrom;
    }
    
    /**
     * 
     * @return park destiny
     */
    public Park getParkTo() {
        return parkTo;
    }
    
    /**
     *
     * @return rental cost
     */
    public double getCost() {
        return cost;
    }

    /**
     *
     * @return rental beginDateHour
     */
    public String getBeginDateHour() {
        return beginDateHour;
    }
    
    /**
     * 
     * @return end date hour
     */
    public String getEndDateHour() {
        return endDateHour;
    }
    
    /**
     *
     * @return rental duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * 
     * @return earned points 
     */
    public int getEarnedPoints() {
        return earnedPoints;
    }

    /**
     *
     * @param idRental new id to be set in caller object
     */
    public void setIdRental(int idRental) {
        this.idRental = idRental;
    }

    /**
     * 
     * @param user new user
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * 
     * @param vehicle new vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * 
     * @param parkFrom new park origin
     */
    public void setParkFrom(Park parkFrom) {
        this.parkFrom = parkFrom;
    }
    
    /**
     * 
     * @param parkTo new park destiny
     */
    public void setParkTo(Park parkTo) {
        this.parkTo = parkTo;
    }
    
    /**
     *
     * @param cost new cost to be set in caller object
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     *
     * @param date new beginDateHour to be set in caller object
     */
    public void setBeginDateHour(String date) {
        this.beginDateHour = date;
    }
    
    /**
     * 
     * @param endDateHour new end date hour 
     */
    public void setEndDateHour(String endDateHour) {
        this.endDateHour = endDateHour;
    }
    
    /**
     *
     * @param duration new duration to be set in caller object
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * 
     * @param earnedPoints new earned points by user in trip 
     */
    public void setEarnedPoints(int earnedPoints) {
        this.earnedPoints = earnedPoints;
    }
    
    /**
     * compares the begin date hour of two rentals
     * @param o Rental object being compared to called object
     * @return 0 if equal, greater than 0 if callee is bigger, lesser than 0 if caller is bigger
     */
    @Override
    public int compareTo(Rental o) {
        return (int)(Long.parseLong(this.getBeginDateHour()) - Long.parseLong(o.getBeginDateHour()));
    }
    
}
