/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import lapr.project.data.VehicleDataHandler;
import lapr.project.model.Bicycle;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;

/**
 *
 * @author G25
 */
public class VehicleController {

    private final VehicleDataHandler vehicleDataHandler;

    public VehicleController(VehicleDataHandler vehicleDataHandler) {
        this.vehicleDataHandler = vehicleDataHandler;
    }

    public Vehicle addScooter(int idPark, int type, int maxBatteryCapacity, int actualBattteryCapacity, int motor, int state, String description, double weight, double aerodynamicCoefficient, double frontalArea) throws SQLException {
        Scooter scooter = new Scooter(type, maxBatteryCapacity, actualBattteryCapacity, motor, state, description, weight, aerodynamicCoefficient, frontalArea);

        scooter.setId(vehicleDataHandler.addScooter(scooter, idPark));

        return scooter;
    }

    public Vehicle addBicycle(int idPark, double wheelSize, int state, String description, double weight, double aerodynamicCoefficient, double frontalArea) throws SQLException {
        Bicycle bicycle = new Bicycle(wheelSize, state, description, weight, aerodynamicCoefficient, frontalArea);

        bicycle.setId(vehicleDataHandler.addBicycle(bicycle, idPark));

        return bicycle;
    }

    public List<Bicycle> getAllBicycles() {
        List<Bicycle> bicycles = new ArrayList<>();

        bicycles = vehicleDataHandler.getAllBicycles();

        return bicycles;
    }

    public List<Bicycle> getAllBicyclesOfPark(int idPark) {
        List<Bicycle> bicycles = new ArrayList<>();

        bicycles = vehicleDataHandler.getBicyclesOfPark(idPark);

        return bicycles;
    }

    public List<Scooter> getAllScootersOfPark(int idPark) {
        List<Scooter> scooter = new ArrayList<>();

        scooter = vehicleDataHandler.getScootersOfPark(idPark);

        return scooter;
    }

    public List<Scooter> getScootersDontHavecapacityToPerformEstimatedTrip(int distance, int idPark, double userCyclingAverageSpeed) {
        List<Scooter> scooter = new ArrayList<>();

        scooter = vehicleDataHandler.getScootersDontHavecapacityToPerformEstimatedTrip(distance, idPark, userCyclingAverageSpeed);

        return scooter;
    }

    public boolean removeVehicle(int idVehicle) throws SQLException {
        boolean removed = false;

        removed = vehicleDataHandler.removeVehicle(idVehicle);

        return removed;
    }

    public Map<Pair<Integer, String>, Pair<Integer, String>> getUnlockedVehicles() {
        Map<Pair<Integer, String>, Pair<Integer, String>> unlockedvehicles = new HashMap<>();

        unlockedvehicles = vehicleDataHandler.getUnlockedVehicles();

        return unlockedvehicles;
    }
    
    public Bicycle getBicycleByDescription(String description){
        return vehicleDataHandler.getBicycleByDescription(description);
    }
    
    public Scooter getScooterByDescription(String description){
        return vehicleDataHandler.getScooterByDescription(description);
    }
    
    public Vehicle getById(int id){
        return vehicleDataHandler.getById(id);
    }
    
    public Vehicle getVehicleByDescription(String description){
        return vehicleDataHandler.getVehicleByDescription(description);
    }
}
