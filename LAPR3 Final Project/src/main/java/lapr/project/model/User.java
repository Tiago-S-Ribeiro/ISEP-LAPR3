/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

/**
 * User's class
 *
 * @author Beatriz Ribeiro
 */
public class User {

    private int idUser;
    private String username;
    private String email;
    private String creditCard;
    private float cyclingAverageSpeed;
    private int height;
    private float weight;
    private String gender;
    private String pwd;
    private int userPoints;

    /**
     *
     * @param username - user's username
     * @param email - user's email
     * @param creditCard - user's credit card number
     * @param cyclingAverageSpeed - user's cycling average speed
     * @param height - user's height
     * @param weight - user's weight
     * @param gender - user's gender
     * @param pwd - user's password account
     */
    public User(String email, String username, String creditCard, float cyclingAverageSpeed, int height, float weight, String gender, String pwd) {
        this.idUser = 0;
        this.email = email;
        this.username = username;
        this.creditCard = creditCard;
        this.cyclingAverageSpeed = cyclingAverageSpeed;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.pwd = pwd;
        this.userPoints = 0;
    }
    public User(int idUser, String email, String username, String creditCard, float cyclingAverageSpeed, int height, float weight, String gender, String pwd) {
        this.idUser = idUser;
        this.email = email;
        this.username = username;
        this.creditCard = creditCard;
        this.cyclingAverageSpeed = cyclingAverageSpeed;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.pwd = pwd;
        this.userPoints = 0;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public float getCyclingAverageSpeed() {
        return cyclingAverageSpeed;
    }

    public int getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public String getPwd() {
        return pwd;
    }
    
    public int getPoints(){
        return userPoints;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setCyclingAverageSpeed(float cyclingAverageSpeed) {
        this.cyclingAverageSpeed = cyclingAverageSpeed;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    public void setPoints(int userPoints){
        this.userPoints = userPoints;
    }
}
