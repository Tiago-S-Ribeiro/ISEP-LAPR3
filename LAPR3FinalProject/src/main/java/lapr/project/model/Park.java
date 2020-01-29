/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author G25
 */
public class Park extends PointInterest{

    /**
     * Representative of an instance of park idPark.
     */
    private int idPark;
    
    /**
     * park reference
     */
    private String refPark;

    /**
     * Representative of an instance of park state.
     */
    private int state;

    /**
     * Representative of an instance of park input voltage.
     */
    private int parkInputVoltage;

    /**
     * Representative of an instance of park input current.
     */
    private int parkInputCurrent;

    /**
     * Representative of an instance of park capacity.
     */
    private List<Capacity> capacity = new LinkedList<>();

    /**
     * Constructor of the class Park with the variables passed as a parameter.
     *
     * @param refPark park reference
     * @param description description of the Park
     * @param state state of the Park
     * @param parkInputVoltage park_input_voltage of the Park
     * @param parkInputCurrent park_input_current of the Park
     * @param geoLocation geographical location
     */
    public Park(String refPark, String description, int state, int parkInputVoltage, int parkInputCurrent, GeographicalLocation geoLocation) {
        super(geoLocation, description);
        this.refPark = refPark;
        this.state = state;
        this.parkInputVoltage = parkInputVoltage;
        this.parkInputCurrent = parkInputCurrent;
        this.capacity = new LinkedList<>();
    }
    
    /**
     * Constructor of the class Park with the variables passed as a parameter.
     *
     * @param idPark park id
     * @param refPark park reference
     * @param description description of the Park
     * @param state state of the Park
     * @param parkInputVoltage park_input_voltage of the Park
     * @param parkInputCurrent park_input_current of the Park
     * @param geoLocation geographical location
     */
    public Park(int idPark, String refPark, String description, int state, int parkInputVoltage, int parkInputCurrent, GeographicalLocation geoLocation) {
        super(idPark, geoLocation, description);
        this.refPark = refPark;
        this.state = state;
        this.parkInputVoltage = parkInputVoltage;
        this.parkInputCurrent = parkInputCurrent;
        this.capacity = new LinkedList<>();
    }

    /**
     * @return the idPark
     */
    public int getId() {
        return idPark;
    }

    /**
     * @param id the idPark to set
     */
    public void setId(int id) {
        this.idPark = id;
    }
    
    /**
     * @return the park reference
     */
    public String getRefPark() {
        return refPark;
    }

    /**
     * @param refPark the description to set
     */
    public void setRefPark(String refPark) {
        this.refPark = refPark;
    }

    /**
     * @return the state
     */
    public int isState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the park_input_voltage
     */
    public int getParkInputVoltage() {
        return parkInputVoltage;
    }

    /**
     * @param parkInputVoltage the park_input_voltage to set
     */
    public void setParkInputVoltage(int parkInputVoltage) {
        this.parkInputVoltage = parkInputVoltage;
    }

    /**
     * @return the park_input_current
     */
    public int getParkInputCurrent() {
        return parkInputCurrent;
    }

    /**
     * @param parkInputCurrent the park_input_current to set
     */
    public void setParkInputCurrent(int parkInputCurrent) {
        this.parkInputCurrent = parkInputCurrent;
    }

    /**
     * @return the capacity
     */
    public List<Capacity> getCapacity() {
        return new LinkedList<>(capacity);
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(List<Capacity> capacity) {
        this.capacity = new LinkedList<>(capacity);
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
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        
        Park other = (Park) obj;
        return (this.idPark == other.idPark);
    }
    
    /**
     * Hashcode
     * @return An integer representative of the hash code of this instance.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.idPark;
        return hash;
    }
}
