/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Bernardo Carvalho
 */
public class AdjacencyMatrix {
    
    public static final int INITIAL_CAPACITY = 10;
    public static final float RESIZE_FACTOR = 1.5F;
    
    int numVertices;
    int numEdges;
    ArrayList<PointInterest> vertices;
    public Double[][] edgeMatrix;
    
    Double privateGet(int x, int y){
	return edgeMatrix[x][y];
    }
    
    void privateSet(int x, int y, Double e){
	edgeMatrix[x][y] = e;
    }
    
    int toIndex(PointInterest vertex) {
	return vertices.indexOf(vertex);
    }
    
    private void resizeMatrix() {
	if(edgeMatrix.length < numVertices){
            int newSize = (int) (edgeMatrix.length * RESIZE_FACTOR);

            @SuppressWarnings("unchecked")
            Double[][] temp = (Double[][]) new Object [newSize][newSize];
            for (int i = 0; i < edgeMatrix.length; i++)
                temp[i] = Arrays.copyOf(edgeMatrix[i], newSize);
	    
            edgeMatrix = temp;
	}
    }

    /**
    * Constructs an empty graph.
    */
    public AdjacencyMatrix() {
	this(INITIAL_CAPACITY);
    }

    /**
    * Constructs a graph with an initial capacity.
     * @param initialSize
    */
    @SuppressWarnings("unchecked")
    public AdjacencyMatrix(int initialSize) {
	vertices = new ArrayList<PointInterest>(initialSize);
		
	edgeMatrix = new Double[initialSize][initialSize];
    }

    /**
    * Returns the number of vertices in the graph
    * @return number of vertices of the graph
    */
    public int numVertices() {
	return numVertices;
    }

    /**
    * Returns the number of edges in the graph
    * @return number of edges of the graph
    */
    public int numEdges() {
	return numEdges;
    }

    /**
    * Returns the actual vertices of the graph
    * @return an iterable collection of vertices
    */
    @SuppressWarnings("unchecked")
    public Iterable<PointInterest> vertices() {
	return (Iterable<PointInterest>) vertices.clone();
    }

    /**
     * Returns the actual edges of the graph
     * @return an iterable collection of all edges
     */
    public Iterable<Double> edges() {
        ArrayList<Double> edges = new ArrayList<Double>();

        // graph is undirected, so only return a single copy of edge
        // graph could actually only keep one copy of the edge but algorithms
        // would then need to consider that case.

        for (int i = 0; i < numVertices - 1; i++)
            for (int j = i + 1; j < numVertices; j++)
                if (edgeMatrix[i][j] != null)
                        edges.add(edgeMatrix[i][j]);

        return edges;
    }

    /**
     * Returns the edge between two vertices
     * @param vertexA
     * @param vertexB  
     * @return the edge or null if source and dest are not adjacent or do not
     *         exist in the graph.
     */
    public Double getEdge(PointInterest vertexA, PointInterest vertexB) {
        int indexA = toIndex(vertexA);
        if (indexA == -1)
                return null;

        int indexB = toIndex(vertexB);
        if (indexB == -1)
                return null;

        return edgeMatrix[indexA][indexB];
    }

    /**
     * Inserts a new vertex with the given element.
     * fails if vertex already exists
     * @param newVertex (vertex contents)
     * @return false if vertex exists in the graph
     */
    public boolean insertVertex(PointInterest newVertex) {
        int index = toIndex(newVertex);
        if (index != -1)
            return false;

        vertices.add(newVertex);
        numVertices++;
        resizeMatrix();
        return true;
    }

    /**
     * Inserts a new edge between two vertices. 
     * Package level method is for use of algorithms class 
     * @param indexA 
     * @param indexB 
     * @param newEdge  
     * @return false if vertices are not in the graph or are the same vertex 
     *         or an edge already exists between the two.
     */
    void insertEdge(int indexA, int indexB, Double newEdge){
        if (edgeMatrix[indexA][indexB] == null){
           edgeMatrix[indexA][indexB] = edgeMatrix[indexB][indexA] = newEdge; // undirected graph
           numEdges++;
        }
    }
	
    public boolean insertEdge(PointInterest vertexA, PointInterest vertexB, Double newEdge) {

        if (vertexA.equals(vertexB))
            return false;

        int indexA = toIndex(vertexA);
        if (indexA == -1)
            return false;

        int indexB = toIndex(vertexB);
        if (indexB == -1)
            return false;

        if (edgeMatrix[indexA][indexB] != null)
            return false;

        insertEdge(indexA, indexB, newEdge);

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.numVertices;
        hash = 53 * hash + this.numEdges;
        hash = 53 * hash + Objects.hashCode(this.vertices);
        hash = 53 * hash + Arrays.deepHashCode(this.edgeMatrix);
        return hash;
    }

    /**
     * Implementation of equals
     * @param oth
     * @return true if both objects represent the same graph
     */
    @Override
    public boolean equals(Object oth) {

        if(oth == null) return false;

        if(this == oth) return true;

        if (!(oth instanceof AdjacencyMatrix))
                return false;

        AdjacencyMatrix other = (AdjacencyMatrix) oth;

        if(numVertices != other.numVertices || numEdges != other.numEdges) return false;

        if(!vertices.equals(other.vertices)) return false;

        if(!Arrays.deepEquals(edgeMatrix,other.edgeMatrix))
                                return false;

        return true;
    }
    
}
