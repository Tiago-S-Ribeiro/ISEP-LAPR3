/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.ui;

import static java.lang.System.exit;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import javafx.util.Pair;
import lapr.project.controller.ParkController;
import lapr.project.controller.RentalController;
import lapr.project.controller.UserController;
import lapr.project.controller.VehicleController;
import lapr.project.data.ParkDataHandler;
import lapr.project.data.RentalDataHandler;
import lapr.project.data.UserDataHandler;
import lapr.project.data.UserSession;
import lapr.project.data.VehicleDataHandler;
import lapr.project.model.Park;
import lapr.project.model.Rental;
import lapr.project.model.Vehicle;
import static lapr.project.ui.LoginUI.isNumeric;

/**
 *
 * @author Tiago Ribeiro
 */
public class UserUI {
    
    /**
     * Scanner
     */
    public static final Scanner INPUT = new Scanner(System.in);
    /**
     * SonarQube suggestion because '\nThere are' partial string is presented more than 2 times in different situations
     */
    public static final String THERE_ARE = "\nThere are ";
    /**
     * SonarQube suggestion because 'available at' partial string is presented more than 2 times in different situations
     */
    public static final String AVAILABLE_AT = " available at ";
    /**
     * SonarQube suggestion because this string is presented more than 2 times in different situations
     */
    public static final String PARK_TO_CHECK_MSG = "\nWhat is the park reference of the park you wish to check?";

    public UserUI() {
        //Dummy constructor to be called and have acess to the instance methods of UserUI
    }

    public static void showUserScreen() {
        System.out.println("\nRIDE SHARING\n------------"
                + "\n 1 - Rent vehicle"
                + "\n 2 - Find parks near me"
                + "\n 3 - Check rentals history"
                + "\n 4 - Park vehicle"
                + "\n 5 - Pay monthly invoice"
                + "\n 6 - Check Spots in a Park for my loaned vehicle"
                + "\n 7 - Check Spots in a Park for Scooters"
                + "\n 8 - Check Spots in a Park for Bicycles"
                + "\n 0 - Exit"
                + "\n Choose one of the options above.");
    }

    public void loop() throws ClassNotFoundException, SQLException {
        String opt;
        do {
            showUserScreen();
            opt = INPUT.nextLine();

            switch (opt) {
                case "1":
                    makeRental();
                    break;
                case "2":
                    getNearestParks();
                    break;
                case "3":
                    getUserHistorical();
                    break;
                case "4":
                    parkVehicleAtAGivenPark();
                    break;
                case "5":
                    System.out.println("Not available at the moment.");
                    break;
                case "6":
                    checkParkFeeSpotsForMyLoanedVehicle();
                    break;
                case "7":
                    checkParkFreeScooterSpots();
                    break;
                case "8":
                    checkParkFreeBicycleSpots();
                    break;
                case "0":
                    exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (!opt.equals("0") || isNumeric(opt));
    }

    public void getUserHistorical() throws ClassNotFoundException, SQLException {
        UserController uc = new UserController(new UserDataHandler());
        List<Rental> historical = uc.getUserHistorical(UserSession.getInstance().getUser().getIdUser());
        if (!historical.isEmpty()) {
            for (Rental str : historical) {
                System.out.println(str);
            }
        } else {
            System.out.println("Empty historical. Rent a vehicle to fill it!");
        }
        
        System.out.println("OK to exit");
        String exit = INPUT.nextLine();
        if (exit.equalsIgnoreCase("OK")) {
            loop();
        }
    }
    
    public void getNearestParks() throws ClassNotFoundException, SQLException {
        ParkController pc = new ParkController(new ParkDataHandler());
        
        System.out.println("\nInsert your current latitude coordinate:");
        double latitude = INPUT.nextDouble();
        
        System.out.println("\nInsert your current longitude coordinate:");
        double longitude = INPUT.nextDouble();
        
        System.out.println("What is the radious you wish to search? (In kilometers)");
        double radius = INPUT.nextDouble();
        
        if(radius < 1){
            radius = 1;
        }
        
        List<Pair<Double, Park>> listOfParks = pc.getNearestParks(latitude, longitude, radius);
        
        for(Pair<Double, Park> p : listOfParks){
            System.out.println("Park Reference: " + p.getValue().getRefPark() + "\n");
        }
    }
    
    public void checkParkFreeBicycleSpots(){
        ParkController pc = new ParkController(new ParkDataHandler());
        
        System.out.println(PARK_TO_CHECK_MSG);
        String parkRef = INPUT.nextLine();
        
        int spots = pc.checkParkFreeBicycleSpots(pc.getParkByRefPark(parkRef).getId());
        
        System.out.println(THERE_ARE + spots + AVAILABLE_AT + parkRef + " for bicycles.");
    }
    
    public void checkParkFreeScooterSpots(){
        ParkController pc = new ParkController(new ParkDataHandler());
        
        System.out.println(PARK_TO_CHECK_MSG);
        String reference = INPUT.nextLine();
        
        int spots = pc.checkParkFreeScooterSpots(pc.getParkByRefPark(reference).getId());
        
        System.out.println(THERE_ARE + spots + AVAILABLE_AT + reference + " for scooters.");
    }
    
    public void parkVehicleAtAGivenPark() throws SQLException{
        ParkController pc = new ParkController(new ParkDataHandler());
        
        System.out.println("What is your reference of the park where you'll park the vehicle?");
        String parkRef = INPUT.nextLine();
        
        int parkId = pc.getParkByRefPark(parkRef).getIdPoint();
        int userId = UserSession.getInstance().getUser().getIdUser();
        
        boolean success = pc.parkVehicle(parkId, userId);
        if(success){
            System.out.println("Please confirm you're e-mail for confirmation of successful lock. Thank you!");
        }else{
            System.out.println("It occured an error.");
        }
    }

    public void checkParkFeeSpotsForMyLoanedVehicle(){
        ParkController pc = new ParkController(new ParkDataHandler());
        
        System.out.println(PARK_TO_CHECK_MSG);
        String reference = INPUT.nextLine();
        
        int parkId = pc.getParkByRefPark(reference).getId();
        int spots = pc.checkParkFreeScooterSpots(parkId);
        
        pc.checkParkFreeSpots(parkId, UserSession.getInstance().getUser().getIdUser());
        
        System.out.println(THERE_ARE + spots + AVAILABLE_AT + reference + " for your loaned vehicle.");
    }
    
    public void makeRental() throws SQLException{
        RentalController rc = new RentalController(new RentalDataHandler());
        ParkController pc = new ParkController(new ParkDataHandler());
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        
        System.out.println("Insert park ID from where the rental will take place: ");
        int idPark = INPUT.nextInt();
        System.out.println("Insert an available vehicle ID: ");
        int idVehicle = INPUT.nextInt();
           
        Park p = pc.getPark(idPark);
        Vehicle vehicle = vc.getById(idVehicle);
        
        rc.makeRental(UserSession.getInstance().getUser(), vehicle, p);
    }
}
