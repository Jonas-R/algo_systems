package tasks;

/**
 * Created by Seoman on 05.05.14.
 */

import graph.Graph;
import graph.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SpanningTree {
    public static Graph getSpanningTree(Graph g) {
        Graph sf = new Graph(false);
        Map<String, Boolean> visited = new HashMap<>();
        for (Node n : g.getNodes()) {
            sf.add_vertex(n.id);
            visited.put(n.id, false);
        }

        Node cur;
        Stack<Node> s = new Stack<>();
        while (true) {
            cur = null;
            for (Node n : g.getNodes()) {
                if (!visited.get(n.id)) {
                    cur = n;
                    break;
                }
            }
            if (cur == null) {
                return sf;
            }
            s.add(cur);
            visited.put(cur.id, true);
            while (!s.isEmpty()) {
                cur = s.pop();
                for (Node n : cur.getAdjacent()) {
                    if (!visited.get(n.id)) {
                        visited.put(n.id, true);
                        s.push(n);
                        sf.add_edge(cur.id, n.id, "");
                    }
                }
            }
        }
    }
}
