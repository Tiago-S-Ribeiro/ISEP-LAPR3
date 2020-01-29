/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import lapr.project.model.User;
import oracle.jdbc.OracleTypes;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.model.InvoiceLine;
import lapr.project.model.Park;
import lapr.project.model.Path;
import lapr.project.model.PointInterest;
import lapr.project.model.Rental;
import lapr.project.model.Vehicle;
import lapr.project.utils.PhysicsAlgorithms;

/**
 *
 * @author Beatriz Ribeiro
 */
public class UserDataHandler {

    private static final Logger WARNING_LOGGER = Logger.getLogger(User.class.getName());

    private final DataHandler dataHandler;

    public UserDataHandler() {
        dataHandler = DataHandler.getInstance();
    }

    public static void warningError() {
        WARNING_LOGGER.log(Level.WARNING, "Error on reading Database.");
    }

    public int add(User element) throws SQLException {
        CallableStatement callStmt = null;
        int id = 0;

        callStmt = dataHandler.getConnection().prepareCall("{? = call funcAddUser(?,?,?,?,?,?,?,?) }");
        callStmt.registerOutParameter(1, OracleTypes.INTEGER);
        callStmt.setString(2, element.getEmail());
        callStmt.setString(3, element.getUsername());
        callStmt.setString(4, element.getCreditCard());
        callStmt.setFloat(5, element.getCyclingAverageSpeed());
        callStmt.setFloat(6, element.getHeight());
        callStmt.setFloat(7, element.getWeight());
        callStmt.setString(8, element.getGender());
        callStmt.setString(9, element.getPwd());
        callStmt.execute();

        id = callStmt.getInt(1);

        try {
            callStmt.close();
        } catch (SQLException | NullPointerException ex ) {
            Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        }

        return id;
    }

    public User getById(int id) {
        String query = "SELECT * FROM user_app WHERE id_user= " + id;
        Statement stm = null;
        ResultSet rst = null;
        User user = null;
        try {
            Connection con = DataHandler.getInstance().getConnection();
            stm = con.createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                String email = rst.getString(2);
                String username = rst.getString(3);
                String creditCard = rst.getString(4);
                Float cyclingAverageSpeed = rst.getFloat(5);
                int height = rst.getInt(6);
                Float weight = rst.getFloat(7);
                String gender = rst.getString(8);
                String pwd = rst.getString(9);
                int points = rst.getInt(10);
                user = new User(id, email, username, creditCard, cyclingAverageSpeed,
                        height, weight, gender, pwd);
                user.setPoints(points);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return user;
    }

    /**
     * Validates if the loggin has been successfull
     *
     * @param email username or email
     * @param password password
     * @return if the loggin was successfull or not
     */
    public int validateLogin(String email, String password) {
        CallableStatement callStmt = null;
        int result = 0;
        try {
            callStmt = dataHandler.getConnection().prepareCall("{ ? = call funcValidateLogin(?,?) }");
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setString(2, email);
            callStmt.setString(3, password);
            callStmt.execute();

            result = callStmt.getInt(1);

        } catch (SQLException e) {
            Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (callStmt != null) {
                    callStmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return result;
    }

    public double calculateAmountCalories(ArrayList<PointInterest> path, int userId, int vehicleId) {

        double calories = 0;
        if (path.size() >= 2) {
            for (int i = 0; i < path.size() - 1; i++) {

                User user = this.getById(userId);
                Vehicle vehicle = new VehicleDataHandler().getById(vehicleId);
                PointInterest initialPoi = path.get(i);
                PointInterest finalPoi = path.get(i + 1);
                Path pa = new ParkDataHandler().getPathByIdParks(initialPoi.getIdPoint(), finalPoi.getIdPoint());

                calories += new PhysicsAlgorithms().calculateCalories(vehicle.getWeight(), user.getWeight(),
                        pa.getKineticCoefficient(), vehicle.getFrontalArea(), user.getCyclingAverageSpeed(),
                        vehicle.getAerodynamicCoefficient(), pa.getWindSpeed(), pa.getWindDirection(),
                        initialPoi.getGeoLocation().getElevation(), finalPoi.getGeoLocation().getElevation(),
                        initialPoi.getGeoLocation().getLatitude(), finalPoi.getGeoLocation().getLatitude(),
                        initialPoi.getGeoLocation().getLongitude(), finalPoi.getGeoLocation().getLongitude());

            }
        }
        return calories;
    }

    public List<Rental> getUserHistorical(int id) {
        String query = "SELECT * FROM rental WHERE (rental_begin_date_hour IS NOT NULL "
                + "AND rental_end_date_hour IS NOT NULL) AND id_user = " + id;

        Statement stm = null;
        ResultSet rst = null;
        ArrayList<Rental> historical = new ArrayList<>();
        VehicleDataHandler vehicleDataHandler = new VehicleDataHandler();
        ParkDataHandler parkDataHandler = new ParkDataHandler();
        UserDataHandler userDataHandler = new UserDataHandler();
        
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            while (rst.next()) {
                Park parkPicking = parkDataHandler.getById(rst.getInt(2));
                Park parkDelivery=parkDataHandler.getById(rst.getInt(3));
                Vehicle vehicle = vehicleDataHandler.getById(rst.getInt(4));
                User user = userDataHandler.getById(rst.getInt(5));
                double cost = rst.getDouble(6);
                String beginDateHour = String.valueOf(rst.getDate(7).getTime());
                String endDateHour = String.valueOf(rst.getDate(8).getTime());
                int duration = rst.getInt(9);
                int earnedPoints = rst.getInt(10);
                Rental rental = new Rental(user, vehicle, parkPicking);
                rental.setParkFrom(parkPicking);
                rental.setParkTo(parkDelivery);
                rental.setCost(cost); 
                rental.setBeginDateHour(beginDateHour); rental.setEndDateHour(endDateHour);
                rental.setDuration(duration); rental.setEarnedPoints(earnedPoints);
                historical.add(rental);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return historical;
    }

    /**
     * Returns the User with the provided username
     *
     * @param username of the User
     * @return User object
     */
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM user_app WHERE username LIKE '" + username + "'";
        Statement stm = null;
        ResultSet rst = null;
        User user = null;
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                int id = rst.getInt(1);
                String email = rst.getString(2);
                username = rst.getString(3);
                String creditCard = rst.getString(4);
                Float cyclingAverageSpeed = rst.getFloat(5);
                int height = rst.getInt(6);
                Float weight = rst.getFloat(7);
                String gender = rst.getString(8);
                String pwd = rst.getString(9);
                int points = rst.getInt(10);
                user = new User(id, email, username, creditCard, cyclingAverageSpeed,
                        height, weight, gender, pwd);
                user.setPoints(points);
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return user;
    }
    
    public TreeMap<Long, InvoiceLine> getUnpaidRentals(int idUser){
        
        String query = "SELECT r.rental_begin_date_hour, il.* FROM invoice_line il INNER JOIN rental r ON il.id_rental = r.id_rental "
                + "WHERE il.id_invoice IS NULL AND r.id_user = " + idUser
                + " UNION "
                + "SELECT r.rental_begin_date_hour, il.* FROM invoice_line il INNER JOIN rental r ON il.id_rental = r.id_rental "
                + "INNER JOIN invoice i ON il.id_invoice = i.id_invoice "
                + "WHERE r.id_user = " + idUser;
        
        TreeMap<Long, InvoiceLine> unpaidRentals = new TreeMap<>();
        Statement stm = null;
        ResultSet rst = null;
        
        try{
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);
            
            while(rst.next()){
                long dateBegin = rst.getDate(1).getTime();
                int idRental = rst.getInt(2);
                //int idInvoice = rst.getInt(3);
                int idVehicle = rst.getInt(4);
                int duration = rst.getInt(5);
                double cost = rst.getDouble(6);
                long dateEnd = rst.getDate(7).getTime();
                int earnedPoints = rst.getInt(8);
                
                InvoiceLine il = new InvoiceLine(idRental, idVehicle, duration, cost, String.valueOf(dateEnd), earnedPoints);
                unpaidRentals.put(dateBegin, il);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return unpaidRentals;
    }
}
