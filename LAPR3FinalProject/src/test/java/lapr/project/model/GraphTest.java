/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

/**
 *
 * @author G25
 */
public class GraphTest {
    
    Graph<String, String> instance = new Graph<>(true) ;
    
    public GraphTest() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    /**
     * Test of numVertices method, of class Graph.
     */
    @Test
    public void testNumVertices() {
        System.out.println("Test numVertices");
                      
        assertTrue("result should be zero", (instance.numVertices()==0));
        
        instance.insertVertex("A");
        assertTrue("result should be one", (instance.numVertices()==1));       
        
        instance.insertVertex("B");
        assertTrue("result should be two", (instance.numVertices()==2));
        
        instance.removeVertex("A");
        assertTrue("result should be one", (instance.numVertices()==1));
        
        instance.removeVertex("B");
        assertTrue("result should be zero", (instance.numVertices()==0));
    }
    
    /**
     * Test of vertices method, of class Graph.
     */
    @Test
    public void testVertices() {
        System.out.println("Test vertices");

        Iterator<String> itVerts = instance.vertices().iterator();
        
        assertTrue("vertices should be empty", itVerts.hasNext()==false);
        
        instance.insertVertex("A");
        instance.insertVertex("B");
        	
        itVerts = instance.vertices().iterator();
                
        assertTrue("first vertice should be A", (itVerts.next().compareTo("A")==0));
        assertTrue("second vertice should be B",(itVerts.next().compareTo("B")==0));

        instance.removeVertex("A");
		
        itVerts = instance.vertices().iterator();
        assertTrue("first vertice should now be B",(itVerts.next().compareTo("B"))==0);

	instance.removeVertex("B");
		
        itVerts = instance.vertices().iterator();
	assertTrue("vertices should now be empty",itVerts.hasNext()==false);	
    }

    /**
     * Test of numEdges method, of class Graph.
     */
    @Test
    public void testNumEdges() {
        System.out.println("Test numEdges");
        
        assertTrue("result should be zero", (instance.numEdges()==0));

        instance.insertEdge("A","B","Edge1",6);
        assertTrue("result should be one", (instance.numEdges()==1));
        
        instance.insertEdge("A","C","Edge2",1);
        assertTrue("result should be two", (instance.numEdges()==2));
        
        instance.removeEdge("A","B");
        assertTrue("result should be one", (instance.numEdges()==1));    

        instance.removeEdge("A","C");
        assertTrue("result should be zero", (instance.numEdges()==0));
    }

    /**
     * Test of edges method, of class Graph.
     */
    @Test
    public void testEdges() {
        System.out.println("Test Edges");

        Iterator<Edge<String,String>> itEdge = instance.edges().iterator();

        assertTrue("edges should be empty", (itEdge.hasNext()==false));

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);

        itEdge = instance.edges().iterator();
        
        itEdge.next(); itEdge.next();
        assertTrue("third edge should be B-D", itEdge.next().getElement().equals("Edge3")==true); 
        
        itEdge.next(); itEdge.next();
        assertTrue("sixth edge should be D-A", itEdge.next().getElement().equals("Edge6")==true);
        
        instance.removeEdge("A","B");

        itEdge = instance.edges().iterator();
        assertTrue("first edge should now be A-C", itEdge.next().getElement().equals("Edge2")==true);

        instance.removeEdge("A","C"); instance.removeEdge("B","D");
        instance.removeEdge("C","D"); instance.removeEdge("C","E");
        instance.removeEdge("D","A"); instance.removeEdge("E","D");
        instance.removeEdge("E","E");
        itEdge = instance.edges().iterator();
        assertTrue("edges should now be empty", (itEdge.hasNext()==false));
    }
    
    @Test
    public void testGetMap(){
        LinkedHashMap<String,String> expResult = new LinkedHashMap<>();
        Map<String, Vertex<String, String>> result = instance.getMap();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getEdge method, of class Graph.
     */
    @Test
    public void testGetEdge() {
        System.out.println("Test getEdge");
		        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		
        assertTrue("edge should be null", instance.getEdge("A","E")==null);
		
        assertTrue("edge between B-D", instance.getEdge("B","D").getElement().equals("Edge3")==true);       
        assertTrue("edge should be null", instance.getEdge("D","B")==null);

	instance.removeEdge("D","A");	
        assertTrue("edge should be null", instance.getEdge("D","A")==null);
        
        assertTrue("edge should be edge8", instance.getEdge("E","E").getElement().equals("Edge8")==true);
    }

    /**
     * Test of endVertices method, of class Graph.
     */
    @Test
    public void testEndVerticesStandard() {
        System.out.println("Test endVertices");
        			 
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
        
        Edge<String,String> edge0 = new Edge<>();
        
        String[] vertices = new String[2];
        
        //assertTrue("endVertices should be null", instance.endVertices(edge0)==null);

        Edge<String,String> edge1 = instance.getEdge("A","B");
        //vertices = instance.endVertices(edge1);
        assertTrue("first vertex should be A", instance.endVertices(edge1)[0].equals("A"));
        assertTrue("second vertex should be B", instance.endVertices(edge1)[1].equals("B"));
    }
    
    /**
     * Test of endVertices method, of class Graph.
     */
    @Test
    public void testEndVertices() {
        System.out.println("Test endVertices");
        
        instance.insertEdge("A","B","Edge1",1);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","C","Edge3",1);
        
        Edge<String,String> edgeTest = instance.getEdge("NON_EXISTENT_1","NON_EXISTENT_2");
        Object[] expResult = instance.endVertices(edgeTest);
        assertArrayEquals(expResult, null);
        
        Edge<String,String> edgeTest2 = instance.getEdge("A","NON_EXISTENT");
        Object[] expResult2 = instance.endVertices(edgeTest2);
        assertArrayEquals(expResult2, null);
        
        Edge<String,String> edgeTest3 = instance.getEdge("NON_EXISTENT","B");
        Object[] expResult3 = instance.endVertices(edgeTest3);
        assertArrayEquals(expResult3, null);
    }
    
    /**
     * Test of opposite method, of class Graph.
     */
    @Test
    public void testOpposite() {
        System.out.println("Test opposite");
        		
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		     
        Edge<String,String> edge5 = instance.getEdge("C","E");
        String vert = instance.opposite("A", edge5);
        assertTrue("opposite should be null", vert==null);
        
        Edge<String,String> edge1 = instance.getEdge("A","B");
        vert = instance.opposite("A", edge1);
        assertTrue("opposite should be B", vert.equals("B")==true);
        
        Edge<String,String> edge8 = instance.getEdge("E","E");
        vert = instance.opposite("E", edge8);
        assertTrue("opposite should be E", vert.equals("E")==true);
    }

    /**
     * Test of outDegree method, of class Graph.
     */
    @Test
    public void testOutDegree() {
        System.out.println("Test outDegree");
        		
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		    
        int outdeg = instance.outDegree("G");    
        assertTrue("degree should be -1", outdeg==-1);
        
        outdeg = instance.outDegree("A");
        assertTrue("degree should be 2", outdeg==2);
        
        outdeg = instance.outDegree("B");
        assertTrue("degree should be 1", outdeg==1);
         
        outdeg = instance.outDegree("E");
        assertTrue("degree should be 2", outdeg==2);    
    }

    /**
     * Test of inDegree method, of class Graph.
     */
    @Test
    public void testInDegree() {
        System.out.println("Test inDegree");
        
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		       
        int indeg = instance.inDegree("G");    
        assertTrue("in degree should be -1", indeg==-1);
        
        indeg = instance.inDegree("A");
        assertTrue("in degree should be 1", indeg==1);
        
        indeg = instance.inDegree("D");
        assertTrue("in degree should be 3", indeg==3);
         
        indeg = instance.inDegree("E");
        assertTrue("in degree should be 2", indeg==2);  
    }

    /**
     * Test of outgoingEdges method, of class Graph.
     */
    @Test
    public void testOutgoingEdges() {
        System.out.println(" Test outgoingEdges");
        		
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		                        
        Iterator<Edge<String,String>> itEdge = instance.outgoingEdges("C").iterator();
        Edge<String,String> first = itEdge.next();
        Edge<String,String> second = itEdge.next();
        assertTrue("Outgoing Edges of vert C should be Edge4 and Edge5",
                  ( (first.getElement().equals("Edge4")==true && second.getElement().equals("Edge5")==true) ||
                    (first.getElement().equals("Edge5")==true && second.getElement().equals("Edge4")==true) )); 
        
        instance.removeEdge("E","E");
        
        itEdge = instance.outgoingEdges("E").iterator();
        assertTrue("first edge should be Edge7", (itEdge.next().getElement().equals("Edge7")==true));
        
        instance.removeEdge("E","D");

        itEdge = instance.outgoingEdges("E").iterator();
        assertTrue("edges should be empty", (itEdge.hasNext()==false));
    }
    
    /**
     * Test of outgoingEdges method, of class Graph.
     */
    @Test
    public void testOutgoingEdges2(){
        System.out.println(" Test outgoingEdges");
        		
        instance.insertVertex("A");
        instance.insertVertex("B");
        
        ArrayList<Edge> expResult = null;
        Iterable<Edge<String, String>> result = instance.outgoingEdges("NON_EXISTENT");
        assertEquals(expResult, result);
    }
    
    /**
     * Test of incomingEdges method, of class Graph.
     */
    @Test
    public void testIncomingEdges2(){
        System.out.println(" Test incomingEdges");
        		
        instance.insertVertex("A");
        instance.insertVertex("B");
        
        ArrayList<Edge> expResult = null;
        Iterable<Edge<String, String>> result = instance.incomingEdges("NON_EXISTENT");
        assertEquals(expResult, result);
    }
    /**
     * Test of incomingEdges method, of class Graph.
     */
    @Test
    public void testIncomingEdges() {
        		
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
        
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		      
        Iterator<Edge<String,String>> itEdge = instance.incomingEdges("D").iterator();
        
        assertTrue("first edge should be edge3", (itEdge.next().getElement().equals("Edge3")==true));
        assertTrue("second edge should be edge4", (itEdge.next().getElement().equals("Edge4")==true));
        assertTrue("third edge should be edge7", (itEdge.next().getElement().equals("Edge7")==true));
        
        itEdge = instance.incomingEdges("E").iterator();
        
        assertTrue("first edge should be Edge5", (itEdge.next().getElement().equals("Edge5")==true));
        assertTrue("second edge should be Edge8", (itEdge.next().getElement().equals("Edge8")==true));
        
        instance.removeEdge("E","E");
        
        itEdge = instance.incomingEdges("E").iterator();
        
        assertTrue("first edge should be Edge5", (itEdge.next().getElement().equals("Edge5")==true));
        
        instance.removeEdge("C","E");

        itEdge = instance.incomingEdges("E").iterator();
        assertTrue("edges should be empty", (itEdge.hasNext()==false));
    }

    /**
     * Test of insertVertex method, of class Graph.
     */
    @Test
    public void testInsertVertex() {
        System.out.println("Test insertVertex");
        
        instance.insertVertex("A");   
        instance.insertVertex("B");    
        instance.insertVertex("C");    
        instance.insertVertex("D");      
        instance.insertVertex("E");
             
        Iterator <String> itVert = instance.vertices().iterator();
		
        assertTrue("first vertex should be A", (itVert.next().equals("A")==true));
        assertTrue("second vertex should be B",(itVert.next().equals("B")==true));
        assertTrue("third vertex should be C", (itVert.next().equals("C")==true));
        assertTrue("fourth vertex should be D",(itVert.next().equals("D")==true));
        assertTrue("fifth vertex should be E", (itVert.next().equals("E")==true));
    }
    
    /**
     * Test of insertVertex method, of class Graph.
     */
    @Test
    public void testInsertVertex2() {
        System.out.println("Test insertVertex");
        
        instance.insertVertex("A");   
        instance.insertVertex("B");    
        instance.insertVertex("C");    
        instance.insertVertex("D");
        
        boolean expResult = false;
        boolean result = instance.insertVertex("A");
        //false because the vertex "A" already exists
        assertEquals(expResult, result);
        
        boolean expResult2 = true;
        boolean result2 = instance.insertVertex("NEW_VERTEX");
        assertEquals(expResult2, result2);
    }    
    
    /**
     * Test of insertEdge method, of class Graph.
     */
    @Test
    public void testInsertEdge() {
        System.out.println("Test insertEdge");
        
        assertTrue("num. edges should be zero", (instance.numEdges()==0));

        instance.insertEdge("A","B","Edge1",6);
        assertTrue("num. edges should be 1", (instance.numEdges()==1));      
        
        instance.insertEdge("A","C","Edge2",1);
        assertTrue("num. edges should be 2", (instance.numEdges()==2));
        
        instance.insertEdge("B","D","Edge3",3);
        assertTrue("num. edges should be 3", (instance.numEdges()==3));
        
        instance.insertEdge("C","D","Edge4",4);
        assertTrue("num. edges should be 4", (instance.numEdges()==4));
        
        instance.insertEdge("C","E","Edge5",1);
        assertTrue("num. edges should be 5", (instance.numEdges()==5));
        
        instance.insertEdge("D","A","Edge6",2);
        assertTrue("num. edges should be 6", (instance.numEdges()==6));
        
        instance.insertEdge("E","D","Edge7",1);
        assertTrue("num. edges should be 7", (instance.numEdges()==7));
        
        instance.insertEdge("E","E","Edge8",1);
        assertTrue("num. edges should be 8", (instance.numEdges()==8));
        
        Iterator <Edge<String,String>> itEd = instance.edges().iterator();
		
        itEd.next(); itEd.next();
        assertTrue("third edge should be Edge3", (itEd.next().getElement().equals("Edge3")==true));
        itEd.next(); itEd.next();
        assertTrue("sixth edge should be Edge6",(itEd.next().getElement().equals("Edge6")==true));
    }
    
    /**
     * Test of insertEdge method, of class Graph.
     */
    @Test
    public void testInsertEdge2() {
        System.out.println("Test insertEdge");
        
        instance.insertVertex("1");
        instance.insertVertex("2");
        instance.insertVertex("3");
        instance.insertVertex("4");
        instance.insertVertex("5");
        instance.insertVertex("6");
        instance.insertVertex("7");
        
        boolean expResult = true;
        //true because its a new edge
        boolean result = instance.insertEdge("1", "2", "edge_test1", 1);
        assertEquals(expResult, result);
        
        boolean expResult2 = false;
        //false because this edge already exists
        boolean result2 = instance.insertEdge("1", "2", "edge_test2", 1);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of removeVertex method, of class Graph.
     */
    @Test
    public void testRemoveVertex() {       
        System.out.println("Test removeVertex");
        
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
 
        instance.removeVertex("C");
        assertTrue("Num vertices should be 4", (instance.numVertices()==4));
      
        Iterator<String> itVert = instance.vertices().iterator();
        assertTrue("first vertex should be A", (itVert.next().equals("A")==true));
        assertTrue("second vertex should be B",(itVert.next().equals("B")==true));
        assertTrue("third vertex should be D", (itVert.next().equals("D")==true));
        assertTrue("fourth vertex should be E",(itVert.next().equals("E")==true));
        
        instance.removeVertex("A");
        assertTrue("Num vertices should be 3", (instance.numVertices()==3));
   
        itVert = instance.vertices().iterator();
        assertTrue("first vertex should be B", (itVert.next().equals("B")==true));
        assertTrue("second vertex should be D",(itVert.next().equals("D")==true));
        assertTrue("third vertex should be E", (itVert.next().equals("E")==true));

        instance.removeVertex("E");
        assertTrue("Num vertices should be 2", (instance.numVertices()==2));

        itVert = instance.vertices().iterator();

        assertTrue("first vertex should be B",itVert.next().equals("B")==true);
        assertTrue("second vertex should be D",itVert.next().equals("D")==true);
        
        instance.removeVertex("B"); instance.removeVertex("D");
        assertTrue("Num vertices should be 4", (instance.numVertices()==0));
    }
    
    /**
     * Test of removeVertex method, of class Graph.
     */
    @Test
    public void testRemoveVertex2() {       
        System.out.println("Test removeVertex");
        instance.insertVertex("A");
        instance.insertVertex("B");
        instance.insertVertex("C");
        instance.insertVertex("D");
        instance.insertVertex("E");
        
        boolean expResult = false;
        boolean result = instance.removeVertex("This vertex doesn't exist");
        assertEquals(expResult, result);
        assertEquals(instance.numVertices(), 5);
        
        boolean expResult2 = true;
        boolean result2 = instance.removeVertex("D");
        assertEquals(expResult2, result2);
        assertEquals(instance.numVertices(), 4);
        assertEquals(instance.getKey("E"), 3);  //key was 4 but with removal of "D", its now 3
        
    }
    
    /**
     * Test of removeEdge method, of class Graph.
     */
    @Test
    public void testRemoveEdgeStandard() {     
        System.out.println("Test removeEdge");
        
        assertTrue("num. edges should be zero", (instance.numEdges()==0));

        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
		     
        assertTrue("Num. edges should be 8", (instance.numEdges()==8));
        
        instance.removeEdge("E","E");
        assertTrue("Num. edges should be 7", (instance.numEdges()==7));
        
        Iterator <Edge<String,String>> itEd = instance.edges().iterator();
		
        itEd.next(); itEd.next();
        assertTrue("third edge should be Edge3", (itEd.next().getElement().equals("Edge3")==true));
        itEd.next(); itEd.next(); 
        assertTrue("sixth edge should be Edge6", (itEd.next().getElement().equals("Edge6")==true));
        
        instance.removeEdge("C","D");
        assertTrue("Num. edges should be 6", (instance.numEdges()==6));
        
        itEd = instance.edges().iterator();	
        itEd.next(); itEd.next();
        assertTrue("third edge should be Edge3", (itEd.next().getElement().equals("Edge3")==true));
        assertTrue("fourth edge should be Edge5", (itEd.next().getElement().equals("Edge5")==true));
        assertTrue("fifth edge should be Edge6", (itEd.next().getElement().equals("Edge6")==true));
        assertTrue("...last edge should be Edge7", (itEd.next().getElement().equals("Edge7")==true));
    }
    
    /**
     * Test of removeEdge method, of class Graph.
     */
    @Test
    public void testRemoveEdge() {     
        System.out.println("Test removeEdge");
        instance.insertEdge("A","B","Edge1",1);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","C","Edge3",1);
        instance.insertEdge("C","D","Edge4",4);
        
        boolean expResult = false;
        boolean result = instance.removeEdge("A", "NON_EXISTENT");
        boolean expResult2 = false;
        boolean result2 = instance.removeEdge("NON_EXISTENT", "B");
        boolean expResult3 = false;
        boolean result3 = instance.removeEdge("NON_EXISTENT", "NON_EXISTENT");
        boolean expResult4 = true;
        boolean result4 = instance.removeEdge("C", "D");
        
        Edge<String, String> expResult5 = instance.getEdge("4", "6");
        //test for the if condition where null is returned if edge is null
        
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
        assertEquals(expResult4, result4);
        assertEquals(expResult5, null);
        
    }
    
    /**
     * Test of clone method, of class Graph.
     */
    @Test
    public void testClone() {
	System.out.println("Test Clone");
         
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
        
        Graph<String,String> instClone = instance.clone();
	
        assertTrue("number of vertices should be equal", instance.numVertices()==instClone.numVertices());
        assertTrue("number of edges should be equal", instance.numEdges()==instClone.numEdges());
	
        //vertices should be equal
        Iterator<String> itvertClone = instClone.vertices().iterator();
        Iterator<String> itvertSource = instance.vertices().iterator();
	while (itvertSource.hasNext())
            assertTrue("vertices should be equal ",(itvertSource.next().equals(itvertClone.next())==true));
    }
    
    /**
     * Test of equals method, of class Graph.
     */
    @Test
    public void testEquals() {
        System.out.println("Test Equals");
              
        instance.insertEdge("A","B","Edge1",6);
        instance.insertEdge("A","C","Edge2",1);
        instance.insertEdge("B","D","Edge3",3);
        instance.insertEdge("C","D","Edge4",4);
        instance.insertEdge("C","E","Edge5",1);
        instance.insertEdge("D","A","Edge6",2);
        instance.insertEdge("E","D","Edge7",1);
        instance.insertEdge("E","E","Edge8",1);
        
        assertFalse("should not be equal to null", instance == null);
		
	assertTrue("should be equal to itself", instance.equals(instance));
		
	assertTrue("should be equal to a clone", instance.equals(instance.clone()));
        
        Graph<String,String> other = instance.clone();
       
        other.removeEdge("E","E");
        assertFalse("instance should not be equal to other", instance.equals(other));
        
        other.insertEdge("E","E","Edge8",1);
        assertTrue("instance should be equal to other", instance.equals(other));
        
        other.removeVertex("D");
        assertFalse("instance should not be equal to other", instance.equals(other));
        
    }
    
    /**
     * Test of equals method, of class Graph.
     */
    @Test
    public void testEqualsBooleanCondition() {
        instance.insertVertex("Han");
        instance.insertVertex("Han");
        
        Graph<String,String> instance2 = instance.clone();
        
        boolean expResult = true;
        boolean result = instance.equals(instance2);
        assertEquals(expResult, result);
        
        instance2.insertVertex("Solo");
        instance2.insertVertex("Leia");
        instance2.insertVertex("C3-PO");
        instance2.insertVertex("BB-8");
        instance2.insertVertex("R2-D2");
        
        boolean expResult2 = false;
        boolean result2 = instance2.equals(instance);
        assertEquals(expResult2, result2);
    }
    
    /**
     * Test of equals method, of class Graph. (for different classes)
     */
    @Test
    public void testEqualsDifferentClasses() {
        instance.insertVertex("Wingardium");
        instance.insertVertex("Leviosa");
        
        GeographicalLocation g = new GeographicalLocation(-34.6131500, -58.3772300, 3.1);
        Park parkTest = new Park("parkReference", "Park Campus Sao Joao", 1, 220, 16, g);
        
        boolean expResult = false;
        boolean result = instance.equals(parkTest);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of hashCode method, of class Graph.
     */
    @Test
    public void testHashCode(){
        int expResult = 4563365;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of toString method, of class Graph.
     */
    @Test
    public void testToString() {
        String expResult = "\nGraph not defined!!";
        String result = instance.toString();
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test of toString method, of class Graph.
     */
    @Test
    public void testToString2() {
        
        instance.insertVertex("Fate");
        instance.insertVertex("finds a way.");
        instance.insertEdge("Fate", "finds a way.", "Always.", 94);
        
        String expResult = "Graph: 2 vertices, 1 edges\n";
        String result = instance.toString();

        assertEquals(expResult, result);
    }

    /**
     * Test of validVertex method, of class Graph.
     */
    @Test
    public void testValidVertex() {
        System.out.println("validVertex");
        String vert = null;
        instance.insertVertex(vert);
        
        boolean expResult = false;
        boolean result = instance.validVertex("");
        assertEquals(expResult, result);
    }
    
    /**
     * Test of validVertex method, of class Graph.
     */
    @Test
    public void testValidVertex2() {
        System.out.println("validVertex2");
        String vert = "Hateful of the Horde";
        instance.insertVertex(vert);
        
        boolean expResult = true;
        boolean result = instance.validVertex(vert);
        assertEquals(expResult, result);
    }

    /**
     * Test of getKey method, of class Graph.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        String v1 = "Lok'tar";
        String v2 = "Ogar";
        String v3 = "For the Horde!";
        instance.insertVertex(v1);
        instance.insertVertex(v2);
        instance.insertVertex("expectedKey: 2");
        instance.insertVertex("expectedKey: 3");
        instance.insertVertex("expectedKey: 4");
        instance.insertVertex("expectedKey: 5");
        instance.insertVertex("expectedKey: 6");
        instance.insertVertex("expectedKey: 7");
        instance.insertVertex("expectedKey: 8");
        instance.insertVertex(v3);
        
        int expResult = 0;
        int result = instance.getKey(v1);
        assertEquals(expResult, result);
        
        int expResult2 = 1;
        int result2 = instance.getKey(v2);
        assertEquals(expResult2, result2);
        
        int expResult3 = 9;
        int result3 = instance.getKey(v3);
        assertEquals(expResult3, result3);
    }

    /**
     * Test of allkeyVerts method, of class Graph.
     */
    @Test
    public void testAllkeyVerts() {
        System.out.println("allkeyVerts");
        
        String[] expResult = (String[]) Array.newInstance(String.class, 3);
        expResult[0] = "2012";
        expResult[1] = "Mito";
        expResult[2] = "THIRD_VERTEX";
        
        instance.insertVertex("2012");
        instance.insertVertex("Mito");
        instance.insertVertex("THIRD_VERTEX");
        
        instance.insertEdge("2012", "Mito", ".", 1);
        instance.insertEdge("2012", "THIRD_VERTEX", ".", 1);
        instance.insertEdge("Mito", "THIRD_VERTEX", ".", 1);
        
        String[] result = instance.allkeyVerts();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of adjVertices method, of class Graph. (vertex non existent)
     */
    @Test
    public void testAdjVertices() {
        System.out.println("adjVertices");
        Iterable expResult = null;
        Iterable result = instance.adjVertices("Vertex_Not_Found");
        assertEquals(expResult, result);
    }

    /**
     * Test of isDirected method, of class Graph.
     */
    @Test
    public void testIsDirected() {
        System.out.println("isDirected");
        
        boolean expResult = true;
        boolean result = instance.isDirected();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isDirected method, of class Graph.
     */
    @Test
    public void testIsDirected2() {
        System.out.println("isDirected2");
        Graph<String, String> instance2 = new Graph<>(false) ;
        
        boolean expResult = false;
        boolean result = instance2.isDirected();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDirected method, of class Graph.
     */
    @Test
    public void testSetDirected() {
        System.out.println("setDirected");
        boolean expResult = false;
        instance.setDirected(expResult);
        boolean result = instance.isDirected();
        
        assertEquals(expResult, result);
        
        boolean expResult2 = true;
        instance.setDirected(expResult2);
        boolean result2 = instance.isDirected();
        
        assertEquals(expResult2, result2);
    }
    
}
