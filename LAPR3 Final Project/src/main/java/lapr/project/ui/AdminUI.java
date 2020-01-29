/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.ui;

import static java.lang.System.exit;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import lapr.project.controller.ParkController;
import lapr.project.controller.VehicleController;
import lapr.project.data.ParkDataHandler;
import lapr.project.data.VehicleDataHandler;
import static lapr.project.ui.LoginUI.isNumeric;

/**
 *
 * @author TiagoRibeiro
 */
public class AdminUI {

    /**
     * Scanner
     */
    public static final Scanner INPUT = new Scanner(System.in);
    /**
     * SonarQube suggestion because 'State:' is presented more than 2 times in different situations
     */
    public static final String STATE = "\nState:\t\t";
    /**
     * SonarQube suggestion because 'added with sucess.' message is presented more than 2 times in different situations (this constant eliminates one codeSmell) 
     */
    public static final String ADDED_W_SUCCESS = "' added with sucess.\n\n";
    
    public AdminUI() {
        //Dummy constructor to be called and have acess to the instance methods of AdminUI
    }

    /**
     * Main menu for the Administrator
     */
    public static void showAdminScreen() {
        System.out.println("\nRIDE SHARING - ADMINISTRATOR MENU\n--------------------------------"
                + "\n 1 - Add park"
                + "\n 2 - Add point of interest"
                + "\n 3 - Remove park"
                + "\n 4 - Update park"
                + "\n 5 - Add vehicle"
                + "\n 6 - Remove vehicle"
                + "\n 7 - Update vehicle"
                + "\n 8 - Report about unlocked vehicles"
                + "\n 9 - Add path"
                + "\n 0 - Exit"
                + "\n Choose one of the options above.");
    }

    /**
     * Menu loop that allows navigation through the various options
     */
    public void loop() {
        String opt;
        do {
            showAdminScreen();
            opt = INPUT.nextLine();

            switch (opt) {
                case "1":
                    addPark();
                    break;
                case "2":
                    addPOI();
                    break;
                case "3":
                    removePark();
                    break;
                case "4":
                    updatePark();
                    break;
                case "5":
                    addVehicle();
                    break;
                case "6":
                    removeVehicle();
                    break;
                case "7":
                    updateVehicle();
                    break;
                case "8":
                    getUnlockedvehicles();
                    break;
                case "9":
                    addPath();
                    break;
                case "0":
                    exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (!opt.equals("0") || isNumeric(opt));
    }

    /**
     * Interface that allows the addition of a park into the system
     */
    public void addPark() {

        System.out.println("\nInsert the park description/name:");
        String description = INPUT.nextLine();

        System.out.println("\nInsert the park reference (Ex: 'Casa da Música' could be CdM):");
        String parkRef = INPUT.nextLine();

        System.out.println("\nIs the park going to be available immediately after creation? (Y/N)");
        String answer = INPUT.nextLine();
        int state;
        if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
            state = 1;
        } else {
            state = 0;
        }

        System.out.println("\nWhat will be the park's input voltage:");
        int inputVoltage = INPUT.nextInt();

        System.out.println("\nWhat will be the park's input current:");
        int inputCurrent = INPUT.nextInt();

        System.out.println("\nWhat will be the park's bicycle capacity:");
        int maxCapacityBike = INPUT.nextInt();

        System.out.println("\nWhat will be the park's scooter capacity:");
        int maxCapacityScooter = INPUT.nextInt();

        System.out.println("\nInsert the new park latitude coordinate:");
        double latitude = INPUT.nextDouble();

        System.out.println("\nInsert the new park longitude coordinate:");
        double longitude = INPUT.nextDouble();

        System.out.println("\nInsert the new park elevation coordinate:");
        double elevation = INPUT.nextDouble();
        
        System.out.println("\nDescription:\t" + description
                + "\nReference:\t" + parkRef
                + STATE + state
                + "\nInput Voltage:\t" + inputVoltage
                + "\nInput Current:\t" + inputCurrent
                + "\nBicycle Spots:\t" + maxCapacityBike
                + "\nScooter Spots:\t" + maxCapacityScooter
                + "\nLatitude:\t" + latitude
                + "\nLongitude:\t" + longitude
                + "\nElevation:\t" + elevation);
        System.out.println("\nPlease confirm the provided information for a new park: (Y/N)");
        INPUT.nextLine();
        String confirmation = INPUT.nextLine();

        if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("YES")) {
            
            ParkController pc = new ParkController(new ParkDataHandler());
            try {
                pc.addPark(parkRef, description, state, inputVoltage, inputCurrent, latitude, longitude, elevation, maxCapacityBike, maxCapacityScooter);
            } catch (SQLException ex) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("\n\nPark '" + description + ADDED_W_SUCCESS);

            loop();
        } else {
            loop();
        }
    }

    /**
     * Interface that allows the addition of a point of interest into the system
     */
    public void addPOI() {

        System.out.println("\nInsert the point of interest description/name:");
        String description = INPUT.nextLine();

        System.out.println("\nInsert the new point of interest latitude coordinate:");
        double latitude = INPUT.nextDouble();

        System.out.println("\nInsert the new point of interest longitude coordinate:");
        double longitude = INPUT.nextDouble();

        System.out.println("\nInsert the new point of interest elevation coordinate:");
        double elevation = INPUT.nextDouble();

        System.out.println("\nDescription:\t" + description
                + "\nLatitude:\t" + latitude
                + "\nLongitude:\t" + longitude
                + "\nElevation:\t" + elevation);
        System.out.println("\nPlease confirm the provided information for a new Point of Interest: (Y/N)");
        INPUT.nextLine();
        String confirmation = INPUT.nextLine();

        if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("YES")) {

            ParkController pc = new ParkController(new ParkDataHandler());
            try {
                pc.addPOI(latitude, longitude, elevation, description);
            } catch (SQLException ex) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("\n\nPoint of Interest '" + description + ADDED_W_SUCCESS);

            loop();
        } else {
            loop();
        }
    }

    /**
     * Interface that allows the removal of a park off the system
     */
    public void removePark() {
        //posso adicionar uma questao a perguntar se quer consultar os parques todos para conseguir ver o ID, e depois peço-o
        System.out.println("Insert the ID of the park to be removed:");
        int idPark = INPUT.nextInt();
        System.out.println("Do you confirm the removal of the park with ID: " + idPark + "? (Y/N)");
        INPUT.nextLine();
        String confirmation = INPUT.nextLine();
        
        ParkController pc = new ParkController(new ParkDataHandler()); 
        
        if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("YES")) {
            try {
                pc.removePark(idPark);
            } catch (SQLException ex) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            loop();
        }
    }

    /**
     * Interface that allows to update the information of a park
     */
    public void updatePark() {

        System.out.println("Insert the ID of the park to be updated:");
        int idPark = INPUT.nextInt();
        
        ParkController pc = new ParkController(new ParkDataHandler()); 
        if(pc.getPark(idPark) != null){
            System.out.println("Insert the new park's description:");
            String description = INPUT.nextLine();
            try {
                pc.updatePark(idPark, description);
            } catch (SQLException ex) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("There isn't any park with the inserted ID.");
        }
    }

    /**
     * Interface that allows the addition of a vehicle into the system
     */
    public void addVehicle() {

        System.out.println("\nChoose the vehicle type:"
                + "\n 1 - Bicycle"
                + "\n 2 - Scooter"
                + "\n 0 - Return");
        int type = INPUT.nextInt();

        switch (type) {
            case 1:
                addBicycle(type);
                break;
            case 2:
                addScooter(type);
                break;
            case 0:
                loop();
                break;
            default:
                System.out.println("Invalid type, try again:");
                addVehicle();
                break;
        }
    }

    /**
     * Interface that allows the addition of a scooter into the system
     *
     * @param type of the scooter
     */
    public void addScooter(int type) {

        System.out.println("Insert the ID of the park the Vehicle will be attributed to:");
        int idPark = INPUT.nextInt();
        //AQUI POSSO CHAMAR UMA VERIFICACAO PARA O PARK AVAILABILITY PARA VER SE O PARK ESCOLHIDO TEM ESPAÇO

        System.out.println("Insert the Scooter maximum battery capacity:");
        int maxBatteryCapacity = INPUT.nextInt();
        int actualBatteryCapacity = maxBatteryCapacity;

        System.out.println("\nIs the scooter going to be available immediately after creation? (Y/N)");
        String answer = INPUT.nextLine();
        int state;
        if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
            state = 1;
        } else {
            state = 0;
        }

        System.out.println("Insert the scooter's description:");
        String description = INPUT.nextLine();

        System.out.println("Insert the scooter's total weight:");
        double weight = INPUT.nextDouble();

        System.out.println("Insert the scooter's aerodynamic coefficient:");
        double aerodynamicCoefficient = INPUT.nextDouble();

        System.out.println("Insert the scooter's frontal area:");
        double frontalArea = INPUT.nextDouble();
        
        System.out.println("Insert the scooter's motor:");
        int motor = INPUT.nextInt();

        System.out.println("\nDescription\t" + description
                + STATE + state
                + "\nPark ID:\t" + idPark
                + "\nBattery Capacity:\t" + maxBatteryCapacity
                + "\nWeight:\t" + weight
                + "\nAerodynamic Coefficient:\t" + aerodynamicCoefficient
                + "\nFrontal Area:\t" + frontalArea);

        System.out.println("\nPlease confirm the provided information for a new scooter: (Y/N)");
        INPUT.nextLine();
        String confirmation = INPUT.nextLine();

        if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("YES")) {

            VehicleController vc = new VehicleController(new VehicleDataHandler());
            try {
                vc.addScooter(idPark, type, maxBatteryCapacity, motor, actualBatteryCapacity, state, description, weight, aerodynamicCoefficient, frontalArea);
            } catch (SQLException ex) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("\n\nScooter '" + description + ADDED_W_SUCCESS);

            loop();
        } else {
            loop();
        }
    }

    /**
     * Interface that allows the addition of a bicycle into the system
     *
     * @param type of the bicycle
     */
    public void addBicycle(int type) {

        System.out.println("Insert the ID of the park the Vehicle will be attributed to:");
        int idPark = INPUT.nextInt();
        //AQUI POSSO CHAMAR UMA VERIFICACAO PARA O PARK AVAILABILITY PARA VER SE O PARK ESCOLHIDO TEM ESPAÇO

        System.out.println("\nIs the bicycle going to be available immediately after creation? (Y/N)");
        INPUT.next();
        String answer = INPUT.nextLine();
        int state;
        if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")) {
            state = 1;
        } else {
            state = 0;
        }

        System.out.println("\nInsert the bicycles's wheel size:");
        double wheelSize = INPUT.nextDouble();

        System.out.println("\nInsert the bicycles's description:");
        INPUT.next();
        String description = INPUT.nextLine();

        System.out.println("\nInsert the bicycles's total weight:");
        double weight = INPUT.nextDouble();

        System.out.println("\nInsert the scooter's aerodynamic coefficient:");
        double aerodynamicCoefficient = INPUT.nextDouble();

        System.out.println("\nInsert the scooter's frontal area:");
        double frontalArea = INPUT.nextDouble();

        System.out.println("\nDescription\t" + description
                + STATE + state
                + "\nPark ID:\t" + idPark
                + "\nWheel Size:\t" + wheelSize
                + "\nWeight:\t" + weight
                + "\nAerodynamic Coefficient:\t" + aerodynamicCoefficient
                + "\nFrontal Area:\t" + frontalArea);

        System.out.println("\nPlease confirm the provided information for a new bicycle: (Y/N)");
        INPUT.nextLine();
        String confirmation = INPUT.nextLine();

        if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("YES")) {

            VehicleController vc = new VehicleController(new VehicleDataHandler());
            try {
                vc.addBicycle(idPark, wheelSize, state, description, weight, aerodynamicCoefficient, frontalArea);
            } catch (SQLException ex) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("\n\nBicycle '" + description + ADDED_W_SUCCESS);

            loop();
        } else {
            loop();
        }
    }

    /**
     * Interface that allows the removal of a vehicle off the system
     */
    public void removeVehicle() {
        //posso adicionar uma questao a perguntar se quer consultar os veiculos todos para conseguir ver o ID, e depois peço-o
        System.out.println("Insert the ID of the vehicle to be removed:");
        int vehicleID = INPUT.nextInt();
        VehicleController vc = new VehicleController(new VehicleDataHandler());

        System.out.println("Do you confirm the removal of the vehicle with ID: " + vehicleID + "? (Y/N)");
        INPUT.nextLine();
        String confirmation = INPUT.nextLine();

        if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("YES")) {
            try {
                vc.removeVehicle(vehicleID);
            } catch (SQLException ex) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("\n\nVehicle was removed with sucess.\n\n");
            loop();
        } else {
            loop();
        }
    }

    /**
     * Interface that allows to update the information of a vehicle
     */
    public void updateVehicle() {
        //AINDA NAO IMPLEMENTADO
        System.out.println("TO BE IMPLEMENTED.");
    }

    public void getUnlockedvehicles() {
        VehicleController vc = new VehicleController(new VehicleDataHandler());
        Map<Pair<Integer, String>, Pair<Integer, String>> report = vc.getUnlockedVehicles();
        if (!report.isEmpty()) {
            for (Pair<Integer, String> keyPair : report.keySet()) {
                System.out.println(keyPair.getKey() + " - " + keyPair.getValue()
                        + " - " + report.get(keyPair).getKey() + " - " + report.get(keyPair).getValue());
            }
        }
        System.out.println("OK to exit");
        String exit = INPUT.nextLine();
        if (exit.equalsIgnoreCase("OK")) {
            loop();
        }
    }
    
    private void addPath() {
        
        System.out.println("\nInsert the id of the first point of interest:");
        int idPoiFrom = INPUT.nextInt();

        System.out.println("\nInsert the id of the second point of interest:");
        int idPoiTo = INPUT.nextInt();

        System.out.println("\nInsert the kicenit coefficient:");
        double kineticCoefficient = INPUT.nextDouble();

        System.out.println("\nInsert the wind direction:");
        float windDirection = INPUT.nextFloat();
        
        System.out.println("\nInsert the wind speed:");
        float windSpeed = INPUT.nextFloat();

        System.out.println("\nId Poi From :\t" + idPoiFrom
                + "\nId Poi To:\t" + idPoiTo
                + "\nKinetic Coefficient:\t" + kineticCoefficient
                + "\nWind Direction:\t" + windDirection
                + "\nWind Speed:\t" + windSpeed);
        System.out.println("\nPlease confirm the provided information for a new Path: (Y/N)");
        INPUT.nextLine();
        String confirmation = INPUT.nextLine();

        if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("YES")) {

            try {
                ParkController pc = new ParkController(new ParkDataHandler());
                pc.addPath(idPoiFrom, idPoiTo, kineticCoefficient, windDirection, windSpeed);
                System.out.println("\n\nPath " + ADDED_W_SUCCESS);
                
                loop();
            } catch (SQLException ex) {
                Logger.getLogger(AdminUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            loop();
        }
    }
}
