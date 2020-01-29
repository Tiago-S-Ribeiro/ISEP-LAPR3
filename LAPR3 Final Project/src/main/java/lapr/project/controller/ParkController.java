/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.sql.SQLException;
import java.util.*;
import javafx.util.Pair;
import lapr.project.data.EmailHandler;
import lapr.project.data.ParkDataHandler;
import lapr.project.data.UserDataHandler;
import lapr.project.model.Capacity;
import lapr.project.model.GeographicalLocation;
import lapr.project.model.Park;
import lapr.project.model.Path;
import lapr.project.model.PointInterest;
import lapr.project.model.Scooter;
import lapr.project.model.User;
import lapr.project.model.Vehicle;

/**
 *
 * @author Tiago Ribeiro
 */
public class ParkController {

    private final ParkDataHandler parkDataHandler;

    public ParkController(ParkDataHandler parkDataHandler) {
        this.parkDataHandler = parkDataHandler;
    }

    /**
     * Allows the Administrator to add a Park to the Database
     *
     * @param parkRef Park's reference
     * @param descripton Park's description
     * @param state Park's State (Wether it's available upon creation or not)
     * @param parkInputVoltage
     * @param parkInputCurrent
     * @param latitude
     * @param longitude
     * @param elevation
     * @param maxCapacityBike total spaces for bicycles
     * @param maxCapacityScooter total spaces for scooters
     * @return the newly created Park
     */
    public Park addPark(String parkRef, String descripton, int state, int parkInputVoltage,
            int parkInputCurrent, double latitude, double longitude, double elevation, int maxCapacityBike, int maxCapacityScooter) throws SQLException {
        GeographicalLocation gl = new GeographicalLocation(latitude, longitude, elevation);
        Park park = new Park(parkRef, descripton, state, parkInputVoltage, parkInputCurrent, gl);
        List<Capacity> capacities = new LinkedList<>();
        capacities.add(new Capacity(Vehicle.BICYCLE_TYPE_ID, maxCapacityBike));
        capacities.add(new Capacity(Vehicle.SCOOTER_TYPE_ID, maxCapacityScooter));
        park.setCapacity(capacities);

        park.setIdPoint(parkDataHandler.addPark(park));

        return park;

    }

    /**
     * @param id park ID
     * @return the park with the ID passed as parameter
     */
    public Park getPark(int id) {
        Park park = null;

        park = parkDataHandler.getById(id);

        return park;
    }

    /**
     * Allows the administrator to remove a park (this is, alter it's
     * availability state from 1 to 0)
     *
     * @param idPark park to be 'removed'
     * @return true if operation was successful, false if not
     */
    public boolean removePark(int idPark) throws SQLException {

        boolean removed = false;

        removed = parkDataHandler.removePark(idPark);

        return removed;
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @param radius
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public List<Pair<Double, Park>> getNearestParks(double latitude, double longitude, double radius) throws ClassNotFoundException, SQLException {
        List<Pair<Double, Park>> nearbyParks = new ArrayList<>();

        nearbyParks = parkDataHandler.getNearestParks(latitude, longitude, radius);

        return nearbyParks;
    }

    /**
     * @return a list of all parks in the system
     * @throws SQLException
     */
    public List<Park> getAllParks() throws SQLException {

        List<Park> list = null;

        list = parkDataHandler.getAllParks();
        return list;

    }

    /**
     * Allows to check a determined park's free spots for the currently loaned
     * vehicle by the user
     *
     * @param parkId park to be acessed in order to check it's availability
     * @param userId user currently loaning a vehicle
     * @return the number of available spots for the type of vehicle being used
     */
    public int checkParkFreeSpots(int parkId, int userId) {
        int spacesAvailable = 0;

        spacesAvailable = parkDataHandler.checkParkFreeSpots(parkId, userId);

        return spacesAvailable;
    }

    /**
     * Allows the user to park it's vehicle, sending a confirmation e-mail in
     * the case of sucess
     *
     * @param parkId ID of the park where the user will park the loaned vehicle
     * @param idUser user parking the vehicle
     * @return true if operation was successful, false if not
     * @throws SQLException
     */
    public boolean parkVehicle(int parkId, int idUser) throws SQLException {
        boolean actionSucess = false;
        UserController uc = new UserController(new UserDataHandler());
        actionSucess = parkDataHandler.parkVehicle(parkId, idUser);

        if (actionSucess == true) {
            try {
                User user = uc.getById(idUser);
                String userEmail = user.getEmail();
                EmailHandler.sendLockedVehicleEmail(userEmail);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return actionSucess;
    }

    /**
     * Add Point of Interest (Actor: Administrator)
     *
     * @param latitude POI latitude
     * @param longitude POI longitude
     * @param elevation POI elevation
     * @param description POI description
     * @return newly created POI
     */
    public PointInterest addPOI(double latitude, double longitude, double elevation, String description) throws SQLException {

        GeographicalLocation geo = new GeographicalLocation(latitude, longitude, elevation);
        PointInterest poi = new PointInterest(geo, description);

        poi.setIdPoint(parkDataHandler.addPOI(poi));

        return poi;
    }

    /**
     * Updates information on a determined Park
     *
     * @param idPark ID of the Park that will have its attributes altered
     * @param description new park description
     * @return true or false on wether the park was properly updated or not
     */
    public boolean updatePark(int idPark, String description) throws SQLException {

        boolean updated = false;

        updated = parkDataHandler.updatePark(idPark, description);

        return updated;
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public Park getParkByCoordinates(double latitude, double longitude) {

        return parkDataHandler.getParkOfCoordinates(latitude, longitude);

    }

    /**
     *
     * @param refPark
     * @return
     */
    public Park getParkByRefPark(String refPark) {

        return parkDataHandler.getParkOfReference(refPark);

    }

    /**
     *
     * @param idpark1
     * @return
     */
    public LinkedList<Pair<Scooter, Double>> calculateTimeNeededToChargeTotalyByPark(int idpark1) {

        return parkDataHandler.calculateTimeNeededToChargeTotalyByPark(idpark1);

    }

    /**
     *
     * @param idpark1
     * @param scooterDescription
     * @return
     */
    public Pair<Scooter, Double> calculateTimeNeededToChargeTotalyByScooter(int idpark1, String scooterDescription) {

        return parkDataHandler.calculateTimeNeededToChargeTotalyByScooter(idpark1, scooterDescription);

    }

    /**
     * Consults the number of free scooter spots in a given park
     *
     * @param parkId id of the park
     * @return nr of free scooter spots in a given park
     */
    public int checkParkFreeScooterSpots(int parkId) {
        int spacesAvailable = 0;

        spacesAvailable = parkDataHandler.checkParkFreeScooterSpots(parkId);

        return spacesAvailable;
    }

    /**
     * Consults the number of free bicycle spots in a given park
     *
     * @param parkId
     * @return nr of free bicycle spots in a given park
     */
    public int checkParkFreeBicycleSpots(int parkId) {
        int freeBikeSpots = 0;

        freeBikeSpots = parkDataHandler.checkParkFreeBicycleSpots(parkId);

        return freeBikeSpots;
    }

    /**
     *
     * @param idPointFrom
     * @param idPointTo
     * @param kinetic
     * @param windDirection
     * @param windSpeed
     * @return
     * @throws SQLException
     */
    public boolean addPath(int idPointFrom, int idPointTo, double kinetic, float windDirection, float windSpeed) throws SQLException {
        boolean addSuccess = false;

        PointInterest from = parkDataHandler.getPoiById(idPointFrom);
        PointInterest to = parkDataHandler.getPoiById(idPointTo);

        addSuccess = parkDataHandler.addPath(new Path(from, to, kinetic, windDirection, windSpeed));

        return addSuccess;
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public PointInterest getPointByCoordinates(double latitude, double longitude) {
        return parkDataHandler.getPointByCoordinates(latitude, longitude);
    }

    /**
     *
     * @param vehicleId
     * @return
     */
    public Park getParkByVehicleID(int vehicleId) {
        return parkDataHandler.getParkByVehicleID(vehicleId);
    }

    /**
     *
     * @param idPointFrom
     * @param idPointTo
     * @return
     */
    public Path getPathByIdParks(int idPointFrom, int idPointTo) {
        return parkDataHandler.getPathByIdParks(idPointFrom, idPointTo);
    }
}
