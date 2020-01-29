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
public class Path {

    private PointInterest pointFrom;
    private PointInterest pointTo;
    private double kineticCoefficient;
    private float windDirection;
    private float windSpeed;
    private static final int CONSTANT_KINETIC_COEFFICIENT = 0;
    private static final int CONSTANT_WIND_DIRECTION = 0;
    private static final int CONSTANT_WIND_SPEED = 0;

    public Path(PointInterest pointFrom, PointInterest pointTo, double kineticCoefficient, float windDirection, float windSpeed) {
        this.pointFrom = pointFrom;
        this.pointTo = pointTo;
        this.kineticCoefficient = kineticCoefficient;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
    }
    
    
    public Path(PointInterest pointFrom, PointInterest pointTo) {
        this.pointFrom = pointFrom;
        this.pointTo = pointTo;
        this.kineticCoefficient = CONSTANT_KINETIC_COEFFICIENT;
        this.windDirection = CONSTANT_WIND_DIRECTION;
        this.windSpeed = CONSTANT_WIND_SPEED;
    }
    
    /**
     * @return the pointFrom
     */
    public PointInterest getPointFrom() {
        return pointFrom;
    }

    /**
     * @return the pointTo
     */
    public PointInterest getPointTo() {
        return pointTo;
    }
    
    /**
     * @return the kineticCoefficient
     */
    public double getKineticCoefficient() {
        return kineticCoefficient;
    }
    
    /**
     * @return the windDirection
     */
    public float getWindDirection() {
        return windDirection;
    }

    /**
     * @return the windSpeed
     */
    public float getWindSpeed() {
        return windSpeed;
    }
    
    /**
     * @param pointFrom the pointFrom to set
     */
    public void setPointFrom(PointInterest pointFrom) {
        this.pointFrom = pointFrom;
    }

    /**
     * @param pointTo the pointTo to set
     */
    public void setPointTo(PointInterest pointTo) {
        this.pointTo = pointTo;
    }
    
    /**
     * @param kineticCoefficient the kineticCoefficient to set
     */
    public void setKineticCoefficient(double kineticCoefficient) {
        this.kineticCoefficient = kineticCoefficient;
    }
    
    /**
     * @param windDirection the windDirection to set
     */
    public void setWindDirection(float windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * @param windSpeed the windSpeed to set
     */
    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }
    
}
