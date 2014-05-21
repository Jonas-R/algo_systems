package graph;

import java.util.Set;
import java.util.HashSet;

public class Node {
    public String id;
    public Set<Edge> edges;
    
    public Node(String id) {
    	this.id = id;
        this.edges = new HashSet<>();
    }
    
    public Node(String id, Set<Edge> edges) {
    	this.id = id;
        this.edges = edges;
    }
    
    public void add_edge(Node end_node, String annotation) {
        edges.add(new Edge(this, end_node, annotation, 1.0));
    }
    
    public void add_edge(Node end_node, String annotation, double weight) {
        edges.add(new Edge(this, end_node, annotation, weight));
    }
    
    public int getDegree() {
    	return edges.size();
    }
    
    public void removeEdge(String id) {
        for (Edge edge : edges) {
            if (edge.target_node.id.equals(id)) {
                edges.remove(edge);
            }
        }
    }  
    
    public Set<Node> getAdjacent(){
        Set<Node> adjacent = new HashSet<>();
        for(Edge edge : edges){
            adjacent.add(edge.target_node);
        }
        return adjacent;
    }
    
    @Override
    public String toString() {
        return id;
    }

    /* Automatically generated hashCode and equals implementation */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
