package io;

import graph.Graph;
import java.util.HashSet;
import java.util.List;
import utils.FileUtils;

public class readGraph {
    public static Graph readDipTabGraph(String filename) {
        List<String> lines = FileUtils.readLines(filename);
        
        Graph g = new Graph(false);
        HashSet<String> processed_nodes = new HashSet<>();
        
        boolean first_line = true;
        for (String line : lines) {
            if (first_line) {
                first_line = false;
            } else {
                String[] tokens = line.split("\\t");
                if (!processed_nodes.contains(tokens[1])) {
                    g.add_vertex(tokens[1]);
                    processed_nodes.add(tokens[1]);
                }
                if (!processed_nodes.contains(tokens[5])) {
                    g.add_vertex(tokens[5]);
                    processed_nodes.add(tokens[5]);
                }
                g.add_edge(tokens[1], tokens[5], line);
            }
        }
        return g;
    }
    
    public static Graph readAdjacencyListGraph(String filename) {
        List<String> lines = FileUtils.readLines(filename);
        
        Graph g = new Graph(true);
        HashSet<String> processed_nodes = new HashSet<>();
        
        int i = 0;
        for (String line : lines) {
            String[] tokens = line.split("->");
            tokens[0] = tokens[0].trim();
            if (!processed_nodes.contains(tokens[0])) {
                g.add_vertex(tokens[0]);      
                processed_nodes.add(tokens[0]);
            } 
            for (String id : tokens[1].split(",")) {
                id = id.trim();
                if (!processed_nodes.contains(id)) {
                    g.add_vertex(id);      
                    processed_nodes.add(id);
                } 
                g.add_edge(tokens[0], id, Integer.toString(i));
                i++;
            }
        }
        return g;
    }
}
