/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.data;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import lapr.project.model.GeographicalLocation;
import lapr.project.model.Park;
import lapr.project.model.Path;
import lapr.project.model.PointInterest;
import lapr.project.model.Scooter;
import lapr.project.utils.PhysicsAlgorithms;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Tiago Ribeiro
 */
public class ParkDataHandler {

    private static final Logger WARNING_LOGGER_PARK = Logger.getLogger(Park.class.getName());

    private final DataHandler dataHandler;

    public ParkDataHandler() {
        dataHandler = DataHandler.getInstance();
    }

    public static void warningError() {
        WARNING_LOGGER_PARK.log(Level.WARNING, "Error on reading Database.");
    }

    /**
     *
     * @param element
     * @return
     * @throws SQLException
     */
    public int addPark(Park element) throws SQLException {
        CallableStatement callStmt = null;
        int id = 0;

        callStmt = dataHandler.getConnection().prepareCall("{? = call funcAddPark(?,?,?,?,?,?,?,?,?,?) }");
        callStmt.registerOutParameter(1, OracleTypes.INTEGER);
        callStmt.setString(2, element.getRefPark());
        callStmt.setString(3, element.getDescription());
        callStmt.setDouble(4, element.getGeoLocation().getLatitude());
        callStmt.setDouble(5, element.getGeoLocation().getLongitude());
        callStmt.setInt(6, element.isState());
        callStmt.setDouble(7, element.getParkInputVoltage());
        callStmt.setDouble(8, element.getParkInputCurrent());
        callStmt.setDouble(9, element.getGeoLocation().getElevation());
        callStmt.setInt(10, element.getCapacity().get(0).getCapacityMax());
        callStmt.setInt(11, element.getCapacity().get(1).getCapacityMax());

        callStmt.execute();

        id = callStmt.getInt(1);
        try {
            callStmt.close();
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        }

        return id;
    }

    /**
     *
     * @param id
     * @return
     */
    public Park getById(int id) {
        String query = "SELECT * "
                + "FROM point_interest poi INNER JOIN park p ON poi.id_point =" + id
                + "WHERE poi.id_point = p.id_park";
        Statement stm = null;
        ResultSet rst = null;

        try {

            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {

                String description = rst.getString(2);
                double latitude = rst.getDouble(3);
                double longitude = rst.getDouble(4);
                double elevation = rst.getDouble(5);
                String refPark = rst.getString(7);
                int state = rst.getInt(8);
                int parkInputVoltage = rst.getInt(9);
                int parkInputCurrent = rst.getInt(10);

                return new Park(id, refPark, description, state, parkInputVoltage, parkInputCurrent, new GeographicalLocation(latitude, longitude, elevation));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return null;
    }

    /**
     * Method used to remove (change state of park to unavailable (0)) a park
     * from table PARK
     *
     * @param idPark id of the park that will be removed.
     * @return
     * @throws java.sql.SQLException
     */
    public boolean removePark(int idPark) throws SQLException {
        CallableStatement callV = null;
        boolean removed = false;

        callV = dataHandler.getConnection().prepareCall("{ call procRemovePark(?) }");

        callV.setInt(1, idPark);

        callV.execute();

        removed = true;

        try {
            callV.close();
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        }

        return removed;
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @param radius
     * @return
     */
    public List<Pair<Double, Park>> getNearestParks(double latitude, double longitude, double radius) {

        List<Park> parksList = this.getAllParks();

        List<Pair<Double, Park>> nearestParks = new ArrayList<>();
        for (Park p : parksList) {
            double distance = new PhysicsAlgorithms().linearDistanceTo(latitude, p.getGeoLocation().getLatitude(), longitude, p.getGeoLocation().getLongitude());
            if (radius <= 0) {
                radius = 1000;
            }
            if (distance <= radius) {
                Pair<Double, Park> pair = new Pair(distance, p);
                nearestParks.add(pair);
            }
        }
        Collections.sort(nearestParks, (final Pair<Double, Park> p1, final Pair<Double, Park> p2) -> {
            if (p1.getKey() > p2.getKey()) {
                return 1;
            } else if (p1.getKey() < p2.getKey()) {
                return -1;
            }
            return 0;
        });

        return nearestParks;
    }

    /**
     * @return list of all parks in the Database
     */
    public List<Park> getAllParks() {
        ArrayList<Park> parkList = new ArrayList<>();
        String query = "SELECT * FROM park p INNER JOIN point_interest poi ON p.id_park = poi.id_point "
                + "WHERE park_state = 1";

        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            while (rst.next()) {
                int parkId = rst.getInt(1);
                String refPark = rst.getString(2);
                String description = rst.getString(7);
                double latitude = rst.getDouble(8);
                double longitude = rst.getDouble(9);
                int state = rst.getInt(3);
                int parkInputVoltage = rst.getInt(4);
                int parkInputCurrent = rst.getInt(5);
                double elevation = rst.getDouble(10);
                if (state != 0) {
                    parkList.add(new Park(parkId, refPark, description, state, parkInputVoltage, parkInputCurrent, new GeographicalLocation(latitude, longitude, elevation)));
                }
            }
            return parkList;
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return parkList;
    }

    /**
     * @return list of all points of interest in the Database
     * @throws SQLException
     */
    public List<PointInterest> getAllPointsOfInterest() {
        ArrayList<PointInterest> poiList = new ArrayList<>();
        String query = "SELECT * FROM point_interest";

        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            while (rst.next()) {
                int poiId = rst.getInt(1);
                String description = rst.getString(2);
                double latitude = rst.getDouble(3);
                double longitude = rst.getDouble(4);
                double elevation = rst.getDouble(5);

                poiList.add(new PointInterest(poiId, new GeographicalLocation(latitude, longitude, elevation), description));
            }
            return poiList;
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return poiList;
    }

    /**
     * @return list of all paths in the Database
     * @throws SQLException
     */
    public List<Path> getAllPaths() {
        ArrayList<Path> pathsList = new ArrayList<>();
        String query = "SELECT * FROM available_paths";

        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            while (rst.next()) {
                int idPointFrom = rst.getInt(1);
                int idPointTo = rst.getInt(2);
                double kineticCoefficient = rst.getDouble(3);
                float windDirection = rst.getFloat(4);
                float windSpeed = rst.getFloat(5);

                PointInterest pointFrom = this.getPoiById(idPointFrom);
                PointInterest pointTo = this.getPoiById(idPointTo);

                pathsList.add(new Path(pointFrom, pointTo, kineticCoefficient, windDirection, windSpeed));
            }
            return pathsList;
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return pathsList;
    }

    /**
     *
     * @param parkId
     * @param userId
     * @return
     */
    public int checkParkFreeSpots(int parkId, int userId) {
        int spacesAvailable = 0;
        String query = "SELECT availability_vehicle\n"
                + "FROM park_capacity pc\n"
                + "WHERE pc.id_vehicle_type =(\n"
                + "    SELECT v.id_vehicle_type\n"
                + "    FROM vehicle v INNER JOIN rental r ON v.id_vehicle = r.id_vehicle\n"
                + "    WHERE r.id_user = " + userId + " AND r.rental_end_date_hour IS NULL) "
                + "  AND pc.id_park = " + parkId;
        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                spacesAvailable = rst.getInt(1);
            }

            return spacesAvailable;
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return spacesAvailable;
    }

    /**
     *
     * @param parkId
     * @param userId
     * @return
     * @throws SQLException
     */
    public boolean parkVehicle(int parkId, int userId) throws SQLException {
        CallableStatement callV = null;
        boolean actionSucessful = false;

        callV = dataHandler.getConnection().prepareCall("{ call procParkVehicle(?, ?) }");
        callV.setInt(1, parkId);
        callV.setInt(2, userId);
        callV.execute();
        actionSucessful = true;

        try {

            callV.close();

        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());

        }

        return actionSucessful;
    }

    /**
     * Addition of the POI to Database
     *
     * @param poi Point of Interest to be added
     * @return POI unique ID on database
     * @throws SQLException
     */
    public int addPOI(PointInterest poi) throws SQLException {
        CallableStatement callStmt = null;

        int id = 0;
        callStmt = dataHandler.getConnection().prepareCall("{? = call funcAddPoi(?,?,?,?) }");
        callStmt.registerOutParameter(1, OracleTypes.INTEGER);
        callStmt.setDouble(2, poi.getGeoLocation().getLatitude());
        callStmt.setDouble(3, poi.getGeoLocation().getLongitude());
        callStmt.setDouble(4, poi.getGeoLocation().getElevation());
        callStmt.setString(5, poi.getDescription());
        callStmt.execute();

        id = callStmt.getInt(1);

        try {

            callStmt.close();

        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());

        }
        return id;
    }

    /**
     *
     * @param idPoi
     * @return
     */
    public PointInterest getPoiById(int idPoi) {
        String query = "SELECT * FROM point_interest "
                + "WHERE id_point = " + idPoi;
        Statement stm = null;
        ResultSet rst = null;

        try {

            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {

                String description = rst.getString(2);
                double latitude = rst.getDouble(3);
                double longitude = rst.getDouble(4);
                double elevation = rst.getDouble(5);

                return new PointInterest(idPoi, new GeographicalLocation(latitude, longitude, elevation), description);
            }
        } catch (SQLException ex) {

            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param idPointFrom
     * @param idPointTo
     * @return
     */
    public Path getPathByIdParks(int idPointFrom, int idPointTo) {
        String query = "SELECT * FROM path_places "
                + "WHERE id_point_from = " + idPointFrom + " and id_point_to = " + idPointTo;
        Statement stm = null;
        ResultSet rst = null;

        try {

            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                PointInterest pointFrom = this.getPoiById(idPointFrom);
                PointInterest pointTo = this.getPoiById(idPointTo);
                double kineticCoefficient = rst.getDouble(3);
                float windDirection = rst.getFloat(4);
                float windSpeed = rst.getFloat(5);

                return new Path(pointFrom, pointTo, kineticCoefficient, windDirection, windSpeed);
            }
        } catch (SQLException ex) {

            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return null;
    }

    /**
     * Updates information on a determined Park
     *
     * @param idPark ID of the Park that will have its attributes altered
     * @param description new park description
     * @return true or false on wether the park was properly updated or not
     * @throws SQLException
     */
    public boolean updatePark(int idPark, String description) throws SQLException {
        CallableStatement callV = null;
        boolean removed = false;

        callV = dataHandler.getConnection().prepareCall("{ call procUpdatePark(?,?) }");

        callV.setInt(1, idPark);
        callV.setString(2, description);

        callV.execute();

        removed = true;

        try {
            callV.close();
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        }

        return removed;
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public Park getParkOfCoordinates(double latitude, double longitude) {
        String query = "SELECT  p.*, poi.poi_description, poi.latitude, poi.longitude, poi.elevation"
                + " FROM point_interest poi INNER JOIN park p "
                + "ON poi.id_point = p.id_park "
                + "WHERE latitude = " + latitude + " AND longitude = " + longitude + " AND p.park_state = 1";
        Statement stm = null;
        ResultSet rst = null;
        Park park = null;
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);
            if (rst.next()) {
                int id = rst.getInt(1);
                String refPark = rst.getString(2);
                int state = rst.getInt(3);
                int parkInputVoltage = rst.getInt(4);
                int parkInputCurrent = rst.getInt(5);
                String description = rst.getString(6);
                double elevation = rst.getDouble(9);

                return new Park(id, refPark, description, state, parkInputVoltage, parkInputCurrent, new GeographicalLocation(latitude, longitude, elevation));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return park;
    }

    /**
     *
     * @param refPark
     * @return
     */
    public Park getParkOfReference(String refPark) {
        String query = "SELECT  p.*, poi.poi_description, poi.latitude, poi.longitude, poi.elevation"
                + " FROM point_interest poi INNER JOIN park p "
                + "ON poi.id_point = p.id_park "
                + "WHERE p.ref_park LIKE '" + refPark + "'";
        Statement stm = null;
        ResultSet rst = null;
        Park park = null;
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);
            if (rst.next()) {

                int id = rst.getInt(1);
                int state = rst.getInt(3);
                int parkInputVoltage = rst.getInt(4);
                int parkInputCurrent = rst.getInt(5);
                String description = rst.getString(6);
                double latitude = rst.getDouble(7);
                double longitude = rst.getDouble(8);
                double elevation = rst.getDouble(9);

                return new Park(id, refPark, description, state, parkInputVoltage, parkInputCurrent, new GeographicalLocation(latitude, longitude, elevation));
            }
        } catch (SQLException e) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return park;
    }

    /**
     *
     * @param idPark
     * @return
     */
    public LinkedList<Pair<Scooter, Double>> calculateTimeNeededToChargeTotalyByPark(int idPark) {
        VehicleDataHandler vehicleDataHandler = new VehicleDataHandler();
        List<Scooter> scootersOfPark = vehicleDataHandler.getScootersOfPark(idPark);
        Park park1 = getById(idPark);
        LinkedList<Pair<Scooter, Double>> chargingStatus = new LinkedList<>();
        int numberOfPoint = new ParkDataHandler().getTotalNumberOfScooterSpots(park1.getId()) - new ParkDataHandler().checkParkFreeScooterSpots(park1.getId());
        for (Scooter scooter : scootersOfPark) {
            double calculateTimeNeededToChargeTotaly = new PhysicsAlgorithms().calculateTimeNeededToChargeTotaly(park1.getParkInputVoltage(), park1.getParkInputCurrent(), scooter.getActualBatteryCapacity(), scooter.getMaxBatteryCapacity(), numberOfPoint);
            double maxTime = new PhysicsAlgorithms().calculateTimeNeededToChargeTotaly(park1.getParkInputVoltage(), park1.getParkInputCurrent(), 0, scooter.getMaxBatteryCapacity(), numberOfPoint);
            double parkPower = park1.getParkInputCurrent() * park1.getParkInputVoltage(); //w
            double pointPower = (parkPower / numberOfPoint);
            int scooterActualBatery = scooter.getActualBatteryCapacity() * scooter.getMaxBatteryCapacity() / 100;
            int newScooterActualBatery = ((int) ((maxTime - calculateTimeNeededToChargeTotaly) * pointPower) + scooterActualBatery) / ((int) scooter.getMaxBatteryCapacity()) * 100;
            scooter.setActualBatteryCapacity(newScooterActualBatery);
            chargingStatus.add(new Pair(scooter, calculateTimeNeededToChargeTotaly));
        }
        return chargingStatus;
    }

    /**
     *
     * @param idPark
     * @param scooterDescription
     * @return
     */
    public Pair<Scooter, Double> calculateTimeNeededToChargeTotalyByScooter(int idPark, String scooterDescription) {
        VehicleDataHandler vehicleDataHandler = new VehicleDataHandler();
        Park park1 = getById(idPark);
        Scooter scooter = vehicleDataHandler.getScooterByDescription(scooterDescription);
        Pair<Scooter, Double> chargingStatus = null;
        int numberOfPoint = new ParkDataHandler().getTotalNumberOfScooterSpots(park1.getId()) - new ParkDataHandler().checkParkFreeScooterSpots(park1.getId());
        double calculateTimeNeededToChargeTotaly = new PhysicsAlgorithms().calculateTimeNeededToChargeTotaly(park1.getParkInputVoltage(), park1.getParkInputCurrent(), scooter.getActualBatteryCapacity(), scooter.getMaxBatteryCapacity(), numberOfPoint);
        double maxTime = new PhysicsAlgorithms().calculateTimeNeededToChargeTotaly(park1.getParkInputVoltage(), park1.getParkInputCurrent(), 0, scooter.getMaxBatteryCapacity(), numberOfPoint);
        double parkPower = park1.getParkInputCurrent() * park1.getParkInputVoltage(); //w
        double pointPower = (parkPower / numberOfPoint);
        int scooterActualBatery = scooter.getActualBatteryCapacity() * scooter.getMaxBatteryCapacity() / 100;
        int newScooterActualBatery = ((int) ((maxTime - calculateTimeNeededToChargeTotaly) * pointPower) + scooterActualBatery) / ((int) scooter.getMaxBatteryCapacity()) * 100;
        scooter.setActualBatteryCapacity(newScooterActualBatery);
        chargingStatus = new Pair(scooter, calculateTimeNeededToChargeTotaly);
        return chargingStatus;
    }

    /**
     * Consults the number of free scooter spots in a given park
     *
     * @param parkId id of the park
     * @return nr of free scooter spots in a given park
     * @throws SQLException
     */
    public int checkParkFreeScooterSpots(int parkId) {

        int freeScooterSpots = 0;

        String query = "SELECT availability_vehicle\n"
                + "FROM park_capacity\n"
                + "WHERE id_vehicle_type = 2 AND id_park = " + parkId;
        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                freeScooterSpots = rst.getInt(1);
            }

            return freeScooterSpots;
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return freeScooterSpots;
    }

    /**
     *
     * @param parkId
     * @return
     */
    public int getTotalNumberOfScooterSpots(int parkId) {

        int totalNumberScooterSpots = 0;

        String query = "SELECT capacity_vehicle\n"
                + "FROM park_capacity\n"
                + "WHERE id_vehicle_type = 2 AND id_park = " + parkId;
        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                totalNumberScooterSpots = rst.getInt(1);
            }

            return totalNumberScooterSpots;
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return totalNumberScooterSpots;
    }

    /**
     * Consults the number of free bicycle spots in a given park
     *
     * @param parkId
     * @return nr of free bicycle spots in a given park
     */
    public int checkParkFreeBicycleSpots(int parkId) {

        int freeBicycleSpots = 0;
        String query = "SELECT availability_vehicle\n"
                + "FROM park_capacity\n"
                + "WHERE id_vehicle_type = 1 AND id_park = " + parkId;
        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                freeBicycleSpots = rst.getInt(1);
            }

            return freeBicycleSpots;
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return freeBicycleSpots;
    }

    /**
     * Adds path into the system
     *
     * @param path object path
     * @return true if path is added, false if not
     * @throws SQLException
     */
    public boolean addPath(Path path) throws SQLException {
        CallableStatement callV = null;
        boolean added = false;

        callV = dataHandler.getConnection().prepareCall("{ call procAddPath(?,?,?,?,?) }");

        callV.setInt(1, path.getPointFrom().getIdPoint());
        callV.setInt(2, path.getPointTo().getIdPoint());
        callV.setDouble(3, path.getKineticCoefficient());
        callV.setDouble(4, path.getWindDirection());
        callV.setDouble(5, path.getWindSpeed());

        callV.execute();

        added = true;

        try {

            callV.close();

        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());

        }
        return added;
    }

    /**
     * Obtains a PointInterest by it's latitude and longitude
     *
     * @param pointLatitude latitude
     * @param pointLongitude longitude
     * @return the PointInterest object with the parameter coordinates
     */
    public PointInterest getPointByCoordinates(double pointLatitude, double pointLongitude) {
        String query = "SELECT * from point_interest "
                + "WHERE latitude = " + pointLatitude + " AND longitude = " + pointLongitude;

        Statement stm = null;
        ResultSet rst = null;

        try {

            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                int idPoint = rst.getInt(1);
                String description = rst.getString(2);
                double latitude = rst.getDouble(3);
                double longitude = rst.getDouble(4);
                double elevation = rst.getDouble(5);

                return new PointInterest(idPoint, new GeographicalLocation(latitude, longitude, elevation), description);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(InvoiceDataHandler.class.getName()).log(Level.WARNING, e.getMessage());
            }
        }
        return null;
    }

    /**
     * gets a Park that has the vehicle with the parameter ID associated
     *
     * @param vehicleId
     * @return the Park that has the vehicle of the parameter ID associated
     */
    public Park getParkByVehicleID(int vehicleId) {
        String query = "SELECT p.id_park, v.id_park, poi.id_point, p.ref_park, poi.poi_description, p.park_state, p.park_input_voltage, p.park_input_current, poi.latitude, poi.longitude, poi.elevation "
                + "FROM park p INNER JOIN vehicle v ON p.id_park = v.id_park INNER JOIN point_interest poi ON p.id_park = poi.id_point "
                + "WHERE v.vehicle_state = 1 AND v.id_vehicle = " + vehicleId ;
        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                int id = rst.getInt(1);
                String refPark = rst.getString(4);
                String description = rst.getString(5);
                int state = rst.getInt(6);
                int voltage = rst.getInt(7);
                int current = rst.getInt(8);
                double latitude = rst.getDouble(9);
                double longitude = rst.getDouble(10);
                double elevation = rst.getDouble(11);

                return new Park(id, refPark, description, state, voltage, current, new GeographicalLocation(latitude, longitude, elevation));
            }
        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
