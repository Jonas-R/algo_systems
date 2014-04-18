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
}
