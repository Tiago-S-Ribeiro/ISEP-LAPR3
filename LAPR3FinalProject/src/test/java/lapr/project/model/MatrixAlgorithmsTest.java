/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bernardo Carvalho
 */
public class MatrixAlgorithmsTest {
    
    public MatrixAlgorithmsTest() {
    }

    /**
     * Test of transitiveClosure method, of class MatrixAlgorithms.
     */
    @Test
    public void testTransitiveClosure() {
        AdjacencyMatrix completeMap2 = new AdjacencyMatrix();
        
        PointInterest p1 = new PointInterest(1, new GeographicalLocation(2, 21, 3), "ponto1");
        PointInterest p2 = new PointInterest(2, new GeographicalLocation(6, 57, 30), "ponto2");
        PointInterest p3 = new PointInterest(3, new GeographicalLocation(8, 4, 90), "ponto3");
        PointInterest p4 = new PointInterest(4, new GeographicalLocation(1, 21, 12), "ponto4");
        
        completeMap2.insertVertex(p1);
        completeMap2.insertVertex(p2);
        completeMap2.insertVertex(p3);
        completeMap2.insertVertex(p4);

        completeMap2.insertEdge(p1, p2, 2.2);
        completeMap2.insertEdge(p2, p1, 2.2);
        completeMap2.insertEdge(p2, p4, 9.3);
        completeMap2.insertEdge(p4, p2, 9.3);
        completeMap2.insertEdge(p2, p3, 2.3);
        completeMap2.insertEdge(p3, p2, 2.3);
        completeMap2.insertEdge(p3, p4, 10.3);
        completeMap2.insertEdge(p4, p3, 10.3);
        
        MatrixAlgorithms.transitiveClosure(completeMap2, null);

        boolean teste = true;
        int i = 0, j = 0;
        while (i < completeMap2.numVertices && teste == true) {
            while (j < completeMap2.numVertices && teste == true) {
                if (i != j) {
                    if (completeMap2.getEdge(completeMap2.vertices.get(i), completeMap2.vertices.get(j)) == null) {
                        teste = false;
                    }
                }
                j++;
            }
            i++;
        }
        
        System.out.println("Teste: transitiveClosure");
        System.out.println(completeMap2);

        assertTrue(teste);
    }
    
}