package tasks;

import graph.Edge;
import graph.Graph;
import graph.Node;
import io.readGraph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import utils.FileUtils;

public class SCC {

    public static void main(String[] args) {
        if (args.length == 5) {

            Graph G = readGraph.readAdjacencyListGraph(args[2]);

            TreeMap<Integer, ArrayList<String>> output_map = calculateSCC(G);
            StringBuilder output = new StringBuilder();
            int i = 0;
            for (ArrayList<String> comp : output_map.values()) {
                output.append("component ").append(i).append(": ");
                Collections.sort(comp);
                String delim = "";
                for (String node : comp) {
                    output.append(delim).append(node);
                    delim = ", ";
                }
                output.append('\n');
                i++;
            }

            if (!args[4].endsWith("/")) {
                args[4] += "/";
            }
            FileUtils.writeString(args[4] + "components.txt", output.toString());
        } else {
            System.out.println("Aufruf: schmidt_raedle_project.jar 3 -file <adjazenz_file> -o <ausgabe-ordner>");
        }
    }

    public static TreeMap<Integer, ArrayList<String>> calculateSCC(Graph G) {
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

        TreeMap<Integer, ArrayList<String>> out_components = new TreeMap<>();

        for (String key : components.keySet()) {
            if (!out_components.containsKey(components.get(key))) {
                out_components.put(components.get(key), new ArrayList<String>());
            }
            out_components.get(components.get(key)).add(key);
        }

        return out_components;
    }

    public static int dfs1(Node node, Set<String> R, int N, Map<Integer, Node> phi) {
        R.add(node.id);
        for (Edge edge : node.edges) {
            if (!R.contains(edge.target_node.id)) {
                N = dfs1(edge.target_node, R, N, phi);
            }
        }
        N++;
        phi.put(N, node);
        return N;
    }

    public static void dfs2(Graph G, Node node, int K, Map<String, Integer> components, Set<String> R) {
        R.add(node.id);
        for (Node other_node : G.getNodes()) {
            for (Edge edge : other_node.edges) {
                if (!R.contains(other_node.id) && edge.target_node.id.equals(node.id)) {
                    dfs2(G, other_node, K, components, R);
                }
            }
        }
        components.put(node.id, K);
    }
}
