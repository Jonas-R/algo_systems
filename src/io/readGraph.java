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
                if (tokens.length >= 6 && tokens[1].startsWith("DIP:")) {
                    tokens[1] = tokens[1].split(":")[1];
                    tokens[6] = tokens[6].split(":")[1];
                    if (!processed_nodes.contains(tokens[1])) {
                        g.add_vertex(tokens[1]);
                        processed_nodes.add(tokens[1]);
                    }
                    if (!processed_nodes.contains(tokens[6])) {
                        g.add_vertex(tokens[6]);
                        processed_nodes.add(tokens[6]);
                    }
                    g.add_edge(tokens[1], tokens[6], line);
                }
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

    public static Graph readAdjacenceGraph(String filename) {
        List<String> lines = FileUtils.readLines(filename);

        Graph g = new Graph(false);
        HashSet<String> processed_nodes = new HashSet<>();

        for (String line : lines) {
            String[] tokens = line.split("( -- )|(, )");
            if (!processed_nodes.contains(tokens[0])) {
                g.add_vertex(tokens[0]);
                processed_nodes.add(tokens[0]);
            }
            for (int i = 1; i < tokens.length; i++) {
                if (tokens[0].compareTo(tokens[i]) <= 0) {
                    if (!processed_nodes.contains(tokens[i])) {
                        g.add_vertex(tokens[i]);
                        processed_nodes.add(tokens[i]);
                    }
                    g.add_edge(tokens[0], tokens[i], "");
                }
            }
        }
        return g;
    }
}
