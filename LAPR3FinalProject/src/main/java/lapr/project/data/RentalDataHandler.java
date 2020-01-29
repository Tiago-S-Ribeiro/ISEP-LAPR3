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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import lapr.project.model.Graph;
import lapr.project.model.PointInterest;
import lapr.project.model.User;
import lapr.project.model.Rental;
import lapr.project.model.Path;
import lapr.project.utils.PhysicsAlgorithms;
import lapr.project.model.Park;
import lapr.project.model.Scooter;
import lapr.project.model.Vehicle;
import lapr.project.model.AdjacencyMatrix;
import lapr.project.model.Bicycle;
import lapr.project.model.GraphAlgorithms;
import lapr.project.model.MatrixAlgorithms;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Bernardo Carvalho
 */
public class RentalDataHandler {

    private static final Logger WARNING_LOGGER = Logger.getLogger(Rental.class.getName());

    private final DataHandler dataHandler;

    public RentalDataHandler() {
        dataHandler = DataHandler.getInstance();
    }

    public static void warningError() {
        WARNING_LOGGER.log(Level.WARNING, "Error on reading Database.");
    }

    /**
     * Method used to create an adjacency matrix of points of interest
     *
     * @param graph Graph that contains the points of interest that we be used
     * to create the adjacency matrix
     * @return adjacency matrix of points of interest
     */
    public AdjacencyMatrix generateAdjacencyMatrix(Graph<PointInterest, Integer> graph) {

        AdjacencyMatrix matrizAdjacencias = new AdjacencyMatrix(graph.numVertices());

        for (PointInterest p1 : graph.vertices()) {
            matrizAdjacencias.insertVertex(p1);
        }

        for (PointInterest p1 : graph.vertices()) {
            for (PointInterest p2 : graph.vertices()) {
                if (graph.getEdge(p1, p2) != null && !p1.equals(p2)) {
                    matrizAdjacencias.insertEdge(p1, p2, graph.getEdge(p1, p2).getWeight());
                }
            }
        }
        matrizAdjacencias = MatrixAlgorithms.transitiveClosure(matrizAdjacencias, null);
        return matrizAdjacencias;
    }

    /**
     *
     * @return 
     */
    public Graph<PointInterest, Integer> generateDistanceGraph(){

        Graph<PointInterest, Integer> routesGraph = new Graph<>(true);

        List<PointInterest> poiList = new ParkDataHandler().getAllPointsOfInterest();
        List<Path> pathsList = new ParkDataHandler().getAllPaths();

        for (PointInterest poi : poiList) {
            routesGraph.insertVertex(poi);
        }

        for (Path path : pathsList) {
            double distance = new PhysicsAlgorithms().calculateDistanceWithElevation(path.getPointFrom().getGeoLocation().getLatitude(),
                    path.getPointTo().getGeoLocation().getLatitude(), path.getPointFrom().getGeoLocation().getLongitude(),
                    path.getPointTo().getGeoLocation().getLongitude(), path.getPointFrom().getGeoLocation().getElevation(),
                    path.getPointTo().getGeoLocation().getElevation());

            routesGraph.insertEdge(path.getPointFrom(), path.getPointTo(), 1, distance);
        }

        return routesGraph;
    }

    /**
     *
     * @param userId
     * @param vehicle
     * @return
     */
    public Graph<PointInterest, Integer> generateEnergyGraph(int userId, Vehicle vehicle){

        Graph<PointInterest, Integer> efficientRoutesGraph = new Graph<>(true);

        List<PointInterest> poiList = new ParkDataHandler().getAllPointsOfInterest();
        List<Path> pathsList = new ParkDataHandler().getAllPaths();
        User user = new UserDataHandler().getById(userId);

        for (PointInterest poi : poiList) {
            efficientRoutesGraph.insertVertex(poi);
        }

        for (Path path : pathsList) {
            if(vehicle instanceof Bicycle){
                double energy = new PhysicsAlgorithms().calculateCalories(vehicle.getWeight(), user.getWeight(),
                        path.getKineticCoefficient(), vehicle.getFrontalArea(), user.getCyclingAverageSpeed(),
                        vehicle.getAerodynamicCoefficient(), path.getWindSpeed(), path.getWindDirection(),
                        path.getPointFrom().getGeoLocation().getElevation(), path.getPointTo().getGeoLocation().getElevation(),
                        path.getPointFrom().getGeoLocation().getLatitude(), path.getPointTo().getGeoLocation().getLatitude(),
                        path.getPointFrom().getGeoLocation().getLongitude(), path.getPointTo().getGeoLocation().getLongitude());

                efficientRoutesGraph.insertEdge(path.getPointFrom(), path.getPointTo(), 1, energy);
            }
            else if((vehicle instanceof Scooter)){
                double energy = new PhysicsAlgorithms().calculateEnergySpentBetweenPoints(vehicle.getWeight(), user.getWeight(),
                        path.getKineticCoefficient(), vehicle.getFrontalArea(),
                        vehicle.getAerodynamicCoefficient(), path.getWindSpeed(), path.getWindDirection(),
                        path.getPointFrom().getGeoLocation().getElevation(), path.getPointTo().getGeoLocation().getElevation(),
                        path.getPointFrom().getGeoLocation().getLatitude(), path.getPointTo().getGeoLocation().getLatitude(),
                        path.getPointFrom().getGeoLocation().getLongitude(), path.getPointTo().getGeoLocation().getLongitude());

                efficientRoutesGraph.insertEdge(path.getPointFrom(), path.getPointTo(), 1, energy);
            }
        }

        return efficientRoutesGraph;
    }
    
    /**
     * 
     * @param paths
     * @param destiny
     * @param routesGraph
     * @param shortestPaths
     * @param numPois
     * @return 
     */
    public List<Pair<Double, List<PointInterest>>> getShortestPaths(List<Pair<Double, List<PointInterest>>> paths, PointInterest destiny, Graph<PointInterest, Integer> routesGraph, List<Pair<Double, List<PointInterest>>> shortestPaths, int numPois, int numIterations){
        if(numIterations < 0){
            for (Pair<Double, List<PointInterest>> pair : paths) {
                if(pair.getValue().size() == numPois+2 && pair.getValue().get(pair.getValue().size()-1).equals(destiny)){
                    if(!shortestPaths.isEmpty() && pair.getKey() < shortestPaths.get(shortestPaths.size()-1).getKey()){
                        shortestPaths.clear();
                        shortestPaths.add(pair);
                    }
                    else if(!shortestPaths.isEmpty() && pair.getKey() == shortestPaths.get(shortestPaths.size()-1).getKey()){
                        shortestPaths.add(pair);
                    }
                    else if(shortestPaths.isEmpty()){
                        shortestPaths.add(pair);
                    }
                }
            }
            return shortestPaths;
        }
        List<Pair<Double, List<PointInterest>>> newPaths = new ArrayList();
        for (Pair<Double, List<PointInterest>> pa : paths) {
            for(PointInterest p : routesGraph.adjVertices(pa.getValue().get(pa.getValue().size()-1))){
                List<PointInterest> path = new ArrayList<>(pa.getValue());
                path.add(p);
                Double weight = pa.getKey() + routesGraph.getEdge(pa.getValue().get(pa.getValue().size()-1), p).getWeight();
                Pair<Double, List<PointInterest>> pair = new Pair<>(weight, path);
                if(!paths.contains(pair)){
                    newPaths.add(pair);
                }
            }
        }
        paths.addAll(newPaths);
        return getShortestPaths(paths, destiny, routesGraph, shortestPaths, numPois, numIterations-1);
    }
    
    /**
     * 
     * @param paths
     * @param destiny
     * @param routesGraph
     * @param shortestPaths
     * @param numPois
     * @return 
     */
    public List<Pair<Double, List<PointInterest>>> getMostEfficientPaths(List<Pair<Double, List<PointInterest>>> paths, PointInterest destiny, Graph<PointInterest, Integer> routesGraph, List<Pair<Double, List<PointInterest>>> shortestPaths, int numPois){
        if(numPois < 0){
            return shortestPaths;
        }
        List<Pair<Double, List<PointInterest>>> newPaths = new ArrayList();
        for(Pair<Double, List<PointInterest>> pa : paths){
            for(PointInterest p : routesGraph.adjVertices(pa.getValue().get(pa.getValue().size()-1))){
                List<PointInterest> path = new ArrayList<>(pa.getValue());
                path.add(p);
                Double weight = pa.getKey() + routesGraph.getEdge(pa.getValue().get(pa.getValue().size()-1), p).getWeight();
                Pair<Double, List<PointInterest>> pair = new Pair<>(weight, path);
                if(p.equals(destiny) && shortestPaths.isEmpty()){
                    shortestPaths.add(pair);
                }
                else if(p.equals(destiny) && !shortestPaths.contains(pair) && !shortestPaths.isEmpty() 
                        && (double)pair.getKey() == shortestPaths.get(0).getKey()){
                    shortestPaths.add(pair);
                }
                else if(p.equals(destiny) && !shortestPaths.contains(pair) && !shortestPaths.isEmpty() 
                        && (double)pair.getKey() < shortestPaths.get(0).getKey()){
                    shortestPaths.clear();
                    shortestPaths.add(pair);
                }
                else if(!paths.contains(pair)){
                    newPaths.add(pair);
                }
            }
        }
        paths.addAll(newPaths);
        return getMostEfficientPaths(paths, destiny, routesGraph, shortestPaths, numPois-1);
    }

    /**
     *
     * @param point1
     * @param point2
     * @param numPois
     * @return
     */
    public List<Pair<Double, List<PointInterest>>> shortestRoute(PointInterest point1, PointInterest point2, int numPois) {

        Graph<PointInterest, Integer> routesGraph = this.generateDistanceGraph();

        if (!routesGraph.validVertex(point1) || !routesGraph.validVertex(point2)) {
            return new ArrayList<>();
        } else {
            List<Pair<Double, List<PointInterest>>> shortestPaths = new ArrayList<>();
            List<Pair<Double, List<PointInterest>>> paths = new ArrayList<>();
            List<PointInterest> initialPath = new ArrayList<>();
            initialPath.add(point1);
            paths.add(new Pair(0.0, initialPath));
            shortestPaths = this.getShortestPaths(paths, point2, routesGraph, shortestPaths, numPois, numPois);
            
            Collections.sort(shortestPaths, (final Pair<Double, List<PointInterest>> p1, final Pair<Double, List<PointInterest>> p2) -> {
                double inclination1 = Math.abs(p1.getValue().get(p1.getValue().size()-1).getGeoLocation().getElevation() - p1.getValue().get(0).getGeoLocation().getElevation());
                
                double inclination2 = Math.abs(p2.getValue().get(p2.getValue().size()-1).getGeoLocation().getElevation() - p2.getValue().get(0).getGeoLocation().getElevation());
                
                if (p1.getValue().size() > p2.getValue().size()) {
                    return 1;
                } else if (p1.getValue().size() < p2.getValue().size()) {
                    return -1;
                } else if (inclination1 > inclination2) {
                    return 1;
                } else if (inclination1 < inclination2) {
                    return -1;
                }
                return 0;
            });
            
            List<Pair<Double, List<PointInterest>>> shortestPathsCopy = new ArrayList<>(shortestPaths);
            
            for (Pair<Double, List<PointInterest>> shortestPath : shortestPathsCopy) {
                if(shortestPath.getValue().size() != numPois + 2){
                    shortestPaths.remove(shortestPath);
                }
            }
            
            return shortestPaths;
        }
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

        Graph<PointInterest, Integer> routesGraph = this.generateEnergyGraph(userId, vehicle);

        if (!routesGraph.validVertex(point1) || !routesGraph.validVertex(point2)) {
            return new ArrayList<>();
        } else {
            List<Pair<Double, List<PointInterest>>> shortestPaths = new ArrayList<>();
            List<Pair<Double, List<PointInterest>>> paths = new ArrayList<>();
            List<PointInterest> initialPath = new ArrayList<>();
            initialPath.add(point1);
            paths.add(new Pair(0.0, initialPath));
            shortestPaths = this.getMostEfficientPaths(paths, point2, routesGraph, shortestPaths, 2);
            
            Collections.sort(shortestPaths, (final Pair<Double, List<PointInterest>> p1, final Pair<Double, List<PointInterest>> p2) -> {
                double inclination1 = Math.abs(p1.getValue().get(p1.getValue().size()-1).getGeoLocation().getElevation() - p1.getValue().get(0).getGeoLocation().getElevation());
                
                double inclination2 = Math.abs(p2.getValue().get(p2.getValue().size()-1).getGeoLocation().getElevation() - p2.getValue().get(0).getGeoLocation().getElevation());
                
                if (p1.getValue().size() > p2.getValue().size()) {
                    return 1;
                } else if (p1.getValue().size() < p2.getValue().size()) {
                    return -1;
                } else if (inclination1 > inclination2) {
                    return 1;
                } else if (inclination1 < inclination2) {
                    return -1;
                }
                return 0;
            });
            
            return shortestPaths;
        }

    }

    /**
     * Generating permutations using Heap Algorithm
     *
     * @param poisList
     * @param size
     * @param pathsPassingThroughPoints
     * @param point1
     * @param point2
     * @param routesGraph
     * @param routesMatrix
     * @return
     */
    public List<Pair<Double, List<PointInterest>>> pathsPermutationAlgorithm(List<PointInterest> poisList, int size, List<Pair<Double, List<PointInterest>>> pathsPassingThroughPoints, PointInterest point1, PointInterest point2, Graph<PointInterest, Integer> routesGraph, AdjacencyMatrix routesMatrix) {

        if (size == 1) {
            List<PointInterest> poisListCopy = new ArrayList<>();
            LinkedList<PointInterest> shortPath = new LinkedList<>();
            GraphAlgorithms.shortestPath(routesGraph, point1, poisList.get(0), shortPath);
            poisListCopy.addAll(shortPath);
            shortPath.clear();
            for (int k = 0; k < poisList.size() - 1; k++) {
                GraphAlgorithms.shortestPath(routesGraph, poisList.get(k), poisList.get(k+1), shortPath);
                shortPath.remove(shortPath.get(0));
                poisListCopy.addAll(shortPath);
                shortPath.clear();
            }
            GraphAlgorithms.shortestPath(routesGraph, poisList.get(poisList.size()-1), point2, shortPath);
            shortPath.remove(shortPath.get(0));
            poisListCopy.addAll(shortPath);
            double distance = 0;
            for (int j = 0; j < poisListCopy.size() - 1; j++) {
                distance += routesMatrix.getEdge(poisListCopy.get(j), poisListCopy.get(j + 1));
            }
            Pair pair = new Pair(distance, poisListCopy);
            if(pathsPassingThroughPoints.isEmpty()){
                pathsPassingThroughPoints.add(pair);
            }
            else if(distance < pathsPassingThroughPoints.get(0).getKey()){
                pathsPassingThroughPoints.clear();
                pathsPassingThroughPoints.add(pair);
            }
            else if(distance == pathsPassingThroughPoints.get(0).getKey()){
                pathsPassingThroughPoints.add(pair);
            }
        }

        for (int i = 0; i < size; i++) {

            pathsPermutationAlgorithm(poisList, size - 1, pathsPassingThroughPoints, point1, point2, routesGraph, routesMatrix);

            if (size % 2 == 1) {
                PointInterest temp = poisList.get(0);
                poisList.set(0, poisList.get(size - 1));
                poisList.set(size - 1, temp);
            } else {
                PointInterest temp2 = poisList.get(i);
                poisList.set(i, poisList.get(size - 1));
                poisList.set(size - 1, temp2);
            }
        }

        return pathsPassingThroughPoints;
    }

    /**
     *
     * @param point1
     * @param point2
     * @param poisList
     * @return
     * @throws SQLException
     */
    public List<Pair<Double, List<PointInterest>>> shortestRoutePassingThroughPoints(PointInterest point1, PointInterest point2, ArrayList<PointInterest> poisList) throws SQLException {

        Graph<PointInterest, Integer> routesGraph = this.generateDistanceGraph();
        AdjacencyMatrix routesMatrix = this.generateAdjacencyMatrix(routesGraph);
        List<Pair<Double, List<PointInterest>>> pathsPassingThroughPoints = new ArrayList<>();

        if (!routesGraph.validVertex(point1) || !routesGraph.validVertex(point2)) {
            return new ArrayList<>();
        } else {
            if(!poisList.isEmpty() || poisList != null){
                pathsPassingThroughPoints = this.pathsPermutationAlgorithm(poisList, poisList.size(), pathsPassingThroughPoints, point1, point2, routesGraph, routesMatrix);
            }
            else{
                return new ArrayList<>();
            }
            
            Collections.sort(pathsPassingThroughPoints, (final Pair<Double, List<PointInterest>> p1, final Pair<Double, List<PointInterest>> p2) -> {
                double inclination1 = Math.abs(p1.getValue().get(p1.getValue().size()-1).getGeoLocation().getElevation() - p1.getValue().get(0).getGeoLocation().getElevation());
                
                double inclination2 = Math.abs(p2.getValue().get(p2.getValue().size()-1).getGeoLocation().getElevation() - p2.getValue().get(0).getGeoLocation().getElevation());
                
                if (p1.getValue().size() > p2.getValue().size()) {
                    return 1;
                } else if (p1.getValue().size() < p2.getValue().size()) {
                    return -1;
                } else if (inclination1 > inclination2) {
                    return 1;
                } else if (inclination1 < inclination2) {
                    return -1;
                }
                return 0;
            });

            return pathsPassingThroughPoints;
        }

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
     * @throws SQLException
     */
    public List<Pair<Double, List<PointInterest>>> mostEfficientRoutePassingThroughPoints(int userId, Vehicle vehicle, PointInterest point1, PointInterest point2, ArrayList<PointInterest> poisList, int numRoutes, boolean ascendingOrder, String sortingCriteria){
        Graph<PointInterest, Integer> routesGraph = this.generateEnergyGraph(userId, vehicle);
        AdjacencyMatrix routesMatrix = this.generateAdjacencyMatrix(routesGraph);
        List<Pair<Double, List<PointInterest>>> pathsPassingThroughPoints = new ArrayList<>();

        if (!routesGraph.validVertex(point1) || !routesGraph.validVertex(point2)) {
            return new ArrayList<>();
        } else {
            if(!poisList.isEmpty() || poisList != null){
                pathsPassingThroughPoints = this.pathsPermutationAlgorithm(poisList, poisList.size(), pathsPassingThroughPoints, point1, point2, routesGraph, routesMatrix);
            }
            else{
                return new ArrayList<>();
            }
            if(sortingCriteria.equalsIgnoreCase("energy")){
                Collections.sort(pathsPassingThroughPoints, (final Pair<Double, List<PointInterest>> p1, final Pair<Double, List<PointInterest>> p2) -> {
                    if (p1.getKey() > p2.getKey()) {
                        return 1;
                    } else if (p1.getKey() < p2.getKey()) {
                        return -1;
                    }
                    return 0;
                });
            }
            else if(sortingCriteria.equalsIgnoreCase("shortest_distance")){
                Collections.sort(pathsPassingThroughPoints, (final Pair<Double, List<PointInterest>> p1, final Pair<Double, List<PointInterest>> p2) -> {
                    double distance1 = 0;
                    for(int i = 0; i < p1.getValue().size() - 1; i++){
                        distance1 += new PhysicsAlgorithms().calculateDistanceWithElevation(p1.getValue().get(i).getGeoLocation().getLatitude(),
                    p1.getValue().get(i+1).getGeoLocation().getLatitude(), p1.getValue().get(i).getGeoLocation().getLongitude(),
                    p1.getValue().get(i+1).getGeoLocation().getLongitude(), p1.getValue().get(i).getGeoLocation().getElevation(),
                    p1.getValue().get(i+1).getGeoLocation().getElevation());
                    }
                    
                    double distance2 = 0;
                    for(int i = 0; i < p2.getValue().size() - 1; i++){
                        distance2 += new PhysicsAlgorithms().calculateDistanceWithElevation(p2.getValue().get(i).getGeoLocation().getLatitude(),
                    p2.getValue().get(i+1).getGeoLocation().getLatitude(), p2.getValue().get(i).getGeoLocation().getLongitude(),
                    p2.getValue().get(i+1).getGeoLocation().getLongitude(), p2.getValue().get(i).getGeoLocation().getElevation(),
                    p2.getValue().get(i+1).getGeoLocation().getElevation());
                    }
                    
                    if (distance1 > distance2) {
                        return 1;
                    } else if (distance1 < distance2) {
                        return -1;
                    }
                    return 0;
                });
            }
            else if(sortingCriteria.equalsIgnoreCase("number_of_points")){
                Collections.sort(pathsPassingThroughPoints, (final Pair<Double, List<PointInterest>> p1, final Pair<Double, List<PointInterest>> p2) -> {
                    if (p1.getValue().size() > p2.getValue().size()) {
                        return 1;
                    } else if (p1.getValue().size() < p2.getValue().size()) {
                        return -1;
                    }
                    return 0;
                });
            }

            if (!ascendingOrder) {
                Collections.reverse(pathsPassingThroughPoints);
            }

            if (numRoutes >= 0 && numRoutes < pathsPassingThroughPoints.size()) {
                pathsPassingThroughPoints.subList(numRoutes, pathsPassingThroughPoints.size()).clear();
            }

            return pathsPassingThroughPoints;
        }

    }

    /**
     *
     * @param park1
     * @param park2
     * @param user
     * @return
     * @throws SQLException
     */
    public List<Scooter> getScootersWith10PercentageExtra(Park park1, Park park2, User user) throws SQLException {
        List<Scooter> lstScooter = new ArrayList<>();
        List<PointInterest> listPois = new ArrayList<>();
        //listPois = this.shortestRoute(park1, park2).getValue();
        List<Scooter> allScooter = new VehicleDataHandler().getScootersOfPark(listPois.get(0).getIdPoint());
        for (Scooter scooter : allScooter) {
            double neededEnergy = 0;
            for(int i = 0; i < listPois.size() -1; i++){

                Path path = new ParkDataHandler().getPathByIdParks(i, i+1);

                neededEnergy += new PhysicsAlgorithms().calculateEnergyforGivenTrip(scooter.getWeight(),
                        user.getWeight(), path.getKineticCoefficient(), scooter.getFrontalArea(),
                        scooter.getAerodynamicCoefficient(), path.getWindSpeed(),
                        path.getWindDirection(), listPois.get(i).getGeoLocation().getElevation(), listPois.get(i+1).getGeoLocation().getElevation(),
                        listPois.get(i).getGeoLocation().getLatitude(), listPois.get(i+1).getGeoLocation().getLatitude(), listPois.get(i).getGeoLocation().getLongitude(),
                        listPois.get(i+1).getGeoLocation().getLongitude());
            }
            
            double actualEnergy = (double) ((scooter.getActualBatteryCapacity() * scooter.getMaxBatteryCapacity())/100)* 3600000;//J
            if (actualEnergy >= neededEnergy) {
                lstScooter.add(scooter);
            }
        }
        
        return lstScooter;
    }

    /**
     *
     * @param idPark
     * @return
     */
    public List<Scooter> getScootersWithMoreEnergy(int idPark){

        String query = "SELECT * FROM vehicle v INNER JOIN  scooter s ON v.id_vehicle = s.id_scooter"
                + " WHERE v.vehicle_state = 1 AND v.id_park = " + idPark + " ORDER BY s.actual_batery_capacity DESC";
        ArrayList<Scooter> lstScooter = new ArrayList<>();

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
                int type = rst.getInt(10);
                int maxBateryCapacity = rst.getInt(11);
                int actualBateryCapacity = rst.getInt(12);
                int motor = rst.getInt(13);

                Scooter scooter = new Scooter(type, maxBateryCapacity, actualBateryCapacity, motor, idVehicle, state, description, weight, aerodynamicCoefficient, frontalArea);
                lstScooter.add(scooter);
            }
        } catch (SQLException e) {
            Logger.getLogger(RentalDataHandler.class.getName()).log(Level.WARNING, e.getMessage());
        } finally {
            try{
            if (rst != null) {
                rst.close();
            }
            if (stm != null) {
                stm.close();
            }
            }catch(SQLException ex){
                Logger.getLogger(RentalDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
            }
        }
        return lstScooter;
    }

    public int makeRental(int idUser, int idVehicle) throws SQLException {
        CallableStatement callStmt = null;

        int id = 0;

        callStmt = dataHandler.getConnection().prepareCall("{? = call funcMakeRental(?,?) }");
        callStmt.registerOutParameter(1, OracleTypes.INTEGER);
        callStmt.setInt(2,idVehicle);
        callStmt.setInt(3,idUser);
        callStmt.execute();

        id = callStmt.getInt(1);

        try {

            callStmt.close();

        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(RentalDataHandler.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return id;
    }
    
    public List<Rental> getUnfinishedTrips(){
        String query = "SELECT * FROM rental WHERE (rental_begin_date_hour IS NOT NULL "
                + "AND rental_end_date_hour IS NULL";

        Statement stm = null;
        ResultSet rst = null;
        ArrayList<Rental> unfinishedTrips = new ArrayList<>();
        VehicleDataHandler vehicleDataHandler = new VehicleDataHandler();
        ParkDataHandler parkDataHandler = new ParkDataHandler();
        UserDataHandler userDataHandler = new UserDataHandler();
                
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
            

            while (rst.next()) {
                Park parkPicking = parkDataHandler.getById(rst.getInt(2));
                Vehicle vehicle = vehicleDataHandler.getById(rst.getInt(4));
                User user = userDataHandler.getById(rst.getInt(5));
                String beginDateHour = simpleDate.format(rst.getDate(7));
                Rental rental = new Rental(user, vehicle, parkPicking);
                rental.setBeginDateHour(beginDateHour); 
                unfinishedTrips.add(rental);
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
        return unfinishedTrips;
    }
    
    public Rental getRentalById(int idRental) {
        UserDataHandler uc = new UserDataHandler();
        ParkDataHandler pc = new ParkDataHandler();
        VehicleDataHandler vc = new VehicleDataHandler();
        
        String query = "SELECT * FROM rental "
                + " WHERE id_rental = " + idRental;

        Statement stm = null;
        ResultSet rst = null;
        try {
            stm = dataHandler.getConnection().createStatement();
            rst = stm.executeQuery(query);

            if (rst.next()) {
                int parkOriginID = rst.getInt(2);
                int parkDestinationID = rst.getInt(3);
                int vehicleID = rst.getInt(4);
                int userID = rst.getInt(5);
                int cost = rst.getInt(6);
                long beginHour = rst.getDate(7).getTime();
                long endHour = rst.getDate(8).getTime();
                int duration = rst.getInt(9);
                int earnedPoints = rst.getInt(10);
                
                Rental rental = new Rental(uc.getById(userID), vc.getById(vehicleID), pc.getById(parkOriginID));
                rental.setParkTo(pc.getById(parkDestinationID));
                rental.setIdRental(idRental);
                rental.setCost(cost);
                rental.setBeginDateHour(String.valueOf(beginHour));
                rental.setEndDateHour(String.valueOf(endHour));
                rental.setDuration(duration);
                rental.setEarnedPoints(earnedPoints);
                return rental;
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
}
