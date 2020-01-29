/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author G25
 */
public class GraphAlgorithms {
    
    public GraphAlgorithms(){
        //SonarQube codeSmell: Add a private constructor to hide the implicit public one.
    }
    /**
     * Computes shortest-path distance from a source vertex to all reachable vertices of a graph g with nonnegative edge weights This implementation uses Dijkstra's algorithm
     *
     * @param g Graph instance
     * @param vOrig Vertex that will be the source of the path
     * @param vertices
     * @param visited set of discovered vertices
     * @param pathKeys minimum path vertices keys
     * @param dist minimum distances
     */
    protected static <V, E> void shortestPathLength(Graph<V, E> g, V vOrig, V[] vertices, boolean[] visited, int[] pathKeys, double[] dist) {

        int oKey = g.getKey(vOrig);
        dist[oKey] = 0;

        while (oKey != -1) {
            visited[g.getKey(vOrig)] = true;

            for (Edge<V, E> e : g.outgoingEdges(vOrig)) {
                V vert = e.getVDest() != vOrig ? e.getVDest() : e.getVOrig();
                if (!visited[g.getKey(vert)] && dist[g.getKey(vert)] > dist[g.getKey(vOrig)] + e.getWeight()) {
                    dist[g.getKey(vert)] = dist[g.getKey(vOrig)] + e.getWeight();
                    pathKeys[g.getKey(vert)] = g.getKey(vOrig);
                }
            }

            Double min = Double.MAX_VALUE;
            oKey = -1;
            for (int i = 0; i < g.numVertices(); i++) {
                if (!visited[i] && dist[i] < min) {
                    min = dist[i];
                    oKey = i;
                }
            }
            for (V v : g.vertices()) {
                if (g.getKey(v) == oKey) {
                    vOrig = v;
                    break;
                }
            }
        }
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf 
     * The path is constructed from the end to the beginning
     *
     * @param g Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @param verts
     * @param pathKeys minimum path vertices keys
     * @param path stack with the minimum path (correct order)
     */
    protected static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest, V[] verts, int[] pathKeys, LinkedList<V> path) {
        int index = g.getKey(vDest);
        path.add(vDest);
        while (index != g.getKey(vOrig)) {
            if (pathKeys[index] == -1) {
                path.clear();
                break;
            }
            path.addFirst(verts[pathKeys[index]]);
            index = pathKeys[index];
        }
    }

    /**
     * shortest-path between vOrig and vDest
     *
     * @param <V>
     * @param <E>
     * @param g
     * @param vOrig
     * @param vDest
     * @param shortPath
     * @return
     */
    public static <V, E> double shortestPath(Graph<V, E> g, V vOrig, V vDest, LinkedList<V> shortPath) {
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return 0;
        }

        V[] vertices = (V[]) new Object[g.numVertices()];
        int[] pathKeys = new int[g.numVertices()];
        double[] dist = new double[g.numVertices()];
        boolean[] visited = new boolean[g.numVertices()];

        for (int i = 0; i < g.numVertices(); i++) {
            visited[i] = false;
            vertices[i] = null;
            dist[i] = Double.MAX_VALUE;
        }

        for (V v : g.vertices()) {
            vertices[g.getKey(v)] = v;
        }

        shortestPathLength(g, vOrig, vertices, visited, pathKeys, dist);
        shortPath.clear();

        if (!visited[g.getKey(vDest)]) {
            return 0;
        }

        getPath(g, vOrig, vDest, vertices, pathKeys, shortPath);
        return dist[g.getKey(vDest)];
    }

    /**
     * shortest-path between vOrig and all other
     *
     * @param <V>
     * @param <E>
     * @param g
     * @param vOrig
     * @param paths
     * @param dists
     * @return
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig, List<LinkedList<V>> paths, List<Double> dists) {

        if (!g.validVertex(vOrig)) {
            return false;
        }

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts]; //default value: false
        int[] pathKeys = new int[nverts];
        double[] dist = new double[nverts];
        V[] vertices = g.allkeyVerts();

        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }

        shortestPathLength(g, vOrig, vertices, visited, pathKeys, dist);

        dists.clear();
        paths.clear();
        for (int i = 0; i < nverts; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (int i = 0; i < nverts; i++) {
            LinkedList<V> shortPath = new LinkedList<>();
            if (dist[i] != Double.MAX_VALUE) {
                getPath(g, vOrig, vertices[i], vertices, pathKeys, shortPath);
            }
            paths.set(i, shortPath);
            dists.set(i, dist[i]);
        }
        return true;
    }
    
}
