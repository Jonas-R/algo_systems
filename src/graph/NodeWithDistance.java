package graph;

import java.util.Set;

public class NodeWithDistance extends Node implements Comparable<NodeWithDistance> {
    public double distance;
    
    public NodeWithDistance(String id) {
    	super(id);
    }
    
    public NodeWithDistance(String id, Set<Edge> edges) {
    	super(id, edges);
    }

    @Override
    public int compareTo(NodeWithDistance other) {
        return (int) (this.distance - other.distance);
    }
    
    
}
