/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.ui;

import static java.lang.System.exit;
import java.sql.SQLException;
import java.util.Scanner;
import lapr.project.controller.UserController;
import lapr.project.data.UserDataHandler;
import lapr.project.data.UserSession;
import lapr.project.model.User;

/**
 *
 * @author TiagoRibeiro
 */
public class LoginUI {
    
    public static final Scanner INPUT = new Scanner(System.in);
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_ACESS = "adminpass";
    
    public LoginUI() {
        //Dummy constructor to be called and have acess to the instance methods of LoginUI
    }
    
    public static void showLoginScreen() {
        System.out.println("\nRIDE SHARING\n------------");
        System.out.println("1- Login    \n"
                         + "2- Register \n"
                         + "0- Exit");
    }
    
    public static boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public void loop() throws ClassNotFoundException, SQLException {
        String opt;
        do {
            showLoginScreen();
            opt = INPUT.nextLine();

            switch (opt) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                case "0":
                    exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (!opt.equals("0") || isNumeric(opt));
    }
    
    public void login() throws ClassNotFoundException, SQLException{
        
        System.out.println("\nEmail:");
        String loginEmail = INPUT.nextLine();

        System.out.println("\nPassword:");
        String pass = INPUT.nextLine();
        User user = null;
        UserController uc = new UserController(new UserDataHandler()); 
        if(loginEmail.equalsIgnoreCase(ADMIN_LOGIN) && pass.equalsIgnoreCase(ADMIN_ACESS)){
            AdminUI aui = new AdminUI();
            aui.loop();
        }else if((user = uc.login(loginEmail, pass)) != null){
            UserUI uui = new UserUI(); 
            UserSession.getInstance().setUser(user);
            uui.loop();
        }else{
            System.err.println("\nE-mail or Password are incorrect.\n");
            loop();
        }                                          
    }
    
    public void register() throws ClassNotFoundException, SQLException{
        
        System.out.println("\nInsert your e-mail:");
        String email = INPUT.nextLine();
        
        System.out.println("\nInsert your username:");
        String username = INPUT.nextLine();
        
        System.out.println("\nInsert your password:");
        String acess = INPUT.nextLine();
        
        System.out.println("\nInsert your credit card number:");
        String creditCard = INPUT.nextLine();
        
        System.out.println("\nWhat is your cycling average speed? (Ex: 10,40)");
        float cyclingAverage = INPUT.nextFloat();
        
        System.out.println("\nWhat is your height? (Ex: 1,85)");
        int height = INPUT.nextInt();
        
        System.out.println("\nWhat is is your weight?");
        float weight = INPUT.nextFloat();
        
        System.out.println("Finally, what is your gender (M/F)");
        INPUT.nextLine();
        String gender = INPUT.nextLine();   //acrescentar aqui validacoes mais tarde
        
        System.out.println("\nE-mail:\t" + email
                + "\nUsername:\t" + username
                + "\nGender:\t" + gender
                + "\nHeight:\t" + height
                + "\nWeight:\t" + weight
                + "\nCredit card:\t" + creditCard
                + "\ncyclicAverage:\t" + cyclingAverage);
        System.out.println("\nPlease confirm the provided information for registration: (Y/N)");
        String confirmation = INPUT.nextLine();
        
        if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("YES")) {
            UserController uc = new UserController(new UserDataHandler());
            uc.addUser(email, username, creditCard, cyclingAverage, height, weight, gender, acess);
            System.out.println("\n\nWelcome to Ride Sharing " + username + "! Thank you.\n\n");
            loop();
        } else {
            loop();
        }
    }
}
