package graph;

public class Edge {
    public Node start_node;
    public Node target_node;
    public String annotation;
    public double weight;
    
    public Edge(Node start_node, Node target_node, String annotation, double weight) {
        this.start_node = start_node;
        this.target_node = target_node;
        this.annotation = annotation;
        this.weight = weight;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}
