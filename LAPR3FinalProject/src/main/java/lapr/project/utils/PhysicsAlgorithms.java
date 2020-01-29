/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

/**
 *
 * @author G25
 */
public class PhysicsAlgorithms {

    private static final double CONSTANT_GRAVITY = 9.80665;
    private static final double AIR_DENSITY = 1.225; //to a temperature of 15 degrees
    private static final double EFFICIENCY_SCOOTERS = 0.7;//70% 
    public static final int EARTH_RADIUS = 6371;// Radius of the earth 
    public static final double EFFICENCY_CHARGE = 0.90;//90% 
    public static final int SCOOTER_AVERAGE_SPEED =6;//m/s

    public double calculateCalories(double bikeWeight, double userWeight, double kineticCoefficient, double frontalArea, float cyclingAverageSpeed, double aerodynamicCoefficient, float windSpeed, float windDirection, double elevationInicial, double elevationFinal, double latitudeRad, double latitude2Rad, double longitudeRad, double longitude2Rad) {
        double totalForce = calculateTotalForce(bikeWeight, userWeight, elevationInicial, elevationFinal, kineticCoefficient, frontalArea, cyclingAverageSpeed, aerodynamicCoefficient, windSpeed, windDirection, latitudeRad, latitude2Rad, longitudeRad, longitude2Rad);
        double newCyclingAverageSpeed = calculateAverageSpeedWithWindDirection(cyclingAverageSpeed, windSpeed, windDirection);
        double distance = calculateDistanceWithElevation(latitudeRad, latitude2Rad, longitudeRad, longitude2Rad, elevationInicial, elevationFinal);//km
        double time =(distance/(newCyclingAverageSpeed));// s
        double calories = ((totalForce * newCyclingAverageSpeed)*time)/4.18 ;//1cal = 4.18J
        if (calories <= 0) {
            return 0;
        }
        return calories;
    }

    public double calculateTotalForce(double bikeWeight, double userWeight, double elevationInicial, double elevationFinal, double kineticCoefficient, double frontalArea, float cyclingAverageSpeed, double aerodynamicCoefficient, float windSpeed, float windDirection, double latitudeRad, double latitude2Rad, double longitudeRad, double longitude2Rad) {
        double gravitationalForce = calculateGravitationalForce(bikeWeight, userWeight, elevationInicial, elevationFinal, latitudeRad, latitude2Rad, longitudeRad, longitude2Rad);
        double frictionalForce = calculateFrictionalForce(bikeWeight, userWeight, kineticCoefficient, elevationInicial, elevationFinal, latitudeRad, latitude2Rad, longitudeRad, longitude2Rad);
        double windForce = calculateWindeForce(frontalArea, cyclingAverageSpeed, aerodynamicCoefficient, windSpeed);
        return gravitationalForce + frictionalForce + windForce;
    }

    public double calculateGravitationalForce(double bikeWeight, double userWeight, double elevationInicial, double elevationFinal, double latitudeRad, double latitude2Rad, double longitudeRad, double longitude2Rad) {
        double totalWeight = bikeWeight + userWeight;
        double pathInclination = calculatePathInclination(elevationInicial, elevationFinal, latitudeRad, latitude2Rad, longitudeRad, longitude2Rad);
        return totalWeight * CONSTANT_GRAVITY * pathInclination;
    }

    public double calculatePathInclination(double elevationInicial, double elevationFinal, double latitudeRad, double latitude2Rad, double longitudeRad, double longitude2Rad) {
        double distance = linearDistanceTo(latitudeRad, latitude2Rad, longitudeRad, longitude2Rad);
        return (elevationFinal - elevationInicial) / distance;
    }

    public double calculateFrictionalForce(double bikeWeight, double userWeight, double kineticCoefficient, double elevationInicial, double elevationFinal, double latitudeRad, double latitude2Rad, double longitudeRad, double longitude2Rad) {
        double totalWeight = bikeWeight + userWeight;
        double pathInclination = calculatePathInclination(elevationInicial, elevationFinal, latitudeRad, latitude2Rad, longitudeRad, longitude2Rad);
        return totalWeight * CONSTANT_GRAVITY * Math.sqrt(1 - Math.pow(pathInclination, 2)) * kineticCoefficient;
    }

    public double calculateWindeForce(double frontalArea, float cyclingAverageSpeed, double aerodynamicCoefficient, float windSpeed) {
        return 0.5 * AIR_DENSITY * frontalArea * aerodynamicCoefficient * Math.pow((cyclingAverageSpeed - windSpeed), 2);
    }

    public double calculateAverageSpeedWithWindDirection(float cyclingAverageSpeed, float windSpeed, float windDirection) {
        if (windDirection == 90) {
            return cyclingAverageSpeed - (windSpeed * Math.cos(0));
        } else if (windDirection > 0 && windDirection < 180) {
            double windDirectionRad = Math.toRadians(windDirection);
            return cyclingAverageSpeed - (windSpeed * Math.abs(Math.cos(windDirectionRad)));
        } else {
            return cyclingAverageSpeed;
        }
    }

    /**
     * Calculate calculateDistanceWithElevation between two points in latitude
     * and longitude taking into account height difference. If you are not
     * interested in height difference pass 0.0. Uses Haversine method as its
     * base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    public static double calculateDistanceWithElevation(double lat1, double lat2, double lon1,
            double lon2, double el1, double el2) {

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * Get the linear calculateDistanceWithElevation from one location to
     * another.
     *
     * @param lat1 Origin latitude in Decimal Degrees.
     * @param lat2 Origin longitude in Decimal Degrees.
     * @param long1 Destiny latitude in Decimal Degrees.
     * @param long2 Destiny longitude in Decimal Degrees.
     * @return Returns the calculateDistanceWithElevation in meters from one
     * location to another.
     */
    public double linearDistanceTo(double lat1, double lat2, double long1, double long2) {
        return calculateDistanceWithElevation(lat1, lat2, long1, long2, 0, 0); //m
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public double calculateDistanceAScooterCanDo(int motor, double scooterActualBatteryCapacity, int scooterMaxBatteryCapacity) {
        scooterMaxBatteryCapacity = scooterMaxBatteryCapacity * 1000* 3600;
        double time = (((scooterActualBatteryCapacity * scooterMaxBatteryCapacity) / 100) / motor) ; //s
        return (time * SCOOTER_AVERAGE_SPEED * EFFICIENCY_SCOOTERS);//m

    }

    /**
     * Calculate the amount of electrical energy required to travel from one
     * location to another.
     */
    public double calculateEnergySpentBetweenPoints(double scooterWeight, double userWeight, double kineticCoefficient, double frontalArea, double aerodynamicCoefficient, float windSpeed, float windDirection, double elevationInicial, double elevationFinal, double latitudeRad, double latitude2Rad, double longitudeRad, double longitude2Rad) {
        double totalForce = calculateTotalForce(scooterWeight, userWeight, elevationInicial, elevationFinal, kineticCoefficient, frontalArea, SCOOTER_AVERAGE_SPEED, aerodynamicCoefficient, windSpeed, windDirection, latitudeRad, latitude2Rad, longitudeRad, longitude2Rad);
        double newAverageSpeed = calculateAverageSpeedWithWindDirection(SCOOTER_AVERAGE_SPEED, windSpeed, windDirection);
        double distance = calculateDistanceWithElevation(latitudeRad, latitude2Rad, longitudeRad, longitude2Rad, elevationInicial, elevationFinal);
        double time = ((distance)/(newAverageSpeed));//s
        return (((totalForce * newAverageSpeed) * time));

    }

    public double calculateEnergyforGivenTrip(double scooterWeight, double userWeight, double kineticCoefficient, double frontalArea,double aerodynamicCoefficient, float windSpeed, float windDirection, double elevationInicial, double elevationFinal, double latitudeRad, double latitude2Rad, double longitudeRad, double longitude2Rad) {
        double calculateEnergySpentBetweenPoints = calculateEnergySpentBetweenPoints(scooterWeight, userWeight, kineticCoefficient, frontalArea,aerodynamicCoefficient, windSpeed, windDirection, elevationInicial, elevationFinal, latitudeRad, latitude2Rad, longitudeRad, longitude2Rad);
        return (calculateEnergySpentBetweenPoints * 0.1) + calculateEnergySpentBetweenPoints;//J
    }

    public double calculateTimeNeededToChargeTotaly(double parkVoltage, double parkCurrent, double scooterActualBatteryCapacity, int scooterMaxBatteryCapacity, int numberOfPoints) {
        scooterMaxBatteryCapacity = scooterMaxBatteryCapacity * 1000;
        double parkPower = parkCurrent * parkVoltage; //w
        double pointPower = (parkPower / numberOfPoints);//W
        if (pointPower > 3000) {
            pointPower = 3000;
        }
        if (pointPower <= 0) {
            return 0;
        }
        return ((scooterMaxBatteryCapacity - ((scooterActualBatteryCapacity * scooterMaxBatteryCapacity) / 100)) / (pointPower * EFFICENCY_CHARGE)) * 3600;//s
    }

}