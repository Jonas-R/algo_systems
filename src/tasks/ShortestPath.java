package tasks;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.NodeWithDistance;
import io.readGraph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ShortestPath {
    public int distance;
    public List<NodeWithDistance> shortest_path;
    
    public static void main(String[] args) {
        Graph G = readGraph.readDipTabGraph("/home/r/raedle/Algo_Netze/Li2004a.tab");
        ShortestPath sp = new ShortestPath(G.getNode("26265N"), G.getNode("24348N"), G);
        System.out.println("");
    }
    
    public ShortestPath(Node start, Node end, Graph G) {
        //Initialization       
        Map<NodeWithDistance, NodeWithDistance> previous = new HashMap<>();
        PriorityQueue<NodeWithDistance> Q = new PriorityQueue<>();
        Map<String, NodeWithDistance> nwds = new HashMap<>();
        for (Node n : G.getNodes()) {
            NodeWithDistance nwd = new NodeWithDistance(n.id, n.edges);
            nwd.distance = Integer.MAX_VALUE;
            Q.add(nwd);
            nwds.put(nwd.id, nwd);
        }
        
        NodeWithDistance startwd = new NodeWithDistance(start.id, start.edges);
        Q.remove(startwd);
        startwd.distance = 0;
        Q.add(startwd);
        
        
        while (!Q.isEmpty()) {
            NodeWithDistance min_node = Q.remove();
            //if (min_node.distance == Integer.MAX_VALUE) break;
            
            for (Edge edge : min_node.edges) {
                double alt = min_node.distance + edge.weight;
                NodeWithDistance target = nwds.get(edge.target_node.id);
                if (alt < target.distance) {
                    Q.remove(target);
                    target.distance = alt;
                    Q.add(target);
                    previous.put(target, min_node);
                }
            }
        }
        
        
        shortest_path = new ArrayList<>();
        NodeWithDistance cur = new NodeWithDistance(end.id, end.edges);
        while (previous.containsKey(cur)) {
            shortest_path.add(cur);
            if(cur.equals(startwd)) break;
            cur = previous.get(cur);
        }
        Collections.reverse(shortest_path);
        System.out.println("");
        
    } 

}
