package tasks;

import java.util.HashMap;
import java.util.Map.Entry;
import graph.Graph;
import graph.Node;
import io.readGraph;


public class DegreeHistogram {
	public static void main(String[] args) {
		Graph g = readGraph.readDipTabGraph("Giot2003a.tab");
		HashMap<Integer, Integer> degree_counts = new HashMap<>();
		for (Node node : g.getNodes()) {
			int degree = node.getDegree();
			if (!degree_counts.containsKey(degree)) {
				degree_counts.put(degree, 0);
			}
			degree_counts.put(degree, degree_counts.get(degree) + 1);
		}
		for (Entry<Integer, Integer> entry : degree_counts.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
}
