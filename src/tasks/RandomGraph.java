package tasks;

import graph.Edge;
import graph.Graph;
import graph.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomGraph {
    
    public static void main (String[] args) {
        Graph G = getRandomGraph(50, false);
        System.out.println(G);
    }

    
    public static Graph getRandomGraph(int number_of_vertices, boolean directed) {
        //initialize nodes
        int n = number_of_vertices;
        Graph G = new Graph(directed);
        for (int i = 0; i < n; i++) {
            G.add_vertex(Integer.toString(i));
        }
        
        //choose number of edges
        Random rand = new Random();
        int number_of_edges;
        if (directed) {
            number_of_edges = rand.nextInt((n * (n-1)) + 1);
        } else {
            number_of_edges = rand.nextInt(((n * (n - 1)) / 2) + 1);
        }

        //generate list of all possible edges
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    edges.add(new Edge(G.getNode(Integer.toString(i)), G.getNode(Integer.toString(j)), "", 1.0));
                }
            }
        }
        Collections.shuffle(edges);
        
        //add edges to Graph
        boolean has_triangle = false;
        boolean has_cycle = false;
        for (int i = 0; i < number_of_edges; i++) {
            Edge next_edge = edges.get(i);
            if (!has_triangle) {
                if (testTriangle(next_edge)) {
                    System.out.println("Found triangle! p = " + getConnectionProbability(n, i + 1, directed));
                    has_triangle = true;
                }
            }
            
            if (!has_cycle) {
                if (testCycle(next_edge.start_node, next_edge.target_node, 5)) {
                    System.out.println("Found cycle! p = " + getConnectionProbability(n, i + 1, directed));
                    has_cycle = true;
                }
            }
            G.add_edge(next_edge);
        }
        
        return G;
    }
    
    
    private static double getConnectionProbability(int num_nodes, int num_edges, boolean directed) {
        if (directed) {
            return ((double) num_edges) / ((num_nodes * (num_nodes - 1)));
        } else {
            return num_edges / ((num_nodes * (num_nodes - 1)) / 2.0);
        }
    } 
    
    //Tests if connecting start_node and end_node in a Graph results in a Triangle
    private static boolean testTriangle(Edge edge) {
        for (Edge outgoing_edge : edge.start_node.edges) {
            for (Edge other_edge : outgoing_edge.target_node.edges) {
                if (other_edge.target_node.id.equals(edge.target_node.id)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean testCycle(Node start_node, Node end_node, int cycle_length) {
        if (cycle_length < 0) return false;
        for (Edge edge : start_node.edges) {
            if (edge.target_node.id.equals(end_node.id) && cycle_length == 0) {
                return true;
            }
            testCycle(edge.start_node, end_node, cycle_length - 1);
        }
        return false;
    }
    
}
