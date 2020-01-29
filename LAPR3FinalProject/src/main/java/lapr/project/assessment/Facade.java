package lapr.project.assessment;

import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import lapr.project.model.*;
import lapr.project.controller.*;
import lapr.project.data.*;
import lapr.project.utils.PhysicsAlgorithms;

public class Facade implements Serviceable {

    @Override
    public int addBicycles(String s) {

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        int count = 0;
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            br = new BufferedReader(new FileReader(s));
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && !line.startsWith("bicycle")) {

                    String[] bikeInfo = line.split(cvsSplitBy);

                    String description = bikeInfo[0].trim();
                    float weight = Float.parseFloat(bikeInfo[1].trim());
                    double parkLatitude = Double.parseDouble(bikeInfo[2].trim());
                    double parkLongitude = Double.parseDouble(bikeInfo[3].trim());
                    float aerodynamicCoefficient = Float.parseFloat(bikeInfo[4].trim());
                    float frontalArea = Float.parseFloat(bikeInfo[5].trim());
                    int wheelSize = Integer.parseInt(bikeInfo[6].trim());
                    int idPark = pc.getParkByCoordinates(parkLatitude, parkLongitude).getIdPoint();
                    System.out.println(idPark);
                    Bicycle bicycle = (Bicycle) vc.addBicycle(idPark, wheelSize, 1, description, weight, aerodynamicCoefficient, frontalArea);
                    if (bicycle.getId() != 0) {
                        count++;
                    }
                }
            }
            DataHandler.getInstance().getConnection().commit();
        } catch (SQLException e) {
            count = 0;
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
                }
            }
        }
        return count;
    }

    @Override
    public int addEscooters(String s) {

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        int count = 0;
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && !line.startsWith("escooter")) {

                    String[] escooterInfo = line.split(cvsSplitBy);

                    String description = escooterInfo[0].trim();
                    float weight = Float.parseFloat(escooterInfo[1]);
                    String escooterType = escooterInfo[2].trim();
                    double parkLatitude = Double.parseDouble(escooterInfo[3].trim());
                    double parkLongitude = Double.parseDouble(escooterInfo[4].trim());
                    float maxBateryCapacity = Float.parseFloat(escooterInfo[5].trim());
                    float actualBateryCapacity = Float.parseFloat(escooterInfo[6].trim());
                    float aerodynamicCoefficient = Float.parseFloat(escooterInfo[7].trim());
                    float frontalArea = Float.parseFloat(escooterInfo[8].trim());
                    int motor = Integer.parseInt(escooterInfo[9].trim());
                    int idPark = pc.getParkByCoordinates(parkLatitude, parkLongitude).getIdPoint();

                    int type = 0;
                    if (escooterType.equalsIgnoreCase("city")) {
                        type = Scooter.SCOOTER_CITY_ID;
                    } else {
                        type = Scooter.SCOOTER_OFF_ROAD_ID;
                    }
                    Scooter scooter = (Scooter) vc.addScooter(idPark, type, (int) maxBateryCapacity, (int) actualBateryCapacity, motor, 1, description, weight, aerodynamicCoefficient, frontalArea);
                    if (scooter.getId() != 0) {
                        count++;
                    }
                }

            }
            DataHandler.getInstance().getConnection().commit();
        } catch (SQLException e) {
            count = 0;
            try {

                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
            }
            System.err.println(e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
                }
            }
        }
        return count;
    }

    @Override
    public int addParks(String s) {
        ParkController pc = new ParkController(new ParkDataHandler());

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        int count = 0;
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            br = new BufferedReader(new FileReader(s));
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && !line.startsWith("park identification")) {
                    String[] parkInfo = line.split(cvsSplitBy);

                    String parkIdentification = parkInfo[0].trim();
                    double parkLatitude = Double.parseDouble(parkInfo[1].trim());
                    double parkLongitude = Double.parseDouble(parkInfo[2].trim());
                    double parkElevation = 0;
                    if (!parkInfo[3].equals(" ")) {
                        parkElevation = Double.parseDouble(parkInfo[3].trim());
                    }
                    String parkDescription = parkInfo[4];
                    int maxBicycles = Integer.parseInt(parkInfo[5].trim());
                    int maxEscooters = Integer.parseInt(parkInfo[6].trim());
                    float parkVoltage = Float.parseFloat(parkInfo[7].trim());
                    float parkCurrent = Float.parseFloat(parkInfo[8].trim());
                    Park park = pc.addPark(parkIdentification, parkDescription, 1, (int) parkVoltage, (int) parkCurrent, parkLatitude, parkLongitude, parkElevation, maxBicycles, maxEscooters);
                    if (park.getIdPoint() != 0) {
                        count++;
                    }
                }
            }
            DataHandler.getInstance().getConnection().commit();
        } catch (SQLException e) {
            count = 0;
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
            }
            System.err.println(e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
                }
            }
        }
        return count;

    }

    /**
     * Reads Points of Interest and adds them to the database
     *
     * @param s file name
     * @return amount of points of interest
     */
    @Override
    public int addPOIs(String s) {

        ParkController pc = new ParkController(new ParkDataHandler());
        BufferedReader br = null;
        String line = "";
        int nrPOISadded = 0;

        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && !line.startsWith("latitude")) {
                    String[] POIs = line.split(";");

                    double latitude = Double.parseDouble(POIs[0].trim());
                    double longitude = Double.parseDouble(POIs[1].trim());
                    double elevation = 0;
                    if (!POIs[2].equals(" ")) {
                        elevation = Double.parseDouble(POIs[2].trim());
                    }
                    String description = POIs[3];
                    PointInterest poi = pc.addPOI(latitude, longitude, elevation, description);
                    if (poi.getIdPoint() != 0) {
                        nrPOISadded++;
                    }
                }
            }
            DataHandler.getInstance().getConnection().commit();
        } catch (SQLException e) {
            nrPOISadded = 0;
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
                }
            }
        }
        return nrPOISadded;
    }

    @Override
    public int addUsers(String s) {
        UserController uc = new UserController(new UserDataHandler());

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        int count = 0;
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && !line.startsWith("username")) {

                    String[] userInfo = line.split(cvsSplitBy);

                    String username = userInfo[0].trim();
                    String email = userInfo[1].trim();
                    int height = Integer.parseInt(userInfo[2].trim());
                    Float weight = Float.parseFloat(userInfo[3].trim());
                    Float cyclingAverageSpeed = Float.parseFloat(userInfo[4].trim());
                    String creditCard = userInfo[5].trim();
                    String gender = userInfo[6].trim();
                    String pwd = userInfo[7].trim();
                    User user = uc.addUser(email, username, creditCard, cyclingAverageSpeed, height, weight, gender, pwd);
                    if (user.getIdUser() != 0) {
                        count++;
                    }
                }
            }
            DataHandler.getInstance().getConnection().commit();
        } catch (SQLException e) {
            count = 0;
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
                }
            }
        }
        return count;
    }

    /**
     * Add Paths to the system.
     *
     * @param s inputFile (Path to file that contains the Paths, according to
     * file input/paths.csv) inputFile order: latitudeFrom, longitudeFrom,
     * latitudeTo, longitudeTo, kineticCoefficient, windDirection, windSpeed
     * @return the number of added Paths.
     */
    @Override
    public int addPaths(String s) {
        ParkController pc = new ParkController(new ParkDataHandler());
        BufferedReader br = null;
        String line = "";
        int addedPaths = 0;

        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            br = new BufferedReader(new FileReader(s));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && !line.startsWith("latitudeA")) {
                    String[] paths = line.split(";");

                    double latitudeFrom = Double.parseDouble(paths[0].trim());
                    double longitudeFrom = Double.parseDouble(paths[1].trim());
                    double latitudeTo = Double.parseDouble(paths[2].trim());
                    double longitudeTo = Double.parseDouble(paths[3].trim());
                    double kinetic = 0;
                    if (!paths[4].equals(" ")) {
                        kinetic = Double.parseDouble(paths[4].trim());
                    }
                    float windDirection = 0;
                    if (!paths[5].equals(" ")) {
                        windDirection = Float.parseFloat(paths[5].trim());
                    }
                    float windSpeed = 0;
                    if (!paths[6].equals(" ")) {
                        windSpeed = Float.parseFloat(paths[6].trim());
                    }

                    int idFrom = pc.getPointByCoordinates(latitudeFrom, longitudeFrom).getIdPoint();
                    int idTo = pc.getPointByCoordinates(latitudeTo, longitudeTo).getIdPoint();
                    boolean wasAdded = pc.addPath(idFrom, idTo, kinetic, windDirection, windSpeed);
                    if (wasAdded == true) {
                        addedPaths++;
                    }
                }
            }
            DataHandler.getInstance().getConnection().commit();
        } catch (SQLException e) {
            addedPaths = 0;
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
                }
            }
        }
        return addedPaths;
    }

    @Override
    public int getNumberOfBicyclesAtPark(double v, double v1, String s) {
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        int idPark = pc.getParkByCoordinates(v, v1).getIdPoint();
        List<Bicycle> bicyclesOfPark = vc.getAllBicyclesOfPark(idPark);

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(s));
            writer.write("bicycle description;wheel size");

            for (Bicycle b : bicyclesOfPark) {
                writer.newLine();
                writer.write(b.getDescription() + ";" + b.getWheelSize());

            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bicyclesOfPark.size();
    }

    /**
     * Distance is returns in metres, rounded to the unit e.g. (281,58 rounds to
     * 282);
     *
     * @param v Latitude in degrees.
     * @param v1 Longitude in degrees.
     * @param s Filename for output.
     */
    @Override
    public void getNearestParks(double v, double v1, String s) {

        BufferedWriter writer = null;

        ParkController pc = new ParkController(new ParkDataHandler());

        List<Pair<Double, Park>> nearestParks = new ArrayList<>();

        try {
            writer = new BufferedWriter(new FileWriter(s));
            writer.write("latitude;longitude;distance in meters");
            nearestParks = pc.getNearestParks(v, v1, 1000);

            for (Pair<Double, Park> p : nearestParks) {
                writer.newLine();
                writer.write(p.getValue().getGeoLocation().getLatitude() + ";" + p.getValue().getGeoLocation().getLongitude()
                        + ";" + (int) Math.round(p.getKey()));
            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public int getNumberOfBicyclesAtPark(String string, String string1) {
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        int idPark = pc.getParkByRefPark(string).getIdPoint();
        List<Bicycle> bicyclesOfPark = vc.getAllBicyclesOfPark(idPark);

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(string1));
            writer.write("bicycle description;wheel size");

            for (Bicycle b : bicyclesOfPark) {
                writer.newLine();
                writer.write(b.getDescription() + ";" + b.getWheelSize());

            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bicyclesOfPark.size();
    }

    /**
     * Remove a park from the system
     *
     * @param string park identification - park to be removed from the system
     * @return the number of removed parks
     */
    @Override
    public int removePark(String string) {
        ParkController pc = new ParkController(new ParkDataHandler());

        int removedParks = 0;
        int parkId = pc.getParkByRefPark(string).getIdPoint();

        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            boolean removal = pc.removePark(parkId);
            DataHandler.getInstance().getConnection().commit();
            if (removal == true) {
                removedParks++;
            }
        } catch (SQLException ex) {
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException e) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return removedParks;
    }

    @Override
    public void getNearestParks(double d, double d1, String string, int i) {
        BufferedWriter writer = null;

        ParkController pc = new ParkController(new ParkDataHandler());

        List<Pair<Double, Park>> nearestParks = new ArrayList<>();

        try {
            writer = new BufferedWriter(new FileWriter(string));
            writer.write("latitude;longitude;distance in meters");
            nearestParks = pc.getNearestParks(d, d1, (double) i);

            for (Pair<Double, Park> p : nearestParks) {
                writer.newLine();
                writer.write(p.getValue().getGeoLocation().getLatitude() + ";" + p.getValue().getGeoLocation().getLongitude()
                        + ";" + (int) Math.round(p.getKey()));
            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public int getFreeBicycleSlotsAtPark(String string, String string1) {
        ParkController pc = new ParkController(new ParkDataHandler());
        int freeBikeSpots;

        int parkId = pc.getParkByRefPark(string).getIdPoint();
        freeBikeSpots = pc.checkParkFreeBicycleSpots(parkId);

        return freeBikeSpots;
    }

    /**
     * Gets the number of free scooter slots at a given park
     *
     * @param string park identification
     * @param string1 username (unused)
     * @return the number of free scooter slots
     */
    @Override
    public int getFreeEscooterSlotsAtPark(String string, String string1) {

        ParkController pc = new ParkController(new ParkDataHandler());
        int numberOfFreeSlots;

        int parkId = pc.getParkByRefPark(string).getIdPoint();
        numberOfFreeSlots = pc.checkParkFreeScooterSpots(parkId);

        return numberOfFreeSlots;
    }

    /**
     * Gets the number of free slots for a given user
     *
     * @param string user's username
     * @param string2 park identification
     * @return the number of free slots of currently loaned vehicle
     */
    @Override
    public int getFreeSlotsAtParkForMyLoanedVehicle(String string, String string2) {

        ParkController pc = new ParkController(new ParkDataHandler());
        UserController uc = new UserController(new UserDataHandler());

        String parkIdentification = string2;
        int numberOfFreeSlots;

        int parkId = pc.getParkByRefPark(parkIdentification).getIdPoint();
        User user = uc.getUserByUsername(string);
        int userId = user.getIdUser();
        numberOfFreeSlots = pc.checkParkFreeSpots(parkId, userId);

        return numberOfFreeSlots;
    }

    @Override
    public int linearDistanceTo(double d, double d1, double d2, double d3) {
        return (int) Math.round(new PhysicsAlgorithms().calculateDistanceWithElevation(d, d2, d1, d3, 0, 0)) * 1000;
    }

    /**
     * gets the shortest path distance from one location to another.
     *
     * @param d origin latitude
     * @param d1 origin longitude
     * @param d2 destiny latitude
     * @param d3 destiny longitude
     * @return the distance in meters from one location to another
     */
    @Override
    public int pathDistanceTo(double d, double d1, double d2, double d3) {
        RentalController rc = new RentalController(new RentalDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());

        PointInterest origin = pc.getPointByCoordinates(d, d1);
        PointInterest destiny = pc.getPointByCoordinates(d2, d3);

        List<Pair<Double, List<PointInterest>>> shortestPaths = rc.shortestRoute(origin, destiny, 0);

        return shortestPaths.get(0).getKey().intValue();  //Key is the distance, values are the points the route passes through
    }

    /**
     * Unlocks a specific bicycle.
     *
     * @param string username that requested the unlock
     * @param string1 bicycleDescription to unlock
     * @return the time in milliseconds at which it was unlocked.
     */
    @Override
    public long unlockBicycle(String string, String string1) {
        RentalController rc = new RentalController(new RentalDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        UserController uc = new UserController(new UserDataHandler());
        VehicleController vc = new VehicleController(new VehicleDataHandler());

        String username = string1;
        String bicycleDescription = string;

        User user = uc.getUserByUsername(username);
        Vehicle vehicle = vc.getBicycleByDescription(bicycleDescription);
        Park park = pc.getParkByVehicleID(vehicle.getId());

        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            Rental rental = rc.makeRental(user, vehicle, park);
            DataHandler.getInstance().getConnection().commit();
            return Long.parseLong(rental.getBeginDateHour());
        } catch (SQLException ex) {
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException e) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return 0;
    }

    /**
     * Unlocks a specific eScooter
     *
     * @param string username that requested the unlock
     * @param string1 scooterDescription to unlock
     * @return the time in milliseconds at which it was unlocked.
     */
    @Override
    public long unlockEscooter(String string, String string1) {
        RentalController rc = new RentalController(new RentalDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        UserController uc = new UserController(new UserDataHandler());
        VehicleController vc = new VehicleController(new VehicleDataHandler());

        String username = string;
        String scooterDescription = string1;

        User user = uc.getUserByUsername(username);
        Vehicle scooter = vc.getScooterByDescription(scooterDescription);
        Park park = pc.getParkByVehicleID(scooter.getId());

        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            Rental rental = rc.makeRental(user, scooter, park);
            DataHandler.getInstance().getConnection().commit();
            return Long.parseLong(rental.getBeginDateHour());
        } catch (SQLException ex) {
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException e) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return 0;
    }

    @Override
    public long lockBicycle(String string, double d, double d1, String string1) {
        ParkController pc = new ParkController(new ParkDataHandler());
        UserController uc = new UserController(new UserDataHandler());
        Park parkDestiny = pc.getParkByCoordinates(d, d1);
        User user = uc.getUserByUsername(string1);

        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            pc.parkVehicle(parkDestiny.getIdPoint(), user.getIdUser());
            DataHandler.getInstance().getConnection().commit();
            return System.currentTimeMillis();
        } catch (SQLException ex) {
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException e) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return 0;
    }

    @Override
    public long lockBicycle(String string, String string1, String string2) {
        ParkController pc = new ParkController(new ParkDataHandler());
        UserController uc = new UserController(new UserDataHandler());
        Park parkDestiny = pc.getParkByRefPark(string1);
        User user = uc.getUserByUsername(string2);

        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            pc.parkVehicle(parkDestiny.getIdPoint(), user.getIdUser());
            DataHandler.getInstance().getConnection().commit();
            return System.currentTimeMillis();
        } catch (SQLException ex) {
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException e) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return 0;
    }

    @Override
    public long lockEscooter(String string, double d, double d1, String string1) {
        return lockBicycle(string, d, d1, string1);
    }

    @Override
    public long lockEscooter(String string, String string1, String string2) {
        return lockBicycle(string, string1, string2);
    }

    /**
     * Register a user on the system.
     *
     * @param string username
     * @param string1 email
     * @param string2 pwd
     * @param string3 creditCard
     * @param i height
     * @param i1 weight
     * @param bd cyclingAverageSpeed
     * @param string4 gender
     * @return 1 if a user is successfully registered, 0 if not.
     */
    @Override
    public int registerUser(String string, String string1, String string2, String string3, int i, int i1, BigDecimal bd, String string4) {
        UserController uc = new UserController(new UserDataHandler());
        String username = string;
        String email = string1;
        String pwd = string2;
        String creditCard = string3;
        int height = i;
        int weight = i1;
        float cyclingAverageSpeed = bd.floatValue();
        String gender = string4;

        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            uc.addUser(email, username, creditCard, cyclingAverageSpeed, height, weight, gender, pwd);
            DataHandler.getInstance().getConnection().commit();
            return 1;
        } catch (SQLException ex) {
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException e) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return 0;
    }

    /**
     * Suggest escooters with enough energy + 10% to go from one Park to
     * another.
     *
     * @param string parkRef where to unlock the scooter
     * @param string1 username
     * @param d destinationParkLatitudeInDegrees
     * @param d1 destinationParkLongitudeInDegrees
     * @param string2 outputFileName - Write the escooters information to a
     * file, according to file output/escooters.csv
     * @return the number of suggested vehicles
     */
    @Override
    public int suggestEscootersToGoFromOneParkToAnother(String string, String string1, double d, double d1, String string2) {
        RentalController rc = new RentalController(new RentalDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        UserController uc = new UserController(new UserDataHandler());
        int suggestedScooters = 0;
        Park origin = pc.getParkByRefPark(string);
        Park destination = pc.getParkByCoordinates(d, d1);
        if (origin == null || destination == null) {
            return suggestedScooters;
        }
        User u = uc.getUserByUsername(string1);
        if (u == null) {
            return suggestedScooters;
        }
        List<Scooter> scootersWith10PercentExtra = rc.getScootersWith10PercentageExtra(origin, destination, u);
        if (scootersWith10PercentExtra.isEmpty()) {
            return suggestedScooters;
        }
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(string2));
            writer.write("escooter description;type;actual battery capacity");

            for (Scooter s : scootersWith10PercentExtra) {
                writer.newLine();

                writer.write(s.getDescription() + ";" + s.getType() + ";" + s.getActualBatteryCapacity());
                suggestedScooters++;
            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return suggestedScooters;
    }

    /**
     * Calculate the most energy efficient route from one park to another.
     * Basic: Does not consider wind. Intermediate: Considers wind. Advanced:
     * Considers the different mechanical and aerodynamic coefficients.
     *
     * @param string originParkIdentification,
     * @param string1 destinationParkIdentification
     * @param string2 typeOfVehicle - The type of vehicle required e.g.
     * "bicycle" or "escooter"
     * @param string3 vehicleSpecs - The specs for the vehicle e.g. "16", "19",
     * "27" or any other number for bicyles and "city" or "off-road" for any
     * escooter
     * @param string4 username
     * @param string5 outputFileName - Write to the file the Route between two
     * parks according to file output/paths.csv. More than one path may exist.
     * If so, sort routes by the ascending number of points between the parks
     * and by ascending order of elevation difference.
     * @return
     */
    @Override
    public long mostEnergyEfficientRouteBetweenTwoParks(String string, String string1, String string2, String string3, String string4, String string5) {
        RentalController rc = new RentalController(new RentalDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        UserController uc = new UserController(new UserDataHandler());

        int count = 1;
        List<Pair<Double, List<PointInterest>>> listOfRoutes = new ArrayList<>();
        Bicycle bicycle = null;
        Scooter scooterOffRoad = null;
        Scooter scooterCity = null;
        Park origin = pc.getParkByRefPark(string);
        Park destination = pc.getParkByRefPark(string1);

        BufferedWriter writer = null;

        if (origin == null || destination == null) {
            return 0;
        }

        PointInterest poiFrom = new PointInterest(origin.getIdPoint(), origin.getGeoLocation(), origin.getDescription());
        PointInterest poiTo = new PointInterest(destination.getIdPoint(), destination.getGeoLocation(), destination.getDescription());
        User u = uc.getUserByUsername(string4);

        if (u == null) {
            return 0;
        }
        int idUser = u.getIdUser();

        if (string2.equalsIgnoreCase("bicycle")) {
            List<Bicycle> bicycles = vc.getAllBicyclesOfPark(poiFrom.getIdPoint());
            for (Bicycle b : bicycles) {
                if ((int) b.getWheelSize() == Integer.parseInt(string3)) {
                    bicycle = b;
                }
            }

            listOfRoutes = rc.mostEnergeticallyEfficientRoute(idUser, bicycle, poiFrom, poiTo);

        } else if (string2.equalsIgnoreCase("escooter")) {
            List<Scooter> scooters = vc.getAllScootersOfPark(poiFrom.getIdPoint());
            for (Scooter s : scooters) {
                if (string3.equalsIgnoreCase("off-road") && s.getType() == 1) {
                    scooterOffRoad = s;
                    listOfRoutes = rc.mostEnergeticallyEfficientRoute(idUser, scooterOffRoad, poiFrom, poiTo);
                } else if (string3.equalsIgnoreCase("city") && s.getType() == 2) {
                    scooterCity = s;
                    listOfRoutes = rc.mostEnergeticallyEfficientRoute(idUser, scooterCity, poiFrom, poiTo);
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
        double distance = 0;
        try {

            writer = new BufferedWriter(new FileWriter(string5));
            for (Pair<Double, List<PointInterest>> pair : listOfRoutes) {

                for (int i = 0; i < pair.getValue().size() - 1; i++) {
                    distance += PhysicsAlgorithms.calculateDistanceWithElevation(pair.getValue().get(i).getGeoLocation().getLatitude(),
                            pair.getValue().get(i + 1).getGeoLocation().getLatitude(), pair.getValue().get(i).getGeoLocation().getLongitude(),
                            pair.getValue().get(i + 1).getGeoLocation().getLongitude(), pair.getValue().get(i).getGeoLocation().getElevation(),
                            pair.getValue().get(i + 1).getGeoLocation().getElevation());
                }
                double elevationDif = pair.getValue().get(0).getGeoLocation().getElevation() - pair.getValue().get(pair.getValue().size() - 1).getGeoLocation().getElevation();

                writer.newLine();
                writer.write("Path " + String.format("%03d", count));
                writer.newLine();
                writer.write("total_distance: " + distance);
                writer.newLine();
                writer.write("total_energy: " + pair.getKey());
                writer.newLine();
                writer.write("elevation: " + elevationDif);

                for (PointInterest poi : pair.getValue()) {
                    writer.newLine();
                    writer.write(poi.getGeoLocation().getLatitude() + ";" + poi.getGeoLocation().getLongitude());
                    System.out.println(poi.getGeoLocation().getLongitude());
                }
                count++;
            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (long) distance;
    }

    @Override
    public int getNumberOfEscootersAtPark(double d, double d1, String string) {
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        int idPark = pc.getParkByCoordinates(d, d1).getIdPoint();
        List<Scooter> scootersOfPark = vc.getAllScootersOfPark(idPark);

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(string));
            writer.write("escooter description;type;actual battery capacity");

            for (Scooter s : scootersOfPark) {
                writer.newLine();
                String scooterType;
                if (s.getType() == Scooter.SCOOTER_CITY_ID) {
                    scooterType = "city";
                } else {
                    scooterType = "off-road";
                }
                writer.write(s.getDescription() + ";" + scooterType + ";" + s.getActualBatteryCapacity());

            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return scootersOfPark.size();
    }

    @Override
    public int getNumberOfEScootersAtPark(String string, String string1) {
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        int idPark = pc.getParkByRefPark(string).getIdPoint();
        List<Scooter> scootersOfPark = vc.getAllScootersOfPark(idPark);

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(string1));
            writer.write("escooter description;type;actual battery capacity");

            for (Scooter s : scootersOfPark) {
                writer.newLine();
                String scooterType;
                if (s.getType() == Scooter.SCOOTER_CITY_ID) {
                    scooterType = "city";
                } else {
                    scooterType = "off-road";
                }
                writer.write(s.getDescription() + ";" + scooterType + ";" + s.getActualBatteryCapacity());

            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return scootersOfPark.size();
    }

    @Override
    public long unlockAnyEscooterAtPark(String string, String string1, String string2) {
        ParkController pc = new ParkController(new ParkDataHandler());
        UserController uc = new UserController(new UserDataHandler());
        RentalController rc = new RentalController(new RentalDataHandler());
        Park park = pc.getParkByRefPark(string);
        List<Scooter> scootersOfPark = rc.getScootersWithMoreEnergy(park.getIdPoint());
        Rental rental = null;
        User user = uc.getUserByUsername(string1);

        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            rental = rc.makeRental(user, scootersOfPark.get(0), park);
            DataHandler.getInstance().getConnection().commit();

        } catch (SQLException ex) {
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException e) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
        }

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(string2));
            writer.write("escooter description;type;actual battery capacity");

            
                writer.newLine();
                String scooterType;
                if (scootersOfPark.get(0).getType() == Scooter.SCOOTER_CITY_ID) {
                    scooterType = "city";
                } else {
                    scooterType = "off-road";
                }
                writer.write(scootersOfPark.get(0).getDescription() + ";" + scooterType + ";" + scootersOfPark.get(0).getActualBatteryCapacity());

          
            if (rental != null) {
                return Long.parseLong(rental.getBeginDateHour());
            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    @Override
    public long unlockAnyEscooterAtParkForDestination(String string, String string1, double d, double d1, String string2) {
        ParkController pc = new ParkController(new ParkDataHandler());
        UserController uc = new UserController(new UserDataHandler());
        RentalController rc = new RentalController(new RentalDataHandler());
        Park park1 = pc.getParkByRefPark(string);
        Park park2 = pc.getParkByCoordinates(d, d1);
        User user = uc.getUserByUsername(string1);
        List<Scooter> scootersEnoughEnergy = rc.getScootersWith10PercentageExtra(park1, park2, user);
        Rental rental = null;
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            rental = rc.makeRental(user, scootersEnoughEnergy.get(0), park1);
            DataHandler.getInstance().getConnection().commit();

        } catch (SQLException ex) {
            try {
                DataHandler.getInstance().getConnection().rollback();
            } catch (SQLException e) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
            }
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
        }

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(string2));
            writer.write("escooter description;type;actual battery capacity");

            
                writer.newLine();
                String scooterType;
                if (scootersEnoughEnergy.get(0).getType() == Scooter.SCOOTER_CITY_ID) {
                    scooterType = "city";
                } else {
                    scooterType = "off-road";
                }
                writer.write(scootersEnoughEnergy.get(0).getDescription() + ";" + scooterType + ";" + scootersEnoughEnergy.get(0).getActualBatteryCapacity());

            
            if (rental != null) {
                return Long.parseLong(rental.getBeginDateHour());
            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    /**
     * Return the current debt for the user.
     *
     * @param string The username.
     * @param string1 The path for the file to output the debt, according to
     * file output/balance.csv. Sort the information by unlock time in ascending
     * order (oldest to newest).
     * @return The User's current debt in euros, rounded to two decimal places.
     */
    @Override
    public double getUserCurrentDebt(String string, String string1) {
        UserController uc = new UserController(new UserDataHandler());
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        RentalController rc = new RentalController(new RentalDataHandler());

        User u = uc.getUserByUsername(string);
        int currentDebt = 0;
        if (u == null) {
            return currentDebt--; //-1 return because it's not correct to return 0 debt when the user doesn't even exists to begin with
        }
        TreeMap<Long, InvoiceLine> rentals = uc.getUnpaidRentals(u.getIdUser());
        if (rentals.isEmpty()) {
            return currentDebt;
        }
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(string1));
            writer.write("vehicleDescription;vehicleUnlockTime;vehicleLockTime;originParkLatitude;originParkLongitude;destinationParkLatitude;destinationParkLongitude;timeSpent;cost");

            Set<Long> keys = rentals.keySet();
            for (Long key : keys) {
                writer.newLine();
                Rental rental = rc.getRentalById(rentals.get(key).getIdRental());
                Vehicle vehicle = vc.getById(rentals.get(key).getIdVehicle());
                writer.write(vehicle.getDescription() + ";" //vehicle description
                        + rental.getBeginDateHour() + ";" //vehicle unlock Time
                        + rental.getEndDateHour() + ";" //vehicle lock time
                        + rental.getParkFrom().getGeoLocation().getLatitude() + ";" //originPark latitude
                        + rental.getParkFrom().getGeoLocation().getLongitude() + ";" //originPark latitude
                        + rental.getParkTo().getGeoLocation().getLatitude() + ";" //destinationPark latitude
                        + rental.getParkTo().getGeoLocation().getLongitude() + ";" //destinationPark longitude
                        + rental.getDuration() * 60 + ";" //duration (in seconds)
                        + rental.getCost());                                  //cost

            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return currentDebt;
    }

    /**
     * Return the current points for the user.
     *
     * @param string The user to get the points report from.
     * @param string1 The path for the file to output the points, according to
     * file output/points.csv. Sort the information by unlock time in ascending
     * order (oldest to newest).
     * @return The User's current points.
     */
    @Override
    public double getUserCurrentPoints(String string, String string1) {
        UserController uc = new UserController(new UserDataHandler());

        User u = uc.getUserByUsername(string);
        List<Rental> rentalList = uc.getUserHistorical(u.getIdUser());
        if (u == null || rentalList.isEmpty()) {
            return 0;
        }
        Collections.sort(rentalList);

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(string1));
            writer.write("vehicleDescription;vehicleUnlockTime;vehicleLockTime;originParkLatitude;originParkLongitude;originParkElevation;destinationParkLatitude;destinationParkLongitude;destinationParkElevation;elevationDifference;points");

            for (Rental rental : rentalList) {
                writer.newLine();

                writer.write(rental.getVehicle().getDescription() + ";"
                        + rental.getBeginDateHour() + ";"
                        + rental.getEndDateHour() + ";"
                        + rental.getParkFrom().getGeoLocation().getLatitude() + ";"
                        + rental.getParkFrom().getGeoLocation().getLongitude() + ";"
                        + rental.getParkFrom().getGeoLocation().getElevation() + ";"
                        + rental.getParkTo().getGeoLocation().getLatitude() + ";"
                        + rental.getParkTo().getGeoLocation().getLongitude() + ";"
                        + rental.getParkTo().getGeoLocation().getElevation() + ";"
                        + Math.abs(rental.getParkTo().getGeoLocation().getElevation() - rental.getParkFrom().getGeoLocation().getElevation()) + ";"
                        + rental.getEarnedPoints());
            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return u.getPoints();
    }

    /**
     * Calculate the amount of electrical energy required to travel from one
     * park to another
     *
     * @param d originLatitudeInDegrees
     * @param d1 originLongitudeInDegrees
     * @param d2 destinationLatitudeInDegrees
     * @param d3 destinationLongitudeInDegrees
     * @param string username
     * @return the electrical energy required in kWh, rounded to two decimal
     * places
     */
    @Override
    public double calculateElectricalEnergyToTravelFromOneLocationToAnother(double d, double d1, double d2, double d3, String string) {
        RentalController rc = new RentalController(new RentalDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        UserController uc = new UserController(new UserDataHandler());

        User user = uc.getUserByUsername(string);
        if (user == null) {
            return 0;
        }
        int idUser = user.getIdUser();
        Park origin = pc.getParkByCoordinates(d, d1);
        Park destination = pc.getParkByCoordinates(d2, d3);
        if (origin == null || destination == null) {
            return 0;
        }
        PointInterest originPOI = new PointInterest(origin.getId(), origin.getGeoLocation(), origin.getDescription());
        PointInterest destinationPOI = new PointInterest(destination.getId(), destination.getGeoLocation(), destination.getDescription());

        Scooter dummyVehicle = new Scooter(2, 100, 100, 250, 1, "dummyVehicle", 12, 0.50, 0.3);   //type(city),maxBatteryCapacity,actualBattteryCapacity,motor,state,description,weight,aerodynamicCoefficient,frontalArea

        List<Pair<Double, List<PointInterest>>> list = rc.mostEnergeticallyEfficientRoute(idUser, dummyVehicle, originPOI, destinationPOI);
        if (list.isEmpty()) {
            return 0;
        } else {
            return Math.abs(list.get(0).getKey());
        }
    }

    /**
     * Get for how long has a vehicle has been unlocked.
     *
     * @param string Vehicle description
     * @return The time in seconds since the vehicle was unlocked.
     */
    @Override
    public long forHowLongAVehicleIsUnlocked(String string) {
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        RentalController rc = new RentalController(new RentalDataHandler());

        Vehicle vehicle = vc.getVehicleByDescription(string);
        int timeUnlocked = 0;

        if (vehicle == null) {
            return timeUnlocked;
        }
        int idVehicle = vehicle.getId();        //ID of the vehicle with the 'description' provided
        List<Rental> unfinishedRentals = rc.getUnfinishedTrips();
        for (Rental r : unfinishedRentals) {
            if (r.getVehicle().getId() == idVehicle) {
                return ((System.currentTimeMillis() - Long.parseLong(r.getBeginDateHour())) / 1000);
            }
        }
        return timeUnlocked;
    }

    @Override
    public long shortestRouteBetweenTwoParks(double d, double d1, double d2, double d3, int i, String string) {
        ParkController pc = new ParkController(new ParkDataHandler());
        RentalController rc = new RentalController(new RentalDataHandler());
        Park park1 = pc.getParkByCoordinates(d, d1);
        Park park2 = pc.getParkByCoordinates(d2, d3);

        if (park1 == null || park2 == null) {
            return 0;
        }

        PointInterest poi1 = new PointInterest(park1.getIdPoint(), park1.getGeoLocation(), park1.getDescription());
        PointInterest poi2 = new PointInterest(park2.getIdPoint(), park2.getGeoLocation(), park2.getDescription());

        List<Pair<Double, List<PointInterest>>> shortestRoutes = rc.shortestRoute(poi1, poi2, i);
        if (shortestRoutes == null || shortestRoutes.isEmpty()) {
            return 0;
        }

        BufferedWriter writer = null;

        int num = 1;

        try {
            writer = new BufferedWriter(new FileWriter(string));

            for (Pair<Double, List<PointInterest>> pair : shortestRoutes) {
                List<Bicycle> listBicycles = new VehicleController(new VehicleDataHandler()).getAllBicyclesOfPark(park1.getId());
                List<Scooter> listScooters = new VehicleController(new VehicleDataHandler()).getAllScootersOfPark(park1.getId());
                double smallerEnergyScooter = Double.MAX_VALUE;
                for (Scooter scooter : listScooters) {
                    double energyScooter = 0;
                    for (int j = 0; j < pair.getValue().size() - 1; j++) {
                        Path path = new ParkController(new ParkDataHandler()).getPathByIdParks(pair.getValue().get(j).getIdPoint(), pair.getValue().get(j + 1).getIdPoint());
                        energyScooter += new PhysicsAlgorithms().calculateEnergySpentBetweenPoints(scooter.getWeight(), 72,
                                path.getKineticCoefficient(), scooter.getFrontalArea(), scooter.getAerodynamicCoefficient(),
                                path.getWindSpeed(), path.getWindDirection(), pair.getValue().get(j).getGeoLocation().getElevation(),
                                pair.getValue().get(j + 1).getGeoLocation().getElevation(), pair.getValue().get(j).getGeoLocation().getLatitude(),
                                pair.getValue().get(j + 1).getGeoLocation().getLatitude(), pair.getValue().get(j).getGeoLocation().getLongitude(),
                                pair.getValue().get(j + 1).getGeoLocation().getLongitude());
                    }
                    if (energyScooter < smallerEnergyScooter) {
                        smallerEnergyScooter = energyScooter;
                    }
                }

                double smallerEnergyBicycle = Double.MAX_VALUE;
                for (Bicycle bicycle : listBicycles) {
                    double energyBicycle = 0;
                    for (int j = 0; j < pair.getValue().size() - 1; j++) {
                        Path path = new ParkController(new ParkDataHandler()).getPathByIdParks(pair.getValue().get(j).getIdPoint(), pair.getValue().get(j + 1).getIdPoint());
                        energyBicycle += (new PhysicsAlgorithms().calculateCalories(bicycle.getWeight(), 72,
                                path.getKineticCoefficient(), bicycle.getFrontalArea(), 3, bicycle.getAerodynamicCoefficient(),
                                path.getWindSpeed(), path.getWindDirection(), pair.getValue().get(j).getGeoLocation().getElevation(),
                                pair.getValue().get(j + 1).getGeoLocation().getElevation(), pair.getValue().get(j).getGeoLocation().getLatitude(),
                                pair.getValue().get(j + 1).getGeoLocation().getLatitude(), pair.getValue().get(j).getGeoLocation().getLongitude(),
                                pair.getValue().get(j + 1).getGeoLocation().getLongitude())) * 4.18;
                    }
                    if (energyBicycle < smallerEnergyBicycle) {
                        smallerEnergyBicycle = energyBicycle;
                    }
                }

                double totalEnergy = 0;
                if (smallerEnergyBicycle < smallerEnergyScooter) {
                    totalEnergy = smallerEnergyBicycle;
                } else {
                    totalEnergy = smallerEnergyScooter;
                }

                double elevationDif = pair.getValue().get(0).getGeoLocation().getElevation() - pair.getValue().get(pair.getValue().size() - 1).getGeoLocation().getElevation();

                writer.write("Path " + String.format("%03d", num));
                writer.newLine();
                writer.write("Total Distance:" + pair.getKey());
                writer.newLine();
                writer.write("Total Energy:" + totalEnergy);
                writer.newLine();
                writer.write("Elevation:" + elevationDif);
                for (PointInterest poi : pair.getValue()) {
                    writer.newLine();
                    writer.write(poi.getGeoLocation().getLatitude() + ";" + poi.getGeoLocation().getLongitude());
                }
                num++;
            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return shortestRoutes.get(0).getKey().intValue();
    }

    @Override
    public long shortestRouteBetweenTwoParks(String string, String string1, int i, String string2) {
        ParkController pc = new ParkController(new ParkDataHandler());
        RentalController rc = new RentalController(new RentalDataHandler());
        Park park1 = pc.getParkByRefPark(string);
        Park park2 = pc.getParkByRefPark(string1);

        return this.shortestRouteBetweenTwoParks(park1.getGeoLocation().getLatitude(), park1.getGeoLocation().getLongitude(),
                park2.getGeoLocation().getLatitude(), park2.getGeoLocation().getLongitude(), i, string2);
    }

    /**
     * The user attributes are assumed because no user information is provided,
     * and it is required in order to accurately calculate the spent energy in a
     * route. And to have the required information for the output file.
     */
    @Override
    public long shortestRouteBetweenTwoParksForGivenPOIs(String string, String string1, String string2, String string3) {
        try {
            BufferedReader br = null;
            String line = "";
            ParkController pc = new ParkController(new ParkDataHandler());
            RentalController rc = new RentalController(new RentalDataHandler());
            ArrayList<PointInterest> pois = new ArrayList<>();
            try {
                br = new BufferedReader(new FileReader(string2));
                while ((line = br.readLine()) != null) {
                    if (!line.startsWith("#") && !line.startsWith("latitude")) {
                        String[] POIs = line.split(";");

                        double latitude = Double.parseDouble(POIs[0].trim());
                        double longitude = Double.parseDouble(POIs[1].trim());
                        PointInterest poi = pc.getPointByCoordinates(latitude, longitude);
                        if (poi == null) {
                            return 0;
                        }
                        pois.add(poi);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
            }
            Park parkFrom = pc.getParkByRefPark(string);
            Park parkTo = pc.getParkByRefPark(string1);
            if (parkFrom == null || parkTo == null) {
                return 0;
            }

            PointInterest poi1 = new PointInterest(parkFrom.getIdPoint(), parkFrom.getGeoLocation(), parkFrom.getDescription());
            PointInterest poi2 = new PointInterest(parkTo.getIdPoint(), parkTo.getGeoLocation(), parkTo.getDescription());
            List<Pair<Double, List<PointInterest>>> shortestRoute = rc.shortestRoutePassingThroughPoints(poi1, poi2, pois);
            if (!shortestRoute.isEmpty()) {
                int num = 1;
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(string3));

                    for (Pair<Double, List<PointInterest>> pair : shortestRoute) {
                        List<Bicycle> listBicycles = new VehicleController(new VehicleDataHandler()).getAllBicyclesOfPark(parkFrom.getId());
                        List<Scooter> listScooters = new VehicleController(new VehicleDataHandler()).getAllScootersOfPark(parkTo.getId());
                        double smallerEnergyScooter = Double.MAX_VALUE;
                        for (Scooter scooter : listScooters) {
                            double energyScooter = 0;
                            for (int j = 0; j < pair.getValue().size() - 1; j++) {
                                Path path = new ParkController(new ParkDataHandler()).getPathByIdParks(pair.getValue().get(j).getIdPoint(), pair.getValue().get(j + 1).getIdPoint());
                                energyScooter += new PhysicsAlgorithms().calculateEnergySpentBetweenPoints(scooter.getWeight(), 72,
                                        path.getKineticCoefficient(), scooter.getFrontalArea(), scooter.getAerodynamicCoefficient(),
                                        path.getWindSpeed(), path.getWindDirection(), pair.getValue().get(j).getGeoLocation().getElevation(),
                                        pair.getValue().get(j + 1).getGeoLocation().getElevation(), pair.getValue().get(j).getGeoLocation().getLatitude(),
                                        pair.getValue().get(j + 1).getGeoLocation().getLatitude(), pair.getValue().get(j).getGeoLocation().getLongitude(),
                                        pair.getValue().get(j + 1).getGeoLocation().getLongitude());
                            }
                            if (energyScooter < smallerEnergyScooter) {
                                smallerEnergyScooter = energyScooter;
                            }
                        }

                        double smallerEnergyBicycle = Double.MAX_VALUE;
                        for (Bicycle bicycle : listBicycles) {
                            double energyBicycle = 0;
                            for (int j = 0; j < pair.getValue().size() - 1; j++) {
                                Path path = new ParkController(new ParkDataHandler()).getPathByIdParks(pair.getValue().get(j).getIdPoint(), pair.getValue().get(j + 1).getIdPoint());
                                energyBicycle += (new PhysicsAlgorithms().calculateCalories(bicycle.getWeight(), 72,
                                        path.getKineticCoefficient(), bicycle.getFrontalArea(), 3, bicycle.getAerodynamicCoefficient(),
                                        path.getWindSpeed(), path.getWindDirection(), pair.getValue().get(j).getGeoLocation().getElevation(),
                                        pair.getValue().get(j + 1).getGeoLocation().getElevation(), pair.getValue().get(j).getGeoLocation().getLatitude(),
                                        pair.getValue().get(j + 1).getGeoLocation().getLatitude(), pair.getValue().get(j).getGeoLocation().getLongitude(),
                                        pair.getValue().get(j + 1).getGeoLocation().getLongitude())) * 4.18;
                            }
                            if (energyBicycle < smallerEnergyBicycle) {
                                smallerEnergyBicycle = energyBicycle;
                            }
                        }

                        double totalEnergy = 0;
                        if (smallerEnergyBicycle < smallerEnergyScooter) {
                            totalEnergy = smallerEnergyBicycle;
                        } else {
                            totalEnergy = smallerEnergyScooter;
                        }

                        double elevationDif = pair.getValue().get(0).getGeoLocation().getElevation() - pair.getValue().get(pair.getValue().size() - 1).getGeoLocation().getElevation();

                        writer.write("Path " + String.format("%03d", num));
                        writer.newLine();
                        writer.write("Total Distance:" + pair.getKey());
                        writer.newLine();
                        writer.write("Total Energy:" + totalEnergy);
                        writer.newLine();
                        writer.write("Elevation:" + elevationDif);
                        writer.newLine();
                        for (PointInterest poi : pair.getValue()) {
                            writer.newLine();
                            writer.write(poi.getGeoLocation().getLatitude() + ";" + poi.getGeoLocation().getLongitude());
                        }
                        num++;
                    }

                } catch (IOException e) {
                    Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
                } finally {
                    try {
                        writer.close();
                    } catch (IOException | NullPointerException ex) {
                        Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                return shortestRoute.get(0).getKey().longValue();
            }
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public long shortestRouteBetweenTwoParksForGivenPOIs(double d, double d1, double d2, double d3, String string, String string1) {
        ParkController pc = new ParkController(new ParkDataHandler());
        Park parkFrom = pc.getParkByCoordinates(d, d1);
        Park parkTo = pc.getParkByCoordinates(d2, d3);
        if (parkFrom == null || parkTo == null) {
            return 0;
        }
        return shortestRouteBetweenTwoParksForGivenPOIs(parkFrom.getRefPark(), parkTo.getRefPark(), string, string1);
    }

    @Override
    public long getParkChargingReport(String string, String string1) {
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        Park park = pc.getParkByRefPark(string);
        List<Scooter> scooters = vc.getAllScootersOfPark(park.getIdPoint());
        List<Pair<Integer, Scooter>> lowerBatery = new ArrayList<>();
        for (Scooter scooter : scooters) {
            if (scooter.getActualBatteryCapacity() < 100) {
                Pair<Scooter, Double> pair = pc.calculateTimeNeededToChargeTotalyByScooter(park.getIdPoint(), scooter.getDescription());
                lowerBatery.add(new Pair(pair.getValue().intValue(), pair.getKey()));
            }
        }

        lowerBatery.sort(new Comparator<Pair<Integer, Scooter>>() {
            @Override
            public int compare(Pair<Integer, Scooter> o1, Pair<Integer, Scooter> o2) {
                if (o1.getKey().compareTo(o2.getKey()) > 0) {
                    return -1;
                }
                if (o1.getKey().compareTo(o2.getKey()) < 0) {
                    return 1;
                }
                return o1.getValue().getDescription().compareTo(o1.getValue().getDescription());
            }
        });

        BufferedWriter writer = null;
        try {

            writer = new BufferedWriter(new FileWriter(string1));

            writer.write("escooter description;type;actual battery capacity;time to finish charge in seconds");

            for (Pair<Integer, Scooter> chargeInfo : lowerBatery) {
                writer.newLine();
                String scooterType;
                if (chargeInfo.getValue().getType() == Scooter.SCOOTER_CITY_ID) {
                    scooterType = "city";
                } else {
                    scooterType = "off-road";
                }
                writer.write(chargeInfo.getValue().getDescription() + ";" + scooterType
                        + ";" + chargeInfo.getValue().getActualBatteryCapacity() + ";"
                        + chargeInfo.getKey().doubleValue());
            }

        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!lowerBatery.isEmpty()) {
            return lowerBatery.size();
        }
        return 0;
    }

    @Override
    public int suggestRoutesBetweenTwoLocations(String string, String string1, String string2, String string3, String string4, int i, boolean bln, String string5, String string6, String string7) {
        ParkController pc = new ParkController(new ParkDataHandler());
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        UserController uc = new UserController(new UserDataHandler());
        RentalController rc = new RentalController(new RentalDataHandler());
        BufferedReader br = null;
        BufferedWriter writer = null;
        int count = 1;
        String line = "";
        ArrayList<PointInterest> pois = new ArrayList<>();
        List<Pair<Double, List<PointInterest>>> listOfRoutes = new ArrayList<>();
        Bicycle bicycle = null;
        Scooter scooter = null;
        Park parkFrom = pc.getParkByRefPark(string);
        Park parkTo = pc.getParkByRefPark(string1);
        PointInterest poiFrom = new PointInterest(parkFrom.getIdPoint(), parkFrom.getGeoLocation(), parkFrom.getDescription());
        PointInterest poiTo = new PointInterest(parkTo.getIdPoint(), parkTo.getGeoLocation(), parkTo.getDescription());
        User user = uc.getUserByUsername(string4);

        if (parkFrom == null || parkTo == null || user == null) {
            return 0;
        }

        try {
            br = new BufferedReader(new FileReader(string6));
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && !line.startsWith("latitude")) {
                    String[] POIs = line.split(";");

                    double latitude = Double.parseDouble(POIs[0].trim());
                    double longitude = Double.parseDouble(POIs[1].trim());
                    PointInterest poi = pc.getPointByCoordinates(latitude, longitude);
                    if (poi == null) {
                        return 0;
                    }
                    pois.add(poi);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, ex.getMessage());
        }

        if (string2.equalsIgnoreCase("bicycle")) {
            List<Bicycle> bicycles = vc.getAllBicyclesOfPark(poiFrom.getIdPoint());
            for (Bicycle b : bicycles) {
                if ((int) b.getWheelSize() == Integer.parseInt(string3)) {
                    bicycle = b;
                }
            }

            listOfRoutes = rc.mostEfficientRoutePassingThroughPoints(user.getIdUser(), bicycle, poiFrom, poiTo, pois, i, bln, string5);

        } else if (string2.equalsIgnoreCase("escooter")) {
            List<Scooter> scooters = vc.getAllScootersOfPark(poiFrom.getIdPoint());
            for (Scooter s : scooters) {
                if (string3.equalsIgnoreCase("off-road") && s.getType() == 1) {
                    scooter = s;
                    listOfRoutes = rc.mostEfficientRoutePassingThroughPoints(user.getIdUser(), scooter, poiFrom, poiTo, pois, i, bln, string5);
                } else if (string3.equalsIgnoreCase("city") && s.getType() == 2) {
                    scooter = s;
                    listOfRoutes = rc.mostEfficientRoutePassingThroughPoints(user.getIdUser(), scooter, poiFrom, poiTo, pois, i, bln, string5);
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
        try {
            writer = new BufferedWriter(new FileWriter(string7));
            for (Pair<Double, List<PointInterest>> pair : listOfRoutes) {
                double distance = 0;

                for (int x = 0; x < pair.getValue().size() - 1; x++) {
                    distance += PhysicsAlgorithms.calculateDistanceWithElevation(pair.getValue().get(x).getGeoLocation().getLatitude(),
                            pair.getValue().get(x + 1).getGeoLocation().getLatitude(), pair.getValue().get(x).getGeoLocation().getLongitude(),
                            pair.getValue().get(x + 1).getGeoLocation().getLongitude(), pair.getValue().get(x).getGeoLocation().getElevation(),
                            pair.getValue().get(x + 1).getGeoLocation().getElevation());
                }
                double elevationDif = pair.getValue().get(0).getGeoLocation().getElevation() - pair.getValue().get(pair.getValue().size() - 1).getGeoLocation().getElevation();

                writer.newLine();
                writer.write("Path " + String.format("%03d", count));
                writer.newLine();
                writer.write("total_distance: " + distance);
                writer.newLine();
                writer.write("total_energy: " + pair.getKey());
                writer.newLine();
                writer.write("elevation: " + elevationDif);

                for (PointInterest poi : pair.getValue()) {
                    writer.newLine();
                    writer.write(poi.getGeoLocation().getLatitude() + ";" + poi.getGeoLocation().getLongitude());
                }
                count++;
            }
        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return listOfRoutes.size();
    }

    @Override
    public double getInvoiceForMonth(int i, String string, String string1) {
        InvoiceController ic = new InvoiceController(new InvoiceDataHandler());
        UserController uc = new UserController(new UserDataHandler());
        RentalController rc = new RentalController(new RentalDataHandler());
        User u = uc.getUserByUsername(string);
        int previousPoints = u.getPoints();
        int earnedPoints = 0;
        double chargedValue = 0;
        Invoice invoice = null;
        try {
            DataHandler.getInstance().getConnection().setAutoCommit(false);
            invoice = ic.addInvoice(u.getIdUser(), i);
            if (invoice.getIdInvoice() != 0) {
                DataHandler.getInstance().getConnection().commit();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedWriter writer = null;

        Invoice invoice2 = ic.getById(invoice.getIdInvoice());
        List<InvoiceLine> invoiceLines = ic.getInvoiceLinesOfInvoice(invoice2.getIdInvoice());
        for (InvoiceLine invoiceLine : invoiceLines) {
            earnedPoints = earnedPoints + invoiceLine.getEarnedPoints();
        }
        int actualPoints = uc.getUserByUsername(string).getPoints();
        try {

            writer = new BufferedWriter(new FileWriter(string1));
            writer.write(u.getUsername());
            writer.newLine();
            writer.write("Previous points:" + previousPoints);
            writer.newLine();
            writer.write("Earned points:" + earnedPoints);
            writer.newLine();
            writer.write("Discounted points:" + (previousPoints - actualPoints));
            writer.newLine();
            writer.write("Actual points:" + actualPoints);
            writer.newLine();
            writer.write("Charged Value:" + invoice2.getTotalPrice());
            writer.newLine();
            writer.write("vehicle description;vehicle unlock time;vehicle lock time;"
                    + "origin park latitude;origin park longitude;destination park longitude;"
                    + "destination park latitude;total time;charged value");

            for (InvoiceLine invoiceLine : invoiceLines) {
                writer.newLine();
                Rental rental = rc.getRentalById(invoiceLine.getIdRental());
                writer.write(rental.getVehicle().getDescription() + ";" + rental.getBeginDateHour() + ";"
                        + rental.getEndDateHour() + ";" + rental.getParkFrom().getGeoLocation().getLatitude() + ";"
                        + rental.getParkFrom().getGeoLocation().getLongitude() + ";"
                        + rental.getParkTo().getGeoLocation().getLatitude() + ";"
                        + rental.getParkTo().getGeoLocation().getLongitude() + ";"
                        + rental.getDuration() * 60 + ";" + rental.getCost());
            }

        } catch (IOException e) {
            Logger.getLogger(Facade.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return chargedValue;
    }
}
