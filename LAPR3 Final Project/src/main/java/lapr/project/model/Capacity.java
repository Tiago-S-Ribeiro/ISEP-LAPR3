/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

/**
 *
 * @author G25
 */
public class Capacity {

    /**
     * Representative of an instance of capacity type.
     */
    private int type;

    /**
     * Representative of an instance of capacity max.
     */
    private int capacityMax;
    
    private int availability;

    /**
     * Constructor of the class Capacity with the variables passed as a
     * parameter.
     *
     * @param type type of the vehicle
     * @param capacityMax maximum park capacity for vehicle type
     */
    public Capacity(int type, int capacityMax) {
        this.type = type;
        this.capacityMax = capacityMax;
        this.availability = capacityMax;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the capcity_max
     */
    public int getCapacityMax() {
        return capacityMax;
    }

    /**
     * @param capacityMax the capcity_max to set
     */
    public void setCapacityMax(int capacityMax) {
        this.capacityMax = capacityMax;
    }
    
    /**
     * @return the capcity_max
     */
    public int getAvailability() {
        return availability;
    }

    /**
     * @param availability park availability
     */
    public void setAvailability(int availability) {
        this.availability = availability;
    }

    /**
     * Equals method.
     *
     * @param obj The other instance passed as a parameter.
     * @return True if both objects are the same, false if not.
     */
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
        Capacity other = (Capacity) obj;
        return this.type == (other.type)
            && this.capacityMax == other.capacityMax;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.type;
        hash = 31 * hash + this.capacityMax;
        return hash;
    }
}
