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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import lapr.project.model.Bicycle;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;
import lapr.project.utils.PhysicsAlgorithms;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Ana Rita Veiga
 */
public class VehicleDataHandler {

    private static final Logger WARNING_LOGGER = Logger.getLogger(Vehicle.class.getName());

    public static void warningError() {
        WARNING_LOGGER.log(Level.WARNING, "Error on reading Database.");
    }

    private final DataHandler dataHandler;

    public VehicleDataHandler() {
        this.dataHandler = DataHandler.getInstance();
    }

    public int addBicycle(Bicycle bicycle, int idPark) throws SQLException {
        CallableStatement callStmt = null;

        int id = 0;
        callStmt = dataHandler.getConnection().prepareCall("{ ? = call funcAddBicycle(?,?,?,?,?,?,?,?) }");
        callStmt.registerOutParameter(1, OracleTypes.INTEGER);
        callStmt.setInt(2, idPark);
        callStmt.setInt(3, Vehicle.BICYCLE_TYPE_ID);
        callStmt.setString(4, bicycle.getDescription());
        callStmt.setInt(5, bicycle.getState());
        callStmt.setDouble(6, bicycle.getWeight());
        callStmt.setDouble(7, bicycle.getAerodynamicCoefficient());
        callStmt.setDouble(8, bicycle.getFrontalArea());
        callStmt.setInt(9, (int) bicycle.getWheelSize());
        callStmt.execute();

        id = callStmt.getInt(1);

        try {

            callStmt.close();

        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return id;
    }

    public int addScooter(Scooter scooter, int idPark) throws SQLException {
        CallableStatement callStmt = null;
        int id = 0;

        callStmt = dataHandler.getConnection().prepareCall("{ ? = call funcAddScooter(?,?,?,?,?,?,?,?,?,?,?) }");
        callStmt.registerOutParameter(1, OracleTypes.INTEGER);
        callStmt.setInt(2, idPark);
        callStmt.setInt(3, Vehicle.SCOOTER_TYPE_ID);
        callStmt.setString(4, scooter.getDescription());
        callStmt.setInt(5, scooter.getState());
        callStmt.setDouble(6, scooter.getWeight());
        callStmt.setDouble(7, scooter.getAerodynamicCoefficient());
        callStmt.setDouble(8, scooter.getFrontalArea());
        callStmt.setInt(9, scooter.getType());
        callStmt.setDouble(10, scooter.getMaxBatteryCapacity());
        callStmt.setDouble(11, scooter.getActualBatteryCapacity());
        callStmt.setInt(12, scooter.getMotor());

        callStmt.execute();

        id = callStmt.getInt(1);

        try {

            callStmt.close();

        } catch (SQLException ex) {

            Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return id;
    }

    public Vehicle getById(int id) {
        String query = "SELECT * FROM vehicle "
                + " WHERE id_vehicle = " + id;

        Statement stm = null;
        ResultSet rst = null;
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);
            if (rst.next()) {

                String description = rst.getString(4);
                int state = rst.getInt(5);
                double weight = rst.getDouble(6);
                double aerodynamicCoefficient = rst.getDouble(7);
                double frontalArea = rst.getDouble(8);

                return new Vehicle(id, state, description, weight, aerodynamicCoefficient, frontalArea);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return null;
    }

    public List<Bicycle> getAllBicycles() {
        ArrayList<Bicycle> bicycles = new ArrayList<>();
        String query = "SELECT * FROM vehicle v INNER JOIN bicycle b ON v.id_vehicle = b.id_bicycle"
                + " WHERE v.vehicle_state = 1";
        Statement stm = null;
        ResultSet rst = null;
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            while (rst.next()) {
                int idVehicle = rst.getInt(1);
                String description = rst.getString(4);
                int state = rst.getInt(5);
                float weight = rst.getFloat(6);
                float aerodynamicCoefficient = rst.getFloat(7);
                float frontalArea = rst.getFloat(8);
                int wheelSize = rst.getInt(10);
                Bicycle bicycle = new Bicycle(idVehicle, wheelSize, state, description, weight, aerodynamicCoefficient, frontalArea);
                bicycles.add(bicycle);
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return bicycles;
    }

    public List<Bicycle> getBicyclesOfPark(int idPark) {
        ArrayList<Bicycle> bicyclesOfPark = new ArrayList<>();
        String query = "SELECT * FROM vehicle v INNER JOIN bicycle b ON v.id_vehicle = b.id_bicycle"
                + " WHERE id_park = " + idPark + " AND v.vehicle_state = 1 ORDER BY v.vehicle_description";

        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            while (rst.next()) {
                int idVehicle = rst.getInt(1);
                String description = rst.getString(4);
                int state = rst.getInt(5);
                double weight = rst.getDouble(6);
                double aerodynamicCoefficient = rst.getDouble(7);
                double frontalArea = rst.getDouble(8);
                int wheelSize = rst.getInt(10);
                Bicycle bicycle = new Bicycle(idVehicle, wheelSize, state, description, weight, aerodynamicCoefficient, frontalArea);
                bicyclesOfPark.add(bicycle);

            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return bicyclesOfPark;
    }

    public List<Scooter> getScootersOfPark(int idPark) {

        ArrayList<Scooter> scootersOfPark = new ArrayList<>();
        String query = "SELECT * FROM vehicle v INNER JOIN scooter s ON v.id_vehicle = s.id_scooter"
                + " WHERE v.id_park = " + idPark + " AND v.vehicle_state = 1 ORDER BY v.vehicle_description";

        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            while (rst.next()) {
                int idVehicle = rst.getInt(1);
                String description = rst.getString(4);
                int state = rst.getInt(5);
                double weight = rst.getDouble(6);
                double aerodynamicCoefficient = rst.getDouble(7);
                double frontalArea = rst.getDouble(8);
                int type = rst.getInt(10);
                int maxBatteryCapacity = rst.getInt(11);
                int actualBattteryCapacity = rst.getInt(12);
                int motor = rst.getInt(13);

                Scooter scooter = new Scooter(type, maxBatteryCapacity, actualBattteryCapacity, motor, idVehicle, state, description, weight, aerodynamicCoefficient, frontalArea);
                scootersOfPark.add(scooter);
            }
        } catch (SQLException e) {
            Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return scootersOfPark;
    }

    /**
     * Method used to remove (change vehicle_state of vehicle to unavailable
     * (0)) a vehicle from table Vehicle
     *
     * @param id identifier of the vehicle that will be removed.
     */
    public boolean removeVehicle(int id) throws SQLException {
        boolean removed = false;
        CallableStatement callV = null;

        callV = dataHandler.getConnection().prepareCall("{ call removeVehicle(?) }");

        callV.setInt(1, id);

        callV.execute();

        removed = true;

        try {

            callV.close();

        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());

        }
        return removed;
    }

    public boolean updateBicycle(Bicycle bike) throws SQLException {
        CallableStatement callSmt = null;

        callSmt = dataHandler.getConnection().prepareCall("{ call updateBicycle(?,?,?,?) }");
        callSmt.setInt(1, bike.getId());
        callSmt.setInt(2, bike.getState());
        callSmt.setDouble(3, bike.getWeight());
        callSmt.setDouble(4, bike.getWheelSize());
        callSmt.execute();

        try {
            callSmt.close();
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        }

        return false;

    }

    public boolean updateScooter(Scooter scooter) throws SQLException {
        CallableStatement callSmt = null;

        callSmt = dataHandler.getConnection().prepareCall("{ call updateScooter(?,?,?,?,?,?) }");
       
 callSmt.setInt(1, scooter.getId());
        callSmt.setInt(2, scooter.getState());
        callSmt.setDouble(4, scooter.getWeight());
        callSmt.setInt(5, scooter.getActualBatteryCapacity());
        callSmt.setInt(5, scooter.getMaxBatteryCapacity());
        callSmt.execute();

        try {

            callSmt.close();

        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        }

        return false;

    }

    public Map<Pair<Integer, String>, Pair<Integer, String>> getUnlockedVehicles() {
        Map<Pair<Integer, String>, Pair<Integer, String>> unlockedVehicles = new HashMap<>();

        String query = "SELECT * FROM unlocked_vehicle";
        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);
            while (rst.next()) {
                int idVehicle = rst.getInt(1);
                String vehicleDescription = rst.getString(2);
                int idUser = rst.getInt(3);
                String email = rst.getString(4);
                unlockedVehicles.put(new Pair(idVehicle, vehicleDescription), new Pair(idUser, email));
            }

        } catch (SQLException e) {
            Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(VehicleDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return unlockedVehicles;
    }

    public List getScootersDontHavecapacityToPerformEstimatedTrip(int distance, int idPark, double userCyclingAverageSpeed) {
        ArrayList<Scooter> lstScooter = new ArrayList<>();

        List<Scooter> allScooter = new VehicleDataHandler().getScootersOfPark(idPark);

        for (Scooter scooter : allScooter) {
            double distanceScooterCanDo =  new PhysicsAlgorithms().calculateDistanceAScooterCanDo(scooter.getMotor(), scooter.getActualBatteryCapacity(), scooter.getMaxBatteryCapacity());
            if (distanceScooterCanDo < distance) {
                lstScooter.add(scooter);
            }
        }
        return lstScooter;
    }
    
    /**
     * 
     * @param description of the bicycle
     * @return bicycle by it's description
     */
    public Bicycle getBicycleByDescription(String description){
        String query = "SELECT * "
                     + "FROM vehicle v INNER JOIN bicycle b ON v.id_vehicle = b.id_bicycle "
                     + "WHERE vehicle_state = 1 AND vehicle_description LIKE '" +description+"'";
        Statement stm = null;
        ResultSet rst = null;
        
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);
            if (rst.next()) {

                int id = rst.getInt(1);
                int state = rst.getInt(5);
                double weight = rst.getDouble(6);
                double aerodynamicCoefficient = rst.getDouble(7);
                double frontalArea = rst.getDouble(8);
                double wheelSize = rst.getDouble(10);

                return new Bicycle(id, wheelSize, state, description, weight, aerodynamicCoefficient, frontalArea);
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
    
    /**
     * 
     * @param description of the scooter
     * @return scooter by it's description
     */
    public Scooter getScooterByDescription(String description){
        String query = "SELECT * "
                     + "FROM vehicle v INNER JOIN scooter s ON v.id_vehicle = s.id_scooter "
                     + "WHERE vehicle_state = 1 AND vehicle_description LIKE '" +description+"'";
        Statement stm = null;
        ResultSet rst = null;
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);
            if (rst.next()) {
                
                int type = rst.getInt(10);
                int maxBattery = rst.getInt(11);
                int actualBattery = rst.getInt(12);
                int motor = rst.getInt(13);
                int id = rst.getInt(1);
                int state = rst.getInt(5);
                double weight = rst.getDouble(6);
                double aerodynamic = rst.getDouble(7);
                double frontalArea = rst.getDouble(8);
                
                return new Scooter(type, maxBattery, actualBattery, motor, id, state, description, weight, aerodynamic, frontalArea);
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
    
    public Vehicle getVehicleByDescription(String description){
        String query = "SELECT * "
                     + "FROM vehicle "
                     + "WHERE vehicle_description LIKE '" +description+ "'";
        Statement stm = null;
        ResultSet rst = null;
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);
            if (rst.next()) {
                int idVehicle = rst.getInt(1);
                //int idPark = rst.getInt(2);
                //int idVehicleType = rst.getInt(3);
                int state = rst.getInt(4);
                double weight = rst.getDouble(5);
                double aerodynamicCoefficient = rst.getDouble(6);
                double frontalArea = rst.getDouble(7);
                return new Vehicle(idVehicle,state,description,weight,aerodynamicCoefficient,frontalArea);
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
