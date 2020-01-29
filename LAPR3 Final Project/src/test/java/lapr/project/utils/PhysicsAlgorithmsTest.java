/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author G25
 */
public class PhysicsAlgorithmsTest {

    private PhysicsAlgorithms physic;

    public PhysicsAlgorithmsTest() {
        physic = new PhysicsAlgorithms();
    }

    /**
     * Test of calculateCalories method, of class Fisica.
     */
    @Test
    public void testCalculateCalories() {

        double expected = 15260.042162781254;
        double obtained = physic.calculateCalories(15, 50,0, 0, 2.5f, 0, 0, 0, 0,100, 41.177219, 41.178125, -8.598707, -8.6309280);
        assertEquals(expected, obtained);

    }

    /**
     * Test of calculateTotalForce method, of class Fisica.
     */
    @Test
    public void testCalculateTotalForce() {
        double expected = 243.0702278726205;
        double obtained = physic.calculateTotalForce(50, 85, 1000, 26900, 0.002, 0.3, 50, 1, 15, 175, -34.6131500, -16.5000000, -58.3772300, -68.1500000);
        assertEquals(expected, obtained);
    }

    /**
     * Test of calculateGravitationalForce method, of class Fisica.
     */
    @Test
    public void testCalculateGravitationalForce() {
        double expected = 15.32885986504441;
        double obtained = physic.calculateGravitationalForce(50, 85, 1000, 26900, -34.6131500, -16.5000000, -58.3772300, -68.1500000);
        assertEquals(expected, obtained);
    }

    /**
     * Test of calculatePathInclination method, of class Fisica.
     */
    @Test
    public void testCalculatePathInclination() {
        double expected = 5.981588539360581;
        double obtained = physic.calculatePathInclination(1000, 25900,41.192855, 41.157930, -8.613727, -8.631638);
        assertEquals(expected, obtained);
    }

    /**
     * Test of calculateFrictionalForce method, of class Fisica.
     */
    @Test
    public void testCalculateFrictionalForce() {
        double expected = 2.647794744317775;
        double obtained = physic.calculateFrictionalForce(50, 85, 0.002, 1000, 2690, -34.6131500, -16.5000000, -58.3772300, -68.1500000);
        assertEquals(expected, obtained);
    }

    /**
     * Test of calculateWindeForce method, of class Fisica.
     */
    @Test
    public void testCalculateWindeForce() {
        double expected = 225.09375;
        double obtained = physic.calculateWindeForce(0.3, 50, 1, 15);
        assertEquals(expected, obtained);
    }

    /**
     * Test of calculateAverageSpeedWithWindDirection method, of class
     * PhysicsAlgorithms.
     */
    @Test
    public void testCalculateAverageSpeedWithWindDirection() {
        float cyclingAvgSpeed = 4.17f;
        float windSpeed = 1;
        float windDirection = 90;
        //     Testing landing on first IF condition with windDirection being == 90
        double obtained = physic.calculateAverageSpeedWithWindDirection(cyclingAvgSpeed, windSpeed, windDirection);
        double expected = 3.1700000762939453;
        double expected2 = 4.17;
        assertEquals(expected, expected2, obtained);
        //  Testing failing first IF but landing on second one with condition being windDirection > 0 and < 180
        double obtained3 = physic.calculateAverageSpeedWithWindDirection(cyclingAvgSpeed, windSpeed, 146);
        double windDirectionRad = Math.toRadians(146);
        double expected3 = 4.17f - (1 * Math.abs(Math.cos(windDirectionRad)));
        assertEquals(obtained3, expected3);
        // Testing failing first two ifs with condition being windDirection == 0
        double obtained4 = physic.calculateAverageSpeedWithWindDirection(cyclingAvgSpeed, windSpeed, 0);
        double expected4 = cyclingAvgSpeed;
        double exp4 = 4.17f;
        assertEquals(obtained4, expected4, exp4);
        //   Testing failing first two ifs with condition being windDirection == 180
        double obtained5 = physic.calculateAverageSpeedWithWindDirection(cyclingAvgSpeed, windSpeed, 180);
        double expected5 = cyclingAvgSpeed;
        double exp5 = 4.17f;
        assertEquals(obtained5, expected5, exp5);
        //   Testing failing first two ifs with condition being windDirection > 180
        double obtained6 = physic.calculateAverageSpeedWithWindDirection(cyclingAvgSpeed, windSpeed, 198);
        double expected6 = cyclingAvgSpeed;
        double exp6 = 4.17f;
        assertEquals(obtained6, expected6, exp6);
        //    Testing failing first two ifs with condition being windDirection < 180
        double obtained7 = physic.calculateAverageSpeedWithWindDirection(cyclingAvgSpeed, windSpeed, 198);
        double expected7 = cyclingAvgSpeed;
        double exp7 = 4.17f;
        assertEquals(obtained7, expected7, exp7);
    }

    /**
     * Test of calculateAverageSpeedWithWindDirection method, of class
     * PhysicsAlgorithms.
     */
    @Test
    public void testCalculateAverageSpeedWithWindDirection2() {
        double expected = 3.4039556331749674;
        double obtained = physic.calculateAverageSpeedWithWindDirection(4.17f, 1, 40);
        assertEquals(expected, obtained);
    }

    /**
     * Test of calculateAverageSpeedWithWindDirection method, of class
     * PhysicsAlgorithms.
     */
    @Test
    public void testCalculateAverageSpeedWithWindDirection3() {
        double expected = 4.1700000762939453;
        double obtained = physic.calculateAverageSpeedWithWindDirection(4.17f, 1, 220);
        assertEquals(expected, obtained);
    }

    /**
     * Test of calculateAverageSpeedWithWindDirection method, of class
     * PhysicsAlgorithms.
     */
    @Test
    public void testCalculateAverageSpeedWithWindDirection4() {
        double expected = 4.1700000762939453;
        double obtained = physic.calculateAverageSpeedWithWindDirection(4.17f, 1, -20);
        assertEquals(expected, obtained);
    }

    /**
     * Test of calculateDistanceAScooterCanDo method, of class
     * PhysicsAlgorithms.
     */
    @Test
    public void testCalculateDistanceAScooterCanDo() {
        double expected = 45360; //m       
        double obtained = physic.calculateDistanceAScooterCanDo(250,75, 1);
        assertEquals(expected, obtained);
    }

    /**
     * Test of calculateEnergySpentBetweenPoints method, of class
     * PhysicsAlgorithms.
     */
    @Test
    public void testCalculateEnergySpentBetweenPoints() {
        double expected =2888933.17358455;
        double obtained = physic.calculateEnergySpentBetweenPoints(12, 60, 1, 0.3,1.1, 1, 4, 120, 300, 41.192855, 41.1579300, -8.613727, -8.613900);
        assertEquals(expected, obtained);
    }

    /**
     * Test of calculateEnergyforGivenTrip method, of class PhysicsAlgorithms.
     */
    @Test
    public void testCalculateEnergyforGivenTrip() {
        double expected = 3177826.4909430053;
        double obtained = physic.calculateEnergyforGivenTrip(12, 60, 1, 0.3,1.1, 1, 4, 120, 300, 41.192855, 41.1579300, -8.613727, -8.613900);
         assertEquals(expected, obtained);
    }

    /**
     * Test of calculateTimeNeededToChargeTotaly method, of class
     * PhysicsAlgorithms.
     */
    @Test
    public void testCalculateTimeNeededToChargeTotaly() {
        double expected = 2840.909090909091;//s
        double obtained = physic.calculateTimeNeededToChargeTotaly(220, 16, 50, 1, 5);
        assertEquals(expected, obtained);
        double obtained3 = physic.calculateTimeNeededToChargeTotaly(3, 2000, 50, 1, 1);
        double exp2 = 666.6666666666666666;
        assertEquals(exp2, obtained3);

        double obtained4 = physic.calculateTimeNeededToChargeTotaly(3, -123, 50, 1, 1);
        double exp3 = 0;
        assertEquals(exp3, obtained4);
    }

    /**
     * Test of linearDistanceTo method, of class PhysicsAlgorithms.
     */
    @Test
    public void testLinearDistanceTo() {
        double expected = 4162.7737909672;//m
        double obtained = physic.linearDistanceTo(41.192855, 41.157930, -8.613727, -8.631638);
        assertEquals(expected, obtained);
    }

    /**
     * Test of calculateDistanceWithElevation method, of class
     * PhysicsAlgorithms.
     */
    @Test
    public void testCalculateDistanceWithElevation() {
        double expected = 2700.413730640633;//m
        double obtained = physic.calculateDistanceWithElevation(41.177219, 41.178125, -8.598707, -8.6309280, 0, 100);
        assertEquals(expected, obtained);
    }
}