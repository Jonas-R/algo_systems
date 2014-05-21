package graph;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

public class CoCycle {
    public Map<String, Integer> edgeIds;
    public Map<String, Integer> nodeIds;
    public Map<Integer, SimpleEntry<String, String>> edgeNumbers;
    
    public CoCycle(Graph G) {
        edgeIds = getEdgeIds(G);
        nodeIds = getNodeIds(G);
    }
    
    public int[][] calculateCoCycleBase(Graph G) {
        
        int[][] base = new int[nodeIds.values().size()][edgeIds.size()];
        for (int[] vector : base) {
            vector = new int[edgeIds.size()];
        }

        for (Node node : G.getNodes()) {        
            for (Edge edge : node.edges) {
                base[nodeIds.get(node.id)][edgeIds.get(edge.annotation)] = 1;
                base[nodeIds.get(edge.target_node.id)][edgeIds.get(edge.annotation)] = -1;
            }
        }
        
        return base;
    }
    
    private Map<String, Integer> getEdgeIds(Graph G) {
        edgeIds = new HashMap<>();
        edgeNumbers = new HashMap<>();
        int i = 0;
        for (Node node : G.getNodes()) {
            for (Edge edge : node.edges) {
                edgeIds.put(edge.annotation, i);
                edgeNumbers.put(i, new SimpleEntry<>(node.id, edge.target_node.id));
                i++;
            }
        }
        return edgeIds;
    }
    
    private Map<String, Integer> getNodeIds(Graph G) {
        nodeIds = new HashMap<>();
        int i = 0;
        for (Node node : G.getNodes()) {
            nodeIds.put(node.id, i);
            i++;
        }
        return nodeIds;
    }
}
