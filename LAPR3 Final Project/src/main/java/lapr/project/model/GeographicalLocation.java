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
public class GeographicalLocation {

    /**
     * Representative of an instance of latitude.
     */
    private double latitude;

    /**
     * Representative of an instance of longitude.
     */
    private double longitude;
    
    /**
     * elevation of geographical location coordinates
     */
    private double elevation;
    /**
     * Constructor of the class Geographical Location with the variables passed as a parameter.
     *
     * @param latitude latitude of Geographical Location
     * @param longitude longitude of Geographical Location
     * @param elevation elevation of Geographical Location
     */
    public GeographicalLocation(double latitude, double longitude, double elevation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    /**
     * 
     * @return geographical location elevation
     */
    public double getElevation(){
        return elevation;
    }
    
    /**
     * 
     * @param elevation new geographical location elevation to be set to caller object
     */
    public void setElevation(double elevation){
        this.elevation = elevation;
    }
    
    /**
     * hashcode
     * @return An integer representative of the hash code of this instance.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.elevation) ^ (Double.doubleToLongBits(this.elevation) >>> 32));
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
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        GeographicalLocation other = (GeographicalLocation) obj;
        return this.latitude == other.latitude
            && this.longitude == other.longitude
            && this.elevation == other.elevation;
    }

    
}
