/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import lapr.project.data.RentalDataHandler;
import lapr.project.model.Park;
import lapr.project.model.PointInterest;
import lapr.project.model.Rental;
import lapr.project.model.Scooter;
import lapr.project.model.User;
import lapr.project.model.Vehicle;

/**
 *
 * @author Bernardo Carvalho
 */
public class RentalController {

    private final RentalDataHandler rentalDataHandler;

    public RentalController(RentalDataHandler rentalDataHandler) {
        this.rentalDataHandler = rentalDataHandler;
    }

    /**
     *
     * @param point1
     * @param point2
     * @param numPois
     * @return
     */
    public List<Pair<Double, List<PointInterest>>> shortestRoute(PointInterest point1, PointInterest point2, int numPois){

        List<Pair<Double, List<PointInterest>>> shortestPath = rentalDataHandler.shortestRoute(point1, point2, numPois);

        return shortestPath;

    }

    /**
     *
     * @param userId
     * @param vehicle
     * @param point1
     * @param point2
     * @return
     */
    public List<Pair<Double, List<PointInterest>>> mostEnergeticallyEfficientRoute(int userId, Vehicle vehicle, PointInterest point1, PointInterest point2){

        List<Pair<Double, List<PointInterest>>> mostEnergeticallyEfficientPath = rentalDataHandler.mostEnergeticallyEfficientRoute(userId, vehicle, point1, point2);

        return mostEnergeticallyEfficientPath;

    }

    /**
     *
     * @param point1
     * @param point2
     * @param poisList
     * @return
     * @throws java.sql.SQLException
     */
    public List<Pair<Double, List<PointInterest>>> shortestRoutePassingThroughPoints(PointInterest point1, PointInterest point2, ArrayList<PointInterest> poisList) throws SQLException {

        List<Pair<Double, List<PointInterest>>> shortestPath = rentalDataHandler.shortestRoutePassingThroughPoints(point1, point2, poisList);

        return shortestPath;
    }

    /**
     *
     * @param userId
     * @param vehicle
     * @param point1
     * @param point2
     * @param poisList
     * @param numRoutes
     * @param ascendingOrder
     * @param sortingCriteria
     * @return
     */
    public List<Pair<Double, List<PointInterest>>> mostEfficientRoutePassingThroughPoints(int userId, Vehicle vehicle, PointInterest point1, PointInterest point2, ArrayList<PointInterest> poisList, int numRoutes, boolean ascendingOrder, String sortingCriteria){

        List<Pair<Double, List<PointInterest>>> shortestPath = rentalDataHandler.mostEfficientRoutePassingThroughPoints(userId, vehicle, point1, point2, poisList, numRoutes, ascendingOrder, sortingCriteria);

        return shortestPath;
    }

    /**
     * 
     * @param park1
     * @param park2
     * @param user
     * @return 
     */
    public List<Scooter> getScootersWith10PercentageExtra(Park park1, Park park2, User user) {
        List<Scooter> listScooter = new ArrayList<>();
        try {
            listScooter = rentalDataHandler.getScootersWith10PercentageExtra(park1, park2, user);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listScooter;
    }

    /**
     *
     * @param idPark
     * @return
     */
    public List<Scooter> getScootersWithMoreEnergy(int idPark){
        List<Scooter> scooter = new ArrayList<>();

        scooter = rentalDataHandler.getScootersWithMoreEnergy(idPark);

        return scooter;
    }

    public Rental makeRental(User user, Vehicle vehicle, Park parkFrom) throws SQLException {
        Rental rental = new Rental(user, vehicle, parkFrom);

        rental.setIdRental(rentalDataHandler.makeRental(user.getIdUser(), vehicle.getId()));

        return rental;
    }
    
    public List<Rental> getUnfinishedTrips(){
       return rentalDataHandler.getUnfinishedTrips();
    }
    
    public Rental getRentalById(int idRental){
        Rental rental = null;
        
        rental = rentalDataHandler.getRentalById(idRental);
        return rental;
    }
}
