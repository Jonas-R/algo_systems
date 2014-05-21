package graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private Map<String, Node> nodes;
    private final boolean is_directed;
    
    public Graph(boolean is_directed) {
        this.nodes = new HashMap<>();
        this.is_directed = is_directed;
    }
    
    public void add_vertex(String id) {
        nodes.put(id, new Node(id));
    }
    
    public void add_edge(String start_node, String end_node, String annotation) {
        nodes.get(start_node).add_edge(nodes.get(end_node), annotation);
        if (!is_directed) {
            nodes.get(end_node).add_edge(nodes.get(start_node), annotation);
        }
    }
    
    public void remove_vertex(String id) {
        nodes.remove(id);
        for (Node node : nodes.values()) {
            node.removeEdge(id);
        }
    }
    
    public void remove_edge(String start_node, String end_node) {
        nodes.get(start_node).removeEdge(end_node);
    }
    
    public Collection<Node> getNodes() {
    	return nodes.values();
    }
    
    public Node getNode(String id) {
        return nodes.get(id);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : getNodes()) {
            sb.append(node.id).append(" -> ");
            for (Edge edge : node.edges) {
                sb.append(edge.target_node.id).append(", ");
            }
            sb.append("\n");
        }
        return sb.toString();
    } 
}
