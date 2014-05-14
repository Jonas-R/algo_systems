package tasks;

import graph.Graph;
import graph.Node;
import io.printGraph;
import io.readGraph;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import utils.FileUtils;

public class SSC {
    public static void main(String[] args) {
        if (args.length == 5) {
        
            Graph G = readGraph.readAdjacencyListGraph("testgraph_ue05.txt");

            String[] output_array = calculateSSC(G);
            String output = "";
            for (String s : output_array) { output += s + "\n"; }
            if (!args[4].endsWith("/")) { args[4] += "/"; }
            FileUtils.writeString(args[4] + "components.txt", output);
        } else {
            System.out.println("Aufruf: schmidt_raedle_project.jar 3 -file <adjazenz_file> -o <ausgabe-ordner>");
        }
    }
    
    public static String[] calculateSSC(Graph G) {
        int N = 0;
        Set<String> R = new HashSet<>();
        Map<String, Integer> components = new HashMap<>();
        Map<Integer, Node> phi = new HashMap<>();
        
        for (Node node : G.getNodes()) {
            if (!R.contains(node.id)) {
                N = dfs1(node, R, N, phi);
            }
        }
        
        R = new HashSet<>();
        int K = 0;
        for (int i = G.getNodes().size(); i > 0; i--) {
            if (!R.contains(phi.get(i).id)) {
                K++;
                dfs2(G, phi.get(i), K, components, R);
            }
        }
        
        String[] out_components = new String[K];
        
        for (int i = 0; i < K; i++) {
            out_components[i] = "comp " + i + ": ";
            for (String key : components.keySet()) {
                if (components.get(key) == i + 1) {
                    out_components[i] += (key + 1) + " ";
                } 
            }
        }
        
        return out_components;
    }
    
    public static int dfs1(Node node, Set<String> R, int N, Map<Integer, Node> phi) {
        R.add(node.id);
        for (SimpleEntry<Node, String> edge : node.edges) {
            if (!R.contains(edge.getKey().id)) {
                N = dfs1(edge.getKey(), R, N, phi);
            }
        }
        N++; phi.put(N, node);
        return N;
    }
    
    public static void dfs2(Graph G, Node node, int K, Map<String, Integer> components, Set<String> R) {
        R.add(node.id);
        for (Node other_node : G.getNodes()) {
            for (SimpleEntry<Node, String> edge : other_node.edges) {
                if (!R.contains(other_node.id) && edge.getKey().id.equals(node.id)) {
                    dfs2(G, other_node, K, components, R);
                }
            }
        }
        components.put(node.id, K);
    }
    

}
