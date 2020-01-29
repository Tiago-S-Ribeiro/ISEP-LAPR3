/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author G25
 * @param <V>
 * @param <E>
 */
public class Graph<V, E> implements GraphInterface<V, E> {

    private int numVert;
    private int numEdge;
    private boolean isDirected;
    private Map<V, Vertex<V, E>> vertices;  //all Vertices of the graph 

    /**
     * constrói um grafo vazio (direcionado ou nao-direcionado)
     *
     * @param directed (false se nao for direcionado, true se for)
     */
    public Graph(boolean directed) {
        numVert = 0;
        numEdge = 0;
        isDirected = directed;
        vertices = new LinkedHashMap<>();
    }

    @Override
    public int numVertices() {
        return numVert;
    }

    @Override
    public Iterable<V> vertices() {
        return vertices.keySet();
    }

    public boolean validVertex(V vert) {

        return vertices.get(vert) != null;
    }

    public int getKey(V vert) {
        return vertices.get(vert).getKey();
    }

    public Map<V, Vertex<V, E>> getMap() {
        return vertices;
    }

    public V[] allkeyVerts() throws NullPointerException {

        V vertElem = null;
        for (Vertex<V, E> vert : vertices.values()) {
            vertElem = vert.getElement();            // To get type
        }
        V[] keyverts = (V[]) Array.newInstance(vertElem.getClass(), numVert);

        for (Vertex<V, E> vert : vertices.values()) {
            keyverts[vert.getKey()] = vert.getElement();
        }

        return keyverts;
    }

    public Iterable<V> adjVertices(V vert) {

        if (!validVertex(vert)) {
            return null;
        }

        Vertex<V, E> vertex = vertices.get(vert);

        return vertex.getAllAdjVerts();
    }

    @Override
    public int numEdges() {
        return numEdge;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void setDirected(boolean orientation) {
        this.isDirected = orientation;
    }

    @Override
    public Iterable<Edge<V, E>> edges() {

        ArrayList<Edge<V, E>> edges = new ArrayList<>();

        for (Map.Entry<V, Vertex<V, E>> entry : vertices.entrySet()) {
            Iterable<Edge<V, E>> eas = entry.getValue().getAllOutEdges();

            for (Edge<V, E> ed : eas) {
                edges.add(ed);
            }
        }

        return edges;
    }

    @Override
    public Edge<V, E> getEdge(V vOrig, V vDest) {

        if (!validVertex(vOrig) || !validVertex(vDest)) {
            return null;
        }

        Vertex<V, E> vorig = vertices.get(vOrig);

        return vorig.getEdge(vDest);
    }

    @Override
    public V[] endVertices(Edge<V, E> edge) {

        if (edge == null) {
            return null;
        }

        if (!validVertex(edge.getVOrig()) || !validVertex(edge.getVDest())) {
            return null;
        }

        Vertex<V, E> vorig = vertices.get(edge.getVOrig());

        if (!edge.equals(vorig.getEdge(edge.getVDest()))) {
            return null;
        }

        return edge.getEndpoints();
    }

    @Override
    public V opposite(V vert, Edge<V, E> edge) {

        if (!validVertex(vert)) {
            return null;
        }

        Vertex<V, E> vertex = vertices.get(vert);

        return vertex.getAdjVert(edge);
    }

    @Override
    public int outDegree(V vert) {

        if (!validVertex(vert)) {
            return -1;
        }

        Vertex<V, E> vertex = vertices.get(vert);

        return vertex.numAdjVerts();
    }

    @Override
    public int inDegree(V vert) {

        if (!validVertex(vert)) {
            return -1;
        }

        int degree = 0;
        for (V otherVert : vertices.keySet()) {
            if (getEdge(otherVert, vert) != null) {
                degree++;
            }
        }

        return degree;
    }

    @Override
    public Iterable<Edge<V, E>> outgoingEdges(V vert) {

        if (!validVertex(vert)) {
            return null;
        }

        Vertex<V, E> vertex = vertices.get(vert);

        return vertex.getAllOutEdges();
    }

    @Override
    public Iterable<Edge<V, E>> incomingEdges(V vert) {
        if (!validVertex(vert)) {
            return null;
        }

        ArrayList<Edge<V, E>> retorno = new ArrayList<>();

        Iterable<Edge<V, E>> outEdges = edges();
        for (Edge<V, E> edge : outEdges) {
            if (edge.getVDest().equals(vert)) {
                retorno.add(edge);
            }
        }

        return retorno;
    }

    @Override
    public boolean insertVertex(V vert) {
        //verifica se o vertice ja existe, e se nao existir, vai criar um vertex passando a informacao
        // e depois incrementa o numero de vertices do grafo
        if (validVertex(vert)) {
            return false;
        }

        Vertex<V, E> vertex = new Vertex<>(numVert, vert);
        vertices.put(vert, vertex);
        numVert++;

        return true;
    }

    @Override
    public boolean insertEdge(V vOrig, V vDest, E eInf, double eWeight) {
        //valida a informacao relativa aos 2 vertices. se nao existirem, ele cria
        //cria um ramo entre 2 vertices e adiciona o ramo
        if (getEdge(vOrig, vDest) != null) {
            return false;
        }

        if (!validVertex(vOrig)) {
            insertVertex(vOrig);
        }

        if (!validVertex(vDest)) {
            insertVertex(vDest);
        }

        Vertex<V, E> vorig = vertices.get(vOrig);
        Vertex<V, E> vdest = vertices.get(vDest);

        Edge<V, E> newEdge = new Edge<>(eInf, eWeight, vorig, vdest);
        vorig.addAdjVert(vDest, newEdge);
        // e incrementa o nr de ramos do grafo
        numEdge++;

        //if graph is not direct insert other edge in the opposite direction 
        
        if (!isDirected && getEdge(vDest, vOrig) == null) {     // if vDest different vOrig
            Edge<V, E> otherEdge = new Edge<>(eInf, eWeight, vdest, vorig);
            vdest.addAdjVert(vOrig, otherEdge);
            numEdge++;
        }

        return true;
    }

    @Override
    public boolean removeVertex(V vert) {

        if (!validVertex(vert)) {
            return false;
        }

        //remove all edges that point to vert
        for (Edge<V, E> edge : incomingEdges(vert)) {
            V vadj = edge.getVOrig();
            removeEdge(vadj, vert);
        }

        Vertex<V, E> vertex = vertices.get(vert);

        //update the keys of subsequent vertices in the map
        for (Vertex<V, E> v : vertices.values()) {
            int keyVert = v.getKey();
            if (keyVert > vertex.getKey()) {
                keyVert = keyVert - 1;
                v.setKey(keyVert);
            }
        }
        //The edges that live from vert are removed with the vertex    
        vertices.remove(vert);

        numVert--;

        return true;
    }

    @Override
    public boolean removeEdge(V vOrig, V vDest) {

        if (!validVertex(vOrig) || !validVertex(vDest)) {
            return false;
        }

        Edge<V, E> edge = getEdge(vOrig, vDest);

        if (edge == null) {
            return false;
        }

        Vertex<V, E> vorig = vertices.get(vOrig);

        vorig.remAdjVert(vDest);
        numEdge--;

        //if graph is not direct 
        if (!isDirected) {
            edge = getEdge(vDest, vOrig);
            if (edge != null) {
                Vertex<V, E> vdest = vertices.get(vDest);
                vdest.remAdjVert(vOrig);
                numEdge--;
            }
        }
        return true;
    }

    /**
     * Returns a clone of the graph
     *
     * @return
     */
    @Override
    public Graph<V, E> clone() {

        Graph<V, E> newObject = new Graph<>(this.isDirected);

        //insert all vertices
        for (V vert : vertices.keySet()) {
            newObject.insertVertex(vert);
        }

        //insert all edges
        for (V vert1 : vertices.keySet()) {
            for (Edge<V, E> e : this.outgoingEdges(vert1)) {
                if (e != null) {
                    V vert2 = this.opposite(vert1, e);
                    newObject.insertEdge(vert1, vert2, e.getElement(), e.getWeight());
                }
            }
        }

        return newObject;
    }

    /* equals implementation
     * @param the other graph to test for equality
     * @return true if both objects represent the same graph
     */
    @Override
    public boolean equals(Object otherObj) {

        if (this == otherObj) {
            return true;
        }

        if (otherObj == null || this.getClass() != otherObj.getClass()) {
            return false;
        }

        Graph<V, E> otherGraph = (Graph<V, E>) otherObj;

        if (numVert != otherGraph.numVertices() || numEdge != otherGraph.numEdges()) {
            return false;
        }

        //graph must have same vertices
        boolean eqvertex;
        for (V v1 : this.vertices()) {
            eqvertex = false;
            for (V v2 : otherGraph.vertices()) {
                if (v1.equals(v2)) {
                    eqvertex = true;
                }
            }

            if (!eqvertex) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.numVert;
        hash = 97 * hash + this.numEdge;
        hash = 97 * hash + Objects.hashCode(this.vertices);
        return hash;
    }

    /**
     * string representation
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "";
        if (numVert == 0) {
            s = "\nGraph not defined!!";
        } else {
            s = "Graph: " + numVert + " vertices, " + numEdge + " edges\n";
        }
        return s;
    }

}
