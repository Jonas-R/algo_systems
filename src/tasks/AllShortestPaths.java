package tasks;

import graph.Edge;
import graph.Graph;
import graph.Node;
import io.readGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import utils.FileUtils;

public class AllShortestPaths {
    public double[][] distances;
    public Map<Node, Integer> indices;
    
    public static void main(String[] args) {
        if (args.length >= 7) {
            if (args[0].equals("2b")) {
                ArrayList<String> subnodes = new ArrayList<>();
                ArrayList<String> ids = new ArrayList<>();
                for (int i = 7; i < args.length; i++) {
                    subnodes.add(trimcommas(args[i].trim()));
                }
                Graph G = readGraph.readDipTabGraph(args[2]);
                Graph subgraph = G.getSubgraph(subnodes);
                AllShortestPaths sp = new AllShortestPaths(subgraph);

                StringBuilder output = new StringBuilder();
                output.append("\t");
                for (Node node : subgraph.getNodes()) {
                    output.append(node.id);
                    output.append("\t");
                    ids.add(node.id);
                }
                output.append('\n');
                for (int i = 0; i < subgraph.getNodes().size(); i++) {
                    String delim = "";
                    output.append(ids.get(i));
                    output.append("\t");
                    for (double elem : sp.distances[i]) {
                        output.append(delim).append(elem);
                        delim = "\t";
                    }
                    output.append('\n');
                }

                if (!args[4].endsWith("/")) {
                        args[4] += "/";
                    }
                FileUtils.writeString(args[4] + "shortest_paths.txt", output.toString());
            } else {
                ShortestPath.main(args);
            }
            
        } else {
            System.out.println("Dijkstra: java -jar schmidt_raedle_project.jar 2a -file <tab-Datei> -o <ausgabe-ordner> -s start-id -z ziel-id\n"
                    + "Floyd-Warshall: java -jar schmidt_raedle_project.jar 2b -file <tab-datei> -o <ausgabe-ordner> -k <knoten-ids>");
        }
    }
    
    public AllShortestPaths(Graph G) {
        //Initialization       
        distances = new double[G.getNodes().size()][G.getNodes().size()];
        indices = new HashMap<>();
        int n = 0;
        for (Node node : G.getNodes()) {
            indices.put(node, n);
            n++;
        }
        
        for(int i = 0; i < G.getNodes().size(); i++) {
            for(int j = 0; j < G.getNodes().size(); j++) {
                distances[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        
        
        for(int i = 0; i < G.getNodes().size(); i++) {
            distances[i][i] = 0;
        }
        
        for (Node node : G.getNodes()) {
            for (Edge edge : node.edges) {
                //ignore loops
                if (!edge.start_node.id.equals(edge.target_node.id)) {
                    distances[indices.get(edge.start_node)][indices.get(edge.target_node)] = edge.weight;
                }
            }
        }
        
        for (int k = 0; k < G.getNodes().size(); k++) {
            for (int i = 0; i < G.getNodes().size(); i++) {
                for (int j = 0; j < G.getNodes().size(); j++) {
                    if (distances[i][j] > distances[i][k] + distances[k][j]) {
                        distances[i][j] = distances[i][k] + distances[k][j];
                    } 
                }
            }
        }
        
    
    } 
    
    public static String trimcommas(String s) {
        while (s.startsWith(",")) s = s.substring(1);
        while (s.endsWith(",")) s = s.substring(0, s.length() - 1);
        return s;
    }
}
