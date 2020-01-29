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
public class Bicycle extends Vehicle {

    private double wheelSize;

    public Bicycle(int id, double wheelSize, int state, String description, double weight, double aerodynamicCoefficient, double frontal_area) {
        super(id, state, description, weight, aerodynamicCoefficient, frontal_area);
        this.wheelSize = wheelSize;
    }

    public Bicycle(double wheelSize, int state, String description, double weight, double aerodynamicCoefficient, double frontalArea){
        super(state, description, weight, aerodynamicCoefficient, frontalArea);
        this.wheelSize = wheelSize;
    }
    
    public double getWheelSize() {
        return wheelSize;
    }

    public void setWheelSize(double wheelSize) {
        this.wheelSize = wheelSize;
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
        Bicycle other = (Bicycle) obj;
        return this.getId() == other.getId();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.getId()) ^ (Double.doubleToLongBits(this.getId()) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "Bycicle{" + "wheelSize=" + wheelSize + '}';
    }

    
}
