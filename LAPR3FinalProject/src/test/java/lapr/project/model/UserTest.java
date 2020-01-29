/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Beatriz Ribeiro
 */
public class UserTest {
    private User instance1;
    private User instance2;
    
    public UserTest() {
        instance1= new User("user1@mail.pt", "user1", "12345678", 20, 160, 50, "F", "lolipop");
        instance2= new User(1994, "user2@mail.pt", "user2", "87654321", 50, 180, 70, "M", "kitkat");
    }

    /**
     * Test of getIdUser method, of class User.
     */
    @Test
    public void testGetIdUser() {
        System.out.println("getIdUser");
        int expResult = 0;
        int result = instance1.getIdUser();
        assertEquals(expResult, result);
        
        int expResult2 = 1994;
        int result2 = instance2.getIdUser();
        assertEquals(expResult2, result2);
        
    }

    /**
     * Test of setIdUser method, of class User.
     */
    @Test
    public void testSetIdUser() {
        System.out.println("setIdUser");
        int idUser = 10;
        instance1.setIdUser(idUser);
        assertEquals(idUser, instance1.getIdUser());
        
        int idUser2 = 20;
        instance2.setIdUser(idUser2);
        assertEquals(idUser2, instance2.getIdUser());
        
    }

    /**
     * Test of getEmail method, of class User.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        
        String expResult = "user1@mail.pt";
        String result = instance1.getEmail();
        assertEquals(expResult, result);
        
        String expResult2 = "user2@mail.pt";
        String result2 = instance2.getEmail();
        assertEquals(expResult2, result2);
        
    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        
        String expResult = "user1";
        String result = instance1.getUsername();
        assertEquals(expResult, result);
        
        String expResult2 = "user2";
        String result2 = instance2.getUsername();
        assertEquals(expResult2, result2);
    }

    /**
     * Test of getCreditCard method, of class User.
     */
    @Test
    public void testGetCreditCard() {
        System.out.println("getCreditCard");
        
        String expResult = "12345678";
        String result = instance1.getCreditCard();
        assertEquals(expResult, result);
        
        String expResult2 = "87654321";
        String result2 = instance2.getCreditCard();
        assertEquals(expResult2, result2);
    }

    /**
     * Test of getCyclingAverageSpeed method, of class User.
     */
    @Test
    public void testGetCyclingAverageSpeed() {
        System.out.println("getCyclingAverageSpeed");
        
        Float expResult = 20f;
        Float result = instance1.getCyclingAverageSpeed();
        assertEquals(expResult, result, 0.0);
        
        Float expResult2 = 50f;
        Float result2 = instance2.getCyclingAverageSpeed();
        assertEquals(expResult2, result2, 0.0);
    }

    /**
     * Test of getHeight method, of class User.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        
        int expResult = 160;
        int result = instance1.getHeight();
        assertEquals(expResult, result, 0.0);
        
        int expResult2 = 180;
        int result2 = instance2.getHeight();
        assertEquals(expResult2, result2, 0.0);
    }

    /**
     * Test of getWeight method, of class User.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        
        Float expResult = 50f;
        Float result = instance1.getWeight();
        assertEquals(expResult, result, 0.0);
        
        Float expResult2 = 70f;
        Float result2 = instance2.getWeight();
        assertEquals(expResult2, result2, 0.0);
    }

    /**
     * Test of getGender method, of class User.
     */
    @Test
    public void testGetGender() {
        System.out.println("getGender");
        
        String expResult = "F";
        String result = instance1.getGender();
        assertEquals(expResult, result);
        
        String expResult2 = "M";
        String result2 = instance2.getGender();
        assertEquals(expResult2, result2);
    }

    /**
     * Test of getPwd method, of class User.
     */
    @Test
    public void testGetPwd() {
        System.out.println("getPwd");
        
        String expResult = "lolipop";
        String result = instance1.getPwd();
        assertEquals(expResult, result);
        
        String expResult2 = "kitkat";
        String result2 = instance2.getPwd();
        assertEquals(expResult2, result2);
    }

    /**
     * Test of setEmail method, of class User.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        String email = "user3@mail.pt";
        String expResult = email;
        instance1.setEmail(email);
        String result = instance1.getEmail();
        assertEquals(expResult, result);
        
        String email2 = "user4@mail.pt";
        String expResult2 = email2;
        instance2.setEmail(email2);
        String result2 = instance2.getEmail();
        assertEquals(expResult2, result2);
    }

    /**
     * Test of setUsername method, of class User.
     */
    @Test
    public void testSetUsername() {
        System.out.println("setUsername");
        String username = "user3";
        String expResult = username;
        instance1.setUsername(username);
        String result = instance1.getUsername();
        assertEquals(expResult, result);
        
        String username2 = "user4";
        String expResult2 = username2;
        instance2.setUsername(username2);
        String result2 = instance2.getUsername();
        assertEquals(expResult2, result2);
    }

    /**
     * Test of setCreditCard method, of class User.
     */
    @Test
    public void testSetCreditCard() {
        System.out.println("setCreditCard");
        String creditCard = "11111111";
        instance1.setCreditCard(creditCard);
        String expResult = creditCard;
        String result = instance1.getCreditCard();
        assertEquals(expResult, result);
        
        String creditCard2 = "22222222";
        instance2.setCreditCard(creditCard2);
        String expResult2 = creditCard2;
        String result2 = instance2.getCreditCard();
        assertEquals(expResult2, result2);
       
    }

    /**
     * Test of setCyclingAverageSpeed method, of class User.
     */
    @Test
    public void testSetCyclingAverageSpeed() {
        System.out.println("setCyclingAverageSpeed");
        float cyclingAverageSpeed = 40.0F;
        instance1.setCyclingAverageSpeed(cyclingAverageSpeed);
        float expResult = cyclingAverageSpeed;
        float result = instance1.getCyclingAverageSpeed();
        assertEquals(expResult, result);
        
        float cyclingAverageSpeed2 = 35.0F;
        instance2.setCyclingAverageSpeed(cyclingAverageSpeed2);
        float expResult2 = cyclingAverageSpeed2;
        float result2 = instance2.getCyclingAverageSpeed();
        assertEquals(expResult2, result2);
    }

    /**
     * Test of setHeight method, of class User.
     */
    @Test
    public void testSetHeight() {
        System.out.println("setHeight");
        int height = 162;
        instance1.setHeight(height);
        assertEquals(height, instance1.getHeight());
        
        int height2 = 182;
        instance2.setHeight(height2);
        assertEquals(height2, instance2.getHeight());
    }

    /**
     * Test of setWeight method, of class User.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setWeight");
        float weight = 54.0F;
        instance1.setWeight(weight);
        assertEquals(weight, instance1.getWeight());
        
        float weight2 = 75.0F;
        instance2.setWeight(weight2);
        assertEquals(weight2, instance2.getWeight());
        
    }

    /**
     * Test of setGender method, of class User.
     */
    @Test
    public void testSetGender() {
        System.out.println("setGender");
        String gender = "M";
        instance1.setGender(gender);
        assertEquals(gender, instance1.getGender());
        
        String gender2 = "F";
        instance2.setGender(gender2);
        assertEquals(gender2, instance2.getGender());
        
    }

    /**
     * Test of setPwd method, of class User.
     */
    @Test
    public void testSetPwd() {
        System.out.println("setPwd");
        String pwd = "helloWorld";
        instance1.setPwd(pwd);
        assertEquals(pwd, instance1.getPwd());
        
        String pwd2 = "isepXpto";
        instance2.setPwd(pwd2);
        assertEquals(pwd2, instance2.getPwd());
        
    }

    /**
     * Test of getPoints method, of class User.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        int expResult = 0;
        int result = instance1.getPoints();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPoints method, of class User.
     */
    @Test
    public void testSetPoints() {
        System.out.println("setPoints");
        instance2.setPoints(46);
        int expResult = 46;
        int result = instance2.getPoints();
        assertEquals(expResult, result);
    }
}
