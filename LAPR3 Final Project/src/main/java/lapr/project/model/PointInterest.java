/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

/**
 *
 * @author Beatriz Ribeiro
 */
public class PointInterest {

    private int idPoint;
    private GeographicalLocation geoLocation;
    private String description;

    public PointInterest(GeographicalLocation geoLocation, String description) {
        this.idPoint = 0;
        this.geoLocation = geoLocation;
        this.description = description;
    }

    public PointInterest(int idPoint, GeographicalLocation geoLocation, String description) {
        this.idPoint = idPoint;
        this.geoLocation = geoLocation;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public GeographicalLocation getGeoLocation() {
        return geoLocation;
    }

    public int getIdPoint() {
        return idPoint;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGeoLocation(GeographicalLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public void setIdPoint(int idPoint) {
        this.idPoint = idPoint;
    }

    /**
     * Hashcode
     *
     * @return An integer representative of the hash code of this instance.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.idPoint;
        return hash;
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

        PointInterest other = (PointInterest) obj;
        return (this.idPoint == other.idPoint);
    }
    
    @Override
    public String toString() {
        return "PointInterest{" + "idPoint=" + idPoint + '}';
    }

}
