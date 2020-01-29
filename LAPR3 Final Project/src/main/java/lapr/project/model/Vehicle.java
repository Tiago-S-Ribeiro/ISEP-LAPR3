/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.logging.Logger;

/**
 *
 * @author Ana Rita Veiga
 */
public class Vehicle{

    private int id;
    private int state;
    private String description;
    private double weight;
    private double aerodynamicCoefficient;
    private double frontalArea;
    public static final int BICYCLE_TYPE_ID = 1;
    public static final int SCOOTER_TYPE_ID = 2;
    
    /**
     * Constructor Vehicle with parameters
     * @param id vehicle id
     * @param state vehicle state (1(available) or 0(unavailable))
     * @param description vehicle description
     * @param weight vehicle weight
     * @param aerodynamicCoefficient vehicle aerodynamic coefficient
     * @param frontalArea vehicle frontal area
     */
    public Vehicle(int id, int state, String description, double weight, double aerodynamicCoefficient, double frontalArea) {
        this.id = id;
        this.state = state;
        this.description = description;
        this.weight = weight;
        this.aerodynamicCoefficient = aerodynamicCoefficient;
        this.frontalArea = frontalArea;
    }

    
       public Vehicle( int state, String description, double weight, double aerodynamicCoefficient, double frontalArea) {
        this.id = 0;
        this.state = state;
        this.description = description;
        this.weight = weight;
        this.aerodynamicCoefficient = aerodynamicCoefficient;
        this.frontalArea = frontalArea;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return the aerodynamicCoefficient
     */
    public double getAerodynamicCoefficient() {
        return aerodynamicCoefficient;
    }

    /**
     * @return the frontalArea
     */
    public double getFrontalArea() {
        return frontalArea;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * @param aerodynamicCoefficient the aerodynamicCoefficient to set
     */
    public void setAerodynamicCoefficient(double aerodynamicCoefficient) {
        this.aerodynamicCoefficient = aerodynamicCoefficient;
    }

    /**
     * @param frontalArea the frontalArea to set
     */
    public void setFrontalArea(double frontalArea) {
        this.frontalArea = frontalArea;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.getId();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vehicle other = (Vehicle) obj;
        
        return this.getId() == other.getId();
    }

    @Override
    public String toString() {
        return "Vehicle{" + "id=" + getId() + ", state=" + getState() + ", description=" + getDescription() + ", weight=" + getWeight() + ", aerodynamic_coefficient=" + getAerodynamicCoefficient() + ", frontal_area=" + getFrontalArea() + '}';
    }
    
}
