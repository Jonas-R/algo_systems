package io;

import graph.Graph;
import graph.Node;

/**
 * Created by Seoman on 05.05.14.
 */

public class printGraph {
    public static void printAdjacenceList(Graph g){
        StringBuilder out = new StringBuilder();
        for(Node n : g.getNodes()){
            out.append(n.id + " -- ");
            int i = 1, degree = n.getDegree();
            for(Node m : n.getAdjacent()){
                out.append(m.id);
                if(i < degree){
                    out.append(", ");
                    i++;
                }
            }
            out.append("\n");
        }
        System.out.print(out);
    }

    public static String returnAdjacenceList(Graph g){
        StringBuilder out = new StringBuilder();
        for(Node n : g.getNodes()){
            out.append(n.id + " -- ");
            int i = 1, degree = n.getDegree();
            for(Node m : n.getAdjacent()){
                out.append(m.id);
                if(i < degree){
                    out.append(", ");
                    i++;
                }
            }
            out.append("\n");
        }
        return out.toString();
    }

    public static String returnGraphViz(Graph g){
        StringBuilder out = new StringBuilder();
        out.append("\nGraph G{\n");
        for(Node n : g.getNodes()){
            for(Node m : n.getAdjacent()){
                if(n.id.compareTo(m.id) <= 0)
                    out.append(n.id + " -- " + m.id + ";\n");
            }
        }
        out.append("}");
        return out.toString();
    }
}
