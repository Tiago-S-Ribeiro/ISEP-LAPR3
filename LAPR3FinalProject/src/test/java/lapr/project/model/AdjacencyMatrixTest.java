/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Bernardo Carvalho
 */
public class AdjacencyMatrixTest {
    
    private AdjacencyMatrix instance;
    
    public AdjacencyMatrixTest() {
        instance = new AdjacencyMatrix();
    }

    /**
     * Test of privateGet method, of class AdjacencyMatrix.
     */
    @Test
    public void testPrivateGet() {
        System.out.println("privateGet");
        int x = 0;
        int y = 0;
        AdjacencyMatrix instance = new AdjacencyMatrix();
        Double expResult = null;
        Double result = instance.privateGet(x, y);
        assertEquals(expResult, result);
    }

    /**
     * Test of privateSet method, of class AdjacencyMatrix.
     */
    @Test
    public void testPrivateSet() {
        System.out.println("privateSet");
        int x = 0;
        int y = 0;
        Double e = null;
        AdjacencyMatrix instance = new AdjacencyMatrix();
        instance.privateSet(x, y, e);
    }

    /**
     * Test of toIndex method, of class AdjacencyMatrix.
     */
    @Test
    public void testToIndex() {
        System.out.println("toIndex");
        PointInterest vertex = null;
        AdjacencyMatrix instance = new AdjacencyMatrix();
        int expResult = -1;
        int result = instance.toIndex(vertex);
        assertEquals(expResult, result);
    }

//    /**
//     * Test of numVertices method, of class AdjacencyMatrix.
//     */
//    @Test
//    public void testNumVertices() {
//        System.out.println("numVertices");
//        AdjacencyMatrix instance = new AdjacencyMatrix();
//        int expResult = 0;
//        int result = instance.numVertices();
//        assertEquals(expResult, result);
//    }

//    /**
//     * Test of numEdges method, of class AdjacencyMatrix.
//     */
//    @Test
//    public void testNumEdges() {
//        System.out.println("numEdges");
//        AdjacencyMatrix instance = new AdjacencyMatrix();
//        int expResult = 0;
//        int result = instance.numEdges();
//        assertEquals(expResult, result);
//    }

    /**
     * Test of vertices method, of class AdjacencyMatrix.
     */
    @Test
    public void testVertices() {
        System.out.println("vertices");
        AdjacencyMatrix instance = new AdjacencyMatrix();
        PointInterest p = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        instance.insertVertex(p);
        Iterable<PointInterest> expResult = instance.vertices();
        Iterable<PointInterest> result = instance.vertices();
        assertEquals(expResult, result);
    }

    /**
     * Test of edges method, of class AdjacencyMatrix.
     */
    @Test
    public void testEdges() {
        System.out.println("edges");
        AdjacencyMatrix instance = new AdjacencyMatrix();
        double d = 9.2;
        instance.insertEdge(0, 0, d);
        Iterable<Double> expResult = instance.edges();
        Iterable<Double> result = instance.edges();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEdge method, of class AdjacencyMatrix.
     */
    @Test
    public void testGetEdge() {
        System.out.println("getEdge");
        PointInterest vertexA = null;
        PointInterest vertexB = null;
        AdjacencyMatrix instance = new AdjacencyMatrix();
        Double expResult = null;
        Double result = instance.getEdge(vertexA, vertexB);
        assertEquals(expResult, result);
    }

    /**
     * Test of insertVertex method, of class AdjacencyMatrix.
     */
    @Test
    public void testInsertVertex() {
        System.out.println("insertVertex");
        PointInterest newVertex = null;
        AdjacencyMatrix instance = new AdjacencyMatrix();
        boolean expResult = true;
        boolean result = instance.insertVertex(newVertex);
        assertEquals(expResult, result);
    }

    /**
     * Test of insertEdge method, of class AdjacencyMatrix.
     */
    @Test
    public void testInsertEdge_3args_1() {
        System.out.println("insertEdge");
        int indexA = 0;
        int indexB = 0;
        Double newEdge = null;
        AdjacencyMatrix instance = new AdjacencyMatrix();
        instance.insertEdge(indexA, indexB, newEdge);
    }

    /**
     * Test of insertEdge method, of class AdjacencyMatrix.
     */
    @Test
    public void testInsertEdge_3args_2() {
        System.out.println("insertEdge");
        PointInterest vertexA = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        PointInterest vertexB = new PointInterest(1, new GeographicalLocation(2, 2, 3), "ponto1");
        Double newEdge = 4.0;
        AdjacencyMatrix instance = new AdjacencyMatrix();
        boolean expResult = false;
        boolean result = instance.insertEdge(vertexA, vertexB, newEdge);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class AdjacencyMatrix. (comparing same object/reference)
     */
    @Test
    public void testEquals() {
        boolean expected = true;
        boolean obtained = instance.equals(instance);
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method, of class AdjacencyMatrix. (first If condition, object being null)
     */
    @Test
    public void testEquals2() {
        AdjacencyMatrix ad1 = null;
        AdjacencyMatrix ad2 = instance = new AdjacencyMatrix(10);   //constructor 1
        AdjacencyMatrix ad3 = new AdjacencyMatrix();                //constructor 2
        
        boolean expected = false;
        boolean obtained = ad2.equals(ad1);
        boolean obtained2 = ad3.equals(ad1);
        
        assertEquals(expected, obtained);
        assertEquals(expected, obtained2);
    }
    
    /**
     * Test of equals method, of class AdjacencyMatrix. (different classes)
     */
    @Test
    public void testEquals3() {
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park park = new Park("THIS IS", "SPARTA!", 1, 220, 16, g);
        Scooter scoot = new Scooter(Scooter.SCOOTER_OFF_ROAD_ID, 1, 75, 2, 1, "PT050", 12, 1.10, 0.3);
        AdjacencyMatrix ad1 = instance = new AdjacencyMatrix(10);  
        AdjacencyMatrix ad2 = new AdjacencyMatrix(); 
        
        boolean expected = false;
        boolean obtained = park.equals(ad1);
        boolean obtained2 = park.equals(ad2);
        boolean obtained3 = scoot.equals(ad1);
        boolean obtained4 = scoot.equals(ad2);
        
        assertEquals(expected, obtained);
        assertEquals(expected, obtained2);
        assertEquals(expected, obtained3);
        assertEquals(expected, obtained4);
    }
    
    /**
     * Test of equals method, of class AdjacencyMatrix. (false 'instanceOf')
     */
    @Test
    public void testEquals4() {
        Object objectTest = new Object();
        
        boolean expected = false;
        boolean obtained = objectTest instanceof AdjacencyMatrix;
        
        assertEquals(expected, obtained);
    }
    
    /**
     * Test of equals method, of class AdjacencyMatrix. (first condidition after cast - numEdges different, but vertices equal)
     */
    @Test
    public void testEquals5() {
        AdjacencyMatrix ad1 = new AdjacencyMatrix();
        AdjacencyMatrix ad2 = new AdjacencyMatrix();
        ad1.numEdges = 15;
        ad2.numEdges = 20;
        ad1.numVertices = 30;
        ad2.numVertices = 30;
        
        boolean expected = false;
        boolean result = ad1.equals(ad2);
        
        assertEquals(expected, result);
    }
    
    /**
     * Test of equals method, of class AdjacencyMatrix. (first condidition after cast - numEdges equal, but vertices different)
     */
    @Test
    public void testEquals6() {
        AdjacencyMatrix ad1 = new AdjacencyMatrix();
        AdjacencyMatrix ad2 = new AdjacencyMatrix();
        ad1.numEdges = 15;
        ad2.numEdges = 15;
        ad1.numVertices = 30;
        ad2.numVertices = 35;
        
        boolean expected = false;
        boolean result = ad1.equals(ad2);
        
        assertEquals(expected, result);
    }
    
    /**
     * Test of equals method, of class AdjacencyMatrix. (first condidition after cast - both different)
     */
    @Test
    public void testEquals7() {
        AdjacencyMatrix ad1 = new AdjacencyMatrix();
        AdjacencyMatrix ad2 = new AdjacencyMatrix();
        ad1.numEdges = 15;
        ad2.numEdges = 20;
        ad1.numVertices = 30;
        ad2.numVertices = 35;
        
        boolean expected = false;
        boolean result = ad1.equals(ad2);
        
        assertEquals(expected, result);
    }
    
    /**
     * Test of equals method, of class AdjacencyMatrix. (first condidition after cast(both equal) but second condition - false)
     */
    @Test
    public void testEquals8() {
        AdjacencyMatrix ad1 = new AdjacencyMatrix();
        AdjacencyMatrix ad2 = new AdjacencyMatrix();
        //Both numEdges and numVertices equal
        ad1.numEdges = 0;
        ad2.numEdges = 0;
        ad1.numVertices = 0;
        ad2.numVertices = 0;
        
        PointInterest instance1 = new PointInterest(1, new GeographicalLocation(2, 2, 3), "Mugello");
        PointInterest instance2 = new PointInterest(2, new GeographicalLocation(1, 4, 2), "San Marino");
        
        ad1.insertVertex(instance1);
        ad2.insertVertex(instance2);
        
        boolean expected = false;
        boolean result = ad1.equals(ad2);
        
        assertEquals(expected, result);
    }
    
    /**
     * Test of equals method, of class AdjacencyMatrix. (first 3 conditions false)
     */
    @Test
    public void testEquals9() {
        AdjacencyMatrix ad1 = new AdjacencyMatrix();
        AdjacencyMatrix ad2 = new AdjacencyMatrix();
        //Both numEdges and numVertices equal
        ad1.numEdges = 0;
        ad2.numEdges = 0;
        ad1.numVertices = 0;
        ad2.numVertices = 0;
        
        PointInterest instance1 = new PointInterest(1, new GeographicalLocation(2, 2, 3), "V");
        PointInterest instance2 = new PointInterest(2, new GeographicalLocation(1, 4, 2), "R");
        PointInterest instance3 = new PointInterest(2, new GeographicalLocation(7, 8, 9), "4");
        PointInterest instance4 = new PointInterest(2, new GeographicalLocation(4, 5, 6), "6");
        
        ad1.insertVertex(instance1);
        ad2.insertVertex(instance2);
        ad1.insertEdge(instance1, instance2, 2.0);
        ad2.insertEdge(instance3, instance4, 3.0);
        
        boolean expected = false;
        boolean result = ad1.equals(ad2);
        
        assertEquals(expected, result);
    }
    
    /**
     * Test of equals method, of class AdjacencyMatrix. (both adjacencyMatrix are equal)
     */
    @Test
    public void testEquals10() {
        AdjacencyMatrix ad1 = new AdjacencyMatrix();
        AdjacencyMatrix ad2 = new AdjacencyMatrix();
        //Both numEdges and numVertices equal
        ad1.numEdges = 0;
        ad2.numEdges = 0;
        ad1.numVertices = 0;
        ad2.numVertices = 0;
        
        PointInterest instance1 = new PointInterest(1, new GeographicalLocation(2, 2, 3), "V");
        PointInterest instance2 = new PointInterest(1, new GeographicalLocation(2, 2, 3), "R");
        
        ad1.insertVertex(instance1);
        ad2.insertVertex(instance2);
        ad1.insertEdge(instance1, instance2, 2.0);
        ad2.insertEdge(instance1, instance2, 2.0);
        
        boolean expected = true;
        boolean result = ad1.equals(ad2);
        
        assertEquals(expected, result);
    }
    
    /**
     * Test of hashCode method, of class AdjacencyMatrix.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        AdjacencyMatrix instance = new AdjacencyMatrix();
        int expResult = 154816041;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of hashCode method, of class AdjacencyMatrix.
     */
    @Test
    public void testEquals_Symmetric() {
        AdjacencyMatrix ad1 = new AdjacencyMatrix();
        AdjacencyMatrix ad2 = new AdjacencyMatrix();
        Assert.assertTrue(ad1.equals(ad2) && ad2.equals(ad1));
        Assert.assertTrue(ad1.hashCode() == ad2.hashCode());
    }
    
    /**
     * Test of hashCode method, of class AdjacencyMatrix.
     */
    @Test
    public void testHashCode3() {
        AdjacencyMatrix ad1 = new AdjacencyMatrix();
        AdjacencyMatrix ad2 = new AdjacencyMatrix();
        Map<AdjacencyMatrix, String> map = new HashMap<>();
        map.put(ad1, "dummy");
        Assert.assertEquals("dummy", map.get(ad2));
    }
}
