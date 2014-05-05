package graph;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class CoCycle {
    public static int[][] calculateCoCycleBase(Graph G) {
        Map<String, Integer> edgeIds = getEdgeIds(G);
        Map<String, Integer> nodeIds = getNodeIds(G);
        
        int[][] base = new int[nodeIds.values().size()][edgeIds.size()];
        for (int[] vector : base) {
            vector = new int[edgeIds.size()];
        }

        System.out.println(edgeIds.size());
        System.out.println(nodeIds.size());
        for (Node node : G.getNodes()) {        
            for (AbstractMap.SimpleEntry<Node, String> edge : node.edges) {
                base[nodeIds.get(node.id)][edgeIds.get(edge.getValue())] = 1;
                base[nodeIds.get(edge.getKey().id)][edgeIds.get(edge.getValue())] = -1;
            }
        }
        
        return base;
    }
    
    public static Map<String, Integer> getEdgeIds(Graph G) {
        Map<String, Integer> edgeIds = new HashMap<>();
        int i = 0;
        for (Node node : G.getNodes()) {
            for (AbstractMap.SimpleEntry<Node, String> edge : node.edges) {
                edgeIds.put(edge.getValue(), i);
                i++;
            }
        }
        return edgeIds;
    }
    
    public static Map<String, Integer> getNodeIds(Graph G) {
        Map<String, Integer> nodeIds = new HashMap<>();
        int i = 0;
        for (Node node : G.getNodes()) {
            nodeIds.put(node.id, i);
            i++;
        }
        return nodeIds;
    }
}
