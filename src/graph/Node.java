package graph;

import java.util.Set;
import java.util.HashSet;
import java.util.AbstractMap.SimpleEntry;

public class Node {
    public String id;
    public double weight;
    private Set<SimpleEntry<Node, String>> edges;
    
    public Node(String id, double weight) {
    	this.id = id;
        this.weight = weight;
        this.edges = new HashSet<>();
    }
    
    public Node(String id, double weight, Set<SimpleEntry<Node, String>> edges) {
    	this.id = id;
        this.weight = weight;
        this.edges = edges;
    }
    
    public void add_edge(Node end_node, String annotation) {
        edges.add(new SimpleEntry<>(end_node, annotation));
    }
    
    public int getDegree() {
    	return edges.size();
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
