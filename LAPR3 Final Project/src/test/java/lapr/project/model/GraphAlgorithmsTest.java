/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

/**
 *
 * @author G25
 */
public class GraphAlgorithmsTest {
    
    Graph<String,String> completeMap = new Graph<>(false);
    Graph<String,String> incompleteMap = new Graph<>(false);
    
    public GraphAlgorithmsTest() {
        
        completeMap.insertVertex("Porto");
        completeMap.insertVertex("Braga");
        completeMap.insertVertex("Vila Real");
        completeMap.insertVertex("Aveiro");
        completeMap.insertVertex("Coimbra");
        completeMap.insertVertex("Leiria");

        completeMap.insertVertex("Viseu");
        completeMap.insertVertex("Guarda");
        completeMap.insertVertex("Castelo Branco");
        completeMap.insertVertex("Lisboa");
        completeMap.insertVertex("Faro");
                
        completeMap.insertEdge("Porto","Aveiro","A1",75);
        completeMap.insertEdge("Porto","Braga","A3",60);
        completeMap.insertEdge("Porto","Vila Real","A4",100);
        completeMap.insertEdge("Viseu","Guarda","A25",75);
        completeMap.insertEdge("Guarda","Castelo Branco","A23",100);
        completeMap.insertEdge("Aveiro","Coimbra","A1",60);
        completeMap.insertEdge("Coimbra","Lisboa","A1",200);
        completeMap.insertEdge("Coimbra","Leiria","A34",80);
        completeMap.insertEdge("Aveiro","Leiria","A17",120);
        completeMap.insertEdge("Leiria","Lisboa","A8",150);
       
        completeMap.insertEdge("Aveiro","Viseu","A25",85);
        completeMap.insertEdge("Leiria","Castelo Branco","A23",170);
        completeMap.insertEdge("Lisboa","Faro","A2",280);
        
        incompleteMap = completeMap.clone();
        
        incompleteMap.removeEdge("Aveiro","Viseu");
        incompleteMap.removeEdge("Leiria","Castelo Branco");
        incompleteMap.removeEdge("Lisboa","Faro");
        
    }

    /**
     * Test of shortestPath method, of class GraphAlgorithms.
     */
    @Test
    public void testShortestPath() {
        System.out.println("Test of shortest path");
		
	LinkedList<String> shortPath = new LinkedList<String>();
	double lenpath=0;
        lenpath=GraphAlgorithms.shortestPath(completeMap,"Porto","LX",shortPath);
        assertTrue("Length path should be 0 if vertex does not exist", lenpath == 0);
	
        lenpath=GraphAlgorithms.shortestPath(incompleteMap,"Porto","Faro",shortPath);
	assertTrue("Length path should be 0 if there is no path", lenpath == 0);
		
        lenpath=GraphAlgorithms.shortestPath(completeMap,"Porto","Porto",shortPath);
        assertTrue("Number of nodes should be 1 if source and vertex are the same", shortPath.size() == 1);
		
	lenpath=GraphAlgorithms.shortestPath(incompleteMap,"Porto","Lisboa",shortPath);
        assertTrue("Path between Porto and Lisboa should be 335 Km", lenpath == 335);
		
        Iterator<String> it = shortPath.iterator();

        assertTrue("First in path should be Porto", it.next().compareTo("Porto")==0);
        assertTrue("then Aveiro", it.next().compareTo("Aveiro")==0);
        assertTrue("then Coimbra", it.next().compareTo("Coimbra")==0);
        assertTrue("then Lisboa", it.next().compareTo("Lisboa")==0);

	lenpath=GraphAlgorithms.shortestPath(incompleteMap,"Braga","Leiria",shortPath);
        assertTrue("Path between Braga and Leiria should be 255 Km", lenpath == 255);
		
        it = shortPath.iterator();

        assertTrue("First in path should be Braga", it.next().compareTo("Braga")==0);
        assertTrue("then Porto", it.next().compareTo("Porto")==0);
        assertTrue("then Aveiro", it.next().compareTo("Aveiro")==0);
        assertTrue("then Leiria", it.next().compareTo("Leiria")==0);
        
        shortPath.clear();
        lenpath=GraphAlgorithms.shortestPath(completeMap,"Porto","Castelo Branco",shortPath);	
	assertTrue("Path between Porto and Castelo Branco should be 335 Km", lenpath == 335);
	assertTrue("N. cities between Porto and Castelo Branco should be 5 ", shortPath.size() == 5);

        it = shortPath.iterator();

        assertTrue("First in path should be Porto", it.next().compareTo("Porto")==0);
        assertTrue("then Aveiro", it.next().compareTo("Aveiro")==0);
        assertTrue("then Viseu", it.next().compareTo("Viseu")==0);
        assertTrue("then Guarda", it.next().compareTo("Guarda")==0);
        assertTrue("then Castelo Branco", it.next().compareTo("Castelo Branco")==0);

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco 
        //should change shortest path between Porto and Castelo Branco

        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.insertEdge("Leiria","Castelo Branco","A23",170);
	shortPath.clear();
        lenpath=GraphAlgorithms.shortestPath(completeMap,"Porto","Castelo Branco",shortPath);
        assertTrue("Path between Porto and Castelo Branco should now be 365 Km", lenpath == 365);
        assertTrue("Path between Porto and Castelo Branco should be 4 cities", shortPath.size() == 4);

        it = shortPath.iterator();

        assertTrue("First in path should be Porto", it.next().compareTo("Porto")==0);
        assertTrue("then Aveiro", it.next().compareTo("Aveiro")==0);
        assertTrue("then Leiria", it.next().compareTo("Leiria")==0);
        assertTrue("then Castelo Branco", it.next().compareTo("Castelo Branco")==0);
    }

    /**
     * Test of shortestPaths method, of class GraphAlgorithms.
     */
    @Test
    public void testShortestPaths() {
        System.out.println("Test of shortest path");
		
	ArrayList <LinkedList<String>> paths = new ArrayList<>();
        ArrayList <Double> dists = new ArrayList<>();
        
        GraphAlgorithms.shortestPaths(completeMap,"Porto",paths,dists);
        
        assertEquals("There should be as many paths as sizes", paths.size(), dists.size());
        assertEquals("There should be a path to every vertex", completeMap.numVertices(), paths.size());
        assertEquals("Number of nodes should be 1 if source and vertex are the same", 1, paths.get(completeMap.getKey("Porto")).size());
        assertEquals("Path to Lisbon", Arrays.asList("Porto","Aveiro","Coimbra","Lisboa"), paths.get(completeMap.getKey("Lisboa")));
	assertEquals("Path to Castelo Branco", Arrays.asList("Porto","Aveiro","Viseu","Guarda","Castelo Branco"), paths.get(completeMap.getKey("Castelo Branco")));
        assertEquals("Path between Porto and Castelo Branco should be 335 Km", 335, dists.get(completeMap.getKey("Castelo Branco")),0.01);

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco 
        //should change shortest path between Porto and Castelo Branco        
        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.insertEdge("Leiria","Castelo Branco","A23",170);
        GraphAlgorithms.shortestPaths(completeMap,"Porto",paths,dists);
        assertEquals("Path between Porto and Castelo Branco should now be 365 Km", 365, dists.get(completeMap.getKey("Castelo Branco")),0.01);
        assertEquals("Path to Castelo Branco", Arrays.asList("Porto","Aveiro","Leiria","Castelo Branco"), paths.get(completeMap.getKey("Castelo Branco")));

        
        
        GraphAlgorithms.shortestPaths(incompleteMap,"Porto",paths,dists);
	assertEquals("Length path should be Double.MAX_VALUE if there is no path", Double.MAX_VALUE, dists.get(completeMap.getKey("Faro")),0.01);
        assertEquals("Path between Porto and Lisboa should be 335 Km", 335, dists.get(completeMap.getKey("Lisboa")),0.01);
        assertEquals("Path to Lisboa", Arrays.asList("Porto","Aveiro","Coimbra","Lisboa"), paths.get(completeMap.getKey("Lisboa")));
        assertEquals("Path between Porto and Lisboa should be 335 Km", 335, dists.get(completeMap.getKey("Lisboa")),0.01);  

        GraphAlgorithms.shortestPaths(incompleteMap,"Braga",paths,dists);
        assertEquals("Path between Braga and Leiria should be 255 Km", 255, dists.get(completeMap.getKey("Leiria")),0.01);
        assertEquals("Path to Leiria", Arrays.asList("Braga", "Porto","Aveiro","Leiria"), paths.get(completeMap.getKey("Leiria")));
    }
    
    @Test
    public void testShortestPathsFirstCondition(){
        completeMap.insertVertex("R");
        completeMap.insertVertex("C");
        completeMap.insertVertex("10");
        
        ArrayList<LinkedList<String>> listTest = new ArrayList<>();
        ArrayList<Double> distsTest = new ArrayList<>();
        
        boolean expResult = false;
        boolean result = GraphAlgorithms.shortestPaths(completeMap, "SHOULD_BE_FALSE1", listTest, distsTest);
        boolean result2 = GraphAlgorithms.shortestPaths(completeMap, "SHOULD_BE_FALSE2", listTest, distsTest);
        boolean result3 = GraphAlgorithms.shortestPaths(completeMap, "SHOULD_BE_FALSE3", listTest, distsTest);
        boolean result4 = GraphAlgorithms.shortestPaths(completeMap, "SHOULD_BE_FALSE4", listTest, distsTest);
        assertEquals(expResult, result);
        assertEquals(expResult, result2);
        assertEquals(expResult, result3);
        assertEquals(expResult, result4);
    }
}
