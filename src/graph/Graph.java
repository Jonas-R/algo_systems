package graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
        nodes.get(start_node).add_edge(nodes.get(end_node), annotation, 1.0);
        if (!is_directed) {
            nodes.get(end_node).add_edge(nodes.get(start_node), annotation, 1.0);
        }
    }
    
     public void add_edge(String start_node, String end_node, String annotation, double weight) {
        nodes.get(start_node).add_edge(nodes.get(end_node), annotation, weight);
        if (!is_directed) {
            nodes.get(end_node).add_edge(nodes.get(start_node), annotation, weight);
        }
    }
    
    public void add_edge(Edge edge) {
        nodes.get(edge.start_node.id).add_edge(edge);
        if (!is_directed) {
            nodes.get(edge.target_node.id).add_edge(nodes.get(edge.start_node.id), edge.annotation, edge.weight);
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
    
    public Graph getSubgraph(List<String> node_ids) {
        Graph G_ = new Graph(this.is_directed);
        for (String id : node_ids) {
            G_.add_vertex(id);
        }
        
        for (String id : node_ids) {
            for (Edge edge : this.getNode(id).edges) {
                if (node_ids.contains(edge.target_node.id)) {
                    G_.add_edge(id, edge.target_node.id, edge.annotation, edge.weight);   
                }
            }
        }
        
        return G_;
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
