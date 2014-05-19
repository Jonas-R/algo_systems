package tasks;

import graph.Graph;
import graph.Node;
import io.readGraph;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import static tasks.SCC.calculateSCC;
import utils.FileUtils;

public class AllSimplePaths {
    
    public static void main(String[] args) {

        Graph G = readGraph.readAdjacencyListGraph("testgraph_ue05.txt");
        getAllSimplePaths(G.getNode("g"), G.getNode("a"), G);

    }

    
    public static ArrayList<ArrayList<Node>> getAllSimplePaths(Node start_node, Node end_node, Graph G) {
        Set<Node> visited = new HashSet<>();
        visited.add(start_node);
        ArrayList<Node> cur_path = new ArrayList<>();
        cur_path.add(start_node);
        
        ArrayList<ArrayList<Node>> paths =  getAllSimplePaths_helper(start_node, end_node, G, visited, new ArrayList<ArrayList<Node>>(), cur_path);
        System.out.println(paths);
        return paths;
    }
    
    private static ArrayList<ArrayList<Node>> getAllSimplePaths_helper(Node cur_node, Node end_node, Graph G, Set<Node> visited, ArrayList<ArrayList<Node>> paths, ArrayList<Node> cur_path) {        
        
        for (SimpleEntry<Node, String> edge : cur_node.edges) {
            if (edge.getKey().id.equals(end_node.id)) {
                ArrayList<Node> tmp = (ArrayList<Node>) cur_path.clone();
                tmp.add(end_node);
                paths.add(tmp);
            } else if (!visited.contains(edge.getKey())) {
                visited.add(edge.getKey());
                ArrayList<Node> tmp = (ArrayList<Node>) cur_path.clone();
                tmp.add(edge.getKey());
                paths = getAllSimplePaths_helper(edge.getKey(), end_node, G, visited, paths, tmp);
            }
        }
        return paths;
    }
}
