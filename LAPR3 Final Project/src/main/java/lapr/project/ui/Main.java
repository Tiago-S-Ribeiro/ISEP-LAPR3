/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.ui;

import java.sql.SQLException;

/**
 *
 * @author TiagoRibeiro
 */
public class Main {
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        LoginUI login = new LoginUI();
        login.loop();
    }
}
