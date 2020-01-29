/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import lapr.project.data.UserDataHandler;
import lapr.project.model.InvoiceLine;
import lapr.project.model.PointInterest;
import lapr.project.model.Rental;
import lapr.project.model.User;

/**
 *
 * @author Beatriz Ribeiro
 */
public class UserController {

    private final UserDataHandler userDataHandler;

    /**
     *
     * @param userDataHandler
     */
    public UserController(UserDataHandler userDataHandler) {
        this.userDataHandler = userDataHandler;
    }

    public User addUser(String email, String username, String creditCard, float cyclingAverage, int height,
            float weight, String gender, String pwd) throws SQLException {
        User user = new User(email, username, creditCard, cyclingAverage, height, weight, gender, pwd);
        user.setIdUser(userDataHandler.add(user));
        return user;
    }

    public User getById(int id) {
        User user = null;

        user = userDataHandler.getById(id);

        return user;
    }

    /**
     * Returns the User with the provided username
     *
     * @param username of the User
     * @return User object
     */
    public User getUserByUsername(String username) {
        User u = null;

        u = userDataHandler.getUserByUsername(username);

        return u;
    }

    public User login(String email, String password) {
        User user = null;

        int id = userDataHandler.validateLogin(email, password);
        user = userDataHandler.getById(id);

        return user;
    }

    public double calculateAmountCalories(ArrayList<PointInterest> path, int userId, int vehicleId) {

        double calories = 0;

        calories = userDataHandler.calculateAmountCalories(path, userId, vehicleId);

        return calories;

    }

    public List<Rental> getUserHistorical(int id) {

        return userDataHandler.getUserHistorical(id);

    }
    
    public TreeMap<Long, InvoiceLine> getUnpaidRentals(int idUser){
        
        return userDataHandler.getUnpaidRentals(idUser);
    }
}
