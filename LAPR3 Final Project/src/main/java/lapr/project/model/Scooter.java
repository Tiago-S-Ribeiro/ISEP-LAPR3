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
public class Scooter extends Vehicle {

    private int type;
    private int maxBatteryCapacity;
    private int actualBatteryCapacity;
    private int motor;
    public static final int SCOOTER_OFF_ROAD_ID = 1; 
    public static final int SCOOTER_CITY_ID = 2;
    
    public Scooter(int type,int maxBatteryCapacity, int actualBattteryCapacity, int motor, int id, int state, String description, double weight, double aerodynamicCoefficient, double frontalArea) {
        super(id,state, description,weight, aerodynamicCoefficient, frontalArea);
        this.type = type;
        this.maxBatteryCapacity = maxBatteryCapacity;
        this.actualBatteryCapacity = actualBattteryCapacity;
        this.motor = motor;
    }

    public Scooter(int type,int maxBatteryCapacity, int actualBattteryCapacity, int motor, int state, String description, double weight, double aerodynamicCoefficient, double frontalArea){
        super(state, description, weight, aerodynamicCoefficient, frontalArea);
        this.type = type;
        this.maxBatteryCapacity = maxBatteryCapacity;
        this.actualBatteryCapacity = actualBattteryCapacity;
        this.motor = motor;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

     public int getMaxBatteryCapacity() {
        return maxBatteryCapacity;
    }

    public void setMaxBatteryCapacity(int maxBatteryCapacity) {
        this.maxBatteryCapacity = maxBatteryCapacity;
    }

    public int getActualBatteryCapacity() {
        return actualBatteryCapacity;
    }

    public void setActualBatteryCapacity(int actualBatteryCapacity) {
        this.actualBatteryCapacity = actualBatteryCapacity;
    }

    public int getMotor() {
        return motor;
    }

    public void setMotor(int motor) {
        this.motor = motor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Scooter other = (Scooter) obj;
        return this.maxBatteryCapacity == other.maxBatteryCapacity
            && this.actualBatteryCapacity == other.actualBatteryCapacity
            && this.type==(other.type);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.type;
        hash = 97 * hash + this.maxBatteryCapacity;
        hash = 97 * hash + this.actualBatteryCapacity;
        return hash;
    }

    @Override
    public String toString() {
        return "Scooter{" + "type=" + type + ",max_batery_capacity=" + maxBatteryCapacity + ", actual_batery_capacity=" + actualBatteryCapacity + '}';
    }

}
